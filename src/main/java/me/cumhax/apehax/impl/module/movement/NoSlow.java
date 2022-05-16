package me.cumhax.apehax.impl.module.movement;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoSlow extends Module {

	public NoSlow(String name, String description, Category category) {
		super(name, description, category);
	}

	@SubscribeEvent
	public void onTick(InputUpdateEvent event) {
		if (mc.player == null || mc.world == null)
			return;
		if (mc.player.isHandActive() && !mc.player.isRiding()) {
			event.getMovementInput().moveStrafe *= 5;
			event.getMovementInput().moveForward *= 5;
		}
	}

}
