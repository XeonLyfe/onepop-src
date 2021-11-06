//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.render.image.management;

import me.rina.turok.render.opengl.TurokRenderGL;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import me.rina.turok.render.opengl.TurokGL;
import java.awt.Color;
import me.rina.turok.render.image.TurokImage;

public class TurokImageManager
{
    public static void render(final TurokImage image, final int x, final int y, final float xx, final float yy, final int w, final int h, final float ww, final float hh, final Color color) {
        TurokGL.enable(3042);
        TurokGL.blendFunc(770, 771);
        TurokGL.enable(3553);
        TurokGL.enable(2884);
        TurokGL.disable(2929);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        Minecraft.getMinecraft().renderEngine.bindTexture(image.getResourceLocation());
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        TurokRenderGL.drawTextureInterpolated((float)x, (float)y, xx, yy, (float)w, (float)h, ww, hh);
        TurokGL.disable(3042);
        TurokGL.disable(3553);
        TurokGL.disable(2884);
        TurokGL.enable(2929);
    }
    
    public static void render(final TurokImage image, final int x, final int y, final int w, final int h, final Color color) {
        render(image, x, y, (float)w, (float)h, 0, 0, 1.0f, 1.0f, color);
    }
}
