// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.module.setting;

import me.rina.turok.render.opengl.TurokShaderGL;
import me.rina.turok.util.TurokMath;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import com.onepop.client.gui.rocan.RocanEntryBox;
import com.onepop.api.setting.value.ValueString;
import com.onepop.client.gui.module.module.widget.ModuleWidget;
import com.onepop.client.gui.module.module.container.ModuleScrollContainer;
import com.onepop.client.gui.module.category.CategoryFrame;
import com.onepop.client.gui.module.ModuleClickGUI;
import com.onepop.api.gui.widget.Widget;

public class SettingEntryBoxWidget extends Widget
{
    private ModuleClickGUI master;
    private CategoryFrame frame;
    private ModuleScrollContainer container;
    private ModuleWidget widget;
    private int offsetX;
    private int offsetY;
    private int offsetWidth;
    private int offsetHeight;
    private int animationX;
    private int animationY;
    private int animationApplierY;
    private ValueString setting;
    private RocanEntryBox entryBox;
    private boolean isStarted;
    public Flag flagMouse;
    public Flag flagAnimation;
    
    public SettingEntryBoxWidget(final ModuleClickGUI master, final CategoryFrame frame, final ModuleScrollContainer container, final ModuleWidget widget, final ValueString setting) {
        super(setting.getName());
        this.setting = setting;
        this.master = master;
        this.frame = frame;
        this.container = container;
        this.widget = widget;
        (this.entryBox = new RocanEntryBox(setting.getName(), this.master.fontWidgetModule, this.master.getMouse())).setText(this.setting.getValue());
        this.rect.setWidth(this.container.getRect().getWidth() - this.offsetX * 2);
        this.rect.setHeight((float)(3 + TurokFontManager.getStringHeight(this.master.fontWidgetModule, this.rect.getTag()) + 3));
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.flagAnimation = Flag.ANIMATION_END;
    }
    
    @Override
    public boolean isEnabled() {
        return this.setting.isEnabled();
    }
    
    public ValueString getSetting() {
        return this.setting;
    }
    
    public void setOffsetX(final int offsetX) {
        this.offsetX = offsetX;
    }
    
    public int getOffsetX() {
        return this.offsetX;
    }
    
    public void setOffsetY(final int offsetY) {
        this.offsetY = offsetY;
    }
    
    public int getOffsetY() {
        return this.offsetY;
    }
    
    public void setOffsetWidth(final int offsetWidth) {
        this.offsetWidth = offsetWidth;
    }
    
    public int getOffsetWidth() {
        return this.offsetWidth;
    }
    
    public void setOffsetHeight(final int offsetHeight) {
        this.offsetHeight = offsetHeight;
    }
    
    public int getOffsetHeight() {
        return this.offsetHeight;
    }
    
    public void setAnimationX(final int animationX) {
        this.animationX = animationX;
    }
    
    public int getAnimationX() {
        return this.animationX;
    }
    
    public void setAnimationY(final int animationY) {
        this.animationY = animationY;
    }
    
    public int getAnimationY() {
        return this.animationY;
    }
    
    public void setAnimationApplierY(final int animationApplierY) {
        this.animationApplierY = animationApplierY;
    }
    
    public int getAnimationApplierY() {
        return this.animationApplierY;
    }
    
    @Override
    public void onScreenOpened() {
    }
    
    @Override
    public void onCustomScreenOpened() {
    }
    
    @Override
    public void onScreenClosed() {
        this.entryBox.onScreenClosed();
    }
    
    @Override
    public void onCustomScreenClosed() {
    }
    
    @Override
    public void onKeyboardPressed(final char charCode, final int keyCode) {
        switch (keyCode) {
            case 1: {
                if (this.entryBox.isFocused()) {
                    this.entryBox.setFocused(false);
                    this.entryBox.setText(this.entryBox.getSave());
                }
                this.master.setCanceledCloseGUI(false);
                break;
            }
            case 28: {
                if (this.entryBox.isFocused()) {
                    this.entryBox.setFocused(false);
                }
                this.master.setCanceledCloseGUI(false);
                break;
            }
        }
    }
    
