//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.client.event.client.RunTickEvent;
import java.util.Iterator;
import com.onepop.api.util.render.RenderUtil;
import net.minecraft.init.Blocks;
import com.onepop.api.util.world.BlockUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import com.onepop.api.util.client.NullUtil;
import com.onepop.Onepop;
import java.util.ArrayList;
import java.awt.Color;
import net.minecraft.util.math.BlockPos;
import java.util.List;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Burrow ESP", tag = "BurrowESP", description = "ESP 3vt.", category = ModuleCategory.RENDER)
public class ModuleBurrowESP extends Module
{
    public static ValueBoolean settingMessage;
    public static ValueBoolean settingRGB;
    public static ValueNumber settingRed;
    public static ValueNumber settingGreen;
    public static ValueNumber settingBlue;
    public static ValueNumber settingAlpha;
    public static ValueNumber settingLineAlpha;
    public static ValueNumber settingLineSize;
    private final List<BlockPos> confirmList;
    Color solidColor;
    Color outlineColor;
    
    public ModuleBurrowESP() {
        this.confirmList = new ArrayList<BlockPos>();
        this.solidColor = new Color(ModuleBurrowESP.settingRed.getValue().intValue(), ModuleBurrowESP.settingGreen.getValue().intValue(), ModuleBurrowESP.settingBlue.getValue().intValue(), ModuleBurrowESP.settingAlpha.getValue().intValue());
        this.outlineColor = new Color(this.solidColor.getRed(), this.solidColor.getGreen(), this.solidColor.getBlue(), ModuleBurrowESP.settingLineAlpha.getValue().intValue());
    }
    
    @Override
    public void onSetting() {
        if (ModuleBurrowESP.settingRGB.getValue()) {
            ModuleBurrowESP.settingRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModuleBurrowESP.settingGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModuleBurrowESP.settingBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        this.solidColor = new Color(ModuleBurrowESP.settingRed.getValue().intValue(), ModuleBurrowESP.settingGreen.getValue().intValue(), ModuleBurrowESP.settingBlue.getValue().intValue(), ModuleBurrowESP.settingAlpha.getValue().intValue());
        this.outlineColor = new Color(this.solidColor.getRed(), this.solidColor.getGreen(), this.solidColor.getBlue(), ModuleBurrowESP.settingLineAlpha.getValue().intValue());
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        for (final EntityPlayer entities : ModuleBurrowESP.mc.world.playerEntities) {
            if (ModuleBurrowESP.mc.player.getDistanceToEntity((Entity)entities) <= 6.0f) {
                if (ModuleBurrowESP.mc.player == entities) {
                    continue;
                }
                final BlockPos targetPosition = new BlockPos(Math.floor(entities.posX), Math.floor(entities.posY + 0.5), Math.floor(entities.posZ));
                final boolean targetFlag = !BlockUtil.isAir(targetPosition) && (BlockUtil.getBlock(targetPosition) == Blocks.OBSIDIAN || BlockUtil.getBlock(targetPosition) == Blocks.ANVIL || BlockUtil.getBlock(targetPosition) == Blocks.ENDER_CHEST);
                if (!targetFlag) {
                    continue;
                }
                RenderUtil.drawSolidBlock(this.camera, targetPosition, this.solidColor);
                RenderUtil.drawOutlineBlock(this.camera, targetPosition, ModuleBurrowESP.settingLineSize.getValue().floatValue(), this.outlineColor);
            }
        }
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (!ModuleBurrowESP.settingMessage.getValue()) {
            this.confirmList.clear();
            return;
        }
        for (final EntityPlayer entities : ModuleBurrowESP.mc.world.playerEntities) {
            if (ModuleBurrowESP.mc.player.getDistanceToEntity((Entity)entities) <= 6.0f) {
                if (ModuleBurrowESP.mc.player == entities) {
                    continue;
                }
                final BlockPos targetPosition = new BlockPos(Math.floor(entities.posX), Math.floor(entities.posY + 0.5), Math.floor(entities.posZ));
                final boolean targetFlag = !BlockUtil.isAir(targetPosition) && (BlockUtil.getBlock(targetPosition) == Blocks.OBSIDIAN || BlockUtil.getBlock(targetPosition) == Blocks.ANVIL || BlockUtil.getBlock(targetPosition) == Blocks.ENDER_CHEST);
                if (targetFlag) {
                    if (this.confirmList.contains(targetPosition)) {
                        continue;
                    }
                    this.print(entities.getName() + " burrowed!");
                    this.confirmList.add(targetPosition);
                }
                else {
                    this.confirmList.remove(targetPosition);
                }
            }
        }
    }
    
    static {
        ModuleBurrowESP.settingMessage = new ValueBoolean("Message", "Message", "Message if an player uses burrow!", true);
        ModuleBurrowESP.settingRGB = new ValueBoolean("RGB", "RGB", "RGB effect.", false);
        ModuleBurrowESP.settingRed = new ValueNumber("Red", "Red", "Color line range red.", 255, 0, 255);
        ModuleBurrowESP.settingGreen = new ValueNumber("Green", "Green", "Color line range green.", 0, 0, 255);
        ModuleBurrowESP.settingBlue = new ValueNumber("Blue", "Blue", "Color line range blue.", 255, 0, 255);
        ModuleBurrowESP.settingAlpha = new ValueNumber("Alpha", "Alpha", "Color line range alpha.", 255, 0, 255);
        ModuleBurrowESP.settingLineAlpha = new ValueNumber("Line Alpha", "LineAlpha", "Line alpha.", 200, 0, 255);
        ModuleBurrowESP.settingLineSize = new ValueNumber("Line Size", "LineSize", "The size of line!", 1.0f, 1.0f, 5.0f);
    }
}
