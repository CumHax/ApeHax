package me.cumhax.apehax.api.command.commands;

import me.cumhax.apehax.Client;
import me.cumhax.apehax.api.command.Command;
import me.cumhax.apehax.api.friend.Friends;
import me.cumhax.apehax.api.util.CommandUtil;

public class Friend extends Command {

	public Friend() {
		super("Friend", "Friend", new String[] { "f", "friend" });
	}

	public void onCommand(final String command, final String[] args) throws Exception {
		if (args[0].equalsIgnoreCase("add")) {
			if (Friends.isFriend(args[1])) {
				CommandUtil.sendChatMessage(args[1] + " is already a friend!");
				return;
			}
			if (!Friends.isFriend(args[1])) {
				Client.getInstance().friends.addFriend(args[1]);
				CommandUtil.sendChatMessage("Added " + args[1] + " to friends list");
			}
		}
		if (args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove")) {
			if (!Friends.isFriend(args[1])) {
				CommandUtil.sendChatMessage(args[1] + " is not a friend!");
				return;
			}
			if (Friends.isFriend(args[1])) {
				Client.getInstance().friends.delFriend(args[1]);
				CommandUtil.sendChatMessage("Removed " + args[1] + " from friends list");
			}
		}
	}

}
