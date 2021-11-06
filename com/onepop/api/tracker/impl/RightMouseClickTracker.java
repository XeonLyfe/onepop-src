//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.tracker.impl;

import com.onepop.Onepop;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import com.onepop.api.util.network.PacketUtil;

public class RightMouseClickTracker extends PacketUtil.PacketTracker
{
    private EnumHand hand;
    
    public RightMouseClickTracker(final EnumHand hand) {
        super("Right Mouse Click Tracker", (Packet)new CPacketPlayerTryUseItem((hand == null) ? EnumHand.MAIN_HAND : hand));
        this.hand = hand;
    }
    
    public void setHand(final EnumHand hand) {
        this.hand = hand;
    }
    
    public EnumHand getHand() {
        return this.hand;
    }
    
    @Override
    public void onPre() {
        if (this.hand != null) {
            Onepop.MC.player.swingArm(this.hand);
        }
    }
    
    @Override
    public void onPost() {
    }
}
