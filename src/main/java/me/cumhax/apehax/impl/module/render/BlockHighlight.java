package me.cumhax.apehax.impl.module.render;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.setting.SettingType;

public class BlockHighlight extends Module {

	public final Setting red = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this)
			.setIntegerValue(210).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	public final Setting green = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this)
			.setIntegerValue(30).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	public final Setting blue = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this)
			.setIntegerValue(30).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	public final Setting alpha = new Setting.Builder(SettingType.INTEGER).setName("Alpha").setModule(this)
			.setIntegerValue(30).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	public BlockHighlight(String name, String description, Category category) {
		super(name, description, category);
	}

}
