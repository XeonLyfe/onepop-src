//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.render.opengl;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager;
import me.rina.turok.util.TurokRect;

public class TurokRenderGL
{
    public static void color(final int r, final int g, final int b, final int a) {
        TurokGL.color(r + 0.0f, g + 0.0f, b + 0.0f, a + 0.0f);
    }
    
    public static void drawTexture(final float x, final float y, final float width, final float height) {
        TurokGL.prepare(7);
        TurokGL.sewTexture(0.0f, 0.0f);
        TurokGL.addVertex(x, y);
        TurokGL.sewTexture(0.0f, 1.0f);
        TurokGL.addVertex(x, y + height);
        TurokGL.sewTexture(1.0f, 1.0f);
        TurokGL.addVertex(x + width, y + height);
        TurokGL.sewTexture(1.0f, 0.0f);
        TurokGL.addVertex(x + width, y);
        TurokGL.release();
    }
    
    public static void drawTextureInterpolated(final float x, final float y, final float xx, final float yy, final float width, final float height, final float ww, final float hh) {
        TurokGL.prepare(7);
        TurokGL.sewTexture(0.0f + xx, 0.0f + hh);
        TurokGL.addVertex(x, y);
        TurokGL.sewTexture(0.0f + xx, 1.0f + hh);
        TurokGL.addVertex(x, y + height);
        TurokGL.sewTexture(1.0f + ww, 1.0f + hh);
        TurokGL.addVertex(x + width, y + height);
        TurokGL.sewTexture(1.0f + ww, 0.0f + hh);
        TurokGL.addVertex(x + width, y);
        TurokGL.release();
    }
    
    public static void drawUpTriangle(final float x, final float y, final float width, final float height, final int offsetX) {
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.prepare(6);
        TurokGL.addVertex(x + width, y + height);
        TurokGL.addVertex(x + width, y);
        TurokGL.addVertex(x - offsetX, y);
        TurokGL.release();
    }
    
    public static void drawDownTriangle(final float x, final float y, final float width, final float height, final int offsetX) {
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.prepare(6);
        TurokGL.addVertex(x, y);
        TurokGL.addVertex(x, y + height);
        TurokGL.addVertex(x + width + offsetX, y + height);
        TurokGL.release();
    }
    
    public static void drawArc(final float cx, final float cy, final float r, final float start_angle, final float end_angle, final float num_segments) {
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.prepare(4);
        for (int i = (int)(num_segments / (360.0f / start_angle)) + 1; i <= num_segments / (360.0f / end_angle); ++i) {
            final float previousAngle = (float)(6.283185307179586 * (i - 1) / num_segments);
            final float angle = (float)(6.283185307179586 * i / num_segments);
            TurokGL.addVertex(cx, cy);
            TurokGL.addVertex((float)(cx + Math.cos(angle) * r), (float)(cy + Math.sin(angle) * r));
            TurokGL.addVertex((float)(cx + Math.cos(previousAngle) * r), (float)(cy + Math.sin(previousAngle) * r));
        }
        TurokGL.release();
    }
    
    public static void drawArc(final float x, final float y, final float radius) {
        drawArc(x, y, radius, 0.0f, 360.0f, 40.0f);
    }
    
    public static void drawArcOutline(final float cx, final float cy, final float r, final float start_angle, final float end_angle, final float num_segments) {
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.prepare(2);
        for (int i = (int)(num_segments / (360.0f / start_angle)) + 1; i <= num_segments / (360.0f / end_angle); ++i) {
            final float angle = (float)(6.283185307179586 * i / num_segments);
            TurokGL.addVertex((float)(cx + Math.cos(angle) * r), (float)(cy + Math.sin(angle) * r));
        }
        TurokGL.release();
    }
    
    public static void drawArcOutline(final float x, final float y, final float radius) {
        drawArcOutline(x, y, radius, 0.0f, 360.0f, 40.0f);
    }
    
    public static void drawOutlineRect(final float x, final float y, final float width, final float height) {
        TurokGL.pushMatrix();
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.lineSize(1.0f);
        TurokGL.prepare(3);
        TurokGL.addVertex(x, y);
        TurokGL.addVertex(x, y + height);
        TurokGL.addVertex(x + width, y + height);
        TurokGL.addVertex(x + width, y);
        TurokGL.addVertex(x, y);
        TurokGL.addVertex(x, y);
        TurokGL.release();
        TurokGL.popMatrix();
    }
    
