// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.module.management;

import com.onepop.api.module.impl.ModuleCategory;
import java.lang.reflect.Field;
import com.onepop.api.setting.Setting;
import java.util.Iterator;
import com.onepop.api.setting.value.ValueBind;
import com.onepop.api.module.Module;
import java.util.ArrayList;
import com.onepop.api.ISLClass;

public class ModuleManager implements ISLClass
{
    public static ModuleManager INSTANCE;
    private ArrayList<Module> moduleList;
    
    public ModuleManager() {
        ModuleManager.INSTANCE = this;
        this.moduleList = new ArrayList<Module>();
    }
    
    public void setModuleList(final ArrayList<Module> moduleList) {
        this.moduleList = moduleList;
    }
    
    public ArrayList<Module> getModuleList() {
        return this.moduleList;
    }
    
    public void onInput(final int keyCode, final ValueBind.InputType inputType) {
        for (final Module modules : this.getModuleList()) {
            modules.onInput(keyCode, inputType);
        }
    }
    
    public void registry(final Module module) {
        try {
            for (final Field fields : module.getClass().getDeclaredFields()) {
                if (Setting.class.isAssignableFrom(fields.getType())) {
                    if (!fields.isAccessible()) {
                        fields.setAccessible(true);
                    }
                    final Setting settingDeclared = (Setting)fields.get(module);
                    module.registry(settingDeclared);
                }
            }
            this.moduleList.add(module);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    public void unregister(final Module module) {
        if (get(module.getClass()) != null) {
            this.moduleList.remove(module);
        }
    }
    
    public static Module get(final Class<?> clazz) {
        for (final Module modules : ModuleManager.INSTANCE.getModuleList()) {
            if (modules.getClass() == clazz) {
                return modules;
            }
        }
        return null;
    }
    
    public static Module get(final String tag) {
        for (final Module modules : ModuleManager.INSTANCE.getModuleList()) {
            if (modules.getTag().equalsIgnoreCase(tag)) {
                return modules;
            }
        }
        return null;
    }
    
    public static ArrayList<Module> get(final ModuleCategory category) {
        final ArrayList<Module> moduleListRequested = new ArrayList<Module>();
        for (final Module modules : ModuleManager.INSTANCE.getModuleList()) {
            if (modules.getCategory() == category) {
                moduleListRequested.add(modules);
            }
        }
        return moduleListRequested;
    }
    
    public static void reload() {
        for (final Module modules : ModuleManager.INSTANCE.getModuleList()) {
            modules.onReload();
        }
    }
    
    public static void refresh() {
        for (final Module modules : ModuleManager.INSTANCE.getModuleList()) {
            modules.onSetting();
        }
    }
    
    @Override
    public void onSave() {
        for (final Module modules : this.getModuleList()) {
            modules.onSave();
        }
    }
    
    @Override
    public void onLoad() {
        for (final Module modules : this.getModuleList()) {
            modules.onLoad();
        }
    }
}
