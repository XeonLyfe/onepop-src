// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.component.impl.ComponentSetting;
import com.onepop.api.component.Component;

public class ComponentWatermark extends Component
{
    private ComponentSetting<Boolean> settingVersion;
    
    public ComponentWatermark() {
        super("Watermark", "Watermark", "Show the client watermark!", StringType.USE);
        this.settingVersion = new ComponentSetting<Boolean>("Version", "Version", "Shows the version!", true);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        final String watermark = ChatFormatting.GOLD + "1pop" + " " + ChatFormatting.GRAY + (this.settingVersion.getValue() ? "2.0beta" : "");
        this.render(watermark, 0.0f, 0.0f);
        this.rect.setWidth((float)this.getStringWidth(watermark));
        this.rect.setHeight((float)this.getStringHeight(watermark));
    }
}
