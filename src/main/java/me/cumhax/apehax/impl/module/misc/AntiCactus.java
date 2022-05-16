package me.cumhax.apehax.impl.module.misc;

import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.impl.event.AddCollisionBoxToListEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiCactus extends Module {

	public AntiCactus(String name, String description, Category category) {
		super(name, description, category);
		// TODO Auto-generated constructor stub
	}

	@SubscribeEvent
	public void onAddCollisionBox(AddCollisionBoxToListEvent event) {
		if (mc.player != null) {
			AxisAlignedBB bb = new AxisAlignedBB(event.getPos()).expand(0, 0.1D, 0);
			if (event.getBlock() == Blocks.CACTUS && isAbovePlayer(event.getPos())
					&& event.getEntityBox().intersects(bb)) {
				event.getCollidingBoxes().add(bb);
			}
		}
	}

	private boolean isAbovePlayer(BlockPos pos) {
		return pos.getY() >= mc.player.posY;
	}

}
