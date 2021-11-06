//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import java.util.Iterator;
import me.rina.turok.util.TurokMath;
import com.onepop.api.ISLClass;
import com.onepop.client.manager.world.HoleManager;
import com.onepop.Onepop;
import com.onepop.api.render.impl.RenderType;
import com.onepop.api.render.Render;
import java.awt.Color;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Hole ESP", tag = "HoleESP", description = "Draws holes to help visibility at crystal pvp.", category = ModuleCategory.RENDER)
public class ModuleHoleESP extends Module
{
    public static ValueBoolean settingDoubleHole;
    public static ValueNumber settingRange;
    public static ValueEnum settingRenderType;
    public static ValueEnum settingCategory;
    public static ValueNumber settingUnsafeOffsetY;
    public static ValueBoolean settingUnsafeRGB;
    public static ValueNumber settingUnsafeRed;
    public static ValueNumber settingUnsafeGreen;
    public static ValueNumber settingUnsafeBlue;
    public static ValueNumber settingUnsafeAlpha;
    public static ValueNumber settingUnsafeOutlineLineSize;
    public static ValueNumber settingUnsafeOutlineAlpha;
    public static ValueNumber settingSafeOffsetY;
    public static ValueBoolean settingSafeRGB;
    public static ValueNumber settingSafeRed;
    public static ValueNumber settingSafeGreen;
    public static ValueNumber settingSafeBlue;
    public static ValueNumber settingSafeAlpha;
    public static ValueNumber settingSafeOutlineLineSize;
    public static ValueNumber settingSafeOutlineAlpha;
    private Color unsafeOutlineColor;
    private Color unsafeSolidColor;
    private Color safeOutlineColor;
    private Color safeSolidColor;
    private final Render render;
    
    public ModuleHoleESP() {
        this.unsafeOutlineColor = new Color(255, 255, 255, 255);
        this.unsafeSolidColor = new Color(255, 255, 255, 255);
        this.safeOutlineColor = new Color(255, 255, 255, 255);
        this.safeSolidColor = new Color(255, 255, 255, 255);
        this.render = new Render(RenderType.NORMAL);
    }
    
