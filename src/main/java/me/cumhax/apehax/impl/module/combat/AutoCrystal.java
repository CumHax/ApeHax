package me.cumhax.apehax.impl.module.combat;

import me.cumhax.apehax.api.friend.Friends;
import me.cumhax.apehax.api.module.Category;
import me.cumhax.apehax.api.module.Module;
import me.cumhax.apehax.api.setting.Setting;
import me.cumhax.apehax.api.setting.SettingType;
import me.cumhax.apehax.api.util.CommandUtil;
import me.cumhax.apehax.api.util.InteractHelper;
import me.cumhax.apehax.api.util.RenderUtil;
import me.cumhax.apehax.api.util.Timah;
import me.cumhax.apehax.impl.event.PacketReceiveEvent;
import me.cumhax.apehax.impl.event.RenderEvent;
import me.cumhax.apehax.mixin.mixins.accessor.IRenderManager;
import ibxm.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AutoCrystal extends Module {

	private final Setting logic = new Setting.Builder(SettingType.ENUM).setName("Logic").setModule(this)
			.setEnumValue("BREAKPLACE").setEnumValues(new ArrayList<>(Arrays.asList("BREAKPLACE", "PLACEBREAK")))
			.build();

	private final Setting place = new Setting.Builder(SettingType.BOOLEAN).setName("Place").setModule(this)
			.setBooleanValue(true).build();

	private final Setting predict = new Setting.Builder(SettingType.BOOLEAN).setName("Predict").setModule(this)
			.setBooleanValue(true).build();

	private final Setting ak47 = new Setting.Builder(SettingType.BOOLEAN).setName("AK47").setModule(this)
			.setBooleanValue(true).build();

	private final Setting rotate = new Setting.Builder(SettingType.BOOLEAN).setName("Rotate").setModule(this)
			.setBooleanValue(true).build();

	private final Setting spoofRotations = new Setting.Builder(SettingType.BOOLEAN).setName("SpoofRotations")
			.setModule(this).setBooleanValue(true).build();

	private final Setting autoSwitch = new Setting.Builder(SettingType.BOOLEAN).setName("AutoSwitch").setModule(this)
			.setBooleanValue(false).build();

	private final Setting hitDelay = new Setting.Builder(SettingType.INTEGER).setName("HitDelay").setModule(this)
			.setIntegerValue(0).setMinIntegerValue(-6).setMaxIntegerValue(600).build();

	private final Setting range = new Setting.Builder(SettingType.INTEGER).setName("Range").setModule(this)
			.setIntegerValue(5).setMinIntegerValue(0).setMaxIntegerValue(6).build();

	private final Setting walls = new Setting.Builder(SettingType.INTEGER).setName("WallRange").setModule(this)
			.setIntegerValue(3).setMinIntegerValue(0).setMaxIntegerValue(4).build();

	private final Setting enemyRange = new Setting.Builder(SettingType.INTEGER).setName("EnemyRange").setModule(this)
			.setIntegerValue(12).setMinIntegerValue(5).setMaxIntegerValue(15).build();

	private final Setting placeRange = new Setting.Builder(SettingType.INTEGER).setName("PlaceRange").setModule(this)
			.setIntegerValue(5).setMinIntegerValue(0).setMaxIntegerValue(6).build();

	private final Setting maxSelfDmg = new Setting.Builder(SettingType.INTEGER).setName("MaxSelfDMG").setModule(this)
			.setIntegerValue(8).setMinIntegerValue(0).setMaxIntegerValue(36).build();

	private final Setting minDmg = new Setting.Builder(SettingType.INTEGER).setName("MinDMG").setModule(this)
			.setIntegerValue(6).setMinIntegerValue(0).setMaxIntegerValue(20).build();

	private final Setting faceplace = new Setting.Builder(SettingType.INTEGER).setName("Faceplace").setModule(this)
			.setIntegerValue(8).setMinIntegerValue(0).setMaxIntegerValue(36).build();

	private final Setting red = new Setting.Builder(SettingType.INTEGER).setName("Red").setModule(this)
			.setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	private final Setting green = new Setting.Builder(SettingType.INTEGER).setName("Green").setModule(this)
			.setIntegerValue(0).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	private final Setting blue = new Setting.Builder(SettingType.INTEGER).setName("Blue").setModule(this)
			.setIntegerValue(255).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	private final Setting alpha = new Setting.Builder(SettingType.INTEGER).setName("Alpha").setModule(this)
			.setIntegerValue(35).setMinIntegerValue(0).setMaxIntegerValue(255).build();

	private final Setting slabRender = new Setting.Builder(SettingType.BOOLEAN).setName("FlatRender").setModule(this)
			.setBooleanValue(true).build();

	private final Setting rainbow = new Setting.Builder(SettingType.BOOLEAN).setName("Rainbow").setModule(this)
			.setBooleanValue(true).build();

	private final Setting renderDmg = new Setting.Builder(SettingType.BOOLEAN).setName("RenderDmg").setModule(this)
			.setBooleanValue(false).build();

	public static EntityPlayer target2;
	BlockPos render;
	BlockPos pos = null;
	String damageString;
	Timah breakTimer = new Timah();
	boolean mainhand = false;
	boolean offhand = false;

	public AutoCrystal(String name, String description, Category category) {
		super(name, description, category);
		addSetting(logic);
		addSetting(place);
		addSetting(predict);
		addSetting(ak47);
		addSetting(rotate);
		addSetting(spoofRotations);
		addSetting(autoSwitch);
		addSetting(hitDelay);
		addSetting(range);
		addSetting(walls);
		addSetting(enemyRange);
		addSetting(placeRange);
		addSetting(maxSelfDmg);
		addSetting(minDmg);
		addSetting(faceplace);
		addSetting(red);
		addSetting(green);
		addSetting(blue);
		addSetting(alpha);
		addSetting(slabRender);
		addSetting(rainbow);
		addSetting(renderDmg);
	}

	public static void placeCrystalOnBlock(BlockPos pos, EnumHand hand) {
		RayTraceResult result = Minecraft.getMinecraft().world
				.rayTraceBlocks(
						new Vec3d(
								Minecraft.getMinecraft().player.posX,
								Minecraft.getMinecraft().player.posY
										+ (double) Minecraft.getMinecraft().player.getEyeHeight(),
								Minecraft.getMinecraft().player.posZ),
						new Vec3d((double) pos.getX() + 0.5, (double) pos.getY() - 0.5, (double) pos.getZ() + 0.5));
		EnumFacing facing = result == null || result.sideHit == null ? EnumFacing.UP : result.sideHit;
		Minecraft.getMinecraft().player.connection
				.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
	}

	public static BlockPos getPlayerPos() {
		return new BlockPos(Math.floor(Minecraft.getMinecraft().player.posX),
				Math.floor(Minecraft.getMinecraft().player.posY), Math.floor(Minecraft.getMinecraft().player.posZ));
	}

	public static List<BlockPos> possiblePlacePositions(float placeRange, boolean specialEntityCheck) {
		NonNullList<BlockPos> positions = NonNullList.create();
		positions.addAll(InteractHelper.getSphere(getPlayerPos(), placeRange, (int) placeRange, false, true, 0).stream()
				.filter(pos -> canPlaceCrystal(pos, specialEntityCheck)).collect(Collectors.toList()));
		return positions;
	}

	public static boolean canPlaceCrystal(BlockPos blockPos, boolean specialEntityCheck) {
		block7: {
			BlockPos boost = blockPos.add(0, 1, 0);
			BlockPos boost2 = blockPos.add(0, 2, 0);
			try {
				if (Minecraft.getMinecraft().world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK
						&& Minecraft.getMinecraft().world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
					return false;
				}
				if (Minecraft.getMinecraft().world.getBlockState(boost).getBlock() != Blocks.AIR
						|| Minecraft.getMinecraft().world.getBlockState(boost2).getBlock() != Blocks.AIR) {
					return false;
				}
				if (specialEntityCheck) {
					for (Entity entity : Minecraft.getMinecraft().world.getEntitiesWithinAABB(Entity.class,
							new AxisAlignedBB(boost))) {
						if (entity instanceof EntityEnderCrystal)
							continue;
						return false;
					}
					for (Entity entity : Minecraft.getMinecraft().world.getEntitiesWithinAABB(Entity.class,
							new AxisAlignedBB(boost2))) {
						if (entity instanceof EntityEnderCrystal)
							continue;
						return false;
					}
					break block7;
				}
				return Minecraft.getMinecraft().world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost))
						.isEmpty()
						&& Minecraft.getMinecraft().world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2))
								.isEmpty();
			} catch (Exception ignored) {
				return false;
			}
		}
		return true;
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (mc.player == null || mc.world == null)
			return;
		dologic();
	}

	void dologic() {
		switch (logic.getEnumValue()) {
		case "BREAKPLACE":
			logic();
			gloop();
			break;
		case "PLACEBREAK":
			gloop();
			logic();
		default:
			break;
		}
	}

	void logic() {
		final EntityEnderCrystal crystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream()
				.filter(entity -> entity instanceof EntityEnderCrystal)
				.min(Comparator.comparing(c -> mc.player.getDistance(c))).orElse(null);
		if (crystal != null && mc.player.getDistance(crystal) <= range.getIntegerValue()) {
			if (ak47.getBooleanValue()) {
				crystal.setDead();
			}

			if (breakTimer.passedMs(hitDelay.getIntegerValue())) {
//            	if (predict.getBooleanValue()) {
//                  final CPacketUseEntity attackPacket = new CPacketUseEntity();
//                  attackPacket.entityId = crystal;
//                  attackPacket.action = CPacketUseEntity.Action.ATTACK;
//                  mc.player.connection.sendPacket((Packet)attackPacket);
//            	}
				mc.playerController.attackEntity(mc.player, crystal);
				mc.player.swingArm(EnumHand.MAIN_HAND);
				breakTimer.reset();
			}
		}
	}

	void gloop() {
		int crystalSlot = mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL
				? mc.player.inventory.currentItem
				: -1;
		if (crystalSlot == -1) {
			for (int l = 0; l < 9; ++l) {
				if (mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
					crystalSlot = l;
					break;
				}
			}
		}

		boolean offhand = false;
		if (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
			offhand = true;
		} else if (crystalSlot == -1) {
			return;
		}
		double dmg = .5;
		mainhand = (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL);
		offhand = (mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
		final List<EntityPlayer> entities = mc.world.playerEntities.stream()
				.filter(entityPlayer -> entityPlayer != mc.player && !Friends.isFriend(entityPlayer.getName()))
				.collect(Collectors.toList());
		if (!offhand && mc.player.inventory.currentItem != crystalSlot) {
			if (autoSwitch.getBooleanValue()) {
				mc.player.inventory.currentItem = crystalSlot;
			}
			return;
		}
		for (EntityPlayer entity2 : entities) {
			if (entity2.getHealth() <= 0.0f || mc.player.getDistance(entity2) > enemyRange.getIntegerValue())
				continue;
			for (final BlockPos blockPos : possiblePlacePositions((float) placeRange.getIntegerValue(), true)) {
				final double d = calcDmg(blockPos, entity2);
				final double self = calcDmg(blockPos, mc.player);
				if (d < minDmg.getIntegerValue()
						&& entity2.getHealth() + entity2.getAbsorptionAmount() > faceplace.getIntegerValue()
						|| maxSelfDmg.getIntegerValue() <= self || d <= dmg)
					continue;
				dmg = d;
				pos = blockPos;
				target2 = entity2;
			}
		}

		if (dmg == .5) {
			render = null;
			return;
		}

		if (place.getBooleanValue()) {
			if (offhand || mainhand) {
				render = pos;
				placeCrystalOnBlock(pos, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
				damageString = String.valueOf(String.format("%.1f", dmg));
			}
		}
	}

	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) {
		if (this.render != null && target2 != null) {
			final float[] hue = { (System.currentTimeMillis() % (360 * 7) / (360f * 7)) };
			int rgb = Color.HSBtoRGB(hue[0], 1, 1);
			int r = (rgb >> 16) & 0xFF;
			int g = (rgb >> 8) & 0xFF;
			int b = rgb & 0xFF;
			final AxisAlignedBB bb = new AxisAlignedBB(render.getX() - mc.getRenderManager().viewerPosX,
					render.getY() - mc.getRenderManager().viewerPosY + 1,
					render.getZ() - mc.getRenderManager().viewerPosZ,
					render.getX() + 1 - mc.getRenderManager().viewerPosX,
					render.getY() + (slabRender.getBooleanValue() ? 1.1 : 0) - mc.getRenderManager().viewerPosY,
					render.getZ() + 1 - mc.getRenderManager().viewerPosZ);
			if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX,
					bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ,
					bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY,
					bb.maxZ + mc.getRenderManager().viewerPosZ))) {
				RenderUtil.drawESP(bb, rainbow.getBooleanValue() ? r : red.getIntegerValue(),
						rainbow.getBooleanValue() ? g : green.getIntegerValue(),
						rainbow.getBooleanValue() ? b : blue.getIntegerValue(), alpha.getIntegerValue());
				RenderUtil.drawESPOutline(bb, rainbow.getBooleanValue() ? r : red.getIntegerValue(),
						rainbow.getBooleanValue() ? g : green.getIntegerValue(),
						rainbow.getBooleanValue() ? b : blue.getIntegerValue(), 255f, 1f);
				if (renderDmg.getBooleanValue()) {
					final double posX = render.getX() - ((IRenderManager) mc.getRenderManager()).getRenderPosX();
					final double posY = render.getY() - ((IRenderManager) mc.getRenderManager()).getRenderPosY();
					final double posZ = render.getZ() - ((IRenderManager) mc.getRenderManager()).getRenderPosZ();
					if (slabRender.getBooleanValue()) {
						RenderUtil.renderTag(damageString, posX + 0.5, posY - 0.6, posZ + 0.5,
								new Color(255, 255, 255, 255).getRGB());
					} else {
						RenderUtil.renderTag(damageString, posX + 0.5, posY - 1.2, posZ + 0.5,
								new Color(255, 255, 255, 255).getRGB());
					}
				}
				GlStateManager.enableDepth();
				GlStateManager.depthMask(true);
				GlStateManager.enableLighting();
				GlStateManager.disableBlend();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				RenderHelper.disableStandardItemLighting();
			}
		}
	}

	public float calcDmg(BlockPos b, EntityPlayer target) {
		return calculateDamage(b.getX() + .5, b.getY() + 1, b.getZ() + .5, target);
	}

	public void onDisable() {
		render = null;
		target2 = null;
	}

	public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
		float doubleExplosionSize = 12.0F;
		double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
		Vec3d vec3d = new Vec3d(posX, posY, posZ);
		double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
		double v = (1.0D - distancedsize) * blockDensity;
		float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
		double finald = 1.0D;
		if (entity instanceof EntityLivingBase) {
			finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage),
					new Explosion(Minecraft.getMinecraft().world, null, posX, posY, posZ, 6.0F, false, true));
		}

		return (float) finald;
	}

	private static float getDamageMultiplied(float damage) {
		int diff = Minecraft.getMinecraft().world.getDifficulty().getId();
		return damage * (diff == 0 ? 0.0F : (diff == 2 ? 1.0F : (diff == 1 ? 0.5F : 1.5F)));
	}

	public static float getBlastReduction(final EntityLivingBase entity, float damage, final Explosion explosion) {
		if (entity instanceof EntityPlayer) {
			final EntityPlayer ep = (EntityPlayer) entity;
			final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
			damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(),
					(float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
			final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
			final float f = MathHelper.clamp((float) k, 0.0f, 20.0f);
			damage *= 1.0f - f / 25.0f;
			if (entity.isPotionActive(Objects.requireNonNull(Potion.getPotionById(11)))) {
				damage -= damage / 4.0f;
			}
			return damage;
		}
		damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(),
				(float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
		return damage;
	}

}