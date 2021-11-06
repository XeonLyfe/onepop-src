// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.module.module.widget;

import me.rina.turok.render.opengl.TurokShaderGL;
import java.awt.Color;
import com.onepop.Onepop;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.util.TurokMath;
import com.onepop.client.Wrapper;
import java.util.Iterator;
import com.onepop.client.gui.module.setting.SettingEntryBoxWidget;
import com.onepop.api.setting.value.ValueString;
import com.onepop.client.gui.module.setting.SettingEnumWidget;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.client.gui.module.setting.SettingNumberWidget;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.client.gui.module.setting.SettingBooleanWidget;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.client.gui.module.setting.SettingBindWidget;
import com.onepop.api.setting.value.ValueBind;
import com.onepop.api.setting.Setting;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import java.util.ArrayList;
import com.onepop.api.module.Module;
import com.onepop.client.gui.module.module.container.ModuleScrollContainer;
import com.onepop.client.gui.module.category.CategoryFrame;
import com.onepop.client.gui.module.ModuleClickGUI;
import com.onepop.api.gui.widget.Widget;

public class ModuleWidget extends Widget
{
    private ModuleClickGUI master;
    private CategoryFrame frame;
    private ModuleScrollContainer container;
    private Module module;
    private int offsetX;
    private int offsetY;
    private int offsetWidth;
    private int offsetHeight;
    private int animationX;
    private int animationY;
    private boolean isWidgetOpened;
    private int alphaAnimationPressed;
    private boolean isMouseClickedLeft;
    private boolean isMouseClickedRight;
    private ArrayList<Widget> loadedWidgetList;
    public Flag flagMouse;
    
    public ModuleWidget(final ModuleClickGUI master, final CategoryFrame frame, final ModuleScrollContainer container, final Module module) {
        super(module.getName());
        this.master = master;
        this.module = module;
        this.container = container;
        this.frame = frame;
        this.rect.setWidth(this.container.getRect().getWidth() - this.offsetX * 2);
        this.rect.setHeight((float)(3 + TurokFontManager.getStringHeight(this.master.fontWidgetModule, this.rect.getTag()) + 3));
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.init();
    }
    
    public void init() {
        this.loadedWidgetList = new ArrayList<Widget>();
        this.offsetHeight = (int)(this.rect.getHeight() + 1.0f);
        for (final Setting settings : this.module.getSettingList()) {
            if (settings instanceof ValueBind) {
                final SettingBindWidget settingBindWidget = new SettingBindWidget(this.master, this.frame, this.container, this, (ValueBind)settings);
                settingBindWidget.setOffsetY(this.offsetHeight);
                settingBindWidget.setAnimationX(2);
                this.loadedWidgetList.add(settingBindWidget);
                this.offsetHeight += (int)(settingBindWidget.getRect().getHeight() + 1.0f);
            }
            if (settings instanceof ValueBoolean) {
                final SettingBooleanWidget settingBooleanWidget = new SettingBooleanWidget(this.master, this.frame, this.container, this, (ValueBoolean)settings);
                settingBooleanWidget.setOffsetY(this.offsetHeight);
                settingBooleanWidget.setAnimationX(2);
                this.loadedWidgetList.add(settingBooleanWidget);
                this.offsetHeight += (int)(settingBooleanWidget.getRect().getHeight() + 1.0f);
            }
            if (settings instanceof ValueNumber) {
                final SettingNumberWidget settingNumberWidget = new SettingNumberWidget(this.master, this.frame, this.container, this, (ValueNumber)settings);
                settingNumberWidget.setOffsetY(this.offsetHeight);
                settingNumberWidget.setAnimationX(2);
                this.loadedWidgetList.add(settingNumberWidget);
                this.offsetHeight += (int)(settingNumberWidget.getRect().getHeight() + 1.0f);
            }
            if (settings instanceof ValueEnum) {
                final SettingEnumWidget settingEnumWidget = new SettingEnumWidget(this.master, this.frame, this.container, this, (ValueEnum)settings);
                settingEnumWidget.setOffsetY(this.offsetHeight);
                settingEnumWidget.setAnimationX(2);
                this.loadedWidgetList.add(settingEnumWidget);
                this.offsetHeight += (int)(settingEnumWidget.getRect().getHeight() + 1.0f);
            }
            if (settings instanceof ValueString) {
                final SettingEntryBoxWidget settingEntryBoxWidget = new SettingEntryBoxWidget(this.master, this.frame, this.container, this, (ValueString)settings);
                settingEntryBoxWidget.setOffsetY(this.offsetHeight);
                settingEntryBoxWidget.setAnimationX(2);
                this.loadedWidgetList.add(settingEntryBoxWidget);
                this.offsetHeight += (int)(settingEntryBoxWidget.getRect().getHeight() + 1.0f);
            }
        }
    }
    
