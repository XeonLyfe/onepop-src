//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.world;

import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.Block;
import java.util.Iterator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import java.util.ArrayList;
import java.util.List;
import com.onepop.api.util.math.PositionUtil;
import net.minecraft.util.math.Vec3i;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;

public class CrystalUtil
{
    static final Minecraft mc;
    
    public static boolean isSelfDamage(final EntityPlayer target, final BlockPos position, final boolean suicide, final int damage) {
        if (suicide) {
            return false;
        }
        final float k = calculateDamage(position.x, position.y, position.z, (Entity)target);
        final float self = calculateDamage(position.x, position.y, position.z, (Entity)CrystalUtil.mc.player);
        return (self > k && k >= target.getHealth()) || self - 0.5 > damage;
    }
    
    public static boolean isFacePlace(final BlockPos pos, final EntityPlayer target) {
        boolean moment = false;
        final BlockPos targetPos = new BlockPos(Math.floor(target.posX), Math.floor(target.posY), Math.floor(target.posZ));
        for (final BlockPos add : BlockUtil.FULL_BLOCK_ADD) {
            final BlockPos added = pos.add((Vec3i)add);
            if (!PositionUtil.collideBlockPos(added, targetPos.down())) {
                if (!PositionUtil.collideBlockPos(added, targetPos.up())) {
                    if (PositionUtil.collideBlockPos(added, targetPos)) {
                        moment = true;
                        break;
                    }
                }
            }
        }
        return moment;
    }
    
    public static List<BlockPos> getSphereCrystalPlace(final float range, final boolean thirteen, final boolean specialEntityCheck) {
        final BlockPos selfPos = new BlockPos(CrystalUtil.mc.player.posX, CrystalUtil.mc.player.posY, CrystalUtil.mc.player.posZ);
        final List<BlockPos> list = new ArrayList<BlockPos>();
        for (int x = (int)(selfPos.x - range); x <= selfPos.x + range; ++x) {
            for (int z = (int)(selfPos.z - range); z <= selfPos.z + range; ++z) {
                for (int y = (int)(selfPos.y - range); y <= selfPos.y + range; ++y) {
                    final double dist = (selfPos.x - x) * (selfPos.x - x) + (selfPos.z - z) * (selfPos.z - z) + (selfPos.y - y) * (selfPos.y - y);
                    if (dist < range * range) {
                        final BlockPos block = new BlockPos(x, y, z);
                        if (isCrystalPlaceable(block, thirteen, specialEntityCheck)) {
                            list.add(block);
                        }
                    }
                }
            }
        }
        return list;
    }
    
    public static boolean isCrystalPlaceable(final BlockPos blockPos, final boolean thirteen, final boolean specialEntityCheck) {
        final BlockPos boostY = blockPos.add(0, 1, 0);
        final BlockPos boostYY = blockPos.add(0, 2, 0);
        final BlockPos boostYYY = blockPos.add(0, 3, 0);
        try {
            if (CrystalUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && CrystalUtil.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
            if (CrystalUtil.mc.world.getBlockState(boostY).getBlock() != Blocks.AIR || (CrystalUtil.mc.world.getBlockState(boostYY).getBlock() != Blocks.AIR && !thirteen)) {
                return false;
            }
            if (!specialEntityCheck) {
                return CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boostY)).isEmpty() && CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boostYY)).isEmpty();
            }
            for (final Object entity : CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boostY))) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    return false;
                }
            }
            for (final Object entity : CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boostYY))) {
                if (!(entity instanceof EntityEnderCrystal)) {
                    return false;
                }
            }
            for (final Object entity : CrystalUtil.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boostYYY))) {
                if (entity instanceof EntityEnderCrystal) {
                    return false;
                }
            }
        }
        catch (Exception ignored) {
            return false;
        }
        return true;
    }
    
    public static boolean isCrystalPlaceable(final BlockPos pos) {
        final Block block = CrystalUtil.mc.world.getBlockState(pos).getBlock();
        if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) {
            final Block floor = CrystalUtil.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock();
            final Block ceil = CrystalUtil.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock();
            if (floor == Blocks.AIR && ceil == Blocks.AIR) {
                return CrystalUtil.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.add(0, 1, 0))).isEmpty() && CrystalUtil.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.add(0, 2, 0))).isEmpty();
            }
        }
        return false;
    }
    
    public static float calculateDamage(final BlockPos pos, final Entity entity) {
        return calculateDamage(pos.x + 0.5f, pos.y + 1, pos.z + 0.5, entity);
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final float doubleExplosionSize = 12.0f;
        final double distancedsize = entity.getDistance(posX, posY, posZ) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = 0.0;
        try {
            blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        }
        catch (Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)CrystalUtil.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            }
            catch (Exception ex) {}
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = CrystalUtil.mc.world.getDifficulty().getDifficultyId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float calculateDamage(final EntityEnderCrystal crystal, final Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
