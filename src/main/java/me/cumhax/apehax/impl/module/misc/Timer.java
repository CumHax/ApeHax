package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.setting.SettingType;
import me.cumhax.apehax.mixin.mixins.accessor.IMinecraft;
import me.cumhax.apehax.mixin.mixins.accessor.ITimer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 9/20/2020
 */
public class Timer extends Module {
	private final Setting speed = new Setting.Builder(SettingType.INTEGER).setName("Speed").setModule(this)
			.setIntegerValue(20).setMinIntegerValue(1).setMaxIntegerValue(300).build();

	public Timer(String name, String description, Category category) {
		super(name, description, category);

		addSetting(speed);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f / (speed.getIntegerValue() / 10f));
	}

	@Override
	public void onDisable() {
		((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f);
	}
}