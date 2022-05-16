package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiLevitate extends Module {

	public AntiLevitate(String name, String description, Category category) {
		super(name, description, category);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player.isPotionActive(Potion.getPotionById(25))) {
			mc.player.removeActivePotionEffect(Potion.getPotionById(25));
		}

	}

}
