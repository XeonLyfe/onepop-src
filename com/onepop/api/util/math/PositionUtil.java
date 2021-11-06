//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.math;

import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;

public class PositionUtil
{
    public static BlockPos toBlockPos(final double x, final double y, final double z) {
        return new BlockPos(x, y, z);
    }
    
    public static BlockPos toBlockPos(final Vec3d pos) {
        return new BlockPos(pos.xCoord, pos.yCoord, pos.zCoord);
    }
    
    public static Vec3d toVec(final BlockPos pos) {
        return new Vec3d((double)pos.x, (double)pos.y, (double)pos.z);
    }
    
    public static boolean collideBlockPos(final BlockPos a, final BlockPos b) {
        return a.getX() == a.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
    }
    
    public static Vec3d calculateHitPlace(final BlockPos pos, final EnumFacing facing) {
        return new Vec3d((Vec3i)pos).addVector(0.5, 0.5, 0.5).add(new Vec3d(facing.getDirectionVec()).scale(0.5));
    }
}
