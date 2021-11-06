// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.overlay.setting;

import java.awt.Color;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.util.TurokMath;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import java.util.ArrayList;
import com.onepop.api.component.impl.ComponentSetting;
import com.onepop.client.gui.overlay.component.widget.ComponentWidget;
import com.onepop.client.gui.overlay.component.frame.ComponentListFrame;
import com.onepop.client.gui.overlay.ComponentClickGUI;
import com.onepop.api.gui.widget.Widget;

public class SettingEnumWidget extends Widget
{
    private ComponentClickGUI master;
    private ComponentListFrame frame;
    private ComponentWidget component;
    private int offsetX;
    private int offsetY;
    private int offsetWidth;
    private int offsetHeight;
    private ComponentSetting<Enum> setting;
    private int alphaAnimationPressed;
    private ArrayList<Enum> enumList;
    private int index;
    private boolean isMouseClickedLeft;
    private boolean isRendering;
    private boolean isStarted;
    public Flag flagMouse;
    
    public SettingEnumWidget(final ComponentClickGUI master, final ComponentListFrame frame, final ComponentWidget component, final ComponentSetting<Enum> setting) {
        super(setting.getName());
        this.master = master;
        this.frame = frame;
        this.component = component;
        this.setting = setting;
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.init();
    }
    
    public void init() {
        this.offsetX = 2;
        this.rect.setWidth(this.component.getRect().getWidth() - this.offsetX);
        this.rect.setHeight((float)(3 + TurokFontManager.getStringHeight(this.master.fontWidgetComponent, this.rect.getTag()) + 3));
        this.enumList = new ArrayList<Enum>();
        for (final Enum enums : (Enum[])this.setting.getValue().getClass().getEnumConstants()) {
            this.enumList.add(enums);
        }
        this.isStarted = true;
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
                if (this.index >= this.enumList.size() - 1) {
                    this.index = 0;
                }
                else {
                    ++this.index;
                }
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
        if (this.isMouseClickedLeft) {
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
        if (this.isStarted) {
            this.index = ((this.enumList.indexOf(this.setting.getValue()) != -1) ? this.enumList.indexOf(this.setting.getValue()) : 0);
            this.setting.setValue(this.enumList.get(this.index));
            this.isStarted = false;
        }
        else {
            this.setting.setValue(this.enumList.get(this.index));
        }
        final String name = this.rect.getTag() + ": " + this.setting.getValue().name();
        TurokFontManager.render(this.master.fontWidgetComponent, name, this.rect.getX() + 1.0f, this.rect.getY() + 3.0f, true, new Color(255, 255, 255));
    }
    
    @Override
    public void onCustomRender() {
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
    }
}
