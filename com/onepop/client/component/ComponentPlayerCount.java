//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.component.Component;

public class ComponentPlayerCount extends Component
{
    public ComponentPlayerCount() {
        super("PlayerCount", "PlayerCount", "Shows the number of players on your screen", StringType.USE);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        final String players = "Players " + ChatFormatting.WHITE + ComponentPlayerCount.mc.player.connection.getPlayerInfoMap().size();
        this.render(players, 0.0f, 0.0f);
        this.rect.setHeight((float)this.getStringHeight(players));
        this.rect.setWidth((float)this.getStringWidth(players));
    }
}
