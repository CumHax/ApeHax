package me.cumhax.apehax.impl.module.movement;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.impl.event.MovementUtils;
import me.cumhax.apehax.mixin.mixins.accessor.IMinecraft;
import me.cumhax.apehax.mixin.mixins.accessor.ITimer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class YPort extends Module {

	public YPort(String name, String description, Category category) {
		super(name, description, category);
	}

	private boolean isOnIce = false;

	public void onDisable() {
		resetTimer();
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null)
			return;
		if (!MovementUtils.isMoving(mc.player) || mc.player.isInWater() && mc.player.isInLava()
				|| mc.player.collidedHorizontally) {
			return;
		}

		if (mc.player.onGround) {
			setTimer(1.20f);
			mc.player.jump();
			MovementUtils.setSpeed(mc.player, MovementUtils.getBaseMoveSpeed() + (isOnIce ? 0.3 : 0.06));
		} else {
			mc.player.motionY = -1;
			resetTimer();
		}
	}

	public static void setTimer(float speed) {
		((ITimer) ((IMinecraft) Minecraft.getMinecraft()).getTimer()).setTickLength(50f / speed);
		;
	}

	public static void resetTimer() {
		((ITimer) ((IMinecraft) Minecraft.getMinecraft()).getTimer()).setTickLength(50f);
		;
	}

}
