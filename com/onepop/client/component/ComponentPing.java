//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.component.Component;

public class ComponentPing extends Component
{
    public ComponentPing() {
        super("Ping", "Ping", "Shows your ping!!", StringType.USE);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        if (ComponentPing.mc.player == null) {
            return;
        }
        final String ping = "Ping " + ChatFormatting.GRAY + this.getPing();
        this.render(ping, 0.0f, 0.0f);
        this.rect.setWidth((float)this.getStringWidth(ping));
        this.rect.setHeight((float)this.getStringHeight(ping));
    }
    
    public String getPing() {
        String ping = "?";
        try {
            ping = "" + ComponentPing.mc.getConnection().getPlayerInfo(ComponentPing.mc.player.getUniqueID()).getResponseTime();
        }
        catch (Exception e) {
            return "?";
        }
        return ping;
    }
}
