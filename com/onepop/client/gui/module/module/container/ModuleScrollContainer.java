// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.module.module.container;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import com.onepop.api.gui.flag.Flag;
import me.rina.turok.render.opengl.TurokShaderGL;
import me.rina.turok.util.TurokMath;
import com.onepop.api.setting.Setting;
import me.rina.turok.util.TurokRect;
import java.util.Iterator;
import com.onepop.client.gui.module.module.widget.ModuleWidget;
import com.onepop.api.module.Module;
import com.onepop.Onepop;
import com.onepop.api.gui.widget.Widget;
import java.util.ArrayList;
import com.onepop.client.gui.module.category.CategoryFrame;
import com.onepop.client.gui.module.ModuleClickGUI;
import com.onepop.api.gui.container.Container;

public class ModuleScrollContainer extends Container
{
    private ModuleClickGUI master;
    private CategoryFrame frame;
    private int offsetX;
    private int offsetY;
    private int animationHeight;
    private int offsetWidth;
    private int offsetHeight;
    private boolean hasWheel;
    private ArrayList<Widget> loadedWidgetList;
    
    public ModuleScrollContainer(final ModuleClickGUI master, final CategoryFrame frame) {
        super(frame.getRect().getTag() + "Scroll");
        this.master = master;
        this.frame = frame;
        this.offsetX = 1;
        this.init();
    }
    
    public void init() {
        this.loadedWidgetList = new ArrayList<Widget>();
        int count = 0;
        for (final Module modules : Onepop.getModuleManager().getModuleList()) {
            if (modules.getCategory() != this.frame.getCategory()) {
                continue;
            }
            final ModuleWidget moduleWidget = new ModuleWidget(this.master, this.frame, this, modules);
            moduleWidget.setAnimationY(this.animationHeight);
            moduleWidget.setAnimationX(2);
            this.loadedWidgetList.add(moduleWidget);
            this.animationHeight += (int)(moduleWidget.getRect().getHeight() + 1.0f);
            if (count <= this.frame.getMaximumModule()) {
                final TurokRect rect = this.frame.getRect();
                rect.height += moduleWidget.getRect().getHeight() + 1.0f;
            }
            else {
                this.frame.setAbleToScissor(true);
            }
            ++count;
        }
    }
    
    public ArrayList<Widget> getLoadedWidgetList() {
        return this.loadedWidgetList;
    }
    
    public void refresh(final Module module, final Setting setting) {
        this.animationHeight = 0;
        this.frame.getRect().setHeight((float)(this.frame.getOffsetHeight() + this.animationHeight));
        final int flag = 0;
        for (final Widget widgets : this.loadedWidgetList) {
            if (widgets instanceof ModuleWidget) {
                final ModuleWidget moduleWidget = (ModuleWidget)widgets;
                moduleWidget.setAnimationY(this.animationHeight);
                moduleWidget.setOffsetY(this.animationHeight);
                if (moduleWidget.isWidgetOpened()) {
                    if (module.getTag().equalsIgnoreCase(((ModuleWidget)widgets).getModule().getTag())) {
                        moduleWidget.refresh(setting);
                    }
                    this.animationHeight += moduleWidget.getOffsetHeight() + ((this.loadedWidgetList.indexOf(widgets) == this.loadedWidgetList.size()) ? 1 : 0);
                }
                else {
                    this.animationHeight += (int)(moduleWidget.getRect().getHeight() + 1.0f);
                }
                this.frame.getRect().setHeight((float)TurokMath.clamp(this.frame.getOffsetHeight() + this.animationHeight, 0, Onepop.getWrapper().clampScrollHeight));
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
    
    public void setAnimationHeight(final int animationHeight) {
        this.animationHeight = animationHeight;
    }
    
    public int getAnimationHeight() {
        return this.animationHeight;
    }
    
    public void clampScroll(final int scissorHeight) {
        if (this.offsetY >= 0) {
            --this.offsetY;
            if (this.offsetY <= 1) {
                this.offsetY = 0;
            }
        }
        if (this.offsetY <= scissorHeight - this.rect.getHeight()) {
            ++this.offsetY;
            if (this.offsetX <= scissorHeight - this.rect.getHeight()) {
                this.offsetX = (int)(scissorHeight - this.rect.getHeight());
            }
        }
    }
    
    @Override
    public void onScreenOpened() {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onScreenOpened();
        }
    }
    
    @Override
    public void onCustomScreenOpened() {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomScreenOpened();
        }
    }
    
    @Override
    public void onScreenClosed() {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onScreenClosed();
        }
    }
    
