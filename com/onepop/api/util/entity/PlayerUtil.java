//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.entity;

import me.rina.turok.util.TurokMath;
import net.minecraft.util.math.Vec3d;
import com.onepop.Onepop;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil
{
    public static BlockPos getBlockPos() {
        return new BlockPos(Math.floor(Onepop.MC.player.posX), Math.floor(Onepop.MC.player.posY), Math.floor(Onepop.MC.player.posZ));
    }
    
    public static Vec3d getVec() {
        return new Vec3d(Onepop.MC.player.posX, Onepop.MC.player.posY, Onepop.MC.player.posZ);
    }
    
    public static double[] getPos() {
        return new double[] { Onepop.MC.player.posX, Onepop.MC.player.posY, Onepop.MC.player.posZ };
    }
    
    public static double[] getLastTickPos() {
        return new double[] { Onepop.MC.player.lastTickPosX, Onepop.MC.player.lastTickPosY, Onepop.MC.player.lastTickPosZ };
    }
    
    public static double[] getPrevPos() {
        return new double[] { Onepop.MC.player.prevPosX, Onepop.MC.player.prevPosY, Onepop.MC.player.prevPosZ };
    }
    
    public static double[] getMotion() {
        return new double[] { Onepop.MC.player.motionX, Onepop.MC.player.motionY, Onepop.MC.player.motionZ };
    }
    
    public static float[] getRotation() {
        return new float[] { Onepop.MC.player.rotationYaw, Onepop.MC.player.rotationPitch };
    }
    
    public static double getBPS() {
        final double[] prevPosition = getPrevPos();
        final double[] position = getPos();
        final double x = position[0] - prevPosition[0];
        final double z = position[2] - prevPosition[2];
        return TurokMath.sqrt(x * x + z * z) / (Onepop.MC.timer.field_194149_e / 1000.0);
    }
    
    public static void setPosition(final double x, final double y, final double z) {
        Onepop.MC.player.setPosition(x, y, z);
    }
    
    public static void setYaw(final float yaw) {
        Onepop.MC.player.rotationYaw = yaw;
        Onepop.MC.player.rotationYawHead = yaw;
    }
    
    public static void setPitch(final float pitch) {
        Onepop.MC.player.rotationPitch = pitch;
    }
    
    public static Dimension getCurrentDimension() {
        Dimension dimension = null;
        if (Onepop.MC.player.dimension == -1) {
            dimension = Dimension.NETHER;
        }
        if (Onepop.MC.player.dimension == 0) {
            dimension = Dimension.WORLD;
        }
        if (Onepop.MC.player.dimension == 1) {
            dimension = Dimension.END;
        }
        return dimension;
    }
    
    public enum Dimension
    {
        WORLD, 
        NETHER, 
        END;
    }
}
