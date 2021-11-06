// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.overlay.setting;

import java.awt.Color;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.util.TurokMath;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import com.onepop.api.component.impl.ComponentSetting;
import com.onepop.client.gui.overlay.component.widget.ComponentWidget;
import com.onepop.client.gui.overlay.component.frame.ComponentListFrame;
import com.onepop.client.gui.overlay.ComponentClickGUI;
import com.onepop.api.gui.widget.Widget;

public class SettingNumberWidget extends Widget
{
    private ComponentClickGUI master;
    private ComponentListFrame frame;
    private ComponentWidget component;
    private int offsetX;
    private int offsetY;
    private double offsetWidth;
    private int offsetHeight;
    private double minimum;
    private double maximum;
    private double value;
    private ComponentSetting<Number> setting;
    private int alphaAnimationPressed;
    private boolean isMouseClickedLeft;
    private boolean isRendering;
    public Flag flagMouse;
    
    public SettingNumberWidget(final ComponentClickGUI master, final ComponentListFrame frame, final ComponentWidget module, final ComponentSetting<Number> setting) {
        super(setting.getName());
        this.master = master;
        this.frame = frame;
        this.component = module;
        this.setting = setting;
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.init();
    }
    
    public void init() {
        this.offsetX = 2;
        this.rect.setWidth(this.component.getRect().getWidth() - this.offsetX);
        this.rect.setHeight((float)(3 + TurokFontManager.getStringHeight(this.master.fontWidgetComponent, this.rect.getTag()) + 3));
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
        this.rect.setX(this.component.getRect().getX() + this.offsetX);
        this.rect.setY(this.frame.getRect().getY() + this.component.getOffsetY() + this.offsetY);
        this.rect.setWidth(this.component.getRect().getWidth() - this.offsetX);
        this.value = this.setting.getValue().doubleValue();
        this.maximum = this.setting.getMaximum().doubleValue();
        this.minimum = this.setting.getMinimum().doubleValue();
        this.offsetWidth = this.rect.getWidth() * (this.value - this.minimum) / (this.maximum - this.minimum);
        final double mouse = Math.min(this.rect.getWidth(), Math.max(0.0f, this.master.getMouse().getX() - this.rect.getX()));
        if (this.isMouseClickedLeft) {
            if (mouse == 0.0) {
                this.setting.setValue(this.setting.getMinimum());
            }
            else if (this.setting.getValue().getClass() == Integer.class) {
                final double rounded = TurokMath.round(mouse / this.rect.getWidth() * (this.maximum - this.minimum) + this.minimum);
                final Integer decimal = (int)rounded;
                this.setting.setValue(decimal);
            }
            else if (this.setting.getValue().getClass() == Double.class) {
                final double rounded = TurokMath.round(mouse / this.rect.getWidth() * (this.maximum - this.minimum) + this.minimum);
                final Double decimal2 = rounded;
                this.setting.setValue(decimal2);
            }
            else if (this.setting.getValue().getClass() == Float.class) {
                final double rounded = TurokMath.round(mouse / this.rect.getWidth() * (this.maximum - this.minimum) + this.minimum);
                final Float decimal3 = (float)rounded;
                this.setting.setValue(decimal3);
            }
            else if (this.setting.getValue().getClass() == Long.class) {
                final double rounded = TurokMath.round(mouse / this.rect.getWidth() * (this.maximum - this.minimum) + this.minimum);
                final Long decimal4 = (long)rounded;
                this.setting.setValue(decimal4);
            }
        }
        TurokRenderGL.color(this.master.guiColor.base[0], this.master.guiColor.base[1], this.master.guiColor.base[2], this.master.guiColor.base[3]);
        TurokRenderGL.drawSolidRect(this.rect.getX(), this.rect.getY(), (float)this.offsetWidth, this.rect.getHeight());
        final String name = this.rect.getTag() + " | " + this.setting.getValue().toString();
        if (this.flagMouse == Flag.MOUSE_OVER) {
            TurokRenderGL.color(this.master.guiColor.highlight[0], this.master.guiColor.highlight[1], this.master.guiColor.highlight[2], this.master.guiColor.highlight[3]);
            TurokRenderGL.drawSolidRect(this.rect);
        }
        TurokFontManager.render(this.master.fontWidgetComponent, name, this.rect.getX() + 1.0f, this.rect.getY() + 3.0f, true, new Color(255, 255, 255));
    }
    
    @Override
    public void onCustomRender() {
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
    }
}
