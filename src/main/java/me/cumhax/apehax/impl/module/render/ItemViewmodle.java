package me.cumhax.apehax.impl.module.render;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.setting.SettingType;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ItemViewmodle extends Module {

	private final Setting FOVSlider = new Setting.Builder(SettingType.INTEGER).setName("FOV").setModule(this)
			.setIntegerValue(130).setMinIntegerValue(110).setMaxIntegerValue(175).build();

	private final Setting FOVItems = new Setting.Builder(SettingType.BOOLEAN).setName("Items").setModule(this)
			.setBooleanValue(true).build();

	private final Setting ItemsFOVSlider = new Setting.Builder(SettingType.INTEGER).setName("ItemsFOV").setModule(this)
			.setIntegerValue(130).setMinIntegerValue(110).setMaxIntegerValue(175).build();

	public ItemViewmodle(String name, String description, Category category) {
		super(name, description, category);

		addSetting(FOVSlider);
		addSetting(FOVItems);
		addSetting(ItemsFOVSlider);
	}

	private float fov;

	@Override
	public void onEnable() {
		fov = mc.gameSettings.fovSetting;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onDisable() {
		mc.gameSettings.fovSetting = fov;
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		mc.gameSettings.fovSetting = FOVSlider.getIntegerValue();
	}

	@SubscribeEvent
	public void fov_event(final EntityViewRenderEvent.FOVModifier m) {
		if (FOVItems.getBooleanValue() == true)
			m.setFOV(ItemsFOVSlider.getIntegerValue());
	}

}
