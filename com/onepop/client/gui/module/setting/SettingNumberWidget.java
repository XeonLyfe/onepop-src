// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.module.setting;

import java.awt.Color;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.util.TurokMath;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.client.gui.module.module.widget.ModuleWidget;
import com.onepop.client.gui.module.module.container.ModuleScrollContainer;
import com.onepop.client.gui.module.category.CategoryFrame;
import com.onepop.client.gui.module.ModuleClickGUI;
import com.onepop.api.gui.widget.Widget;

public class SettingNumberWidget extends Widget
{
    private ModuleClickGUI master;
    private CategoryFrame frame;
    private ModuleScrollContainer container;
    private ModuleWidget module;
    private int offsetX;
    private int offsetY;
    private double offsetWidth;
    private int offsetHeight;
    private int animationX;
    private int animationY;
    private int animationApplierY;
    private final ValueNumber setting;
    private int offsetAnimation;
    private boolean isMouseClickedLeft;
    private boolean isRendering;
    public Flag flagMouse;
    
    public SettingNumberWidget(final ModuleClickGUI master, final CategoryFrame frame, final ModuleScrollContainer container, final ModuleWidget module, final ValueNumber setting) {
        super(setting.getName());
        this.master = master;
        this.frame = frame;
        this.container = container;
        this.module = module;
        this.setting = setting;
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.init();
    }
    
    public void init() {
        this.rect.setWidth(this.module.getRect().getWidth() - this.offsetX);
        this.rect.setHeight((float)(3 + TurokFontManager.getStringHeight(this.master.fontWidgetModule, this.rect.getTag()) + 3));
    }
    
    @Override
    public boolean isEnabled() {
        return this.setting.isEnabled();
    }
    
    public ValueNumber getSetting() {
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
    
    public void setOffsetWidth(final double offsetWidth) {
        this.offsetWidth = offsetWidth;
    }
    
    public double getOffsetWidth() {
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
        final double value = this.setting.getValue().floatValue();
        final float maximum = this.setting.getMaximum().floatValue();
        final float minimum = this.setting.getMinimum().floatValue();
        this.offsetWidth = this.rect.getWidth() * (value - minimum) / (maximum - minimum);
        final float mouse = Math.min(this.rect.getWidth(), Math.max(0.0f, this.master.getMouse().getX() - this.rect.getX()));
        if (this.isMouseClickedLeft) {
            if (mouse == 0.0f) {
                this.setting.setValue(this.setting.getMinimum());
            }
            else if (this.setting.getValue() instanceof Integer) {
                final int roundedValue = (int)TurokMath.round(mouse / this.rect.getWidth() * (maximum - minimum) + minimum);
                this.setting.setValue(roundedValue);
            }
            else if (this.setting.getValue() instanceof Double) {
                final double roundedValue2 = TurokMath.round(mouse / this.rect.getWidth() * (maximum - minimum) + minimum);
                this.setting.setValue(roundedValue2);
            }
            else if (this.setting.getValue() instanceof Float) {
                final float roundedValue3 = (float)TurokMath.round(mouse / this.rect.getWidth() * (maximum - minimum) + minimum);
                this.setting.setValue(roundedValue3);
            }
        }
        TurokRenderGL.color(this.master.guiColor.base[0], this.master.guiColor.base[1], this.master.guiColor.base[2], this.master.guiColor.base[3]);
        TurokRenderGL.drawSolidRect(this.rect.getX(), this.rect.getY(), (float)this.offsetWidth, this.rect.getHeight());
        final String currentSettingValue = (this.setting.getSmooth() == ValueNumber.Smooth.INTEGER) ? ((int)this.setting.getValue().floatValue() + "") : this.setting.getValue().toString();
        final String name = this.rect.getTag() + " | " + currentSettingValue;
        if (this.flagMouse == Flag.MOUSE_OVER) {
            TurokRenderGL.color(this.master.guiColor.highlight[0], this.master.guiColor.highlight[1], this.master.guiColor.highlight[2], this.master.guiColor.highlight[3]);
            TurokRenderGL.drawSolidRect(this.rect);
        }
        TurokFontManager.render(this.master.fontWidgetModule, name, this.rect.getX() + 1.0f + this.offsetAnimation, this.rect.getY() + 3.0f, true, new Color(255, 255, 255));
    }
    
    @Override
    public void onCustomRender() {
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
    }
}
