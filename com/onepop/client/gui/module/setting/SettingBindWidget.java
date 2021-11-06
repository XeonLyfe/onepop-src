// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.module.setting;

import java.awt.Color;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.util.TurokMath;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import com.onepop.api.setting.value.ValueBind;
import com.onepop.client.gui.module.module.widget.ModuleWidget;
import com.onepop.client.gui.module.module.container.ModuleScrollContainer;
import com.onepop.client.gui.module.category.CategoryFrame;
import com.onepop.client.gui.module.ModuleClickGUI;
import com.onepop.api.gui.widget.Widget;

public class SettingBindWidget extends Widget
{
    private ModuleClickGUI master;
    private CategoryFrame frame;
    private ModuleScrollContainer container;
    private ModuleWidget widget;
    private int offsetX;
    private int offsetY;
    private int offsetWidth;
    private int offsetHeight;
    private int alphaAnimationPressed;
    private int animationX;
    private int animationY;
    private int animationApplierY;
    private boolean isMouseClickedLeft;
    private boolean isMouseClickedRight;
    private boolean isBinding;
    private ValueBind setting;
    public Flag flagMouse;
    public Flag flagAnimation;
    
    public SettingBindWidget(final ModuleClickGUI master, final CategoryFrame frame, final ModuleScrollContainer container, final ModuleWidget widget, final ValueBind setting) {
        super(setting.getName());
        this.setting = setting;
        this.master = master;
        this.frame = frame;
        this.container = container;
        this.widget = widget;
        this.rect.setWidth(this.container.getRect().getWidth() - this.offsetX * 2);
        this.rect.setHeight((float)(3 + TurokFontManager.getStringHeight(this.master.fontWidgetModule, this.rect.getTag()) + 3));
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.flagAnimation = Flag.ANIMATION_END;
    }
    
    @Override
    public boolean isEnabled() {
        return this.setting.isEnabled();
    }
    
    public ValueBind getSetting() {
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
        this.isMouseClickedLeft = false;
    }
    
    @Override
    public void onCustomScreenClosed() {
    }
    
    @Override
    public void onKeyboardPressed(final char charCode, final int keyCode) {
        switch (keyCode) {
            case 1: {
                if (this.isBinding) {
                    this.isBinding = false;
                    this.master.setCanceledCloseGUI(false);
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public void onCustomKeyboardPressed(final char charCode, final int keyCode) {
        if (this.isBinding) {
            switch (keyCode) {
                case 1: {
                    break;
                }
                case 211: {
                    this.isBinding = false;
                    this.setting.setKeyCode(-1);
                    this.setting.setInputType(ValueBind.InputType.KEYBOARD);
                    this.master.setCanceledCloseGUI(false);
                    break;
                }
                default: {
                    this.isBinding = false;
                    this.setting.setKeyCode(keyCode);
                    this.setting.setInputType(ValueBind.InputType.KEYBOARD);
                    this.master.setCanceledCloseGUI(false);
                    break;
                }
            }
        }
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (this.isBinding && (button == 0 || button == 1 || button == 2)) {
            this.isBinding = false;
            this.master.setCanceledCloseGUI(false);
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        if (button != 0 && button != 1 && button != 2 && this.isBinding) {
            this.setting.setKeyCode(button);
            this.setting.setInputType(ValueBind.InputType.MOUSE);
            this.master.setCanceledCloseGUI(false);
            this.isBinding = false;
        }
        if (this.flagMouse == Flag.MOUSE_OVER) {
            if (this.isMouseClickedLeft) {
                this.isBinding = true;
                this.master.setCanceledCloseGUI(true);
                this.isMouseClickedLeft = false;
            }
            if (this.isMouseClickedRight) {
                this.setting.setState(!this.setting.getState());
                this.isMouseClickedRight = false;
            }
        }
        else {
            this.isMouseClickedLeft = false;
            this.isMouseClickedRight = false;
        }
    }
    
    @Override
    public void onMouseClicked(final int button) {
        if (this.isBinding && (button == 0 || button == 1 || button == 2)) {
            this.isBinding = false;
            this.master.setCanceledCloseGUI(false);
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        if (this.flagMouse == Flag.MOUSE_OVER) {
            if (button == 0) {
                this.isMouseClickedLeft = true;
            }
            if (button == 1) {
                this.isMouseClickedRight = true;
            }
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
        if (this.setting.getState()) {
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
        final String keyCodeName = this.setting.getName() + " " + (this.isBinding ? "<Binding>" : ((this.setting.getKeyCode() != -1) ? ("<" + ((this.setting.getInputType() == ValueBind.InputType.KEYBOARD) ? Keyboard.getKeyName(this.setting.getKeyCode()) : Mouse.getButtonName(this.setting.getKeyCode())) + ">") : "<NONE>"));
        TurokFontManager.render(this.master.fontWidgetModule, keyCodeName, this.rect.getX() + 1.0f, this.rect.getY() + 3.0f, true, new Color(255, 255, 255));
    }
    
    @Override
    public void onCustomRender() {
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
    }
}
