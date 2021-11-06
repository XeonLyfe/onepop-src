//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.component.management;

import org.lwjgl.opengl.Display;
import me.rina.turok.util.TurokMath;
import me.rina.turok.util.TurokDisplay;
import net.minecraft.client.Minecraft;
import com.onepop.Onepop;
import java.util.Iterator;
import java.lang.reflect.Field;
import com.onepop.api.component.impl.ComponentSetting;
import java.awt.Font;
import me.rina.turok.render.font.TurokFont;
import me.rina.turok.util.TurokRect;
import me.rina.turok.util.TurokTick;
import com.onepop.api.component.Component;
import java.util.ArrayList;

public class ComponentManager
{
    public static ComponentManager INSTANCE;
    private ArrayList<Component> componentList;
    private ArrayList<Component> componentListTopLeft;
    private ArrayList<Component> componentListTopRight;
    private ArrayList<Component> componentListBottomLeft;
    private ArrayList<Component> componentListBottomRight;
    private final TurokTick tickRefresh;
    private boolean isUpdate;
    private TurokRect rectTopLeft;
    private TurokRect rectTopRight;
    private TurokRect rectBottomLeft;
    private TurokRect rectBottomRight;
    public TurokFont font;
    private int offsetChat;
    
    public ComponentManager() {
        this.tickRefresh = new TurokTick();
        this.rectTopLeft = new TurokRect("TopLeft", 0.0f, 0.0f, 10.0f, 0.0f);
        this.rectTopRight = new TurokRect("TopRight", 0.0f, 0.0f, 10.0f, 0.0f);
        this.rectBottomLeft = new TurokRect("BottomLeft", 0.0f, 0.0f, 10.0f, 0.0f);
        this.rectBottomRight = new TurokRect("BottomRight", 0.0f, 0.0f, 10.0f, 0.0f);
        ComponentManager.INSTANCE = this;
        this.componentList = new ArrayList<Component>();
        this.componentListTopLeft = new ArrayList<Component>();
        this.componentListTopRight = new ArrayList<Component>();
        this.componentListBottomLeft = new ArrayList<Component>();
        this.componentListBottomRight = new ArrayList<Component>();
        this.font = new TurokFont(new Font("Whitney", 0, 19), true, true);
    }
    
