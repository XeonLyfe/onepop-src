//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.component.Component;

public class ComponentDurability extends Component
{
    public ComponentDurability() {
        super("Durability", "durability", "Shows the durability of an item", StringType.USE);
    }
    
    @Override
    public void onRender() {
        final int d = ComponentDurability.mc.player.getHeldItemMainhand().getItemDamage() - ComponentDurability.mc.player.getHeldItemMainhand().getMaxDamage();
        final String dura = "Durability " + ChatFormatting.WHITE + d;
        this.render(dura, 0.0f, 0.0f);
        this.rect.setWidth((float)this.getStringWidth(dura));
        this.rect.setHeight((float)this.getStringHeight(dura));
    }
}
