//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.render.font.management;

import net.minecraft.client.Minecraft;
import me.rina.turok.render.opengl.TurokGL;
import org.apache.commons.lang3.CharUtils;
import me.rina.turok.util.TurokMath;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import me.rina.turok.render.font.TurokFont;

public class TurokFontManager
{
    public static void render(final TurokFont fontRenderer, final String string, final float x, final float y, final boolean shadow, final int factor) {
        final float[] currentColor360 = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int cycleColor = Color.HSBtoRGB(currentColor360[0], 1.0f, 1.0f);
        Color currentColor361 = new Color(cycleColor >> 16 & 0xFF, cycleColor >> 8 & 0xFF, cycleColor & 0xFF);
        final float hueIncrement = 1.0f / factor;
        float currentHue = Color.RGBtoHSB(currentColor361.getRed(), currentColor361.getGreen(), currentColor361.getBlue(), null)[0];
        final float saturation = Color.RGBtoHSB(currentColor361.getRed(), currentColor361.getGreen(), currentColor361.getBlue(), null)[1];
        final float brightness = Color.RGBtoHSB(currentColor361.getRed(), currentColor361.getGreen(), currentColor361.getBlue(), null)[2];
        float currentWidth = 0.0f;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        ChatFormatting colorCache = ChatFormatting.GRAY;
        for (int i = 0; i < string.length(); ++i) {
            final char currentChar = string.charAt(i);
            final char nextChar = string.charAt(TurokMath.clamp(i + 1, 0, string.length() - 1));
            final String nextFormatting = String.valueOf(currentChar) + nextChar;
            if (nextFormatting.equals("§r") && !shouldRainbow) {
                shouldRainbow = true;
            }
            else if (String.valueOf(currentChar).equals("§")) {
                shouldRainbow = false;
            }
            if (shouldContinue) {
                shouldContinue = false;
            }
            else if (String.valueOf(currentChar).equals("§")) {
                shouldContinue = true;
                colorCache = ChatFormatting.getByChar(CharUtils.toChar(nextFormatting.replaceAll("§", "")));
            }
            else {
                render(fontRenderer, (shouldRainbow ? "" : colorCache) + String.valueOf(currentChar), x + currentWidth, y, shadow, currentColor361);
                currentWidth += getStringWidth(fontRenderer, String.valueOf(currentChar));
                if (!String.valueOf(currentChar).equals(" ")) {
                    currentColor361 = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
                    currentHue += hueIncrement;
                }
            }
        }
    }
    
    public static void render(final String string, final float x, final float y, final boolean shadow, final int factor) {
        final float[] currentColor360 = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int cycleColor = Color.HSBtoRGB(currentColor360[0], 1.0f, 1.0f);
        Color currentColor361 = new Color(cycleColor >> 16 & 0xFF, cycleColor >> 8 & 0xFF, cycleColor & 0xFF);
        final float hueIncrement = 1.0f / factor;
        float currentHue = Color.RGBtoHSB(currentColor361.getRed(), currentColor361.getGreen(), currentColor361.getBlue(), null)[0];
        final float saturation = Color.RGBtoHSB(currentColor361.getRed(), currentColor361.getGreen(), currentColor361.getBlue(), null)[1];
        final float brightness = Color.RGBtoHSB(currentColor361.getRed(), currentColor361.getGreen(), currentColor361.getBlue(), null)[2];
        float currentWidth = 0.0f;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        ChatFormatting colorCache = ChatFormatting.GRAY;
        for (int i = 0; i < string.length(); ++i) {
            final char currentChar = string.charAt(i);
            final char nextChar = string.charAt(TurokMath.clamp(i + 1, 0, string.length() - 1));
            final String nextFormatting = String.valueOf(currentChar) + nextChar;
            if (nextFormatting.equals("§r") && !shouldRainbow) {
                shouldRainbow = true;
            }
            else if (String.valueOf(currentChar).equals("§")) {
                shouldRainbow = false;
            }
            if (shouldContinue) {
                shouldContinue = false;
            }
            else if (String.valueOf(currentChar).equals("§")) {
                shouldContinue = true;
                colorCache = ChatFormatting.getByChar(CharUtils.toChar(nextFormatting.replaceAll("§", "")));
            }
            else {
                render((shouldRainbow ? "" : colorCache) + String.valueOf(currentChar), x + currentWidth, y, shadow, currentColor361);
                currentWidth += getStringWidth(String.valueOf(currentChar));
                if (!String.valueOf(currentChar).equals(" ")) {
                    currentColor361 = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
                    currentHue += hueIncrement;
                }
            }
        }
    }
    
    public static void render(final String string, final float x, final float y, final boolean shadow, final Color color) {
        TurokGL.pushMatrix();
        TurokGL.enable(3553);
        if (shadow) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(string, (float)(int)x, (float)(int)y, color.getRGB());
        }
        else {
            Minecraft.getMinecraft().fontRendererObj.drawString(string, (int)x, (int)y, color.getRGB());
        }
        TurokGL.disable(3553);
        TurokGL.popMatrix();
    }
    
    public static void render(final TurokFont fontRenderer, final String string, final float x, final float y, final boolean shadow, final Color color) {
        TurokGL.pushMatrix();
        TurokGL.enable(3553);
        if (shadow) {
            if (fontRenderer.isRenderingCustomFont()) {
                fontRenderer.drawStringWithShadow(string, x, y, color.getRGB());
            }
            else {
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(string, (float)(int)x, (float)(int)y, color.getRGB());
            }
        }
        else if (fontRenderer.isRenderingCustomFont()) {
            fontRenderer.drawString(string, x, y, color.getRGB());
        }
        else {
            Minecraft.getMinecraft().fontRendererObj.drawString(string, (int)x, (int)y, color.getRGB());
        }
        TurokGL.disable(3553);
        TurokGL.popMatrix();
    }
    
    public static int getStringWidth(final TurokFont fontRenderer, final String string) {
        return fontRenderer.isRenderingCustomFont() ? fontRenderer.getStringWidth(string) : Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
    }
    
    public static int getStringWidth(final String string) {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
    }
    
    public static int getStringHeight(final TurokFont fontRenderer, final String string) {
        return fontRenderer.isRenderingCustomFont() ? fontRenderer.getStringHeight(string) : (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * fontRenderer.getFontSize());
    }
    
    public static int getStringHeight(final String string) {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }
}
