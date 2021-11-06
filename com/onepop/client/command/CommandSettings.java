// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.command;

import com.onepop.api.module.management.ModuleManager;
import com.onepop.Onepop;
import com.onepop.api.command.Command;

public class CommandSettings extends Command
{
    public CommandSettings() {
        super(new String[] { "settings", "config" }, "Config command.");
    }
    
    @Override
    public String setSyntax() {
        return "settings/config <char>";
    }
    
    @Override
    public void onCommand(final String[] args) {
        String mode = null;
        if (args.length > 1) {
            mode = args[1];
        }
        if (args.length > 2 || mode == null) {
            this.splash();
            return;
        }
        if (this.verify(mode, "save")) {
            Onepop.getModuleManager().onSave();
            Onepop.getSocialManager().onSave();
            this.print("Settings saved");
        }
        if (this.verify(mode, "load")) {
            Onepop.getModuleManager().onLoad();
            Onepop.getSocialManager().onLoad();
            ModuleManager.reload();
            ModuleManager.refresh();
            this.print("Settings loaded");
        }
    }
}