    @Override
    public void onCustomKeyboardPressed(final char charCode, final int keyCode) {
        this.entryBox.onKeyboardPressed(charCode, keyCode);
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (this.entryBox.isFocused() && this.flagMouse == Flag.MOUSE_NOT_OVER) {
            this.entryBox.setFocused(false);
            this.master.setCanceledCloseGUI(false);
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        this.entryBox.onMouseReleased(button);
    }
    
    @Override
    public void onMouseClicked(final int button) {
        if (this.entryBox.isFocused() && this.flagMouse == Flag.MOUSE_NOT_OVER) {
            this.entryBox.setFocused(false);
            this.master.setCanceledCloseGUI(false);
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        this.entryBox.onMouseClicked(button);
        if (this.flagMouse == Flag.MOUSE_OVER && !this.entryBox.isFocused()) {
            this.entryBox.setFocused(true);
        }
    }
    
    @Override
    public void onRender() {
        final double diffValue = this.animationApplierY - this.animationY;
        final double diffFinal = TurokMath.sqrt(diffValue * diffValue);
        if (diffFinal < 10.0) {
            this.animationApplierY = this.animationY;
        }
        else {
            this.animationApplierY = (int)TurokMath.serp((float)this.animationApplierY, (float)this.animationY, this.master.getDisplay().getPartialTicks());
        }
        this.offsetX = 2;
        this.rect.setX(this.widget.getRect().getX() + this.animationX);
        this.rect.setY(this.container.getRect().getY() + this.widget.getOffsetY() + this.animationApplierY);
        this.rect.setWidth(this.container.getRect().getWidth() - this.offsetX);
        this.entryBox.setMouse(this.master.getMouse());
        this.entryBox.setPartialTicks(this.master.getDisplay().getPartialTicks());
        this.entryBox.getRect().copy(this.rect);
        if (this.isStarted) {
            this.setting.setValue(this.entryBox.getText());
        }
        else {
            this.entryBox.setText(this.setting.getValue());
            this.isStarted = true;
        }
        this.entryBox.colorBackgroundOutline = new int[] { 0, 0, 0, 0 };
        this.entryBox.setScissored(false);
        TurokShaderGL.drawScissor((this.rect.getX() < this.frame.getRect().getX()) ? this.frame.getRect().getX() : this.rect.getX(), this.frame.getRect().getY() + this.frame.getOffsetHeight(), (this.rect.getX() + this.rect.getWidth() >= this.master.getClosedWidth()) ? (this.master.getClosedWidth() - (this.rect.getX() + this.rect.getWidth())) : this.rect.getWidth(), this.frame.getRect().getHeight() - this.frame.getOffsetHeight());
        this.offsetHeight = (int)TurokMath.lerp((float)this.offsetHeight, this.entryBox.isFocused() ? 255.0f : 0.0f, this.master.getDisplay().getPartialTicks());
        this.entryBox.onRender();
        this.entryBox.colorBackground = new int[] { 255, 255, 255, this.offsetHeight };
        if (this.entryBox.isFocused()) {
            this.master.setCanceledCloseGUI(true);
            this.entryBox.doMouseScroll();
            this.entryBox.colorString = new int[] { 0, 0, 0, 255 };
        }
        else {
            this.entryBox.colorString = new int[] { 255, 255, 255, 255 };
        }
        TurokShaderGL.drawScissor(this.frame.getRect().getX(), this.frame.getRect().getY() + this.frame.getOffsetHeight(), (this.frame.getRect().getX() + this.frame.getRect().getWidth() >= this.master.getClosedWidth()) ? (this.master.getClosedWidth() - (this.frame.getRect().getX() + this.frame.getRect().getWidth())) : this.frame.getRect().getWidth(), this.frame.getRect().getHeight() - this.frame.getOffsetHeight());
    }
    
    @Override
    public void onCustomRender() {
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
        this.entryBox.flagMouse = this.flagMouse;
    }
}
