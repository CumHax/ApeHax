package me.cumhax.apehax.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.cumhax.apehax.api.module.ModuleManager;
import me.cumhax.apehax.impl.event.EventDamageBlock;
import me.cumhax.apehax.impl.module.misc.Reach;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

@Mixin(PlayerControllerMP.class)
public class PlayerControllerMPMixin {

	@Inject(method = "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z", at = @At("HEAD"), cancellable = true)
	private void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing,
			CallbackInfoReturnable<Boolean> cir) {
		EventDamageBlock event = new EventDamageBlock(posBlock, directionFacing);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			cir.setReturnValue(false);
	}
}