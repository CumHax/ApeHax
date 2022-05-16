package me.cumhax.apehax;

import me.cumhax.apehax.api.EventHandler;
import me.cumhax.apehax.api.command.CommandManager;
import me.cumhax.apehax.api.friend.Friends;
import me.cumhax.apehax.api.gui.clickgui.ClickGUI;
import me.cumhax.apehax.api.module.ModuleManager;
import me.cumhax.apehax.api.setting.SettingManager;
import me.cumhax.apehax.api.util.ConfigUtils;
import me.cumhax.apehax.api.util.font.CustomFontRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.awt.*;

import org.json.alt.simple.parser.JSONParser;
import org.lwjgl.opengl.Display;

/**
 * @author yoink
 * @since 9/20/2020
 */
@Mod(modid = "apehack", name = "ApeHack", version = "v1")
public class Client {
	public static Client INSTANCE;

	public Client() {
		Client.INSTANCE = this;
	}

	public static String MODID = "apehack";
	public static String MODNAME = "ApeHack";
	public static String MODVER = "v1";
	public static String credits = "CumHax";
	public static ModuleManager moduleManager;
	public static SettingManager settingManager;
	public static CustomFontRenderer customFontRenderer;
	public static ClickGUI clickGUI;
	public static CommandManager commandManager;
	public static ConfigUtils configUtils;
	public Friends friends;

	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event) {
		this.friends = new Friends();
		settingManager = new SettingManager();
		moduleManager = new ModuleManager();
		customFontRenderer = new CustomFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, false);
		clickGUI = new ClickGUI();
		CommandManager.initialize();
		configUtils = new ConfigUtils();

		MinecraftForge.EVENT_BUS.register(new EventHandler());

		Display.setTitle(MODID + " " + MODVER);
	}

	public static Client getInstance() {
		return Client.INSTANCE;
	}
}
