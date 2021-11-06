//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.entity;

import net.minecraft.network.play.server.SPacketSpawnObject;
import java.util.UUID;
import com.onepop.api.event.Event;

public class PredictPacketObjectSpawn extends Event
{
    private final int entityId;
    private final UUID uniqueId;
    private final double x;
    private final double y;
    private final double z;
    private final int speedX;
    private final int speedY;
    private final int speedZ;
    private final int pitch;
    private final int yaw;
    private final int type;
    private final int data;
    
    public PredictPacketObjectSpawn(final SPacketSpawnObject packet) {
        this.entityId = packet.getEntityID();
        this.uniqueId = packet.getUniqueId();
        this.x = packet.getX();
        this.y = packet.getY();
        this.z = packet.getZ();
        this.speedX = packet.getSpeedX();
        this.speedY = packet.getSpeedY();
        this.speedZ = packet.getSpeedZ();
        this.pitch = packet.getPitch();
        this.yaw = packet.getYaw();
        this.type = packet.getType();
        this.data = packet.getData();
    }
    
    public int getEntityId() {
        return this.entityId;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public int getSpeedX() {
        return this.speedX;
    }
    
    public int getSpeedY() {
        return this.speedY;
    }
    
    public int getSpeedZ() {
        return this.speedZ;
    }
    
    public int getPitch() {
        return this.pitch;
    }
    
    public int getYaw() {
        return this.yaw;
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getData() {
        return this.data;
    }
}
