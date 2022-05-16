package me.cumhax.apehax.api.command.commands;

import java.awt.Desktop;
import java.io.File;

import me.cumhax.apehax.Discord;
import me.cumhax.apehax.api.command.Command;
import me.cumhax.apehax.api.command.CommandManager;
import me.cumhax.apehax.api.util.CommandUtil;

public class OpenFolder extends Command {

	public OpenFolder() {
		super("OpenFolder", "OpenFolder", new String[] { "openfolder" });
	}

	@Override
	public void onCommand(String arguments) {
		CommandUtil.sendChatMessage(String.format("opening folder"));
		try {
			Desktop.getDesktop().open(new File("ApeHack"));
		} catch (Exception e) {

		}
	}
}