    @Override
    public void onCustomScreenClosed() {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomScreenClosed();
        }
    }
    
    @Override
    public void onKeyboardPressed(final char charCode, final int keyCode) {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onKeyboardPressed(charCode, keyCode);
        }
    }
    
    @Override
    public void onCustomKeyboardPressed(final char charCode, final int keyCode) {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomKeyboardPressed(charCode, keyCode);
        }
    }
    
    @Override
    public void onMouseReleased(final int button) {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onMouseReleased(button);
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomMouseReleased(button);
        }
    }
    
    @Override
    public void onMouseClicked(final int button) {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onMouseClicked(button);
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomMouseClicked(button);
        }
    }
    
    @Override
    public void onRender() {
        final double diffValue = this.frame.getRect().getHeight() - (this.frame.getOffsetHeight() + this.animationHeight);
        final double diffFinal = TurokMath.sqrt(diffValue * diffValue);
        if (diffFinal < 20.0) {
            this.frame.getRect().setHeight((float)TurokMath.clamp(this.frame.getOffsetHeight() + this.animationHeight, 0, Onepop.getWrapper().clampScrollHeight));
        }
        else {
            this.frame.getRect().setHeight(TurokMath.lerp(this.frame.getRect().getHeight(), (float)TurokMath.clamp(this.frame.getOffsetHeight() + this.animationHeight, 0, Onepop.getWrapper().clampScrollHeight), this.master.getDisplay().getPartialTicks()));
        }
        this.rect.setX(this.frame.getRect().getX() + this.offsetX);
        this.rect.setY(TurokMath.lerp(this.rect.getY(), this.frame.getRect().getY() + this.frame.getOffsetHeight() + this.offsetY, this.master.getDisplay().getPartialTicks()));
        this.rect.setWidth(this.frame.getRect().getWidth() - this.offsetX * 2);
        this.rect.setHeight((float)this.animationHeight);
        final int realHeightScissor = (int)(this.frame.getRect().getHeight() - this.frame.getOffsetHeight());
        for (final Widget widgets : this.loadedWidgetList) {
            TurokShaderGL.pushScissor();
            TurokShaderGL.drawScissor(this.rect.getX(), this.frame.getRect().getY() + this.frame.getOffsetHeight(), (this.rect.getX() + this.rect.getWidth() >= this.master.getClosedWidth()) ? (this.master.getClosedWidth() - (this.rect.getX() + this.rect.getWidth())) : this.rect.getWidth(), (float)realHeightScissor);
            widgets.onRender();
            TurokShaderGL.popScissor();
            if (widgets instanceof ModuleWidget) {
                final ModuleWidget moduleWidget = (ModuleWidget)widgets;
                moduleWidget.flagMouse = Flag.MOUSE_NOT_OVER;
            }
        }
    }
    
    @Override
    public void onCustomRender() {
        final int realHeightScissor = (int)(this.frame.getRect().getHeight() - this.frame.getOffsetHeight());
        final TurokRect scrollRect = new TurokRect(this.rect.getX(), this.frame.getRect().getY() + this.frame.getOffsetHeight(), this.rect.getWidth(), (float)realHeightScissor);
        final int minimumScroll = (int)(this.frame.getRect().getHeight() - this.frame.getOffsetHeight() - this.rect.getHeight());
        final int maximumScroll = 0;
        final boolean isScrollLimit = this.rect.getY() + this.rect.getHeight() >= this.frame.getRect().getY() + this.frame.getRect().getHeight() - realHeightScissor - 1.0f;
        final int i = -((this.hasWheel ? Mouse.getDWheel() : 0) / 10);
        if (scrollRect.collideWithMouse(this.master.getMouse())) {
            this.hasWheel = Mouse.hasWheel();
        }
        else {
            this.hasWheel = false;
        }
        if (this.hasWheel && isScrollLimit && scrollRect.collideWithMouse(this.master.getMouse()) && !Keyboard.isKeyDown(157) && !Keyboard.isKeyDown(29)) {
            this.offsetY -= i;
        }
        if (this.offsetY <= minimumScroll) {
            this.offsetY = minimumScroll;
        }
        else if (this.offsetY >= maximumScroll) {
            this.offsetY = maximumScroll;
        }
        for (final Widget widgets : this.loadedWidgetList) {
            if (scrollRect.collideWithMouse(this.master.getMouse())) {
                widgets.onCustomRender();
            }
        }
    }
}
