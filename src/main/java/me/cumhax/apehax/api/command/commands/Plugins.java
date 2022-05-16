package me.cumhax.apehax.api.command.commands;

import me.cumhax.apehax.api.command.Command;
import me.cumhax.apehax.api.module.ModuleManager;

public class Plugins extends Command {

	public Plugins() {
		super("Plugins", "Plugins", new String[] { "plugins", "pl" });
	}

	public void onCommand(String arguments)
	{
		ModuleManager.getModule("PluginsGrabber").enable();
	}

}
