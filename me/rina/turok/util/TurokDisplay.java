//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.util;

import net.minecraft.util.math.MathHelper;
import net.minecraft.client.Minecraft;

public class TurokDisplay
{
    private Minecraft mc;
    private int scaleFactor;
    private float partialTicks;
    
    public TurokDisplay(final Minecraft mc) {
        this.mc = mc;
        this.onUpdate();
    }
    
    public int getWidth() {
        return this.mc.displayWidth;
    }
    
    public int getHeight() {
        return this.mc.displayHeight;
    }
    
    public int getScaledWidth() {
        return MathHelper.ceil(this.mc.displayWidth / (double)this.scaleFactor);
    }
    
    public int getScaledHeight() {
        return MathHelper.ceil(this.mc.displayHeight / (double)this.scaleFactor);
    }
    
    public int getScaleFactor() {
        return this.scaleFactor;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    protected void onUpdate() {
        this.scaleFactor = 1;
        final boolean isUnicode = this.mc.isUnicode();
        int minecraftScale = this.mc.gameSettings.guiScale;
        if (minecraftScale == 0) {
            minecraftScale = 1000;
        }
        while (this.scaleFactor < minecraftScale && this.getWidth() / (this.scaleFactor + 1) >= 320 && this.getHeight() / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (isUnicode && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
    }
}
