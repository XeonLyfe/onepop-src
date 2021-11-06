// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import java.util.Iterator;
import me.rina.turok.util.TurokMath;
import java.awt.Color;
import me.rina.turok.render.opengl.deprecated.TurokRenderGL;
import me.rina.turok.util.TurokDisplay;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import com.onepop.Onepop;
import com.onepop.api.module.management.ModuleManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import me.rina.turok.util.TurokRect;
import com.onepop.api.module.Module;
import java.util.List;
import com.onepop.api.component.impl.ComponentSetting;
import com.onepop.api.component.Component;

public class ComponentArrayList extends Component
{
    public static ComponentSetting<Boolean> settingAnimation;
    public static ComponentSetting<Integer> settingBackgroundAlpha;
    public static ComponentSetting<Boolean> settingStyle;
    private List<Module> hackList;
    private List<TurokRect> rectList;
    
    public ComponentArrayList() {
        super("Array List", "ArrayList", "List of enabled modules.", StringType.USE);
        this.hackList = new ArrayList<Module>();
        this.rectList = new ArrayList<TurokRect>();
    }
    
    @Override
    public void onRender(final float partialTicks) {
        final Comparator<Module> comparatorModule = (module1, module2) -> {
            new StringBuilder().append(module1.getTag());
            String str;
            if (module1.getStatus() == null) {
                str = "";
            }
            else if (ComponentArrayList.settingStyle.getValue()) {
                str = ChatFormatting.GRAY + " [" + module1.getStatus() + ChatFormatting.GRAY + "]";
            }
            else {
                str = ChatFormatting.GRAY + module1.getStatus();
            }
            final StringBuilder sb;
            final String k = sb.append(str).toString();
            new StringBuilder().append(module2.getTag());
            String str2;
            if (module2.getStatus() == null) {
                str2 = "";
            }
            else if (ComponentArrayList.settingStyle.getValue()) {
                str2 = ChatFormatting.GRAY + " [" + module2.getStatus() + ChatFormatting.GRAY + "]";
            }
            else {
                str2 = ChatFormatting.GRAY + module2.getStatus();
            }
            final StringBuilder sb2;
            final String s = sb2.append(str2).toString();
            final float diff = (float)(this.getStringWidth(s) - this.getStringWidth(k));
            if (this.getDock() == Dock.TOP_LEFT || this.getDock() == Dock.TOP_RIGHT) {
                return (diff != 0.0f) ? ((int)diff) : s.compareTo(k);
            }
            else {
                return (int)diff;
            }
        };
        final Comparator<TurokRect> comparatorRect = (r1, r2) -> {
            final Module rect2 = ModuleManager.get(r1.getTag());
            final Module rect3 = ModuleManager.get(r2.getTag());
            new StringBuilder().append(rect2.getTag());
            String str3;
            if (rect2.getStatus() == null) {
                str3 = "";
            }
            else if (ComponentArrayList.settingStyle.getValue()) {
                str3 = ChatFormatting.GRAY + " [" + rect2.getStatus() + ChatFormatting.GRAY + "]";
            }
            else {
                str3 = ChatFormatting.GRAY + rect2.getStatus();
            }
            final StringBuilder sb3;
            final String i = sb3.append(str3).toString();
            new StringBuilder().append(rect3.getTag());
            String str4;
            if (rect3.getStatus() == null) {
                str4 = "";
            }
            else if (ComponentArrayList.settingStyle.getValue()) {
                str4 = ChatFormatting.GRAY + " [" + rect3.getStatus() + ChatFormatting.GRAY + "]";
            }
            else {
                str4 = ChatFormatting.GRAY + rect3.getStatus();
            }
            final StringBuilder sb4;
            final String s2 = sb4.append(str4).toString();
            final float diff2 = (float)(this.getStringWidth(s2) - this.getStringWidth(i));
            if (this.getDock() == Dock.TOP_LEFT || this.getDock() == Dock.TOP_RIGHT) {
                return (diff2 != 0.0f) ? ((int)diff2) : s2.compareTo(i);
            }
            else {
                return (int)diff2;
            }
        };
        boolean top = true;
        if (this.getDock() == Dock.TOP_LEFT || this.getDock() == Dock.TOP_RIGHT) {
            this.hackList = Onepop.getModuleManager().getModuleList().stream().filter(Module::shouldRenderOnArrayList).sorted((Comparator<? super Object>)comparatorModule).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        }
        else {
            top = false;
            final Module module;
            this.hackList = Onepop.getModuleManager().getModuleList().stream().filter(Module::shouldRenderOnArrayList).sorted(Comparator.comparing(module -> {
                new StringBuilder().append(module.getTag());
                String str5;
                if (module.getStatus() == null) {
                    str5 = "";
                }
                else if (ComponentArrayList.settingStyle.getValue()) {
                    str5 = ChatFormatting.GRAY + " [" + module.getStatus() + ChatFormatting.GRAY + "]";
                }
                else {
                    str5 = ChatFormatting.GRAY + module.getStatus();
                }
                final StringBuilder sb5;
                return Integer.valueOf(this.getStringWidth(sb5.append(str5).toString()));
            })).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
        }
        if (!ComponentArrayList.settingAnimation.getValue()) {
            this.rectList.clear();
        }
        for (final Module modules : this.hackList) {
            if (modules.isEnabled()) {
                final TurokRect rect = this.getRect(modules.getTag());
                if (rect != null) {
                    continue;
                }
                this.rectList.add(new TurokRect(modules.getTag(), 0.0f, 0.0f));
            }
        }
        if (top) {
            this.rectList.sort(comparatorRect);
        }
        else {
            final TurokRect rects;
            this.rectList.sort(Comparator.comparing(rects -> {
                new StringBuilder().append(ModuleManager.get(rects.getTag()).getTag());
                String str6;
                if (ModuleManager.get(rects.getTag()).getStatus() == null) {
                    str6 = "";
                }
                else if (ComponentArrayList.settingStyle.getValue()) {
                    str6 = ChatFormatting.GRAY + " [" + ModuleManager.get(rects.getTag()).getStatus() + ChatFormatting.GRAY + "]";
                }
                else {
                    str6 = ChatFormatting.GRAY + ModuleManager.get(rects.getTag()).getStatus();
                }
                final StringBuilder sb6;
                return Integer.valueOf(this.getStringWidth(sb6.append(str6).toString()));
            }));
        }
        final TurokDisplay display = new TurokDisplay(ComponentArrayList.mc);
        TurokRenderGL.enableState(3089);
        TurokRenderGL.drawScissor(this.rect.getX(), this.rect.getY(), this.rect.getWidth(), this.rect.getHeight(), display);
        int cache = 0;
        final int offsetX = (this.dock == Dock.TOP_LEFT || this.dock == Dock.BOTTOM_LEFT) ? 0 : 0;
        final int offsetW = (this.dock == Dock.TOP_RIGHT || this.dock == Dock.BOTTOM_RIGHT) ? 0 : 0;
        final Iterator<TurokRect> iterator2 = this.rectList.iterator();
        while (iterator2.hasNext()) {
            final TurokRect rects = iterator2.next();
            final Module module = ModuleManager.get(rects.getTag());
            if (module != null) {
                if (!module.shouldRenderOnArrayList()) {
                    continue;
                }
                final String tag = module.getTag() + ((module.getStatus() == null) ? "" : (ComponentArrayList.settingStyle.getValue() ? (ChatFormatting.GRAY + " [" + module.getStatus() + ChatFormatting.GRAY + "]") : (ChatFormatting.GRAY + module.getStatus())));
                rects.setWidth((float)this.getStringWidth(tag));
                rects.setHeight((float)this.getStringHeight(tag));
                if (ComponentArrayList.settingBackgroundAlpha.getValue() != 0) {
                    this.render((int)rects.getX() + offsetW, cache, (float)(this.getStringWidth(tag) + offsetX), (float)this.getStringHeight(tag), new Color(0, 0, 0, ComponentArrayList.settingBackgroundAlpha.getValue()));
                }
                this.render(tag, rects.getX(), (float)cache);
                if (rects.getWidth() >= this.rect.getWidth()) {
                    this.rect.setWidth(rects.getWidth());
                }
                if (!module.isEnabled()) {
                    rects.setX(TurokMath.lerp(rects.getX(), (float)this.verifyDock((float)this.getClamp(rects, 0), rects.getWidth()), partialTicks * 0.1f));
                }
                else {
                    rects.setX(TurokMath.lerp(rects.getX(), 0.0f, partialTicks * 0.1f));
                }
                if (this.verifyClamp(rects, 2)) {
                    continue;
                }
                cache += (int)rects.getHeight();
            }
        }
        this.rect.setHeight((float)cache);
        TurokRenderGL.disable(3089);
    }
    
