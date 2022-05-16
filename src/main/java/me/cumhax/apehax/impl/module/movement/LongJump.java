package me.cumhax.apehax.impl.module.movement;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.setting.SettingType;
import me.cumhax.apehax.impl.event.MoveEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class LongJump extends Module {
	private final Setting speed = new Setting.Builder(SettingType.INTEGER).setName("Speed").setModule(this)
			.setIntegerValue(30).setMinIntegerValue(1).setMaxIntegerValue(100).build();

	private final Setting packet = new Setting.Builder(SettingType.BOOLEAN).setName("Packet").setModule(this)
			.setBooleanValue(false).build();

	private boolean jumped = false;
	private boolean boostable = false;

	public LongJump(String name, String description, Category category) {
		super(name, description, category);

		addSetting(speed);
		addSetting(packet);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null || mc.world == null)
			return;

		if (jumped) {
			if (mc.player.onGround || mc.player.capabilities.isFlying) {
				jumped = false;

				mc.player.motionX = 0.0;
				mc.player.motionZ = 0.0;

				if (packet.getBooleanValue()) {
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY,
							mc.player.posZ, mc.player.onGround));
					mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX, 0.0,
							mc.player.posZ + mc.player.motionZ, mc.player.onGround));
				}

				return;
			}

			if (!(mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f))
				return;
			double yaw = getDirection();
			mc.player.motionX = -Math.sin(yaw) * (((float) Math
					.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ))
					* (boostable ? (speed.getIntegerValue() / 10f) : 1f));
			mc.player.motionZ = Math.cos(yaw) * (((float) Math
					.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ))
					* (boostable ? (speed.getIntegerValue() / 10f) : 1f));

			boostable = false;
		}
	}

	@SubscribeEvent
	public void onMove(MoveEvent event) {
		if (mc.player == null || mc.world == null)
			return;

		if (!(mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f) && jumped) {
			mc.player.motionX = 0.0;
			mc.player.motionZ = 0.0;
			event.setX(0);
			event.setY(0);
		}
	}

	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event) {
		if (event.getEntity() == mc.player) {
			if ((mc.player.movementInput.moveForward != 0f || mc.player.movementInput.moveStrafe != 0f)) {
				jumped = true;
				boostable = true;
			}
		}
	}

	private double getDirection() {
		float rotationYaw = mc.player.rotationYaw;
		if (mc.player.moveForward < 0f)
			rotationYaw += 180f;
		float forward = 1f;
		if (mc.player.moveForward < 0f)
			forward = -0.5f;
		else if (mc.player.moveForward > 0f)
			forward = 0.5f;
		if (mc.player.moveStrafing > 0f)
			rotationYaw -= 90f * forward;
		if (mc.player.moveStrafing < 0f)
			rotationYaw += 90f * forward;
		return Math.toRadians(rotationYaw);
	}
}
