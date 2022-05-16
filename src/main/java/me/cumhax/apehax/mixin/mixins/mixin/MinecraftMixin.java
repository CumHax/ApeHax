package me.cumhax.apehax.mixin.mixins.mixin;

import me.cumhax.apehax.Client;
import me.cumhax.apehax.api.module.ModuleManager;
import me.cumhax.apehax.mixin.mixins.accessor.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 * @since 9/20/2020
 */
@Mixin(Minecraft.class)
public class MinecraftMixin implements IMinecraft {
	@Shadow
	@Final
	private Timer timer;

	@Shadow
	public int rightClickDelayTimer;

	@Override
	public Timer getTimer() {
		return timer;
	}

	@Override
	public void setRightClickDelayTimer(final int i) {
		this.rightClickDelayTimer = i;
	}

	@Inject(method = { "shutdown()V" }, at = { @At("HEAD") })
	public void saveSettingsOnShutdown(final CallbackInfo ci) {
		Client.configUtils.saveMods();
		System.out.println("Saved Clientmod+2 mods!");
		Client.configUtils.saveSettingsList();
		System.out.println("Saved Clientmod+2 settings!");
		Client.configUtils.saveBinds();
		System.out.println("Saved Clientmod+2 binds!");
		Client.configUtils.savePrefix();
		Client.configUtils.saveFriends();
	}
}
