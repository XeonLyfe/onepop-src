// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client;

import com.onepop.client.module.client.ModuleClickGUI;
import java.awt.Font;
import me.rina.turok.render.font.TurokFont;

public class Wrapper
{
    public static final int FLAG_COMPONENT_CLOSED = -200;
    public static int FLAG_COMPONENT_OPENED;
    public int[] background;
    public int[] base;
    public int[] highlight;
    public int clampScrollHeight;
    public TurokFont fontBigWidget;
    public TurokFont fontNormalWidget;
    public TurokFont fontSmallWidget;
    public TurokFont fontNameTags;
    
    public Wrapper() {
        this.background = new int[] { 0, 0, 0, 0 };
        this.base = new int[] { 0, 0, 0, 0 };
        this.highlight = new int[] { 0, 0, 0, 0 };
        this.clampScrollHeight = 200;
        this.fontBigWidget = new TurokFont(new Font("Whitney", 0, 18), true, true);
        this.fontNormalWidget = new TurokFont(new Font("Whitney", 0, 19), true, true);
        this.fontSmallWidget = new TurokFont(new Font("Whitney", 0, 16), true, true);
        this.fontNameTags = new TurokFont(new Font("Whitney", 0, 19), true, true);
    }
    
    public void onUpdateColor() {
        final int baseRed = ModuleClickGUI.settingBaseRed.getValue().intValue();
        final int baseGreen = ModuleClickGUI.settingBaseGreen.getValue().intValue();
        final int baseBlue = ModuleClickGUI.settingBaseBlue.getValue().intValue();
        final int backgroundRed = ModuleClickGUI.settingBackgroundRed.getValue().intValue();
        final int backgroundGreen = ModuleClickGUI.settingBackgroundGreen.getValue().intValue();
        final int backgroundBlue = ModuleClickGUI.settingBackgroundBlue.getValue().intValue();
        this.clampScrollHeight = ModuleClickGUI.settingScrollHeight.getValue().intValue();
        this.background = new int[] { backgroundRed, backgroundGreen, backgroundBlue, 100 };
        this.base = new int[] { baseRed, baseGreen, baseBlue, 150 };
        this.highlight = new int[] { baseRed, baseGreen, baseBlue, 50 };
    }
    
    static {
        Wrapper.FLAG_COMPONENT_OPENED = 2;
    }
}
