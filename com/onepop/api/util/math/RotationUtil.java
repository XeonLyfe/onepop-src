//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.math;

import net.minecraft.util.math.MathHelper;
import me.rina.turok.util.TurokMath;
import net.minecraft.entity.Entity;
import com.onepop.api.util.entity.EntityUtil;
import com.onepop.Onepop;
import net.minecraft.util.math.Vec3d;

public class RotationUtil
{
    public static float[] getPlaceRotation(final Vec3d pos) {
        final Vec3d eye = EntityUtil.eye((Entity)Onepop.MC.player);
        final double x = pos.xCoord - eye.xCoord;
        final double y = pos.yCoord - eye.yCoord;
        final double z = pos.zCoord - eye.zCoord;
        final double diff = TurokMath.sqrt(x * x + z * z);
        final float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(y, diff)));
        return new float[] { Onepop.MC.player.rotationYaw + MathHelper.wrapDegrees(yaw - Onepop.MC.player.rotationYaw), Onepop.MC.player.rotationPitch + MathHelper.wrapDegrees(pitch - Onepop.MC.player.rotationPitch) };
    }
    
    public static float[] getBreakRotation(final Vec3d vec) {
        double dirX = Onepop.MC.player.posX - vec.xCoord;
        double dirY = Onepop.MC.player.posY - vec.yCoord;
        double dirZ = Onepop.MC.player.posZ - vec.zCoord;
        final double len = TurokMath.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX /= len;
        dirY /= len;
        dirZ /= len;
        float pitch = (float)Math.asin(dirY);
        float yaw = (float)Math.atan2(dirZ, dirX);
        pitch = (float)(pitch * 180.0 / 3.141592653589793);
        yaw = (float)(yaw * 180.0 / 3.141592653589793);
        yaw += 90.0f;
        return new float[] { yaw, pitch };
    }
}
