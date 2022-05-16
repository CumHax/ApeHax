package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.impl.event.PacketSendEvent;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GreenText extends Module {

	public GreenText(String name, String description, Category category) {
		super(name, description, category);
	}

	private final String suffix = ">";

	@SubscribeEvent
	public void onPacket(final PacketSendEvent event) {
		if (event.getPacket() instanceof CPacketChatMessage) {
			String s = ((CPacketChatMessage) event.getPacket()).getMessage();
			if (s.startsWith("/") || s.startsWith(">")) {
				return;
			}
			if (s.length() >= 256) {
				s = s.substring(0, 256);
			}

			event.setCanceled(true);
			CPacketChatMessage newpacket = new CPacketChatMessage(suffix + s);
			mc.player.connection.sendPacket(newpacket);

		}
	}
}
