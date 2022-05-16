package me.cumhax.apehax.impl.module.hud;

import me.cumhax.apehax.Client;
import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.setting.SettingType;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class ClickGUI extends Module {
	private final Setting red = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this)
			.setIntegerValue(0).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	private final Setting green = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this)
			.setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	private final Setting blue = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this)
			.setIntegerValue(0).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	private final Setting rainbow = new Setting.Builder(SettingType.BOOLEAN).setName("Rainbow").setModule(this)
			.setBooleanValue(false).build();

	public ClickGUI(String name, String description, Category category) {
		super(name, description, category);

		setBind(Keyboard.KEY_P);
		addSetting(red);
		addSetting(green);
		addSetting(blue);
		addSetting(rainbow);
	}

	@Override
	public void onEnable() {
		mc.displayGuiScreen(Client.clickGUI);
	}
}
