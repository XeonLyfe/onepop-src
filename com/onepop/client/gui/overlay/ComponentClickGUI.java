//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.overlay;

import me.rina.turok.util.TurokMath;
import com.onepop.api.gui.flag.Flag;
import me.rina.turok.render.opengl.TurokGL;
import me.rina.turok.render.opengl.TurokShaderGL;
import com.onepop.client.gui.module.category.CategoryFrame;
import com.onepop.client.module.client.ModuleHUD;
import java.util.Iterator;
import com.onepop.client.gui.overlay.component.frame.ComponentFrame;
import com.onepop.api.component.Component;
import com.onepop.Onepop;
import com.onepop.client.gui.overlay.component.frame.ComponentListFrame;
import java.awt.Font;
import me.rina.turok.render.font.TurokFont;
import com.onepop.api.gui.frame.Frame;
import java.util.ArrayList;
import com.onepop.client.Wrapper;
import me.rina.turok.hardware.mouse.TurokMouse;
import me.rina.turok.util.TurokDisplay;
import net.minecraft.client.gui.GuiScreen;

public class ComponentClickGUI extends GuiScreen
{
    private TurokDisplay display;
    private TurokMouse mouse;
    public Wrapper guiColor;
    private ArrayList<Frame> loadedFrameList;
    private Frame focusedFrame;
    private int closedWidth;
    private boolean isClosingGUI;
    private boolean isEventing;
    public TurokFont fontComponentListFrame;
    public TurokFont fontWidgetComponent;
    
    public ComponentClickGUI() {
        this.fontComponentListFrame = new TurokFont(new Font("Whitney", 0, 24), true, true);
        this.fontWidgetComponent = new TurokFont(new Font("Whitney", 0, 16), true, true);
        this.guiColor = new Wrapper();
        this.mouse = new TurokMouse();
        this.init();
    }
    
    public void init() {
        this.loadedFrameList = new ArrayList<Frame>();
        final ComponentListFrame componentListFrame = new ComponentListFrame(this, "Component HUD");
        this.loadedFrameList.add(componentListFrame);
        for (final Component components : Onepop.getComponentManager().getComponentList()) {
            final ComponentFrame componentFrame = new ComponentFrame(this, components);
            this.loadedFrameList.add(componentFrame);
        }
    }
    
    public TurokMouse getMouse() {
        return this.mouse;
    }
    
    public TurokDisplay getDisplay() {
        return this.display;
    }
    
    public void moveFocusedFrameToTopMatrix() {
        if (this.focusedFrame != null) {
            this.loadedFrameList.remove(this.focusedFrame);
            this.loadedFrameList.add(this.focusedFrame);
        }
    }
    
    public void setClosingGUI(final boolean closingGUI) {
        this.isClosingGUI = closingGUI;
    }
    
    public boolean isClosingGUI() {
        return this.isClosingGUI;
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void onGuiClosed() {
        ModuleHUD.INSTANCE.setDisabled();
        for (final Frame frames : this.loadedFrameList) {
            frames.onScreenClosed();
        }
        if (this.focusedFrame != null) {
            this.focusedFrame.onCustomScreenClosed();
        }
    }
    
    public void initGui() {
        if (this.isClosingGUI) {
            this.isClosingGUI = false;
        }
        for (final Frame frames : this.loadedFrameList) {
            frames.onScreenOpened();
        }
        if (this.focusedFrame != null) {
            this.focusedFrame.onCustomScreenOpened();
        }
    }
    
    public void keyTyped(final char charCode, final int keyCode) {
        if (keyCode == 1) {
            this.isClosingGUI = true;
        }
    }
    
    public void mouseReleased(final int mx, final int my, final int button) {
        for (final Frame frames : this.loadedFrameList) {
            frames.onMouseReleased(button);
            if (frames instanceof CategoryFrame) {
                final CategoryFrame categoryFrame = (CategoryFrame)frames;
                if (!categoryFrame.verify(this.mouse)) {
                    continue;
                }
                this.focusedFrame = categoryFrame;
            }
        }
        if (this.focusedFrame != null) {
            this.focusedFrame.onCustomMouseReleased(button);
        }
    }
    
    public void mouseClicked(final int mx, final int my, final int button) {
        for (final Frame frames : this.loadedFrameList) {
            frames.onMouseClicked(button);
            if (frames instanceof CategoryFrame) {
                final CategoryFrame categoryFrame = (CategoryFrame)frames;
                if (!categoryFrame.verify(this.mouse)) {
                    continue;
                }
                this.focusedFrame = categoryFrame;
            }
        }
        if (this.focusedFrame != null) {
            this.focusedFrame.onCustomMouseClicked(button);
        }
    }
    
    public void drawScreen(final int mx, final int my, final float partialTicks) {
        this.guiColor.onUpdateColor();
        (this.display = new TurokDisplay(this.mc)).setPartialTicks(partialTicks);
        this.mouse.setPos(mx, my);
        TurokShaderGL.init(this.display, this.mouse);
        TurokGL.pushMatrix();
        TurokGL.translate((float)this.display.getScaledWidth(), (float)this.display.getScaledHeight());
        TurokGL.scale(0.5f, 0.5f, 0.5f);
        TurokGL.popMatrix();
        TurokGL.disable(3553);
        for (final Frame frames : this.loadedFrameList) {
            frames.onRender();
            if (frames.verifyFocus(mx, my)) {
                this.focusedFrame = frames;
            }
            if (frames instanceof ComponentListFrame) {
                ((ComponentListFrame)frames).flagMouse = Flag.MOUSE_NOT_OVER;
                ((ComponentListFrame)frames).flagOffsetMouse = Flag.MOUSE_NOT_OVER;
            }
            if (frames instanceof ComponentFrame) {
                ((ComponentFrame)frames).flagMouse = Flag.MOUSE_NOT_OVER;
            }
        }
        if (this.focusedFrame != null) {
            this.focusedFrame.onCustomRender();
        }
        TurokGL.disable(3553);
        TurokGL.disable(3042);
        TurokGL.enable(3553);
        final int closingValueCalculated = 10;
        if (this.isClosingGUI) {
            this.closedWidth = (int)TurokMath.serp((float)this.closedWidth, 0.0f, this.display.getPartialTicks());
            if (this.closedWidth <= closingValueCalculated && this.isEventing) {
                this.onGuiClosed();
                this.mc.setIngameFocus();
                this.mc.displayGuiScreen((GuiScreen)null);
                this.isEventing = false;
            }
        }
        else {
            this.closedWidth = (int)TurokMath.serp((float)this.closedWidth, (float)(this.display.getScaledWidth() + 10), this.display.getPartialTicks());
            this.isEventing = true;
        }
    }
    
    public void onSaveAll() {
        for (final Frame frames : this.loadedFrameList) {
            if (frames instanceof ComponentListFrame) {
                final ComponentListFrame componentListFrame = (ComponentListFrame)frames;
                componentListFrame.onSave();
            }
        }
    }
    
    public void onLoadAll() {
        for (final Frame frames : this.loadedFrameList) {
            if (frames instanceof ComponentListFrame) {
                final ComponentListFrame componentListFrame = (ComponentListFrame)frames;
                componentListFrame.onLoad();
            }
        }
    }
}
