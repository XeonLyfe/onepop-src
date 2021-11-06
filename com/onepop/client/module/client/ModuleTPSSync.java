// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.client;

import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "TPS Sync", tag = "TPSSync", description = "Sync client actions with TPS.", category = ModuleCategory.CLIENT)
public class ModuleTPSSync extends Module
{
    public static ModuleTPSSync INSTANCE;
    
    public ModuleTPSSync() {
        ModuleTPSSync.INSTANCE = this;
    }
}
