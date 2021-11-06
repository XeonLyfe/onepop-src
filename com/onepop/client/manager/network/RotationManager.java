//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.manager.network;

import net.minecraft.network.Packet;
import com.onepop.api.util.entity.PlayerRotationUtil;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.manager.Manager;

public class RotationManager extends Manager
{
    public static RotationManager INSTANCE;
    private float yaw;
    private float pitch;
    private boolean rotating;
    
    public RotationManager() {
        super("Rotation Manager", "Good rotations need good managers!");
        RotationManager.INSTANCE = this;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setRotating(final boolean rotating) {
        this.rotating = rotating;
    }
    
    public boolean isRotating() {
        return this.rotating;
    }
    
    @Listener
    public void onSendPacket(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && this.isRotating()) {
            final CPacketPlayer packet = (CPacketPlayer)event.getPacket();
            packet.pitch = this.pitch;
            packet.yaw = this.yaw;
            this.doClearTask();
        }
    }
    
    public static RotationManager task(final Enum<?> rotation, final float[] rotates) {
        switch (rotation) {
            case MANUAL: {
                PlayerRotationUtil.manual(rotates[0], rotates[1]);
                break;
            }
            case REL: {
                RotationManager.INSTANCE.setYaw(rotates[0]);
                RotationManager.INSTANCE.setPitch(rotates[1]);
                RotationManager.INSTANCE.setRotating(true);
                break;
            }
            case SEND: {
                RotationManager.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotates[0], rotates[1], RotationManager.mc.player.onGround));
                break;
            }
        }
        return RotationManager.INSTANCE;
    }
    
    public void doClearTask() {
        this.rotating = false;
    }
    
    @Override
    public void onUpdateAll() {
    }
    
    public enum Rotation
    {
        SEND, 
        REL, 
        MANUAL, 
        NONE;
    }
}
