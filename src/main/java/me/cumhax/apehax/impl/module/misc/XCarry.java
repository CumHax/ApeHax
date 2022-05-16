package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.impl.event.PacketSendEvent;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class XCarry extends Module {

	public XCarry(String name, String description, Category category) {
		super(name, description, category);
		// TODO Auto-generated constructor stub
	}

	@SubscribeEvent
	public void onPacketSend(PacketSendEvent event) {
		if (event.getPacket() instanceof CPacketCloseWindow) {
			event.setCanceled(true);
		}
	}
}
