// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.overlay.component.widget;

import me.rina.turok.render.opengl.TurokShaderGL;
import java.awt.Color;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.util.TurokMath;
import java.util.Iterator;
import com.onepop.client.gui.overlay.setting.SettingEnumWidget;
import com.onepop.client.gui.overlay.setting.SettingNumberWidget;
import com.onepop.client.gui.overlay.setting.SettingBooleanWidget;
import com.onepop.api.component.impl.ComponentSetting;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import java.util.ArrayList;
import com.onepop.api.component.Component;
import com.onepop.client.gui.overlay.component.frame.ComponentListFrame;
import com.onepop.client.gui.overlay.ComponentClickGUI;
import com.onepop.api.gui.widget.Widget;

public class ComponentWidget extends Widget
{
    private ComponentClickGUI master;
    private ComponentListFrame frame;
    private Component component;
    private int offsetX;
    private int offsetY;
    private int offsetWidth;
    private int offsetHeight;
    private int alphaAnimationPressed;
    private boolean isMouseClickedLeft;
    private boolean isMouseClickedRight;
    private boolean isWidgetOpened;
    private ArrayList<Widget> loadedWidgetList;
    public Flag flagMouse;
    
    public ComponentWidget(final ComponentClickGUI master, final ComponentListFrame frame, final Component component) {
        super(component.getName());
        this.master = master;
        this.component = component;
        this.frame = frame;
        this.offsetX = 1;
        this.rect.setWidth(this.frame.getRect().getWidth() - this.offsetX * 2);
        this.rect.setHeight((float)(3 + TurokFontManager.getStringHeight(this.master.fontWidgetComponent, this.rect.getTag()) + 3));
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.init();
    }
    
    public void init() {
        this.loadedWidgetList = new ArrayList<Widget>();
        this.offsetHeight = (int)(this.rect.getHeight() + 1.0f);
        for (final ComponentSetting<?> settings : this.component.getSettingList()) {
            if (settings.getValue() instanceof Boolean) {
                final ComponentSetting<Boolean> componentSetting = (ComponentSetting<Boolean>)settings;
                final SettingBooleanWidget settingBooleanWidget = new SettingBooleanWidget(this.master, this.frame, this, componentSetting);
                settingBooleanWidget.setOffsetY(this.offsetHeight);
                this.loadedWidgetList.add(settingBooleanWidget);
                this.offsetHeight += (int)(settingBooleanWidget.getRect().getHeight() + 1.0f);
            }
            if (settings.getValue() instanceof Number) {
                final ComponentSetting<Number> componentSetting2 = (ComponentSetting<Number>)settings;
                final SettingNumberWidget settingBooleanWidget2 = new SettingNumberWidget(this.master, this.frame, this, componentSetting2);
                settingBooleanWidget2.setOffsetY(this.offsetHeight);
                this.loadedWidgetList.add(settingBooleanWidget2);
                this.offsetHeight += (int)(settingBooleanWidget2.getRect().getHeight() + 1.0f);
            }
            if (settings.getValue() instanceof Enum) {
                final ComponentSetting<Enum> componentSetting3 = (ComponentSetting<Enum>)settings;
                final SettingEnumWidget settingBooleanWidget3 = new SettingEnumWidget(this.master, this.frame, this, componentSetting3);
                settingBooleanWidget3.setOffsetY(this.offsetHeight);
                this.loadedWidgetList.add(settingBooleanWidget3);
                this.offsetHeight += (int)(settingBooleanWidget3.getRect().getHeight() + 1.0f);
            }
        }
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
    
    public void setWidgetOpened(final boolean widgetOpened) {
        this.isWidgetOpened = widgetOpened;
    }
    
    public boolean isWidgetOpened() {
        return this.isWidgetOpened;
    }
    
    public Component getComponent() {
        return this.component;
    }
    
    @Override
    public void onScreenOpened() {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onScreenOpened();
            }
        }
    }
    
    @Override
    public void onCustomScreenOpened() {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onCustomScreenOpened();
            }
        }
    }
    
    @Override
    public void onScreenClosed() {
        this.isMouseClickedLeft = false;
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onScreenClosed();
            }
        }
    }
    
    @Override
    public void onCustomScreenClosed() {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onCustomScreenClosed();
            }
        }
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (this.flagMouse == Flag.MOUSE_OVER) {
            if (this.isMouseClickedLeft) {
                this.component.setEnabled(!this.component.isEnabled());
                this.isMouseClickedLeft = false;
            }
            if (this.isMouseClickedRight) {
                this.isWidgetOpened = !this.isWidgetOpened;
                this.frame.refresh();
                this.isMouseClickedRight = false;
            }
        }
        else {
            this.isMouseClickedLeft = false;
            this.isMouseClickedRight = false;
        }
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onMouseReleased(button);
            }
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onCustomMouseReleased(button);
            }
        }
    }
    
    @Override
    public void onMouseClicked(final int button) {
        if (this.flagMouse == Flag.MOUSE_OVER) {
            if (button == 0) {
                this.isMouseClickedLeft = true;
            }
            if (button == 1) {
                this.isMouseClickedRight = true;
            }
        }
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onMouseClicked(button);
            }
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onCustomMouseClicked(button);
            }
        }
    }
    
    @Override
    public void onRender() {
        this.rect.setX(this.frame.getRect().getX() + this.offsetX);
        this.rect.setY(this.frame.getRect().getY() + this.offsetY);
        this.rect.setWidth(this.frame.getRect().getWidth() - this.offsetX * 2);
        if (this.component.isEnabled()) {
            this.alphaAnimationPressed = (int)TurokMath.serp((float)this.alphaAnimationPressed, (float)this.master.guiColor.base[3], this.master.getDisplay().getPartialTicks());
        }
        else {
            this.alphaAnimationPressed = (int)TurokMath.serp((float)this.alphaAnimationPressed, 0.0f, this.master.getDisplay().getPartialTicks());
        }
        TurokRenderGL.color(this.master.guiColor.base[0], this.master.guiColor.base[1], this.master.guiColor.base[2], this.alphaAnimationPressed);
        TurokRenderGL.drawSolidRect(this.rect);
        if (this.flagMouse == Flag.MOUSE_OVER) {
            TurokRenderGL.color(this.master.guiColor.highlight[0], this.master.guiColor.highlight[1], this.master.guiColor.highlight[2], this.master.guiColor.highlight[3]);
            TurokRenderGL.drawSolidRect(this.rect);
        }
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onRender();
            }
            TurokShaderGL.drawSolidRectFadingMouse(this.rect.getX(), this.rect.getY() + this.rect.getHeight() + 1.0f, 1.0f, this.offsetHeight - (this.rect.getHeight() + 2.0f), 50, new Color(this.master.guiColor.base[0], this.master.guiColor.base[1], this.master.guiColor.base[2], 255));
        }
        TurokFontManager.render(this.master.fontWidgetComponent, this.rect.getTag(), this.rect.getX() + 1.0f, this.rect.getY() + 3.0f, true, new Color(255, 255, 255, 255));
    }
    
    @Override
    public void onCustomRender() {
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onCustomRender();
            }
        }
    }
}
