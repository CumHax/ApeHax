package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.setting.SettingType;

public class Reach extends Module {

	public final Setting range = new Setting.Builder(SettingType.INTEGER).setName("Range").setModule(this)
			.setIntegerValue(1).setMinIntegerValue(0).setMaxIntegerValue(10).build();

	public Reach(String name, String description, Category category) {
		super(name, description, category);
		addSetting(range);
		// TODO Auto-generated constructor stub
	}

}
