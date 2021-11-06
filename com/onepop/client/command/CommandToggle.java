// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.command;

import com.onepop.api.module.Module;
import com.onepop.api.setting.value.ValueBoolean;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.module.management.ModuleManager;
import com.onepop.api.command.Command;

public class CommandToggle extends Command
{
    public CommandToggle() {
        super(new String[] { "t", "toggle" }, "Toggle modules.");
    }
    
    @Override
    public String setSyntax() {
        return "t/toggle <module>";
    }
    
    @Override
    public void onCommand(final String[] args) {
        String tag = null;
        if (args.length > 1) {
            tag = args[1];
        }
        if (args.length > 2 || tag == null) {
            this.splash();
            return;
        }
        final Module module = ModuleManager.get(tag);
        if (module == null) {
            this.print(ChatFormatting.RED + "Unknown module");
            return;
        }
        module.toggle();
        final ValueBoolean toggleMessage = (ValueBoolean)module.get("ToggleMessage");
        if (!toggleMessage.getValue()) {
            this.print("Module has been updated to " + module.isEnabled());
        }
    }
}
