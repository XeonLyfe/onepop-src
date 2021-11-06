//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.entity;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.Onepop;
import com.onepop.api.util.math.RotationUtil;
import net.minecraft.util.math.Vec3d;

public class PlayerRotationUtil
{
    public static void spamPacketRotation(final Vec3d vec) {
        final float[] rotate = RotationUtil.getPlaceRotation(vec);
        final float yaw = rotate[0];
        final float pitch = rotate[1];
        final boolean flag = Onepop.MC.player.onGround;
        Onepop.MC.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, flag));
    }
    
    public static void spamPacketRotation(final float yaw, final float pitch) {
        final boolean flag = Onepop.MC.player.onGround;
        Onepop.MC.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, flag));
    }
    
    public static void manual(final Vec3d vec) {
        final float[] rotate = RotationUtil.getPlaceRotation(vec);
        final float yaw = rotate[0];
        final float pitch = rotate[1];
        PlayerUtil.setYaw(yaw);
        PlayerUtil.setPitch(pitch);
    }
    
    public static void manual(final float yaw, final float pitch) {
        PlayerUtil.setYaw(yaw);
        PlayerUtil.setPitch(pitch);
    }
}
