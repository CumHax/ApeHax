package me.cumhax.apehax.impl.module.render;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoBob extends Module {

	public NoBob(String name, String description, Category category) {
		super(name, description, category);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null)
			return;

		mc.gameSettings.viewBobbing = false;

	}

}
