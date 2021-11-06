//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import com.onepop.client.module.client.ModuleAntiCheat;
import com.onepop.api.ISLClass;
import com.onepop.api.util.client.NullUtil;
import com.onepop.Onepop;
import com.onepop.api.render.impl.RenderType;
import java.awt.Color;
import com.onepop.api.render.Render;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Block Highlight", tag = "BlockHighlight", description = "Draw block over the mouse.", category = ModuleCategory.RENDER)
public class ModuleBlockHighlight extends Module
{
    public static ValueBoolean settingRGB;
    public static ValueNumber settingRed;
    public static ValueNumber settingGreen;
    public static ValueNumber settingBlue;
    public static ValueNumber settingAlpha;
    public static ValueNumber settingOutlineLineSize;
    public static ValueNumber settingOutlineAlpha;
    private Render render;
    private Color outline;
    private Color solid;
    
    public ModuleBlockHighlight() {
        this.render = new Render(RenderType.NORMAL);
        this.outline = new Color(255, 255, 255, 255);
        this.solid = new Color(255, 255, 255, 255);
    }
    
    @Override
    public void onSetting() {
        if (ModuleBlockHighlight.settingRGB.getValue()) {
            ModuleBlockHighlight.settingRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModuleBlockHighlight.settingGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModuleBlockHighlight.settingBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        this.solid = new Color(ModuleBlockHighlight.settingRed.getValue().intValue(), ModuleBlockHighlight.settingGreen.getValue().intValue(), ModuleBlockHighlight.settingBlue.getValue().intValue(), ModuleBlockHighlight.settingAlpha.getValue().intValue());
        this.outline = new Color(ModuleBlockHighlight.settingRed.getValue().intValue(), ModuleBlockHighlight.settingGreen.getValue().intValue(), ModuleBlockHighlight.settingBlue.getValue().intValue(), ModuleBlockHighlight.settingOutlineAlpha.getValue().intValue());
        this.render.setType(RenderType.NORMAL);
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isWorld()) {
            return;
        }
        final RayTraceResult splitResult = ISLClass.mc.player.rayTrace((double)ModuleAntiCheat.getRange(), ISLClass.mc.getRenderPartialTicks());
        if (splitResult != null && splitResult.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos block = splitResult.getBlockPos();
            final float l = ModuleBlockHighlight.settingOutlineLineSize.getValue().floatValue();
            this.render.onRender(this.camera, block, 1.0, 1.0, 1.0, l, this.solid, this.outline);
        }
    }
    
    static {
        ModuleBlockHighlight.settingRGB = new ValueBoolean("RGB", "RGB", "RGB effect.", false);
        ModuleBlockHighlight.settingRed = new ValueNumber("Red", "Red", "Color line range red.", 255, 0, 255);
        ModuleBlockHighlight.settingGreen = new ValueNumber("Green", "Green", "Color line range green.", 0, 0, 255);
        ModuleBlockHighlight.settingBlue = new ValueNumber("Blue", "Blue", "Color line range blue.", 255, 0, 255);
        ModuleBlockHighlight.settingAlpha = new ValueNumber("Alpha", "Alpha", "Color line range alpha.", 255, 0, 255);
        ModuleBlockHighlight.settingOutlineLineSize = new ValueNumber("Outline Line Size", "OutlineLineSize", "Line size.", 1.0f, 1.0f, 3.0f);
        ModuleBlockHighlight.settingOutlineAlpha = new ValueNumber("Outline Alpha", "OutlineAlpha", "Color line range alpha.", 255, 0, 255);
    }
}
