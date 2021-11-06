//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.module;

import me.rina.turok.util.TurokMath;
import com.onepop.api.gui.flag.Flag;
import me.rina.turok.render.opengl.TurokGL;
import me.rina.turok.render.opengl.TurokShaderGL;
import java.io.IOException;
import me.rina.turok.util.TurokRect;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import java.util.Iterator;
import com.onepop.client.gui.module.category.CategoryFrame;
import com.onepop.api.module.impl.ModuleCategory;
import java.awt.Font;
import me.rina.turok.render.font.TurokFont;
import com.onepop.api.gui.frame.Frame;
import java.util.ArrayList;
import com.onepop.client.Wrapper;
import me.rina.turok.hardware.mouse.TurokMouse;
import me.rina.turok.util.TurokDisplay;
import net.minecraft.client.gui.GuiScreen;

public class ModuleClickGUI extends GuiScreen
{
    public TurokDisplay display;
    public TurokMouse mouse;
    public Wrapper guiColor;
    public ArrayList<Frame> loadedFrameList;
    private int animationX;
    private int animationY;
    private int closedWidth;
    private boolean isClosingGUI;
    private boolean isEventing;
    private boolean isOverFrame;
    private Frame focusedFrame;
    private boolean isCanceledCloseGUI;
    public TurokFont fontFrameCategory;
    public TurokFont fontWidgetModule;
    
    public ModuleClickGUI() {
        this.fontFrameCategory = new TurokFont(new Font("Whitney", 0, 24), true, true);
        this.fontWidgetModule = new TurokFont(new Font("Whitney", 0, 16), true, true);
        this.guiColor = new Wrapper();
        this.mouse = new TurokMouse();
        this.init();
    }
    
    public void init() {
        this.loadedFrameList = new ArrayList<Frame>();
        int cacheX = 1;
        for (final ModuleCategory categories : ModuleCategory.values()) {
            final CategoryFrame categoryFrame = new CategoryFrame(this, categories);
            categoryFrame.getRect().setX((float)cacheX);
            categoryFrame.getRect().setY(1.0f);
            this.loadedFrameList.add(categoryFrame);
            cacheX += categoryFrame.getOffsetWidth() + 1;
            this.focusedFrame = categoryFrame;
        }
    }
    
    public void matrixMoveFocusedFrameToLast() {
        this.loadedFrameList.remove(this.focusedFrame);
        this.loadedFrameList.add(this.focusedFrame);
    }
    
    public void setCanceledCloseGUI(final boolean canceledCloseGUI) {
        this.isCanceledCloseGUI = canceledCloseGUI;
    }
    
    public boolean isCanceledCloseGUI() {
        return this.isCanceledCloseGUI;
    }
    
    public TurokDisplay getDisplay() {
        return this.display;
    }
    
    public TurokMouse getMouse() {
        return this.mouse;
    }
    
    public void onSaveList() {
        for (final Frame frames : this.loadedFrameList) {
            frames.onSave();
        }
    }
    
    public void onLoadList() {
        for (final Frame frames : this.loadedFrameList) {
            frames.onLoad();
        }
    }
    
    public void setClosingGUI(final boolean closingGUI) {
        this.isClosingGUI = closingGUI;
    }
    
    public boolean isClosingGUI() {
        return this.isClosingGUI;
    }
    
    public void setClosedWidth(final int closedWidth) {
        this.closedWidth = closedWidth;
    }
    
    public int getClosedWidth() {
        return this.closedWidth;
    }
    
    public void setOverFrame(final boolean overFrame) {
        this.isOverFrame = overFrame;
    }
    
    public boolean isOverFrame() {
        return this.isOverFrame;
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void onGuiClosed() {
        if (com.onepop.client.module.client.ModuleClickGUI.INSTANCE.isEnabled()) {
            com.onepop.client.module.client.ModuleClickGUI.INSTANCE.setDisabled();
        }
        for (final Frame frames : this.loadedFrameList) {
            frames.onScreenClosed();
        }
        this.focusedFrame.onCustomScreenClosed();
    }
    
    public void initGui() {
        if (this.isClosingGUI) {
            this.isClosingGUI = false;
        }
        for (final Frame frames : this.loadedFrameList) {
            frames.onScreenOpened();
        }
        this.focusedFrame.onCustomScreenOpened();
    }
    
    public void keyTyped(final char charCode, final int keyCode) {
        if (this.isCanceledCloseGUI) {
            this.focusedFrame.onCustomKeyboardPressed(charCode, keyCode);
        }
        else if (keyCode == 1) {
            this.isClosingGUI = true;
        }
        for (final Frame frames : this.loadedFrameList) {
            frames.onKeyboardPressed(charCode, keyCode);
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
        this.focusedFrame.onCustomMouseReleased(button);
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
        this.focusedFrame.onCustomMouseClicked(button);
    }
    
    public void handleMouseInput() throws IOException {
        if (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29)) {
            if (Mouse.getEventDWheel() > 0) {
                for (final Frame frames : this.loadedFrameList) {
                    final TurokRect rect = frames.getRect();
                    rect.y += 10.0f;
                }
            }
            if (Mouse.getEventDWheel() < 0) {
                for (final Frame frames : this.loadedFrameList) {
                    final TurokRect rect2 = frames.getRect();
                    rect2.y -= 10.0f;
                }
            }
        }
        super.handleMouseInput();
    }
    
    public void drawScreen(final int mx, final int my, final float partialTicks) {
        (this.display = new TurokDisplay(this.mc)).setPartialTicks(partialTicks);
        this.mouse.setPos(mx, my);
        this.guiColor.onUpdateColor();
        TurokShaderGL.init(this.display, this.mouse);
        this.drawDefaultBackground();
        TurokGL.pushMatrix();
        TurokGL.translate((float)this.display.getScaledWidth(), (float)this.display.getScaledHeight());
        TurokGL.scale(0.5f, 0.5f, 0.5f);
        TurokGL.popMatrix();
        TurokGL.disable(3553);
        this.isOverFrame = false;
        for (final Frame frames : this.loadedFrameList) {
            TurokShaderGL.pushScissor();
            TurokShaderGL.drawScissor(0.0f, 0.0f, (float)this.closedWidth, (float)this.display.getScaledHeight());
            frames.onRender();
            TurokShaderGL.popScissor();
            if (frames instanceof CategoryFrame) {
                final CategoryFrame categoryFrame = (CategoryFrame)frames;
                if (categoryFrame.verify(this.mouse)) {
                    this.focusedFrame = categoryFrame;
                    this.isOverFrame = true;
                }
                categoryFrame.flagOffsetMouse = Flag.MOUSE_NOT_OVER;
                categoryFrame.flagMouse = Flag.MOUSE_NOT_OVER;
            }
        }
        this.focusedFrame.onCustomRender();
        TurokGL.disable(3553);
        TurokGL.disable(3042);
        TurokGL.enable(3553);
        TurokGL.color(255.0f, 255.0f, 255.0f);
        final int closingValueCalculated = 10;
        if (this.isClosingGUI) {
            this.closedWidth = (int)TurokMath.serp((float)this.closedWidth, 0.0f, partialTicks);
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
}
