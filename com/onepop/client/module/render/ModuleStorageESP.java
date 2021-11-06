//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import java.util.Iterator;
import com.onepop.api.util.render.RenderUtil;
import java.awt.Color;
import net.minecraft.tileentity.TileEntity;
import com.onepop.api.util.client.NullUtil;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Storage ESP", tag = "StorageESP", description = "Render storages close of you.", category = ModuleCategory.RENDER)
public class ModuleStorageESP extends Module
{
    public static ValueBoolean settingChunk;
    public static ValueNumber settingRange;
    public static ValueNumber settingAlpha;
    public static ValueNumber settingLineAlpha;
    public static ValueNumber settingLineSize;
    
    @Override
    public void onSetting() {
        ModuleStorageESP.settingRange.setEnabled(!ModuleStorageESP.settingChunk.getValue());
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        for (final TileEntity tileEntities : ModuleStorageESP.mc.world.loadedTileEntityList) {
            if (tileEntities == null) {
                return;
            }
            if (!ModuleStorageESP.settingChunk.getValue() && ModuleStorageESP.mc.player.getDistance((double)tileEntities.getPos().x, (double)tileEntities.getPos().y, (double)tileEntities.getPos().z) > ModuleStorageESP.settingRange.getValue().intValue()) {
                continue;
            }
            if (!this.doAccept(tileEntities)) {
                continue;
            }
            final int alpha = ModuleStorageESP.settingAlpha.getValue().intValue();
            final int lineAlpha = ModuleStorageESP.settingLineAlpha.getValue().intValue();
            final float lineSize = (float)ModuleStorageESP.settingLineSize.getValue().intValue();
            final int color = this.getColor(tileEntities);
            final int r = (color & 0xFF0000) >> 16;
            final int g = (color & 0xFF00) >> 8;
            final int b = color & 0xFF;
            final Color solid = new Color(r, g, b, alpha);
            final Color outline = new Color(r, g, b, lineAlpha);
            RenderUtil.drawSolidBlock(this.camera, tileEntities.getPos(), solid);
            RenderUtil.drawOutlineBlock(this.camera, tileEntities.getPos(), lineSize, outline);
        }
    }
    
    public boolean doAccept(final TileEntity tile) {
        boolean accepted = false;
        if (tile instanceof TileEntityChest) {
            accepted = true;
        }
        if (tile instanceof TileEntityBrewingStand) {
            accepted = true;
        }
        if (tile instanceof TileEntityEnderChest) {
            accepted = true;
        }
        if (tile instanceof TileEntityShulkerBox) {
            accepted = true;
        }
        if (tile instanceof TileEntityDropper) {
            accepted = true;
        }
        if (tile instanceof TileEntityHopper) {
            accepted = true;
        }
        if (tile instanceof TileEntityDispenser) {
            accepted = true;
        }
        if (tile instanceof TileEntityFurnace) {
            accepted = true;
        }
        return accepted;
    }
    
    public int getColor(final TileEntity tile) {
        if (tile instanceof TileEntityChest) {
            return new Color(255, 162, 0).getRGB();
        }
        if (tile instanceof TileEntityDropper) {
            return -11645362;
        }
        if (tile instanceof TileEntityDispenser) {
            return -11645362;
        }
        if (tile instanceof TileEntityHopper) {
            return -11645362;
        }
        if (tile instanceof TileEntityFurnace) {
            return -13816531;
        }
        if (tile instanceof TileEntityBrewingStand) {
            return -15222318;
        }
        if (tile instanceof TileEntityEnderChest) {
            return new Color(110, 0, 189).getRGB();
        }
        if (tile instanceof TileEntityShulkerBox) {
            final TileEntityShulkerBox shulkerBox = (TileEntityShulkerBox)tile;
            return 0xFF000000 | shulkerBox.func_190592_s().func_193350_e();
        }
        return -1;
    }
    
    static {
        ModuleStorageESP.settingChunk = new ValueBoolean("Chunk", "Chunk", "Render all in chunk.", false);
        ModuleStorageESP.settingRange = new ValueNumber("Range", "Range", "Range for esp.", 500, 0, 1000);
        ModuleStorageESP.settingAlpha = new ValueNumber("Alpha", "Alpha", "Sets alpha.", 150, 0, 255);
        ModuleStorageESP.settingLineAlpha = new ValueNumber("Line Alpha", "LineAlpha", "Sets line alpha.", 255, 0, 255);
        ModuleStorageESP.settingLineSize = new ValueNumber("Line Size", "LineSize", "Sets line size.", 1.0f, 1.0f, 5.0f);
    }
}
