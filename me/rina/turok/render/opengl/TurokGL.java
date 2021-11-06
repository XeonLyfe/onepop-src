// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.render.opengl;

import org.lwjgl.opengl.GL11;

public class TurokGL
{
    public static void scissor(final int x, final int y, final int w, final int h) {
        GL11.glScissor(x, y, w, h);
    }
    
    public static void pushMatrix() {
        GL11.glPushMatrix();
    }
    
    public static void popMatrix() {
        GL11.glPopMatrix();
    }
    
    public static void translate(final float x, final float y, final float z) {
        GL11.glTranslatef(x, y, z);
    }
    
    public static void translate(final double x, final double y, final double z) {
        GL11.glTranslated(x, y, z);
    }
    
    public static void translate(final float x, final float y) {
        GL11.glTranslatef(x, y, 0.0f);
    }
    
    public static void translate(final double x, final double y) {
        GL11.glTranslated(x, y, 0.0);
    }
    
    public static void rotate(final float angle, final float x, final float y, final float z) {
        GL11.glRotatef(angle, x, y, z);
    }
    
    public static void scale(final float x, final float y, final float z) {
        GL11.glScalef(x, y, z);
    }
    
    public static void hint(final int target, final int target1) {
        GL11.glHint(target, target1);
    }
    
    public static void enable(final int state) {
        GL11.glEnable(state);
    }
    
    public static void disable(final int state) {
        GL11.glDisable(state);
    }
    
    public static void blendFunc(final int a, final int b) {
        GL11.glBlendFunc(a, b);
    }
    
    public static void prepare(final int mode) {
        GL11.glBegin(mode);
    }
    
    public static void release() {
        GL11.glEnd();
    }
    
    public static void color(final float r, final float g, final float b, final float a) {
        GL11.glColor4f(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
    }
    
    public static void color(final float r, final float g, final float b) {
        GL11.glColor3f(r / 255.0f, g / 255.0f, b / 255.0f);
    }
    
    public static void lineSize(final float size) {
        GL11.glLineWidth(size);
    }
    
    public static void pointSize(final float size) {
        GL11.glPointSize(size);
    }
    
    public static void addVertex(final float x, final float y, final float z) {
        GL11.glVertex3f(x, y, z);
    }
    
    public static void addVertex(final float x, final float y) {
        GL11.glVertex2f(x, y);
    }
    
    public static void sewTexture(final float s, final float t, final float r) {
        GL11.glTexCoord3f(s, t, r);
    }
    
    public static void sewTexture(final float s, final float t) {
        GL11.glTexCoord2f(s, t);
    }
    
    public static void polygonOffset(final float factor, final float units) {
        GL11.glPolygonOffset(factor, units);
    }
    
    public static void polygonOffset(final double factor, final double units) {
        GL11.glPolygonOffset((float)factor, (float)units);
    }
    
    public static void polygonOffset(final int factor, final int units) {
        GL11.glPolygonOffset((float)factor, (float)units);
    }
    
    public static void polygonMode(final int face, final int mode) {
        GL11.glPolygonMode(face, mode);
    }
    
    public static void shaderMode(final int mode) {
        GL11.glShadeModel(mode);
    }
    
    public static void pushAttrib(final int mask) {
        GL11.glPushAttrib(mask);
    }
    
    public static void popAttrib() {
        GL11.glPopAttrib();
    }
    
    public static void depthMask(final boolean flag) {
        GL11.glDepthMask(flag);
    }
}
