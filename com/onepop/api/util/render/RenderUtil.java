//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import java.awt.Color;
import net.minecraft.client.renderer.culling.ICamera;
import me.rina.turok.render.opengl.TurokGL;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import com.onepop.Onepop;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;

public class RenderUtil
{
    public static Tessellator tessellator;
    private static boolean DEPTH;
    private static boolean TEXTURE;
    private static boolean CLEAN;
    private static boolean BIND;
    private static boolean OVERRIDE;
    
    public static void glPrepare(final float lineWidth) {
        RenderUtil.DEPTH = GL11.glIsEnabled(2896);
        RenderUtil.TEXTURE = GL11.glIsEnabled(3042);
        RenderUtil.CLEAN = GL11.glIsEnabled(3553);
        RenderUtil.BIND = GL11.glIsEnabled(2929);
        RenderUtil.OVERRIDE = GL11.glIsEnabled(2848);
        glProcess(RenderUtil.DEPTH, RenderUtil.TEXTURE, RenderUtil.CLEAN, RenderUtil.BIND, RenderUtil.OVERRIDE, lineWidth);
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final Minecraft mc = Onepop.getMinecraft();
        final float scale = 0.02666667f;
        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.player.rotationPitch, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-0.02666667f, -0.02666667f, 0.02666667f);
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }
    
    public static void drawText(final BlockPos pos, final String text) {
        final Minecraft mc = Onepop.getMinecraft();
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, (EntityPlayer)mc.player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(mc.fontRendererObj.getStringWidth(text) / 2.0), 0.0, 0.0);
        mc.fontRendererObj.drawStringWithShadow(text, 0.0f, 0.0f, -5592406);
        GlStateManager.popMatrix();
    }
    
    public static float[][] getBipedRotations(final ModelBiped biped) {
        final float[][] rotations = new float[5][];
        final float[] headRotation = { biped.bipedHead.rotateAngleX, biped.bipedHead.rotateAngleY, biped.bipedHead.rotateAngleZ };
        rotations[0] = headRotation;
        final float[] rightArmRotation = { biped.bipedRightArm.rotateAngleX, biped.bipedRightArm.rotateAngleY, biped.bipedRightArm.rotateAngleZ };
        rotations[1] = rightArmRotation;
        final float[] leftArmRotation = { biped.bipedLeftArm.rotateAngleX, biped.bipedLeftArm.rotateAngleY, biped.bipedLeftArm.rotateAngleZ };
        rotations[2] = leftArmRotation;
        final float[] rightLegRotation = { biped.bipedRightLeg.rotateAngleX, biped.bipedRightLeg.rotateAngleY, biped.bipedRightLeg.rotateAngleZ };
        rotations[3] = rightLegRotation;
        final float[] leftLegRotation = { biped.bipedLeftLeg.rotateAngleX, biped.bipedLeftLeg.rotateAngleY, biped.bipedLeftLeg.rotateAngleZ };
        rotations[4] = leftLegRotation;
        return rotations;
    }
    
    private static void glProcess(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override, final float lineWidth) {
        if (depth) {
            GL11.glDisable(2896);
        }
        if (!texture) {
            GL11.glEnable(3042);
        }
        GL11.glLineWidth(lineWidth);
        if (clean) {
            GL11.glDisable(3553);
        }
        if (bind) {
            GL11.glDisable(2929);
        }
        if (!override) {
            GL11.glEnable(2848);
        }
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
    }
    
    private static void glRefresh(final boolean depth, final boolean texture, final boolean clean, final boolean bind, final boolean override) {
        GlStateManager.depthMask(true);
        if (!override) {
            GL11.glDisable(2848);
        }
        if (bind) {
            GL11.glEnable(2929);
        }
        if (clean) {
            GL11.glEnable(3553);
        }
        if (!texture) {
            GL11.glDisable(3042);
        }
        if (depth) {
            GL11.glEnable(2896);
        }
    }
    
    public static void glRefresh() {
        glRefresh(RenderUtil.DEPTH, RenderUtil.TEXTURE, RenderUtil.CLEAN, RenderUtil.BIND, RenderUtil.OVERRIDE);
    }
    
    public static void prepare(final float line) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        TurokGL.enable(2848);
        TurokGL.hint(3154, 4354);
        TurokGL.lineSize(line);
    }
    
    public static void release() {
        TurokGL.disable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawSolidBlock(final ICamera camera, final BlockPos blockpos, final Color color) {
        drawSolidBlock(camera, blockpos.x, blockpos.y, blockpos.z, 1.0, 1.0, 1.0, color);
    }
    
    public static void drawSolidBlock(final ICamera camera, final double x, final double y, final double z, final Color color) {
        drawSolidBlock(camera, x, y, z, 1.0, 1.0, 1.0, color);
    }
    
    public static void drawSolidBlock(final ICamera camera, final double x, final double y, final double z, final double offsetX, final double offsetY, final double offsetZ, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(x - Onepop.getMinecraft().getRenderManager().viewerPosX, y - Onepop.getMinecraft().getRenderManager().viewerPosY, z - Onepop.getMinecraft().getRenderManager().viewerPosZ, x + offsetX - Onepop.getMinecraft().getRenderManager().viewerPosX, y + offsetY - Onepop.getMinecraft().getRenderManager().viewerPosY, z + offsetZ - Onepop.getMinecraft().getRenderManager().viewerPosZ);
        camera.setPosition(Onepop.getMinecraft().getRenderViewEntity().posX, Onepop.getMinecraft().getRenderViewEntity().posY, Onepop.getMinecraft().getRenderViewEntity().posZ);
        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.minY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.minZ + Onepop.getMinecraft().getRenderManager().viewerPosZ, bb.maxX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.maxY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.maxZ + Onepop.getMinecraft().getRenderManager().viewerPosZ))) {
            prepare(1.0f);
            RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            release();
        }
    }
    
    public static void drawOutlineBlock(final ICamera camera, final BlockPos blockpos, final Color color) {
        drawOutlineBlock(camera, blockpos.x, blockpos.y, blockpos.z, 1.0, 1.0, 1.0, 1.0f, color);
    }
    
    public static void drawOutlineBlock(final ICamera camera, final BlockPos blockpos, final float line, final Color color) {
        drawOutlineBlock(camera, blockpos.x, blockpos.y, blockpos.z, 1.0, 1.0, 1.0, line, color);
    }
    
    public static void drawOutlineBlock(final ICamera camera, final double x, final double y, final double z, final Color color) {
        drawOutlineBlock(camera, x, y, z, 1.0, 1.0, 1.0, 1.0f, color);
    }
    
    public static void drawOutlineBlock(final ICamera camera, final double x, final double y, final double z, final float line, final Color color) {
        drawOutlineBlock(camera, x, y, z, 1.0, 1.0, 1.0, line, color);
    }
    
    public static void drawOutlineBlock(final ICamera camera, final double x, final double y, final double z, final double offsetX, final double offsetY, final double offsetZ, final Color color) {
        drawOutlineBlock(camera, x, y, z, offsetX, offsetY, offsetZ, 1.0f, color);
    }
    
    public static void drawOutlineBlock(final ICamera camera, final double x, final double y, final double z, final double offsetX, final double offsetY, final double offsetZ, final float line, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(x - Onepop.getMinecraft().getRenderManager().viewerPosX, y - Onepop.getMinecraft().getRenderManager().viewerPosY, z - Onepop.getMinecraft().getRenderManager().viewerPosZ, x + offsetX - Onepop.getMinecraft().getRenderManager().viewerPosX, y + offsetY - Onepop.getMinecraft().getRenderManager().viewerPosY, z + offsetZ - Onepop.getMinecraft().getRenderManager().viewerPosZ);
        camera.setPosition(Onepop.getMinecraft().getRenderViewEntity().posX, Onepop.getMinecraft().getRenderViewEntity().posY, Onepop.getMinecraft().getRenderViewEntity().posZ);
        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.minY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.minZ + Onepop.getMinecraft().getRenderManager().viewerPosZ, bb.maxX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.maxY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.maxZ + Onepop.getMinecraft().getRenderManager().viewerPosZ))) {
            prepare(line);
            RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            release();
        }
    }
    
    public static void drawOutlineLegacyBlock(final ICamera camera, final BlockPos blockpos, final Color color) {
        drawOutlineLegacyBlock(camera, blockpos.x, blockpos.y, blockpos.z, 1.0, 1.0, 1.0, 1.0f, color);
    }
    
    public static void drawOutlineLegacyBlock(final ICamera camera, final BlockPos blockpos, final float line, final Color color) {
        drawOutlineLegacyBlock(camera, blockpos.x, blockpos.y, blockpos.z, 1.0, 1.0, 1.0, line, color);
    }
    
    public static void drawOutlineLegacyBlock(final ICamera camera, final double x, final double y, final double z, final Color color) {
        drawOutlineLegacyBlock(camera, x, y, z, 1.0, 1.0, 1.0, 1.0f, color);
    }
    
    public static void drawOutlineLegacyBlock(final ICamera camera, final double x, final double y, final double z, final float line, final Color color) {
        drawOutlineLegacyBlock(camera, x, y, z, 1.0, 1.0, 1.0, line, color);
    }
    
    public static void drawOutlineLegacyBlock(final ICamera camera, final double x, final double y, final double z, final double offsetX, final double offsetY, final double offsetZ, final Color color) {
        drawOutlineLegacyBlock(camera, x, y, z, offsetX, offsetY, offsetZ, 1.0f, color);
    }
    
    public static void drawOutlineLegacyBlock(final ICamera camera, final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ, final float line, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(minX - Onepop.getMinecraft().getRenderManager().viewerPosX, minY - Onepop.getMinecraft().getRenderManager().viewerPosY, minZ - Onepop.getMinecraft().getRenderManager().viewerPosZ, minX + maxX - Onepop.getMinecraft().getRenderManager().viewerPosX, minY + maxY - Onepop.getMinecraft().getRenderManager().viewerPosY, minZ + maxZ - Onepop.getMinecraft().getRenderManager().viewerPosZ);
        camera.setPosition(Onepop.getMinecraft().getRenderViewEntity().posX, Onepop.getMinecraft().getRenderViewEntity().posY, Onepop.getMinecraft().getRenderViewEntity().posZ);
        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.minY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.minZ + Onepop.getMinecraft().getRenderManager().viewerPosZ, bb.maxX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.maxY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.maxZ + Onepop.getMinecraft().getRenderManager().viewerPosZ))) {
            final float red = color.getRed() / 255.0f;
            final float green = color.getGreen() / 255.0f;
            final float blue = color.getBlue() / 255.0f;
            final float alpha = color.getAlpha() / 255.0f;
            prepare(line);
            GL11.glEnable(3042);
            final BufferBuilder bufferBuilder = RenderUtil.tessellator.getBuffer();
            bufferBuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(bb.minX, bb.maxY / 2.0, bb.minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
            RenderUtil.tessellator.draw();
            release();
        }
    }
    
    public static void drawSolidLegacyBlock(final ICamera camera, final BlockPos blockpos, final Color color) {
        drawSolidLegacyBlock(camera, blockpos.x, blockpos.y, blockpos.z, 1.0, 1.0, 1.0, color);
    }
    
    public static void drawSolidLegacyBlock(final ICamera camera, final BlockPos blockpos, final double offsetX, final double offsetY, final double offsetZ, final Color color) {
        drawSolidLegacyBlock(camera, blockpos.x, blockpos.y, blockpos.z, offsetX, offsetY, offsetZ, color);
    }
    
    public static void drawSolidLegacyBlock(final ICamera camera, final double x, final double y, final double z, final Color color) {
        drawSolidLegacyBlock(camera, x, y, z, 1.0, 1.0, 1.0, color);
    }
    
    public static void drawSolidLegacyBlock(final ICamera camera, final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ, final Color color) {
        final AxisAlignedBB bb = new AxisAlignedBB(minX - Onepop.getMinecraft().getRenderManager().viewerPosX, minY - Onepop.getMinecraft().getRenderManager().viewerPosY, minZ - Onepop.getMinecraft().getRenderManager().viewerPosZ, minX + maxX - Onepop.getMinecraft().getRenderManager().viewerPosX, minY + maxY - Onepop.getMinecraft().getRenderManager().viewerPosY, minZ + maxZ - Onepop.getMinecraft().getRenderManager().viewerPosZ);
        camera.setPosition(Onepop.getMinecraft().getRenderViewEntity().posX, Onepop.getMinecraft().getRenderViewEntity().posY, Onepop.getMinecraft().getRenderViewEntity().posZ);
        if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.minY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.minZ + Onepop.getMinecraft().getRenderManager().viewerPosZ, bb.maxX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.maxY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.maxZ + Onepop.getMinecraft().getRenderManager().viewerPosZ))) {
            final float red = color.getRed() / 255.0f;
            final float green = color.getGreen() / 255.0f;
            final float blue = color.getBlue() / 255.0f;
            final float alpha = color.getAlpha() / 255.0f;
            prepare(1.0f);
            final BufferBuilder bufferBuilder = RenderUtil.tessellator.getBuffer();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(minX, minY, minZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(minX, maxY, maxZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(maxX, maxY, maxZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(maxX, maxY, minZ).color(red, green, blue, 0.0f).endVertex();
            bufferBuilder.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
            bufferBuilder.pos(maxX, minY, minZ).color(red, green, blue, 0.0f).endVertex();
            RenderUtil.tessellator.draw();
            release();
        }
    }
    
    static {
        RenderUtil.tessellator = Tessellator.getInstance();
        RenderUtil.DEPTH = GL11.glIsEnabled(2896);
        RenderUtil.TEXTURE = GL11.glIsEnabled(3042);
        RenderUtil.CLEAN = GL11.glIsEnabled(3553);
        RenderUtil.BIND = GL11.glIsEnabled(2929);
        RenderUtil.OVERRIDE = GL11.glIsEnabled(2848);
    }
}
