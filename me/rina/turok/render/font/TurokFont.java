// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.render.font;

import java.awt.Font;
import me.rina.turok.render.font.hal.CFontRenderer;

public class TurokFont extends CFontRenderer
{
    private Font font;
    private boolean isRenderingCustomFont;
    
    public TurokFont(final Font font, final boolean antiAlias, final boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.font = font;
        this.isRenderingCustomFont = true;
    }
    
    public void setRenderingCustomFont(final boolean renderingCustomFont) {
        this.isRenderingCustomFont = renderingCustomFont;
    }
    
    public boolean isRenderingCustomFont() {
        return this.isRenderingCustomFont;
    }
}
