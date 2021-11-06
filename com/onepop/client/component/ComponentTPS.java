// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import com.onepop.Onepop;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.component.Component;

public class ComponentTPS extends Component
{
    public ComponentTPS() {
        super("TPS", "TPS", "Shows the current TPS!!", StringType.USE);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        final String tps = "TPS " + ChatFormatting.GRAY + String.format("%.1f", Onepop.getTPSManager().getTPS());
        this.render(tps, 0.0f, 0.0f);
        this.rect.setWidth((float)this.getStringWidth(tps));
        this.rect.setHeight((float)this.getStringHeight(tps));
    }
}
