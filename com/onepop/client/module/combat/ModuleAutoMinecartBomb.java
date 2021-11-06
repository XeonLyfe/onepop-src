// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto Minecart Bomb", tag = "AutoMinecartBomb", description = "Bombs enemy using minecart with TNT!", category = ModuleCategory.COMBAT)
public class ModuleAutoMinecartBomb extends Module
{
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
    }
}