    @Override
    public void onSetting() {
        if (ModuleHoleESP.settingUnsafeRGB.getValue()) {
            ModuleHoleESP.settingUnsafeRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModuleHoleESP.settingUnsafeGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModuleHoleESP.settingUnsafeBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        if (ModuleHoleESP.settingSafeRGB.getValue()) {
            ModuleHoleESP.settingSafeRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModuleHoleESP.settingSafeGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModuleHoleESP.settingSafeBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        this.unsafeSolidColor = new Color(ModuleHoleESP.settingUnsafeRed.getValue().intValue(), ModuleHoleESP.settingUnsafeGreen.getValue().intValue(), ModuleHoleESP.settingUnsafeBlue.getValue().intValue(), ModuleHoleESP.settingUnsafeAlpha.getValue().intValue());
        this.safeSolidColor = new Color(ModuleHoleESP.settingSafeRed.getValue().intValue(), ModuleHoleESP.settingSafeGreen.getValue().intValue(), ModuleHoleESP.settingSafeBlue.getValue().intValue(), ModuleHoleESP.settingSafeAlpha.getValue().intValue());
        this.unsafeOutlineColor = new Color(this.unsafeSolidColor.getRed(), this.unsafeSolidColor.getGreen(), this.unsafeSolidColor.getBlue(), ModuleHoleESP.settingUnsafeOutlineAlpha.getValue().intValue());
        this.safeOutlineColor = new Color(this.safeSolidColor.getRed(), this.safeSolidColor.getGreen(), this.safeSolidColor.getBlue(), ModuleHoleESP.settingSafeOutlineAlpha.getValue().intValue());
        ModuleHoleESP.settingUnsafeOffsetY.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.UNSAFE);
        ModuleHoleESP.settingUnsafeRGB.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.UNSAFE);
        ModuleHoleESP.settingUnsafeRed.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.UNSAFE);
        ModuleHoleESP.settingUnsafeGreen.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.UNSAFE);
        ModuleHoleESP.settingUnsafeBlue.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.UNSAFE);
        ModuleHoleESP.settingUnsafeAlpha.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.UNSAFE);
        ModuleHoleESP.settingUnsafeOutlineLineSize.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.UNSAFE);
        ModuleHoleESP.settingUnsafeOutlineAlpha.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.UNSAFE);
        ModuleHoleESP.settingSafeOffsetY.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.SAFE);
        ModuleHoleESP.settingSafeRGB.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.SAFE);
        ModuleHoleESP.settingSafeRed.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.SAFE);
        ModuleHoleESP.settingSafeGreen.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.SAFE);
        ModuleHoleESP.settingSafeBlue.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.SAFE);
        ModuleHoleESP.settingSafeAlpha.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.SAFE);
        ModuleHoleESP.settingSafeOutlineLineSize.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.SAFE);
        ModuleHoleESP.settingSafeOutlineAlpha.setEnabled(ModuleHoleESP.settingCategory.getValue() == Category.SAFE);
        this.render.setType((RenderType)ModuleHoleESP.settingRenderType.getValue());
    }
    
    @Override
    public void onRender3D() {
        for (final HoleManager.Hole holes : Onepop.getHoleManager().getHoleList()) {
            if (holes.getPosition().getDistance((int)ISLClass.mc.player.posX, (int)ISLClass.mc.player.posY, (int)ISLClass.mc.player.posZ) >= ModuleHoleESP.settingRange.getValue().intValue()) {
                continue;
            }
            final Color safeOutlineAlpha = new Color(this.safeOutlineColor.getRed(), this.safeOutlineColor.getGreen(), this.safeOutlineColor.getBlue(), TurokMath.clamp(ModuleHoleESP.settingSafeOutlineAlpha.getValue().intValue() - (int)TurokMath.distancingValues((float)ModuleHoleESP.mc.player.getDistanceSq(holes.getPosition()), (float)(ModuleHoleESP.settingRange.getValue().intValue() * ModuleHoleESP.settingRange.getValue().intValue()), (float)ModuleHoleESP.settingSafeOutlineAlpha.getValue().intValue()), 0, ModuleHoleESP.settingSafeOutlineAlpha.getValue().intValue()));
            final Color unsafeOutlineAlpha = new Color(this.unsafeOutlineColor.getRed(), this.unsafeOutlineColor.getGreen(), this.unsafeOutlineColor.getBlue(), TurokMath.clamp(ModuleHoleESP.settingUnsafeOutlineAlpha.getValue().intValue() - (int)TurokMath.distancingValues((float)ModuleHoleESP.mc.player.getDistanceSq(holes.getPosition()), (float)(ModuleHoleESP.settingRange.getValue().intValue() * ModuleHoleESP.settingRange.getValue().intValue()), (float)ModuleHoleESP.settingUnsafeOutlineAlpha.getValue().intValue()), 0, ModuleHoleESP.settingUnsafeOutlineAlpha.getValue().intValue()));
            final Color safeSolidAlpha = new Color(this.safeSolidColor.getRed(), this.safeSolidColor.getGreen(), this.safeSolidColor.getBlue(), TurokMath.clamp(ModuleHoleESP.settingSafeAlpha.getValue().intValue() - (int)TurokMath.distancingValues((float)ModuleHoleESP.mc.player.getDistanceSq(holes.getPosition()), (float)(ModuleHoleESP.settingRange.getValue().intValue() * ModuleHoleESP.settingRange.getValue().intValue()), (float)ModuleHoleESP.settingSafeAlpha.getValue().intValue()), 0, ModuleHoleESP.settingSafeAlpha.getValue().intValue()));
            final Color unsafeSolidAlpha = new Color(this.unsafeSolidColor.getRed(), this.unsafeSolidColor.getGreen(), this.unsafeSolidColor.getBlue(), TurokMath.clamp(ModuleHoleESP.settingUnsafeAlpha.getValue().intValue() - (int)TurokMath.distancingValues((float)ModuleHoleESP.mc.player.getDistanceSq(holes.getPosition()), (float)(ModuleHoleESP.settingRange.getValue().intValue() * ModuleHoleESP.settingRange.getValue().intValue()), (float)ModuleHoleESP.settingUnsafeAlpha.getValue().intValue()), 0, ModuleHoleESP.settingUnsafeAlpha.getValue().intValue()));
            final float unsafeOffsetY = ModuleHoleESP.settingUnsafeOffsetY.getValue().intValue() / 100.0f;
            final float safeOffsetY = ModuleHoleESP.settingSafeOffsetY.getValue().intValue() / 100.0f;
            this.render.onRender(this.camera, holes.getPosition(), 1.0, (holes.getType() == 1) ? ((double)safeOffsetY) : ((double)unsafeOffsetY), 1.0, (holes.getType() == 1) ? ModuleHoleESP.settingSafeOutlineLineSize.getValue().floatValue() : ModuleHoleESP.settingUnsafeOutlineLineSize.getValue().floatValue(), (holes.getType() == 1) ? safeSolidAlpha : unsafeSolidAlpha, (holes.getType() == 1) ? safeOutlineAlpha : unsafeOutlineAlpha);
        }
    }
    
    static {
        ModuleHoleESP.settingDoubleHole = new ValueBoolean("Double Hole", "DoubleHole", "Render double holes.", true);
        ModuleHoleESP.settingRange = new ValueNumber("Range", "Range", "Maximum distance to render.", 8.0f, 1.0f, 10.0f);
        ModuleHoleESP.settingRenderType = new ValueEnum("Render Type", "RenderType", "Type to render.", RenderType.NORMAL);
        ModuleHoleESP.settingCategory = new ValueEnum("", "Category", "Categories for render!", Category.UNSAFE);
        ModuleHoleESP.settingUnsafeOffsetY = new ValueNumber("Unsafe Offset Y", "UnsafeOffsetY", "Offset y block render.", 25, 0, 200);
        ModuleHoleESP.settingUnsafeRGB = new ValueBoolean("Unsafe RGB", "UnsafeRGB", "RGB effect.", false);
        ModuleHoleESP.settingUnsafeRed = new ValueNumber("Unsafe Red", "UnsafeRed", "Color line range red.", 255, 0, 255);
        ModuleHoleESP.settingUnsafeGreen = new ValueNumber("Unsafe Green", "UnsafeGreen", "Color line range green.", 0, 0, 255);
        ModuleHoleESP.settingUnsafeBlue = new ValueNumber("Unsafe Blue", "UnsafeBlue", "Color line range blue.", 255, 0, 255);
        ModuleHoleESP.settingUnsafeAlpha = new ValueNumber("Unsafe Alpha", "UnsafeAlpha", "Color line range alpha.", 255, 0, 255);
        ModuleHoleESP.settingUnsafeOutlineLineSize = new ValueNumber("Unsafe Line Size", "UnsafeLineSize", "Line size.", 1.0f, 1.0f, 3.0f);
        ModuleHoleESP.settingUnsafeOutlineAlpha = new ValueNumber("Unsafe Line Alpha", "UnsafeLineAlpha", "Color line range alpha.", 255, 0, 255);
        ModuleHoleESP.settingSafeOffsetY = new ValueNumber("Safe Offset Y", "SafeOffsetY", "Offset y block render.", 25, 0, 200);
        ModuleHoleESP.settingSafeRGB = new ValueBoolean("Safe RGB", "SafeRGB", "RGB effect.", false);
        ModuleHoleESP.settingSafeRed = new ValueNumber("Safe Red", "SafeRed", "Color line range red.", 255, 0, 255);
        ModuleHoleESP.settingSafeGreen = new ValueNumber("Safe Green", "SafeGreen", "Color line range green.", 0, 0, 255);
        ModuleHoleESP.settingSafeBlue = new ValueNumber("Safe Blue", "SafeBlue", "Color line range blue.", 255, 0, 255);
        ModuleHoleESP.settingSafeAlpha = new ValueNumber("Safe Alpha", "SafeAlpha", "Color line range alpha.", 255, 0, 255);
        ModuleHoleESP.settingSafeOutlineLineSize = new ValueNumber("Safe Line Size", "SafeLineSize", "Line size.", 1.0f, 1.0f, 3.0f);
        ModuleHoleESP.settingSafeOutlineAlpha = new ValueNumber("Safe Line Alpha", "SafeLineAlpha", "Color line range alpha.", 255, 0, 255);
    }
    
    public enum Category
    {
        SAFE, 
        UNSAFE;
    }
}
