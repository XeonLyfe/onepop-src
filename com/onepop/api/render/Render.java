//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.RenderGlobal;
import com.onepop.api.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import com.onepop.Onepop;
import java.awt.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.culling.ICamera;
import com.onepop.api.render.impl.RenderType;

public class Render
{
    private RenderType type;
    
    public Render(final RenderType type) {
        this.type = type;
    }
    
    public void setType(final RenderType type) {
        this.type = type;
    }
    
    public RenderType getType() {
        return this.type;
    }
    
    public void onRender(final ICamera frustum, final BlockPos pos, final double x, final double y, final double z, final float line, final Color solid, final Color outline) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.x - Onepop.getMinecraft().getRenderManager().viewerPosX, pos.y - Onepop.getMinecraft().getRenderManager().viewerPosY, pos.z - Onepop.getMinecraft().getRenderManager().viewerPosZ, pos.x + x - Onepop.getMinecraft().getRenderManager().viewerPosX, pos.y + y - Onepop.getMinecraft().getRenderManager().viewerPosY, pos.z + z - Onepop.getMinecraft().getRenderManager().viewerPosZ);
        if (Onepop.getMinecraft().getRenderViewEntity() == null) {
            return;
        }
        frustum.setPosition(Onepop.getMinecraft().getRenderViewEntity().posX, Onepop.getMinecraft().getRenderViewEntity().posY, Onepop.getMinecraft().getRenderViewEntity().posZ);
        if (frustum.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.minY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.minZ + Onepop.getMinecraft().getRenderManager().viewerPosZ, bb.maxX + Onepop.getMinecraft().getRenderManager().viewerPosX, bb.maxY + Onepop.getMinecraft().getRenderManager().viewerPosY, bb.maxZ + Onepop.getMinecraft().getRenderManager().viewerPosZ))) {
            this.doDraw(bb, line, solid, outline);
        }
    }
    
    protected void doDraw(final AxisAlignedBB bb, final float line, final Color solid, final Color outline) {
        switch (this.type) {
            case NORMAL: {
                this.doDrawNormal(bb, line, solid, outline);
                break;
            }
            case FADING: {
                this.drawGradient(bb, line, solid, outline);
                break;
            }
        }
    }
    
    protected void doDrawNormal(final AxisAlignedBB bb, final float line, final Color solid, final Color outline) {
        RenderUtil.prepare(1.0f);
        RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, solid.getRed() / 255.0f, solid.getGreen() / 255.0f, solid.getBlue() / 255.0f, solid.getAlpha() / 255.0f);
        RenderUtil.release();
        RenderUtil.prepare(line);
        RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, outline.getRed() / 255.0f, outline.getGreen() / 255.0f, outline.getBlue() / 255.0f, outline.getAlpha() / 255.0f);
        RenderUtil.release();
    }
    
    protected void drawGradient(final AxisAlignedBB bb, final float line, final Color solid, final Color outline) {
        RenderUtil.prepare(1.0f);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder solidBuilder = tessellator.getBuffer();
        final float solidRed = solid.getRed() / 255.0f;
        final float solidGreen = solid.getGreen() / 255.0f;
        final float solidBlue = solid.getBlue() / 255.0f;
        final float solidAlpha = solid.getAlpha() / 255.0f;
        final double minX = bb.minX;
        final double minY = bb.minY;
        final double minZ = bb.minZ;
        final double maxX = bb.maxX;
        final double maxY = bb.maxY + 0.5;
        final double maxZ = bb.maxZ;
        GL11.glShadeModel(7425);
        GL11.glDisable(2884);
        solidBuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        solidBuilder.pos(minX, minY, minZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(minX, minY, minZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(minX, minY, minZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(minX, minY, maxZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(minX, maxY, minZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(minX, maxY, maxZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(minX, maxY, maxZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(minX, minY, maxZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(maxX, maxY, maxZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(maxX, minY, maxZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(maxX, minY, maxZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(maxX, minY, minZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(maxX, maxY, maxZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(maxX, maxY, minZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(maxX, maxY, minZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(maxX, minY, minZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(minX, maxY, minZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(minX, minY, minZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(minX, minY, minZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(maxX, minY, minZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(minX, minY, maxZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(maxX, minY, maxZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(maxX, minY, maxZ).color(solidRed, solidGreen, solidBlue, solidAlpha).endVertex();
        solidBuilder.pos(minX, maxY, minZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(minX, maxY, minZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(minX, maxY, maxZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(maxX, maxY, minZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(maxX, maxY, maxZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(maxX, maxY, maxZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        solidBuilder.pos(maxX, maxY, maxZ).color(solidRed, solidGreen, solidBlue, 0.0f).endVertex();
        tessellator.draw();
        GL11.glEnable(2884);
        RenderUtil.release();
    }
}
