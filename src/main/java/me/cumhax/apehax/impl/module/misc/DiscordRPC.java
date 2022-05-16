package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.Discord;
import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;

public class DiscordRPC extends Module {

	public DiscordRPC(String name, String description, Category category) {
		super(name, description, category);
	}

	@Override
	public void onEnable() {
		Discord.start();
	}

	@Override
	public void onDisable() {
		Discord.stop();
	}

}
