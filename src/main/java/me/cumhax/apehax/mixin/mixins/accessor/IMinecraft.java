package me.cumhax.apehax.mixin.mixins.accessor;

import net.minecraft.util.Timer;

/**
 * @author yoink
 * @since 9/20/2020
 */
public interface IMinecraft {
	Timer getTimer();

	void setRightClickDelayTimer(final int p0);
}
