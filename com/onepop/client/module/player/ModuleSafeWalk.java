// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Safe Walk", tag = "SafeWalk", description = "Sneak on normal speed.", category = ModuleCategory.PLAYER)
public class ModuleSafeWalk extends Module
{
    public static ModuleSafeWalk INSTANCE;
    
    public ModuleSafeWalk() {
        ModuleSafeWalk.INSTANCE = this;
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
    }
}
