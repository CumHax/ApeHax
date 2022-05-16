package me.cumhax.apehax.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import me.cumhax.apehax.mixin.mixins.accessor.IItemRenderer;
import me.cumhax.apehax.mixin.mixins.accessor.IRenderManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin implements IItemRenderer {

	@Shadow
	private float equippedProgressMainHand;

	@Shadow
	private float equippedProgressOffHand;

	@Override
	public void setMainHand(float mainHand) {
		equippedProgressMainHand = mainHand;

	}

	@Override
	public void setOffHand(float offHand) {
		equippedProgressOffHand = offHand;

	}

}