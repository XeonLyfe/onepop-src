// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.module.setting;

import java.awt.Color;
import com.onepop.api.setting.Setting;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.util.TurokMath;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.client.gui.module.module.widget.ModuleWidget;
import com.onepop.client.gui.module.module.container.ModuleScrollContainer;
import com.onepop.client.gui.module.category.CategoryFrame;
import com.onepop.client.gui.module.ModuleClickGUI;
import com.onepop.api.gui.widget.Widget;

public class SettingEnumWidget extends Widget
{
    private ModuleClickGUI master;
    private CategoryFrame frame;
    private ModuleScrollContainer container;
    private ModuleWidget module;
    private int offsetX;
    private int offsetY;
    private int offsetWidth;
    private int offsetHeight;
    private ValueEnum setting;
    private int alphaAnimationPressed;
    private int animationX;
    private int animationY;
    private int animationApplierY;
    private boolean isMouseClickedLeft;
    private boolean isRendering;
    private boolean isStarted;
    public Flag flagMouse;
    
    public SettingEnumWidget(final ModuleClickGUI master, final CategoryFrame frame, final ModuleScrollContainer container, final ModuleWidget module, final ValueEnum setting) {
        super(setting.getName());
        this.master = master;
        this.frame = frame;
        this.container = container;
        this.module = module;
        this.setting = setting;
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.init();
    }
    
    @Override
    public boolean isEnabled() {
        return this.setting.isEnabled();
    }
    
    public void init() {
        this.rect.setWidth(this.module.getRect().getWidth() - this.offsetX);
        this.rect.setHeight((float)(3 + TurokFontManager.getStringHeight(this.master.fontWidgetModule, this.rect.getTag()) + 3));
    }
    
    public ValueEnum getSetting() {
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
    
    public void setRendering(final boolean rendering) {
        this.isRendering = rendering;
    }
    
    public boolean isRendering() {
        return this.isRendering;
    }
    
    @Override
    public void onScreenOpened() {
    }
    
    @Override
    public void onCustomScreenOpened() {
    }
    
    @Override
    public void onScreenClosed() {
        this.isMouseClickedLeft = false;
    }
    
    @Override
    public void onCustomScreenClosed() {
    }
    
    @Override
    public void onKeyboardPressed(final char charCode, final int keyCode) {
    }
    
    @Override
    public void onCustomKeyboardPressed(final char charCode, final int keyCode) {
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (this.flagMouse == Flag.MOUSE_OVER) {
            if (this.isMouseClickedLeft) {
                this.setting.updateIndex();
                this.isMouseClickedLeft = false;
            }
        }
        else {
            this.isMouseClickedLeft = false;
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
    }
    
    @Override
    public void onMouseClicked(final int button) {
        if (this.flagMouse == Flag.MOUSE_OVER && button == 0) {
            this.isMouseClickedLeft = true;
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
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
        this.rect.setX(this.module.getRect().getX() + this.animationX);
        this.rect.setY(this.container.getRect().getY() + this.module.getOffsetY() + this.animationApplierY);
        this.rect.setWidth(this.container.getRect().getWidth() - this.offsetX);
        if (this.isMouseClickedLeft && this.module.isWidgetOpened()) {
            this.alphaAnimationPressed = (int)TurokMath.lerp((float)this.alphaAnimationPressed, (float)this.master.guiColor.base[3], this.master.getDisplay().getPartialTicks());
        }
        else {
            this.alphaAnimationPressed = (int)TurokMath.lerp((float)this.alphaAnimationPressed, 0.0f, this.master.getDisplay().getPartialTicks());
        }
        TurokRenderGL.color(this.master.guiColor.base[0], this.master.guiColor.base[1], this.master.guiColor.base[2], this.alphaAnimationPressed);
        TurokRenderGL.drawSolidRect(this.rect);
        if (this.flagMouse == Flag.MOUSE_OVER) {
            TurokRenderGL.color(this.master.guiColor.highlight[0], this.master.guiColor.highlight[1], this.master.guiColor.highlight[2], this.master.guiColor.highlight[3]);
            TurokRenderGL.drawSolidRect(this.rect);
        }
        if (this.setting.getValue() != this.setting.getValueList().get(this.setting.getIndex())) {
            this.setting.setValue(this.setting.getValueList().get(this.setting.getIndex()));
            this.container.refresh(this.module.getModule(), this.setting);
        }
        TurokFontManager.render(this.master.fontWidgetModule, (this.rect.getTag().isEmpty() ? this.rect.getTag() : (this.rect.getTag() + ": ")) + this.setting.getValue().name(), this.rect.getX() + 1.0f, this.rect.getY() + 3.0f, true, new Color(255, 255, 255));
    }
    
    @Override
    public void onCustomRender() {
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
    }
}
