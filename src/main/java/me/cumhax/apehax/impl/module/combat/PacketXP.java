package me.cumhax.apehax.impl.module.combat;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.impl.event.PacketSendEvent;
import me.cumhax.apehax.mixin.mixins.accessor.ICPacketPlayer;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PacketXP extends Module {

	public PacketXP(String name, String description, Category category) {
		super(name, description, category);
	}

	@SubscribeEvent
	public void listener(final PacketSendEvent event) {
		if (event.getPacket() instanceof CPacketPlayer
				&& mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
			final CPacketPlayer packet = (CPacketPlayer) event.getPacket();
			((ICPacketPlayer) packet).setPitch(90.0f);
		}
	}
}