    public ArrayList<Widget> getLoadedWidgetList() {
        return this.loadedWidgetList;
    }
    
    public void refresh(final Setting setting) {
        this.offsetHeight = (int)(this.rect.getHeight() + 1.0f);
        this.module.onSetting();
        this.module.onSync();
        final int counterFlag = 0;
        for (final Widget widgets : this.loadedWidgetList) {
            if (widgets instanceof SettingBindWidget) {
                final SettingBindWidget settingBindWidget = (SettingBindWidget)widgets;
                settingBindWidget.setAnimationY(this.offsetHeight);
                if (setting != null && settingBindWidget.getSetting().getOld() == settingBindWidget.getSetting().isEnabled()) {
                    settingBindWidget.setAnimationApplierY(this.offsetHeight);
                }
                int flag = -200;
                if (settingBindWidget.getSetting().isEnabled()) {
                    settingBindWidget.getSetting().updateSetting();
                    this.offsetHeight += (int)(settingBindWidget.getRect().getHeight() + 1.0f);
                    flag = Wrapper.FLAG_COMPONENT_OPENED;
                }
                settingBindWidget.setAnimationX(flag);
            }
            if (widgets instanceof SettingBooleanWidget) {
                final SettingBooleanWidget settingBooleanWidget = (SettingBooleanWidget)widgets;
                settingBooleanWidget.setAnimationY(this.offsetHeight);
                if (setting != null && settingBooleanWidget.getSetting().getOld() == settingBooleanWidget.getSetting().isEnabled()) {
                    settingBooleanWidget.setAnimationApplierY(this.offsetHeight);
                }
                int flag = -200;
                if (settingBooleanWidget.getSetting().isEnabled()) {
                    settingBooleanWidget.getSetting().updateSetting();
                    this.offsetHeight += (int)(settingBooleanWidget.getRect().getHeight() + 1.0f);
                    flag = Wrapper.FLAG_COMPONENT_OPENED;
                }
                settingBooleanWidget.setAnimationX(flag);
            }
            if (widgets instanceof SettingNumberWidget) {
                final SettingNumberWidget settingNumberWidget = (SettingNumberWidget)widgets;
                settingNumberWidget.setAnimationY(this.offsetHeight);
                if (setting != null && settingNumberWidget.getSetting().getOld() == settingNumberWidget.getSetting().isEnabled()) {
                    settingNumberWidget.setAnimationApplierY(this.offsetHeight);
                }
                int flag = -200;
                if (settingNumberWidget.getSetting().isEnabled()) {
                    settingNumberWidget.getSetting().updateSetting();
                    this.offsetHeight += (int)(settingNumberWidget.getRect().getHeight() + 1.0f);
                    flag = Wrapper.FLAG_COMPONENT_OPENED;
                }
                settingNumberWidget.setAnimationX(flag);
            }
            if (widgets instanceof SettingEnumWidget) {
                final SettingEnumWidget settingEnumWidget = (SettingEnumWidget)widgets;
                settingEnumWidget.setAnimationY(this.offsetHeight);
                if (setting != null && settingEnumWidget.getSetting().getOld() == settingEnumWidget.getSetting().isEnabled()) {
                    settingEnumWidget.setAnimationApplierY(this.offsetHeight);
                }
                int flag = -200;
                if (settingEnumWidget.getSetting().isEnabled()) {
                    settingEnumWidget.getSetting().updateSetting();
                    this.offsetHeight += (int)(settingEnumWidget.getRect().getHeight() + 1.0f);
                    flag = Wrapper.FLAG_COMPONENT_OPENED;
                }
                settingEnumWidget.setAnimationX(flag);
            }
            if (widgets instanceof SettingEntryBoxWidget) {
                final SettingEntryBoxWidget settingEntryBoxWidget = (SettingEntryBoxWidget)widgets;
                settingEntryBoxWidget.setAnimationY(this.offsetHeight);
                if (setting != null && settingEntryBoxWidget.getSetting().getOld() == settingEntryBoxWidget.getSetting().isEnabled()) {
                    settingEntryBoxWidget.setAnimationApplierY(this.offsetHeight);
                }
                int flag = -200;
                if (settingEntryBoxWidget.getSetting().isEnabled()) {
                    settingEntryBoxWidget.getSetting().updateSetting();
                    this.offsetHeight += (int)(settingEntryBoxWidget.getRect().getHeight() + 1.0f);
                    flag = Wrapper.FLAG_COMPONENT_OPENED;
                }
                settingEntryBoxWidget.setAnimationX(flag);
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
    
    public Module getModule() {
        return this.module;
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
    
    @Override
    public void onScreenOpened() {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onScreenOpened();
                }
            }
        }
    }
    
    @Override
    public void onCustomScreenOpened() {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onCustomScreenOpened();
                }
            }
        }
    }
    
    @Override
    public void onScreenClosed() {
        this.isMouseClickedLeft = false;
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onScreenClosed();
                }
            }
        }
    }
    
    @Override
    public void onCustomScreenClosed() {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onCustomScreenClosed();
                }
            }
        }
    }
    
    @Override
    public void onKeyboardPressed(final char charCode, final int keyCode) {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onKeyboardPressed(charCode, keyCode);
                }
            }
        }
    }
    
    @Override
    public void onCustomKeyboardPressed(final char charCode, final int keyCode) {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onCustomKeyboardPressed(charCode, keyCode);
                }
            }
        }
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (this.flagMouse == Flag.MOUSE_OVER) {
            if (this.isMouseClickedLeft) {
                this.module.toggle();
                this.isMouseClickedLeft = false;
            }
            if (this.isMouseClickedRight) {
                this.isWidgetOpened = !this.isWidgetOpened;
                this.container.refresh(this.getModule(), null);
                this.isMouseClickedRight = false;
            }
        }
        else {
            this.isMouseClickedLeft = false;
            this.isMouseClickedRight = false;
        }
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onMouseReleased(button);
                }
            }
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onCustomMouseReleased(button);
                }
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
                if (widgets.isEnabled()) {
                    widgets.onMouseClicked(button);
                }
            }
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onCustomMouseClicked(button);
                }
            }
        }
    }
    
    @Override
    public void onRender() {
        final double diffValue = this.offsetY - this.animationY;
        final double diffFinal = TurokMath.sqrt(diffValue * diffValue);
        if (diffFinal < 20.0) {
            this.offsetY = this.animationY;
        }
        else {
            this.offsetY = (int)TurokMath.serp((float)this.offsetY, (float)this.animationY, this.master.getDisplay().getPartialTicks());
        }
        this.rect.setX(this.container.getRect().getX() + this.offsetX);
        this.rect.setY(this.container.getRect().getY() + this.offsetY);
        this.rect.setWidth(this.container.getRect().getWidth() - this.offsetX * 2);
        if (this.module.isEnabled()) {
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
        TurokFontManager.render(Onepop.getWrapper().fontSmallWidget, this.rect.getTag(), this.rect.getX() + 1.0f, this.rect.getY() + 3.0f, true, new Color(255, 255, 255));
        if (this.isWidgetOpened) {
            TurokShaderGL.drawSolidRectFadingMouse(this.rect.getX(), this.rect.getY() + this.rect.getHeight() + 1.0f, 1.0f, this.offsetHeight - (this.rect.getHeight() + 2.0f), 50, new Color(this.master.guiColor.base[0], this.master.guiColor.base[1], this.master.guiColor.base[2], 255));
            for (final Widget widgets : this.loadedWidgetList) {
                widgets.onRender();
                if (widgets instanceof SettingBindWidget) {
                    ((SettingBindWidget)widgets).flagMouse = Flag.MOUSE_NOT_OVER;
                }
                if (widgets instanceof SettingBooleanWidget) {
                    ((SettingBooleanWidget)widgets).flagMouse = Flag.MOUSE_NOT_OVER;
                }
                if (widgets instanceof SettingNumberWidget) {
                    ((SettingNumberWidget)widgets).flagMouse = Flag.MOUSE_NOT_OVER;
                }
                if (widgets instanceof SettingEnumWidget) {
                    ((SettingEnumWidget)widgets).flagMouse = Flag.MOUSE_NOT_OVER;
                }
                if (widgets instanceof SettingEntryBoxWidget) {
                    ((SettingEntryBoxWidget)widgets).flagMouse = Flag.MOUSE_NOT_OVER;
                }
            }
        }
    }
    
    @Override
    public void onCustomRender() {
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
        if (this.isWidgetOpened) {
            for (final Widget widgets : this.loadedWidgetList) {
                if (widgets.isEnabled()) {
                    widgets.onCustomRender();
                }
            }
        }
    }
}
