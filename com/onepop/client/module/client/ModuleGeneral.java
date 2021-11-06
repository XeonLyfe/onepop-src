// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.client;

import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "General", tag = "General", description = "General client settings!", category = ModuleCategory.CLIENT)
public class ModuleGeneral extends Module
{
    public static ValueBoolean settingCompactWattermarkNotify;
    public static ValueBoolean settingFriendedMessage;
    public static ValueBoolean settingEnemyMesssage;
    
    @Override
    public void onSetting() {
        if (!this.isEnabled()) {
            this.setEnabled();
        }
    }
    
    static {
        ModuleGeneral.settingCompactWattermarkNotify = new ValueBoolean("Compact Wattermark Notify", "CompactWattermarkNotify", "Compact chat wattermark style!", true);
        ModuleGeneral.settingFriendedMessage = new ValueBoolean("Friended Message", "FriendedMessage", "Sends message for new friends!", false);
        ModuleGeneral.settingEnemyMesssage = new ValueBoolean("Enemy Message", "EnemyMessage", "Sends message for new enemies!", false);
    }
}
