package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FakeGamemode extends Module {

	public FakeGamemode(String name, String description, Category category) {
		super(name, description, category);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null) {
			return;
		}
		Minecraft.getMinecraft();
		mc.playerController.setGameType(GameType.CREATIVE);
	}

	@Override
	public void onDisable() {
		if (mc.player == null) {
			return;
		}
		mc.playerController.setGameType(GameType.SURVIVAL);
	}

}
