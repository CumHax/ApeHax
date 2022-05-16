package me.cumhax.apehax.impl.module.render;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.setting.SettingType;
import me.cumhax.apehax.mixin.mixins.accessor.IItemRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class LowHands extends Module {

	ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;

	private final Setting main = new Setting.Builder(SettingType.INTEGER).setName("MainHand").setModule(this)
			.setIntegerValue(5).setMinIntegerValue(0).setMaxIntegerValue(10).build();

	private final Setting off = new Setting.Builder(SettingType.INTEGER).setName("OffHand").setModule(this)
			.setIntegerValue(5).setMinIntegerValue(0).setMaxIntegerValue(10).build();

	public LowHands(String name, String description, Category category) {
		super(name, description, category);
		addSetting(main);
		addSetting(off);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		((IItemRenderer) itemRenderer).setOffHand((float) off.getIntegerValue() / 10);
		((IItemRenderer) itemRenderer).setMainHand((float) main.getIntegerValue() / 10);
	}

}
