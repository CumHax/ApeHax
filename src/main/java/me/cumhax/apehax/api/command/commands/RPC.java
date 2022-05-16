package me.cumhax.apehax.api.command.commands;

import me.cumhax.apehax.Discord;
import me.cumhax.apehax.api.command.Command;
import me.cumhax.apehax.api.command.CommandManager;
import me.cumhax.apehax.api.util.CommandUtil;

public class RPC extends Command {

	public RPC() {
		super("rpc", "prikol", new String[] { "rpc" });
	}

	@Override
	public void onCommand(String arguments) {
		if (arguments.equals("")) {
			CommandUtil.sendChatMessage(String.format("&7Usage: %srpc <string>", CommandManager.getPrefix()));
		}

		Discord.setState(arguments);
		CommandUtil.sendChatMessage(String.format("&7Set rpc to %s", arguments));
	}

}
