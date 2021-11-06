//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.network;

import com.onepop.Onepop;
import net.minecraft.network.Packet;

public class PacketUtil
{
    public static void send(final Packet<?> packet) {
        Onepop.MC.player.connection.sendPacket((Packet)packet);
    }
    
    public static class PacketTracker
    {
        private String name;
        private Packet<?> packet;
        private boolean isCanceled;
        
        public PacketTracker(final String name, final Packet packet) {
            this.name = name;
            this.packet = (Packet<?>)packet;
        }
        
        public void setName(final String name) {
            this.name = name;
        }
        
        public void setPacket(final Packet<?> packet) {
            this.packet = packet;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Packet<?> getPacket() {
            return this.packet;
        }
        
        public boolean isCanceled() {
            return this.isCanceled;
        }
        
        public void setCanceled(final boolean canceled) {
            this.isCanceled = canceled;
        }
        
        public void onPre() {
        }
        
        public void onPost() {
        }
    }
}
