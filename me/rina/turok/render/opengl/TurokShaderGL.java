//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.render.opengl;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import me.rina.turok.util.TurokMath;
import java.awt.Color;
import me.rina.turok.util.TurokRect;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import me.rina.turok.hardware.mouse.TurokMouse;
import me.rina.turok.util.TurokDisplay;

public class TurokShaderGL
{
    private static TurokShaderGL INSTANCE;
    private TurokDisplay display;
    private TurokMouse mouse;
    public static final Tessellator TESSELLATOR;
    
    public static void init(final TurokDisplay display, final TurokMouse mouse) {
        TurokShaderGL.INSTANCE = new TurokShaderGL();
        TurokShaderGL.INSTANCE.display = display;
        TurokShaderGL.INSTANCE.mouse = mouse;
    }
    
    public static BufferBuilder start() {
        return TurokShaderGL.TESSELLATOR.getBuffer();
    }
    
    public static void end() {
        TurokShaderGL.TESSELLATOR.draw();
    }
    
    public static void drawOutlineRectFadingMouse(final TurokRect rect, final int radius, final Color color) {
        drawOutlineRectFadingMouse(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), radius, color);
    }
    
    public static void drawOutlineRectFadingMouse(final float x, final float y, final float w, final float h, final int radius, final Color color) {
        final float offset = 0.5f;
        final float vx = x - TurokShaderGL.INSTANCE.mouse.getX();
        final float vy = y - TurokShaderGL.INSTANCE.mouse.getY();
        final float vw = x + w - TurokShaderGL.INSTANCE.mouse.getX();
        final float vh = y + h - TurokShaderGL.INSTANCE.mouse.getY();
        final int valueAlpha = color.getAlpha();
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.shaderMode(7425);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
        TurokGL.lineSize(1.0f);
        TurokGL.prepare(2);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), valueAlpha - TurokMath.clamp(TurokMath.sqrt(vx * vx + vy * vy) / (radius / 100.0f), 0.0f, (float)valueAlpha));
        TurokGL.addVertex(x + offset, y);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), valueAlpha - TurokMath.clamp(TurokMath.sqrt(vx * vx + vh * vh) / (radius / 100.0f), 0.0f, (float)valueAlpha));
        TurokGL.addVertex(x + offset, y + h + offset);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), valueAlpha - TurokMath.clamp(TurokMath.sqrt(vw * vw + vh * vh) / (radius / 100.0f), 0.0f, (float)valueAlpha));
        TurokGL.addVertex(x + w, y + h);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), valueAlpha - TurokMath.clamp(TurokMath.sqrt(vw * vw + vy * vy) / (radius / 100.0f), 0.0f, (float)valueAlpha));
        TurokGL.addVertex(x + w, y);
        TurokGL.release();
    }
    
    public static void drawSolidRectFadingMouse(final TurokRect rect, final int radius, final Color color) {
        drawSolidRectFadingMouse(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), radius, color);
    }
    
    public static void drawSolidRectFadingMouse(final float x, final float y, final float w, final float h, final int radius, final Color color) {
        final float vx = x - TurokShaderGL.INSTANCE.mouse.getX();
        final float vy = y - TurokShaderGL.INSTANCE.mouse.getY();
        final float vw = x + w - TurokShaderGL.INSTANCE.mouse.getX();
        final float vh = y + h - TurokShaderGL.INSTANCE.mouse.getY();
        final int valueAlpha = color.getAlpha();
        TurokGL.pushMatrix();
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.shaderMode(7425);
        TurokGL.prepare(7);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), valueAlpha - TurokMath.clamp(TurokMath.sqrt(vx * vx + vy * vy) / (radius / 100.0f), 0.0f, (float)valueAlpha));
        TurokGL.addVertex(x, y);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), valueAlpha - TurokMath.clamp(TurokMath.sqrt(vx * vx + vh * vh) / (radius / 100.0f), 0.0f, (float)valueAlpha));
        TurokGL.addVertex(x, y + h);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), valueAlpha - TurokMath.clamp(TurokMath.sqrt(vw * vw + vh * vh) / (radius / 100.0f), 0.0f, (float)valueAlpha));
        TurokGL.addVertex(x + w, y + h);
        TurokGL.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), valueAlpha - TurokMath.clamp(TurokMath.sqrt(vw * vw + vy * vy) / (radius / 100.0f), 0.0f, (float)valueAlpha));
        TurokGL.addVertex(x + w, y);
        TurokGL.release();
        TurokGL.popMatrix();
    }
    
    public static void drawLine(final float x, final float y, final float x1, final float y1, final float w, final int[] c) {
        final Color color = new Color(TurokMath.clamp(c[0], 0, 255), TurokMath.clamp(c[1], 0, 255), TurokMath.clamp(c[2], 0, 255), TurokMath.clamp(c[3], 0, 255));
        final float r = (color.getRGB() >> 16 & 0xFF) / 255.0f;
        final float g = (color.getRGB() >> 8 & 0xFF) / 255.0f;
        final float b = (color.getRGB() & 0xFF) / 255.0f;
        final float a = (color.getRGB() >> 24 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(w);
        final BufferBuilder bufferBuilder = start();
        bufferBuilder.begin(6913, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)x, (double)y, 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)x1, (double)y1, 0.0).color(r, g, b, a).endVertex();
        end();
        GlStateManager.disableBlend();
    }
    
    public static void drawSolidRect(final TurokRect rect, final int[] color) {
        drawSolidRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), color);
    }
    
    public static void drawSolidRect(final float x, final float y, final float w, final float h, final int[] c) {
        final Color color = new Color(TurokMath.clamp(c[0], 0, 255), TurokMath.clamp(c[1], 0, 255), TurokMath.clamp(c[2], 0, 255), TurokMath.clamp(c[3], 0, 255));
        final float r = (color.getRGB() >> 16 & 0xFF) / 255.0f;
        final float g = (color.getRGB() >> 8 & 0xFF) / 255.0f;
        final float b = (color.getRGB() & 0xFF) / 255.0f;
        final float a = (color.getRGB() >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final BufferBuilder bufferBuilder = start();
        bufferBuilder.begin(9, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)x, (double)y, 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)x, (double)(y + h), 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)(x + w), (double)(y + h), 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)(x + w), (double)y, 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).color(r, g, b, a).endVertex();
        end();
        GlStateManager.popMatrix();
    }
    
    public static void drawOutlineRect(final TurokRect rect, final int[] color) {
        drawOutlineRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), color);
    }
    
    public static void drawOutlineRect(final float x, final float y, final float w, final float h, final int[] c) {
        final Color color = new Color(TurokMath.clamp(c[0], 0, 255), TurokMath.clamp(c[1], 0, 255), TurokMath.clamp(c[2], 0, 255), TurokMath.clamp(c[3], 0, 255));
        final float r = (color.getRGB() >> 16 & 0xFF) / 255.0f;
        final float g = (color.getRGB() >> 8 & 0xFF) / 255.0f;
        final float b = (color.getRGB() & 0xFF) / 255.0f;
        final float a = (color.getRGB() >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(0.5f);
        GlStateManager.shadeModel(7425);
        final BufferBuilder bufferBuilder = start();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.pos((double)x, (double)y, 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)x, (double)(y + h), 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)(x + w), (double)(y + h), 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)(x + w), (double)y, 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).color(r, g, b, a).endVertex();
        bufferBuilder.pos((double)x, (double)y, 0.0).color(r, g, b, a).endVertex();
        end();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void pushScissor() {
        TurokGL.enable(3089);
    }
    
    public static void pushScissorMatrix() {
        TurokGL.pushMatrix();
        TurokGL.enable(3089);
    }
    
    public static void pushScissorAttrib() {
        TurokGL.pushAttrib(524288);
        TurokGL.enable(3089);
    }
    
    public static void drawScissor(final TurokRect rect) {
        drawScissor(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }
    
    public static void drawScissor(final float x, final float y, final float w, final float h) {
        final int calculatedW = (int)(x + w);
        final int calculatedH = (int)(y + h);
        TurokGL.scissor((int)(x * TurokShaderGL.INSTANCE.display.getScaleFactor()), TurokShaderGL.INSTANCE.display.getHeight() - calculatedH * TurokShaderGL.INSTANCE.display.getScaleFactor(), (int)((calculatedW - x) * TurokShaderGL.INSTANCE.display.getScaleFactor()), (int)((calculatedH - y) * TurokShaderGL.INSTANCE.display.getScaleFactor()));
    }
    
    public static void popScissor() {
        TurokGL.disable(3089);
    }
    
    public static void popScissorMatrix() {
        TurokGL.disable(3089);
        TurokGL.popMatrix();
    }
    
    public static void popScissorAttrib() {
        TurokGL.disable(3089);
        TurokGL.popAttrib();
    }
    
    static {
        TESSELLATOR = Tessellator.getInstance();
    }
}
