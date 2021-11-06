// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.client;

import com.onepop.Onepop;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "RPC", tag = "RPC", description = "The cool RPC.", category = ModuleCategory.CLIENT)
public class ModuleRPC extends Module
{
    public static ModuleRPC INSTANCE;
    public static ValueBoolean showName;
    
    public ModuleRPC() {
        ModuleRPC.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        Onepop.getRPC().run();
    }
    
    @Override
    public void onDisable() {
        Onepop.getRPC().stop();
    }
    
    static {
        ModuleRPC.showName = new ValueBoolean("Show Name", "RPCShowName", "shows your name in the rpc", true);
    }
}