    public static void drawOutlineRect(final TurokRect rect) {
        drawOutlineRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }
    
    public static void drawOutlineRoundedRect(final float x, final float y, final float width, final float height, final float radius, final float dR, final float dG, final float dB, final float dA, final float line_width) {
        drawRoundedRect(x, y, width, height, radius);
        TurokGL.color(dR, dG, dB, dA);
        drawRoundedRect(x + line_width, y + line_width, width - line_width * 2.0f, height - line_width * 2.0f, radius);
    }
    
    public static void drawRoundedRect(final float x, final float y, final float width, final float height, final float radius) {
        TurokGL.pushMatrix();
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        drawArc(x + width - radius, y + height - radius, radius, 0.0f, 90.0f, 16.0f);
        drawArc(x + radius, y + height - radius, radius, 90.0f, 180.0f, 16.0f);
        drawArc(x + radius, y + radius, radius, 180.0f, 270.0f, 16.0f);
        drawArc(x + width - radius, y + radius, radius, 270.0f, 360.0f, 16.0f);
        TurokGL.prepare(4);
        TurokGL.addVertex(x + width - radius, y);
        TurokGL.addVertex(x + radius, y);
        TurokGL.addVertex(x + width - radius, y + radius);
        TurokGL.addVertex(x + width - radius, y + radius);
        TurokGL.addVertex(x + radius, y);
        TurokGL.addVertex(x + radius, y + radius);
        TurokGL.addVertex(x + width, y + radius);
        TurokGL.addVertex(x, y + radius);
        TurokGL.addVertex(x, y + height - radius);
        TurokGL.addVertex(x + width, y + radius);
        TurokGL.addVertex(x, y + height - radius);
        TurokGL.addVertex(x + width, y + height - radius);
        TurokGL.addVertex(x + width - radius, y + height - radius);
        TurokGL.addVertex(x + radius, y + height - radius);
        TurokGL.addVertex(x + width - radius, y + height);
        TurokGL.addVertex(x + width - radius, y + height);
        TurokGL.addVertex(x + radius, y + height - radius);
        TurokGL.addVertex(x + radius, y + height);
        TurokGL.release();
        TurokGL.popMatrix();
    }
    
    public static void drawRoundedRect(final TurokRect rect, final float size) {
        drawRoundedRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), size);
    }
    
    public static void drawSolidRect(final float x, final float y, final float width, final float height) {
        TurokGL.pushMatrix();
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.prepare(7);
        TurokGL.addVertex(x, y);
        TurokGL.addVertex(x, y + height);
        TurokGL.addVertex(x + width, y + height);
        TurokGL.addVertex(x + width, y);
        TurokGL.release();
        TurokGL.popMatrix();
    }
    
    public static void drawSolidRect(final int x, final int y, final int width, final int height) {
        drawSolidRect((float)x, (float)y, (float)width, (float)height);
    }
    
    public static void drawSolidRect(final TurokRect rect) {
        drawSolidRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }
    
    public static void drawLine(final int x, final int y, final int x1, final int xy, final float line) {
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.lineSize(line);
        TurokGL.prepare(2848);
        TurokGL.addVertex((float)x, (float)y);
        TurokGL.addVertex((float)x1, (float)xy);
        TurokGL.release();
    }
    
    public static void drawLine3D(final double x, final double y, final double z, final double x1, final double y1, final double z1, final int r, final int g, final int b, final int a, final float line) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        TurokGL.lineSize(line);
        TurokGL.enable(2848);
        TurokGL.hint(3154, 4354);
        GlStateManager.disableDepth();
        TurokGL.enable(34383);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos(x, y, z).color(r, g, b, a).endVertex();
        bufferBuilder.pos(x1, y1, z1).color(r, g, b, a).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        TurokGL.disable(2848);
        GlStateManager.enableDepth();
        TurokGL.disable(34383);
        TurokGL.disable(3042);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    
    public static void prepareOverlay() {
        TurokGL.pushMatrix();
        TurokGL.enable(3553);
        TurokGL.enable(3042);
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.popMatrix();
    }
    
    public static void releaseOverlay() {
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }
    
    public static void prepare3D(final float size) {
        TurokGL.blendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(size);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
    }
    
    public static void release3D() {
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }
}
