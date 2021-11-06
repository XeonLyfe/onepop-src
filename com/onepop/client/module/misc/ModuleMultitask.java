// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Multitask", tag = "Multitask", description = "Magic hands!", category = ModuleCategory.MISC)
public class ModuleMultitask extends Module
{
    public static ModuleMultitask INSTANCE;
    
    public ModuleMultitask() {
        ModuleMultitask.INSTANCE = this;
    }
}
