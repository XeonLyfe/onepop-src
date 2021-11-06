// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.command;

import com.onepop.api.command.management.CommandManager;
import com.onepop.api.command.Command;

public class CommandPrefix extends Command
{
    public CommandPrefix() {
        super(new String[] { "prefix", "p" }, "Sets a new prefix.");
    }
    
    @Override
    public String setSyntax() {
        return "prefix/p <char>";
    }
    
    @Override
    public void onCommand(final String[] args) {
        String _char = null;
        if (args.length > 1) {
            _char = args[1];
        }
        if (args.length > 2 || _char == null) {
            this.splash();
            return;
        }
        CommandManager.getCommandPrefix().setPrefix(_char);
        this.print("Chat prefix has been update to '" + CommandManager.getCommandPrefix().getPrefix() + "'");
    }
}
