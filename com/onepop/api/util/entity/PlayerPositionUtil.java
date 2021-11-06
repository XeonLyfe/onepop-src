//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.entity;

import me.rina.turok.util.TurokMath;
import net.minecraft.network.Packet;
import com.onepop.api.util.network.PacketUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.Onepop;
import net.minecraft.util.math.Vec3d;
import com.onepop.api.util.math.PositionUtil;
import net.minecraft.util.math.BlockPos;

public class PlayerPositionUtil
{
    public static void teleportation(final BlockPos pos) {
        final Vec3d vec = PositionUtil.toVec(pos);
        teleportation(vec);
    }
    
    public static void teleportation(final Vec3d vec) {
        final boolean flag = Onepop.MC.player.onGround;
        teleportation(vec, flag);
    }
    
    public static void teleportation(final Vec3d vec, final boolean onGround) {
        final double x = vec.xCoord;
        final double y = vec.yCoord;
        final double z = vec.zCoord;
        PacketUtil.send((Packet<?>)new CPacketPlayer.Position(x, y, z, onGround));
        PlayerUtil.setPosition(x, y, z);
    }
    
    public static void smooth(final Vec3d vec, final float partialTicks) {
        final boolean flag = Onepop.MC.player.onGround;
        smooth(vec, flag, partialTicks);
    }
    
    public static void smooth(final Vec3d vec, final boolean onGround, final float partialTicks) {
        final Vec3d last = PlayerUtil.getVec();
        final Vec3d interpolation = TurokMath.lerp(last, vec, partialTicks);
        PlayerUtil.setPosition(interpolation.xCoord, interpolation.yCoord, interpolation.zCoord);
        PacketUtil.send((Packet<?>)new CPacketPlayer.Position(interpolation.xCoord, interpolation.yCoord, interpolation.zCoord, onGround));
    }
}
