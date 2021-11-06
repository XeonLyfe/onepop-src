//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import net.minecraft.client.Minecraft;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.component.Component;

public class ComponentFPS extends Component
{
    public ComponentFPS() {
        super("FPS", "FPS", "Shows your FPS!!", StringType.USE);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        final String fps = "FPS " + ChatFormatting.GRAY + Minecraft.getDebugFPS();
        this.render(fps, 0.0f, 0.0f);
        this.rect.setWidth((float)this.getStringWidth(fps));
        this.rect.setHeight((float)this.getStringHeight(fps));
    }
}
