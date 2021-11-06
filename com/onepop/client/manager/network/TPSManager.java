//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.manager.network;

import java.util.Arrays;
import net.minecraft.util.math.MathHelper;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.manager.Manager;

public class TPSManager extends Manager
{
    final float[] ticks;
    private long lastUpdate;
    private int nextIndex;
    
    public TPSManager() {
        super("TPS Manager", "Manager tps on client.");
        this.ticks = new float[20];
        this.nextIndex = 0;
        this.reset();
    }
    
    @Listener
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            this.refresh();
        }
    }
    
    public float getTPS() {
        float num_ticks = 0.0f;
        float sum_ticks = 0.0f;
        for (final float tick : this.ticks) {
            if (tick > 0.0f) {
                sum_ticks += tick;
                ++num_ticks;
            }
        }
        return MathHelper.clamp(sum_ticks / num_ticks, 0.0f, 20.0f);
    }
    
    public void reset() {
        this.nextIndex = 0;
        this.lastUpdate = -1L;
        Arrays.fill(this.ticks, 0.0f);
    }
    
    public void refresh() {
        if (this.lastUpdate != -1L) {
            final float time = (System.currentTimeMillis() - this.lastUpdate) / 1000.0f;
            this.ticks[this.nextIndex % this.ticks.length] = MathHelper.clamp(20.0f / time, 0.0f, 20.0f);
            ++this.nextIndex;
        }
        this.lastUpdate = System.currentTimeMillis();
    }
}