    public void registry(final Component component) {
        try {
            for (final Field fields : component.getClass().getDeclaredFields()) {
                if (ComponentSetting.class.isAssignableFrom(fields.getType())) {
                    if (!fields.isAccessible()) {
                        fields.setAccessible(true);
                    }
                    final ComponentSetting<?> setting = (ComponentSetting<?>)fields.get(component);
                    component.registry(setting);
                }
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        this.componentList.add(component);
    }
    
    public void setFont(final TurokFont font) {
        this.font = font;
    }
    
    public TurokFont getFont() {
        return this.font;
    }
    
    public void setComponentList(final ArrayList<Component> componentList) {
        this.componentList = componentList;
    }
    
    public ArrayList<Component> getComponentList() {
        return this.componentList;
    }
    
    public void setComponentListTopLeft(final ArrayList<Component> componentListTopLeft) {
        this.componentListTopLeft = componentListTopLeft;
    }
    
    public ArrayList<Component> getComponentListTopLeft() {
        return this.componentListTopLeft;
    }
    
    public void setComponentListTopRight(final ArrayList<Component> componentListTopRight) {
        this.componentListTopRight = componentListTopRight;
    }
    
    public ArrayList<Component> getComponentListTopRight() {
        return this.componentListTopRight;
    }
    
    public void setComponentListBottomLeft(final ArrayList<Component> componentListBottomLeft) {
        this.componentListBottomLeft = componentListBottomLeft;
    }
    
    public ArrayList<Component> getComponentListBottomLeft() {
        return this.componentListBottomLeft;
    }
    
    public void setComponentListBottomRight(final ArrayList<Component> componentListBottomRight) {
        this.componentListBottomRight = componentListBottomRight;
    }
    
    public ArrayList<Component> getComponentListBottomRight() {
        return this.componentListBottomRight;
    }
    
    public void setRectTopLeft(final TurokRect rectTopLeft) {
        this.rectTopLeft = rectTopLeft;
    }
    
    public TurokRect getRectTopLeft() {
        return this.rectTopLeft;
    }
    
    public void setRectTopRight(final TurokRect rectTopRight) {
        this.rectTopRight = rectTopRight;
    }
    
    public TurokRect getRectTopRight() {
        return this.rectTopRight;
    }
    
    public void setRectBottomLeft(final TurokRect rectBottomLeft) {
        this.rectBottomLeft = rectBottomLeft;
    }
    
    public TurokRect getRectBottomLeft() {
        return this.rectBottomLeft;
    }
    
    public void setRectBottomRight(final TurokRect rectBottomRight) {
        this.rectBottomRight = rectBottomRight;
    }
    
    public TurokRect getRectBottomRight() {
        return this.rectBottomRight;
    }
    
    public static Component get(final Class clazz) {
        for (final Component components : ComponentManager.INSTANCE.getComponentList()) {
            if (components.getClass() == clazz) {
                return components;
            }
        }
        return null;
    }
    
    public static Component get(final String tag) {
        for (final Component components : ComponentManager.INSTANCE.getComponentList()) {
            if (components.getRect().getTag().equalsIgnoreCase(tag)) {
                return components;
            }
        }
        return null;
    }
    
    public void onSaveList() {
        for (final Component components : this.componentList) {
            components.onSave();
        }
    }
    
    public void onLoadList() {
        for (final Component components : this.componentList) {
            components.onLoad();
        }
    }
    
    public void onRenderComponentList() {
        for (final Component components : this.componentList) {
            if (components.isEnabled()) {
                components.onRender();
            }
        }
    }
    
    public void onCornerDetectorComponentList(float partialTicks) {
        partialTicks = Onepop.getClientEventManager().getCurrentRender2DPartialTicks();
        final TurokDisplay display = new TurokDisplay(Minecraft.getMinecraft());
        if (Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
            this.offsetChat = (int)TurokMath.serp((float)this.offsetChat, 14.0f, partialTicks);
        }
        else {
            this.offsetChat = (int)TurokMath.serp((float)this.offsetChat, 0.0f, partialTicks);
        }
        if (Display.wasResized() && !this.isUpdate) {
            this.isUpdate = true;
            this.tickRefresh.reset();
        }
        if (this.tickRefresh.isPassedMS(13500.0f)) {
            this.isUpdate = false;
        }
        for (final Component components : this.componentList) {
            if (this.isUpdate) {
                return;
            }
            if (components.isEnabled() && components.getRect().collideWithRect(this.rectTopLeft)) {
                if (!this.componentListTopLeft.contains(components)) {
                    this.componentListTopLeft.add(components);
                }
            }
            else if (this.componentListTopLeft.contains(components) && !this.isUpdate) {
                this.componentListTopLeft.remove(components);
            }
            if (components.isEnabled() && components.getRect().collideWithRect(this.rectTopRight)) {
                if (!this.componentListTopRight.contains(components)) {
                    this.componentListTopRight.add(components);
                }
            }
            else if (this.componentListTopRight.contains(components) && !this.isUpdate) {
                this.componentListTopRight.remove(components);
            }
            if (components.isEnabled() && components.getRect().collideWithRect(this.rectBottomLeft)) {
                if (!this.componentListBottomLeft.contains(components)) {
                    this.componentListBottomLeft.add(components);
                }
            }
            else if (this.componentListBottomLeft.contains(components) && !this.isUpdate) {
                this.componentListBottomLeft.remove(components);
            }
            if (components.isEnabled() && components.getRect().collideWithRect(this.rectBottomRight)) {
                if (this.componentListBottomRight.contains(components)) {
                    continue;
                }
                this.componentListBottomRight.add(components);
            }
            else {
                if (!this.componentListBottomRight.contains(components) || this.isUpdate) {
                    continue;
                }
                this.componentListBottomRight.remove(components);
            }
        }
        int memoryPositionLengthTopLeft = 1;
        for (final Component components2 : this.componentListTopLeft) {
            if (!components2.isDragging()) {
                components2.getRect().setX(1.0f);
                components2.getRect().setY((float)memoryPositionLengthTopLeft);
            }
            memoryPositionLengthTopLeft = (int)(components2.getRect().getY() + components2.getRect().getHeight() + 1.0f);
        }
        this.rectTopLeft.setX(1.0f);
        this.rectTopLeft.setY(1.0f);
        this.rectTopLeft.setHeight((float)memoryPositionLengthTopLeft);
        int memoryPositionLengthTopRight = 1;
        for (final Component components3 : this.componentListTopRight) {
            if (!components3.isDragging()) {
                components3.getRect().setX(display.getScaledWidth() - components3.getRect().getWidth() - 1.0f);
                components3.getRect().setY((float)memoryPositionLengthTopRight);
            }
            memoryPositionLengthTopRight = (int)(components3.getRect().getY() + components3.getRect().getHeight() + 1.0f);
        }
        this.rectTopRight.setX(display.getScaledWidth() - this.rectTopRight.getWidth() - 1.0f);
        this.rectTopRight.setY(1.0f);
        this.rectTopRight.setHeight((float)memoryPositionLengthTopRight);
        int memoryPositionLengthBottomLeft = display.getScaledHeight() - this.offsetChat - 1;
        for (final Component components4 : this.componentListBottomLeft) {
            if (!components4.isDragging()) {
                components4.getRect().setX(1.0f);
                components4.getRect().setY(memoryPositionLengthBottomLeft - components4.getRect().getHeight() - 1.0f);
            }
            memoryPositionLengthBottomLeft = (int)(components4.getRect().getY() - 1.0f);
        }
        this.rectBottomLeft.setX(1.0f);
        this.rectBottomLeft.setY(display.getScaledHeight() - this.rectBottomLeft.getHeight() - 1.0f);
        this.rectBottomLeft.setHeight((float)(display.getScaledHeight() - memoryPositionLengthBottomLeft));
        int memoryPositionLengthBottomRight = display.getScaledHeight();
        for (final Component components5 : this.componentListBottomRight) {
            if (!components5.isDragging()) {
                components5.getRect().setX(display.getScaledWidth() - components5.getRect().getWidth() - 1.0f);
                components5.getRect().setY(memoryPositionLengthBottomRight - components5.getRect().getHeight() - this.offsetChat - 1.0f);
            }
            memoryPositionLengthBottomRight = (int)(components5.getRect().getY() - 1.0f);
        }
        this.rectBottomRight.setX(display.getScaledWidth() - this.rectBottomRight.getWidth() - 1.0f);
        this.rectBottomRight.setY(display.getScaledHeight() - this.rectBottomRight.getHeight() - 1.0f);
        this.rectBottomRight.setHeight((float)(display.getScaledHeight() - memoryPositionLengthBottomRight));
    }
}
