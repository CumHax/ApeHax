package me.cumhax.apehax.api;

import me.cumhax.apehax.Client;
import me.cumhax.apehax.api.command.CommandManager;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class EventHandler {
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (!Keyboard.getEventKeyState() || Keyboard.getEventKey() == Keyboard.KEY_NONE)
			return;

		for (Module module : Client.moduleManager.getModules()) {
			if (module.getBind() == Keyboard.getEventKey()) {
				module.toggle();
			}
		}
	}

	public static void onChatSend(ClientChatEvent event) {
		if (!event.getMessage().startsWith("-") || event.getMessage().startsWith("/")
				|| event.getMessage().startsWith(".") || event.getMessage().startsWith("#")) {

			Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
			for (Module module : ModuleManager.getModules()) {
				if (module.isEnabled()) {

				}
			}
			Minecraft.getMinecraft().player.connection.sendPacket(new CPacketChatMessage(event.getMessage()));
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void clientChatEvent(ClientChatEvent event) {
		if (event.getMessage().startsWith(CommandManager.getPrefix())) {
			try {
				event.setCanceled(true);
				Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
				CommandManager.onCommand(event.getMessage());
				return;
			} catch (Exception ignored) {
			}
		}
		onChatSend(event);
	}
}
