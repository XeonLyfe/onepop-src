//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.manager.network;

import com.onepop.api.util.client.NullUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import com.onepop.api.manager.Manager;

public class CurrentItemPacketManager extends Manager
{
    public static CurrentItemPacketManager INSTANCE;
    private boolean sync;
    
    public CurrentItemPacketManager() {
        super("Current Item Packet Manager", "Silent packets!");
        CurrentItemPacketManager.INSTANCE = this;
    }
    
    public static void heldItem(final int slot) {
        CurrentItemPacketManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        CurrentItemPacketManager.INSTANCE.setSync(false);
    }
    
    public static void sync() {
        CurrentItemPacketManager.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(CurrentItemPacketManager.mc.player.inventory.currentItem));
        CurrentItemPacketManager.INSTANCE.setSync(true);
    }
    
    public void setSync(final boolean sync) {
        this.sync = sync;
    }
    
    public boolean isSync() {
        return this.sync;
    }
    
    @Override
    public void onUpdateAll() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (!this.isSync()) {
            sync();
        }
    }
}
