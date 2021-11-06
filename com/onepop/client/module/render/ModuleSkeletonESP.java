//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.client.model.ModelBiped;
import com.onepop.client.event.render.RenderModelEntityLivingEvent;
import java.util.Iterator;
import com.onepop.api.util.entity.EntityUtil;
import net.minecraft.entity.Entity;
import com.onepop.api.util.render.RenderUtil;
import com.onepop.Onepop;
import java.util.HashMap;
import java.awt.Color;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Skeleton ESP", tag = "SkeletonESP", description = "Half-gods humans in Minecraft doesn't have skeleton, but now yes!", category = ModuleCategory.RENDER)
public class ModuleSkeletonESP extends Module
{
    public static ValueBoolean settingRGB;
    public static ValueNumber settingRed;
    public static ValueNumber settingGreen;
    public static ValueNumber settingBlue;
    public static ValueNumber settingAlpha;
    private final Map<EntityPlayer, float[][]> rotationList;
    private Color color;
    
    public ModuleSkeletonESP() {
        this.rotationList = new HashMap<EntityPlayer, float[][]>();
    }
    
    @Override
    public void onSetting() {
        if (ModuleSkeletonESP.settingRGB.getValue()) {
            ModuleSkeletonESP.settingRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModuleSkeletonESP.settingGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModuleSkeletonESP.settingBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        this.color = new Color(ModuleSkeletonESP.settingRed.getValue().intValue(), ModuleSkeletonESP.settingGreen.getValue().intValue(), ModuleSkeletonESP.settingBlue.getValue().intValue(), ModuleSkeletonESP.settingAlpha.getValue().intValue());
    }
    
    @Override
    public void onDisable() {
        this.rotationList.clear();
    }
    
    @Override
    public void onRender3D() {
        RenderUtil.glPrepare(1.5f);
        for (final EntityPlayer entities : ModuleSkeletonESP.mc.world.playerEntities) {
            if (entities != null && entities != ModuleSkeletonESP.mc.getRenderViewEntity() && entities.isEntityAlive() && !entities.isPlayerSleeping() && this.rotationList.get(entities) != null && ModuleSkeletonESP.mc.player.getDistanceSqToEntity((Entity)entities) < 2500.0) {
                this.renderSkeleton(entities, this.rotationList.get(entities), EntityUtil.getColor(entities, this.color));
            }
        }
        RenderUtil.glRefresh();
    }
    
    @Listener
    public void onRenderModel(final RenderModelEntityLivingEvent event) {
        if (event.getEntityLivingBase() instanceof EntityPlayer && event.getModelBase() instanceof ModelBiped) {
            final ModelBiped biped = (ModelBiped)event.getModelBase();
            final EntityPlayer player = (EntityPlayer)event.getEntityLivingBase();
            final float[][] rotations = RenderUtil.getBipedRotations(biped);
            this.rotationList.put(player, rotations);
        }
    }
    
    private void renderSkeleton(final EntityPlayer player, final float[][] rotations, final Color color) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        final Vec3d interpolatedVec = EntityUtil.getInterpolatedLinearVec((Entity)player, ModuleSkeletonESP.mc.getRenderPartialTicks());
        final double pX = interpolatedVec.xCoord - ModuleSkeletonESP.mc.getRenderManager().renderPosX;
        final double pY = interpolatedVec.yCoord - ModuleSkeletonESP.mc.getRenderManager().renderPosY;
        final double pZ = interpolatedVec.zCoord - ModuleSkeletonESP.mc.getRenderManager().renderPosZ;
        GlStateManager.translate(pX, pY, pZ);
        GlStateManager.rotate(-player.renderYawOffset, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0, 0.0, player.isSneaking() ? -0.235 : 0.0);
        final float sneak = player.isSneaking() ? 0.6f : 0.75f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.125, (double)sneak, 0.0);
        if (rotations[3][0] != 0.0f) {
            GlStateManager.rotate(rotations[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        if (rotations[3][1] != 0.0f) {
            GlStateManager.rotate(rotations[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (rotations[3][2] != 0.0f) {
            GlStateManager.rotate(rotations[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, (double)(-sneak), 0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.125, (double)sneak, 0.0);
        if (rotations[4][0] != 0.0f) {
            GlStateManager.rotate(rotations[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        if (rotations[4][1] != 0.0f) {
            GlStateManager.rotate(rotations[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (rotations[4][2] != 0.0f) {
            GlStateManager.rotate(rotations[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, (double)(-sneak), 0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.translate(0.0, 0.0, player.isSneaking() ? 0.25 : 0.0);
        GlStateManager.pushMatrix();
        double sneakOffset = 0.0;
        if (player.isSneaking()) {
            sneakOffset = -0.05;
        }
        GlStateManager.translate(0.0, sneakOffset, player.isSneaking() ? -0.01725 : 0.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.375, sneak + 0.55, 0.0);
        if (rotations[1][0] != 0.0f) {
            GlStateManager.rotate(rotations[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        if (rotations[1][1] != 0.0f) {
            GlStateManager.rotate(rotations[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (rotations[1][2] != 0.0f) {
            GlStateManager.rotate(-rotations[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, -0.5, 0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.375, sneak + 0.55, 0.0);
        if (rotations[2][0] != 0.0f) {
            GlStateManager.rotate(rotations[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        if (rotations[2][1] != 0.0f) {
            GlStateManager.rotate(rotations[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (rotations[2][2] != 0.0f) {
            GlStateManager.rotate(-rotations[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, -0.5, 0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, sneak + 0.55, 0.0);
        if (rotations[0][0] != 0.0f) {
            GlStateManager.rotate(rotations[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
        }
        GlStateManager.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.3, 0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
        GlStateManager.rotate(player.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
        if (player.isSneaking()) {
            sneakOffset = -0.16175;
        }
        GlStateManager.translate(0.0, sneakOffset, player.isSneaking() ? -0.48025 : 0.0);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, (double)sneak, 0.0);
        GlStateManager.glBegin(3);
        GL11.glVertex3d(-0.125, 0.0, 0.0);
        GL11.glVertex3d(0.125, 0.0, 0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, (double)sneak, 0.0);
        GlStateManager.glBegin(3);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.55, 0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0, sneak + 0.55, 0.0);
        GlStateManager.glBegin(3);
        GL11.glVertex3d(-0.375, 0.0, 0.0);
        GL11.glVertex3d(0.375, 0.0, 0.0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }
    
    static {
        ModuleSkeletonESP.settingRGB = new ValueBoolean("RGB", "RGB", "RGB effect.", false);
        ModuleSkeletonESP.settingRed = new ValueNumber("Red", "Red", "Color red.", 190, 0, 255);
        ModuleSkeletonESP.settingGreen = new ValueNumber("Green", "Green", "Color green.", 190, 0, 255);
        ModuleSkeletonESP.settingBlue = new ValueNumber("Blue", "Blue", "Color blue.", 190, 0, 255);
        ModuleSkeletonESP.settingAlpha = new ValueNumber("Alpha", "Alpha", "Color alpha.", 200, 0, 255);
    }
}
