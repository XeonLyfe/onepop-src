//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.world;

import java.util.Arrays;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.EntityLivingBase;
import me.rina.turok.util.TurokMath;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Iterator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import com.onepop.Onepop;
import net.minecraft.util.math.Vec3i;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.util.math.BlockPos;

public class BlockUtil
{
    public static float[] ONE_BLOCK_HEIGHT;
    public static float[] TWO_BLOCKS_HEIGHT;
    public static float[] THREE_BLOCKS_HEIGHT;
    public static float[] FOUR_BLOCKS_HEIGHT;
    public static final BlockPos[] BED_POSSIBLE_LOCATIONS;
    public static final BlockPos[] FULL_BLOCK_ADD;
    public static final List<Block> BLACK_LIST;
    public static final List<Material> REPLACEABLE_LIST;
    
    public static boolean isPlaceableExcludingBlackListAndEntity(final BlockPos position) {
        boolean yes = false;
        for (final EnumFacing faces : EnumFacing.values()) {
            final BlockPos offset = position.offset(faces);
            if (!isAir(offset)) {
                yes = true;
                break;
            }
        }
        return yes;
    }
    
    public static boolean isReplaceableAndNotLiquid(final BlockPos pos) {
        final IBlockState state = getState(pos);
        return state.getMaterial().isReplaceable() && !state.getMaterial().isLiquid();
    }
    
    public static boolean isReplaceable(final BlockPos pos) {
        final IBlockState state = getState(pos);
        return state.getMaterial().isReplaceable();
    }
    
    public static boolean isPlaceableExcludingEntity(final BlockPos position) {
        boolean yes = false;
        for (final EnumFacing faces : EnumFacing.values()) {
            final BlockPos offset = position.offset(faces);
            if (!isAir(offset) && !BlockUtil.BLACK_LIST.contains(getBlock(offset))) {
                yes = true;
                break;
            }
        }
        return yes;
    }
    
    public static boolean isPlaceableExcludingBlackList(final BlockPos position) {
        boolean yes = false;
        for (final EnumFacing faces : EnumFacing.values()) {
            final BlockPos offset = position.offset(faces);
            if (!isAir(offset)) {
                yes = true;
                break;
            }
        }
        return yes && !isEntityOver(position);
    }
    
    public static boolean isPlaceable(final BlockPos position) {
        boolean yes = false;
        for (final EnumFacing faces : EnumFacing.values()) {
            final BlockPos offset = position.offset(faces);
            if (!isAir(offset) && !BlockUtil.BLACK_LIST.contains(getBlock(offset))) {
                yes = true;
                break;
            }
        }
        return yes && !isEntityOver(position);
    }
    
    public static boolean isPlaceableCustomMask(final BlockPos position, final BlockPos[] mask) {
        boolean yes = false;
        for (final BlockPos add : mask) {
            final BlockPos added = position.add((Vec3i)add);
            if (!isAir(added) && !BlockUtil.BLACK_LIST.contains(getBlock(added))) {
                yes = true;
                break;
            }
        }
        return yes && !isEntityOver(position);
    }
    
