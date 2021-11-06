//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.overlay.component.frame;

import net.minecraft.client.renderer.GlStateManager;
import me.rina.turok.render.opengl.TurokShaderGL;
import java.awt.Color;
import me.rina.turok.render.opengl.TurokRenderGL;
import com.onepop.api.gui.flag.Flag;
import com.onepop.api.component.Component;
import com.onepop.client.gui.overlay.ComponentClickGUI;
import com.onepop.api.gui.frame.Frame;

public class ComponentFrame extends Frame
{
    private ComponentClickGUI master;
    private Component component;
    private int dragX;
    private int dragY;
    private boolean isMouseClickedLeft;
    private int alpha;
    public Flag flagMouse;
    
    public ComponentFrame(final ComponentClickGUI master, final Component component) {
        super(component.getName());
        this.master = master;
        this.component = component;
        this.flagMouse = Flag.MOUSE_NOT_OVER;
    }
    
    @Override
    public boolean isEnabled() {
        return this.component.isEnabled();
    }
    
    @Override
    public boolean verifyFocus(final int mx, final int my) {
        boolean verified = false;
        if (this.isEnabled() && this.component.getRect().collideWithMouse(this.master.getMouse())) {
            verified = true;
        }
        return verified;
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (this.isMouseClickedLeft) {
            this.isMouseClickedLeft = false;
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        this.master.moveFocusedFrameToTopMatrix();
    }
    
    @Override
    public void onMouseClicked(final int button) {
        if (this.component.isEnabled() && this.flagMouse == Flag.MOUSE_OVER && button == 0) {
            this.dragX = (int)(this.master.getMouse().getX() - this.component.getRect().getX());
            this.dragY = (int)(this.master.getMouse().getY() - this.component.getRect().getY());
            this.isMouseClickedLeft = true;
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        this.master.moveFocusedFrameToTopMatrix();
    }
    
    @Override
    public void onRender() {
        if (this.component.isEnabled()) {
            TurokRenderGL.color(0, 0, 0, 100);
            TurokRenderGL.drawSolidRect(this.component.getRect());
            if (this.flagMouse == Flag.MOUSE_OVER) {
                TurokRenderGL.color(255, 255, 255, 50);
                TurokRenderGL.drawSolidRect(this.component.getRect());
            }
            TurokRenderGL.color(0, 0, 255, this.alpha);
            TurokRenderGL.drawSolidRect(this.component.getRect());
            TurokShaderGL.drawOutlineRectFadingMouse(this.component.getRect(), 50, new Color(0, 0, 0, 255));
            GlStateManager.disableBlend();
            GlStateManager.disableTexture2D();
            this.component.onRender();
            if (this.isMouseClickedLeft) {
                this.component.getRect().setX((float)(this.master.getMouse().getX() - this.dragX));
                this.component.getRect().setY((float)(this.master.getMouse().getY() - this.dragY));
                this.alpha = 50;
                this.component.setDragging(true);
            }
            else {
                this.alpha = 0;
                this.component.setDragging(false);
            }
        }
        this.component.cornerDetector();
    }
    
    @Override
    public void onCustomRender() {
        if (this.component.isEnabled()) {
            this.flagMouse = (this.component.getRect().collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
        }
    }
}
