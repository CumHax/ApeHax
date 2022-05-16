package me.cumhax.apehax.api.command.commands;

import org.lwjgl.input.Keyboard;

import me.cumhax.apehax.api.command.Command;
import me.cumhax.apehax.api.command.CommandManager;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.module.ModuleManager;
import me.cumhax.apehax.api.util.CommandUtil;

public class Bind extends Command {
	public Bind() {
		super("Bind", "Set the Bind", new String[] { "bind" });
	}

	@Override
	public void onCommand(String arguments) {
		if (arguments.equals("")) {
			CommandUtil.sendChatMessage(String.format("&7Usage: %sbind <Module> <Key>", CommandManager.getPrefix()));
			return;
		}

		boolean moduleFound = false;

		String[] arg = arguments.split(" ");
		int key = Keyboard.getKeyIndex(arg[1].toUpperCase());

		for (Module module : ModuleManager.getModules()) {
			if (module.getName().equalsIgnoreCase(arg[0])) {
				try {
					if (Keyboard.getKeyName(key).equals("NONE")) {
						module.setBind(Keyboard.KEYBOARD_SIZE);
					} else {
						module.setBind(key);

					}
					CommandUtil.sendChatMessage(
							String.format("bound %s to %s", module.getName(), Keyboard.getKeyName(key)));
					moduleFound = true;
				} catch (Exception ignored) {
				}
			}
		}

		if (!moduleFound) {
			CommandUtil.sendChatMessage("&7Module not found");
		}

	}
}
