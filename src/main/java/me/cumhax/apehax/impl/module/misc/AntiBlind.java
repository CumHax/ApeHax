package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiBlind extends Module {

	public AntiBlind(String name, String description, Category category) {
		super(name, description, category);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null)
			return;
		if (mc.player.isPotionActive(Potion.getPotionById(9))) {
			mc.player.removeActivePotionEffect(Potion.getPotionById(9));
		}

		if (mc.player.isPotionActive(Potion.getPotionById(15))) {
			mc.player.removeActivePotionEffect(Potion.getPotionById(15));
		}

	}

}