    public static boolean isEntityOver(final BlockPos pos) {
        final List<Entity> sizeOne = Onepop.MC.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos));
        boolean isOver = false;
        if (sizeOne.isEmpty()) {
            return false;
        }
        for (final Object entity : sizeOne) {
            if (!(entity instanceof EntityItem)) {
                isOver = true;
                break;
            }
        }
        final List<Entity> sizeTwo = Onepop.MC.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos.up()));
        if (sizeTwo.isEmpty()) {
            return false;
        }
        for (final Object entity2 : sizeTwo) {
            if (!(entity2 instanceof EntityItem)) {
                isOver = true;
                break;
            }
        }
        return isOver;
    }
    
    public static EnumFacing getBedPlaceableFaces(final BlockPos pos, final boolean airPlace) {
        final Block blockMain = getBlock(pos);
        final Block blockMainUp = getBlock(pos.up());
        final boolean bed = blockMainUp == Blocks.BED;
        if (!bed && (blockMain == Blocks.AIR || blockMainUp != Blocks.AIR || BlockUtil.REPLACEABLE_LIST.contains(getState(pos).getMaterial()) || BlockUtil.REPLACEABLE_LIST.contains(getState(pos.up()).getMaterial()) || BlockUtil.BLACK_LIST.contains(blockMain) || BlockUtil.BLACK_LIST.contains(blockMainUp))) {
            return null;
        }
        EnumFacing facing = null;
        for (final EnumFacing faces : EnumFacing.values()) {
            if (faces != EnumFacing.UP) {
                if (faces != EnumFacing.DOWN) {
                    final BlockPos offset = pos.offset(faces);
                    final Block blockOffset = getBlock(offset);
                    final Block blockOffsetUp = getBlock(offset.up());
                    if (bed && blockOffsetUp == Blocks.BED) {
                        facing = faces;
                        break;
                    }
                    if (!bed) {
                        if (!BlockUtil.BLACK_LIST.contains(blockOffset)) {
                            if (!BlockUtil.BLACK_LIST.contains(blockOffsetUp)) {
                                if (blockOffset != Blocks.AIR || airPlace) {
                                    if (!BlockUtil.REPLACEABLE_LIST.contains(getState(offset).getMaterial())) {
                                        if (blockOffsetUp == Blocks.AIR || BlockUtil.REPLACEABLE_LIST.contains(getState(offset.up()).getMaterial())) {
                                            facing = faces;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return facing;
    }
    
    public static Block getBlock(final BlockPos pos) {
        return Onepop.MC.world.getBlockState(pos).getBlock();
    }
    
    public static boolean isAir(final BlockPos pos) {
        final IBlockState state = Onepop.MC.world.getBlockState(pos);
        return state.getBlock() == Blocks.AIR || state.getMaterial().isReplaceable();
    }
    
    public static boolean isUnbreakable(final BlockPos pos) {
        final IBlockState blockState = Onepop.MC.world.getBlockState(pos);
        return Onepop.MC.world.getBlockState(pos).getBlockHardness((World)Onepop.MC.world, pos) == -1.0f;
    }
    
    public static float getHardness(final BlockPos pos) {
        final IBlockState blockState = Onepop.MC.world.getBlockState(pos);
        return blockState.getBlockHardness((World)Onepop.MC.world, pos);
    }
    
    public static IBlockState getState(final BlockPos pos) {
        return Onepop.MC.world.getBlockState(pos);
    }
    
    public static int getDistanceI(final BlockPos pos, final Entity entity) {
        final int x = (int)(pos.x - entity.posX);
        final int y = (int)(pos.y - entity.posY);
        final int z = (int)(pos.z - entity.posZ);
        return TurokMath.sqrt(x * x + y * y + z * z);
    }
    
    public static double getDistanceD(final BlockPos pos, final Entity entity) {
        final double x = pos.x - entity.posX;
        final double y = pos.y - entity.posY;
        final double z = pos.z - entity.posZ;
        return TurokMath.sqrt(x * x + y * y + z * z);
    }
    
    public static EnumFacing getSideFacing(final BlockPos pos, final EntityLivingBase entityLivingBase) {
        return EnumFacing.func_190914_a(pos, entityLivingBase);
    }
    
    public static EnumFacing getFacing(final BlockPos pos, final EntityLivingBase entityLivingBase) {
        final Vec3d eye = entityLivingBase.getPositionEyes(1.0f);
        final float x = (float)eye.xCoord - (pos.x + 0.5f);
        final float y = (float)eye.yCoord - (pos.y + 0.5f);
        final float z = (float)eye.zCoord - (pos.z + 0.5f);
        return EnumFacing.getFacingFromVector(x, y, z);
    }
    
    static {
        BlockUtil.ONE_BLOCK_HEIGHT = new float[] { 0.42f, 0.75f };
        BlockUtil.TWO_BLOCKS_HEIGHT = new float[] { 0.4f, 0.75f, 0.5f, 0.41f, 0.83f, 1.16f, 1.41f, 1.57f, 1.58f, 1.42f };
        BlockUtil.THREE_BLOCKS_HEIGHT = new float[] { 0.42f, 0.78f, 0.63f, 0.51f, 0.9f, 1.21f, 1.45f, 1.43f, 1.78f, 1.63f, 1.51f, 1.9f, 2.21f, 2.45f, 2.43f };
        BlockUtil.FOUR_BLOCKS_HEIGHT = new float[] { 0.42f, 0.78f, 0.63f, 0.51f, 0.9f, 1.21f, 1.45f, 1.43f, 1.78f, 1.63f, 1.51f, 1.9f, 2.21f, 2.45f, 2.43f, 2.78f, 2.63f, 2.51f, 2.9f, 3.21f, 3.45f, 3.43f };
        BED_POSSIBLE_LOCATIONS = new BlockPos[] { new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        FULL_BLOCK_ADD = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 1, 0), new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1) };
        BLACK_LIST = Arrays.asList(Blocks.ENDER_CHEST, (Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, (Block)Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER, Blocks.TRAPDOOR, Blocks.ENCHANTING_TABLE);
        REPLACEABLE_LIST = Arrays.asList(Material.WATER, Material.LAVA, Material.PLANTS, Material.FIRE, Material.CIRCUITS, Material.GLASS, Material.PACKED_ICE, Material.SNOW);
    }
    
    public static class BlockDamage
    {
        private BlockPos pos;
        private EnumFacing facing;
        
        public BlockDamage(final BlockPos pos, final EnumFacing facing) {
            this.pos = pos;
            this.facing = facing;
        }
        
        public BlockPos getPos() {
            return this.pos;
        }
        
        public EnumFacing getFacing() {
            return this.facing;
        }
    }
}
