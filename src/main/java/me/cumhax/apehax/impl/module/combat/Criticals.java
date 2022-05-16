package me.cumhax.apehax.impl.module.combat;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.impl.event.PacketSendEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class Criticals extends Module {
	public Criticals(String name, String description, Category category) {
		super(name, description, category);
	}

	@SubscribeEvent
	public void onPacketSend(PacketSendEvent event) {
		if (mc.player == null || mc.world == null)
			return;

		if (event.getPacket() instanceof CPacketUseEntity
				&& ((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK
				&& mc.player.onGround) {
			mc.player.connection.sendPacket(
					new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1, mc.player.posZ, false));
			mc.player.connection
					.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
		}
	}
}