    public TurokRect getRect(final String tag) {
        for (final TurokRect rects : this.rectList) {
            if (rects.getTag().equalsIgnoreCase(tag)) {
                return rects;
            }
        }
        return null;
    }
    
    public boolean verifyClamp(final TurokRect k, final int diff) {
        boolean flag = false;
        if (this.dock == Dock.TOP_LEFT || this.dock == Dock.BOTTOM_LEFT) {
            final int clamp = this.getClamp(k, diff);
            if (k.getX() <= clamp) {
                flag = true;
            }
        }
        if (this.dock == Dock.TOP_RIGHT || this.dock == Dock.BOTTOM_RIGHT) {
            final int clamp = this.getClamp(k, diff);
            if (this.verifyDock(k.getX(), k.getWidth()) >= clamp) {
                flag = true;
            }
        }
        return flag;
    }
    
    public int getClamp(final TurokRect k, final int diff) {
        if (this.dock == Dock.TOP_LEFT || this.dock == Dock.BOTTOM_LEFT) {
            return (int)(-k.getWidth() + diff);
        }
        if (this.dock == Dock.TOP_RIGHT || this.dock == Dock.BOTTOM_RIGHT) {
            return (int)(this.rect.getWidth() * 2.0f - diff);
        }
        return (int)this.rect.getX();
    }
    
    static {
        ComponentArrayList.settingAnimation = new ComponentSetting<Boolean>("Animation", "Animation", "Smooth animation!", false);
        ComponentArrayList.settingBackgroundAlpha = new ComponentSetting<Integer>("Background Alpha", "Background Alpha", "Background alpha!", 100, 0, 255);
        ComponentArrayList.settingStyle = new ComponentSetting<Boolean>("[<- Detail ->]", "Detail", "Cool detail!", true);
    }
}
