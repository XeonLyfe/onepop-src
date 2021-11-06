// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.command.management;

import java.util.Iterator;
import com.onepop.api.command.impl.CommandPrefix;
import com.onepop.api.command.Command;
import java.util.ArrayList;

public class CommandManager
{
    public static CommandManager INSTANCE;
    private ArrayList<Command> commandList;
    private CommandPrefix commandPrefix;
    
    public CommandManager() {
        CommandManager.INSTANCE = this;
        this.commandList = new ArrayList<Command>();
        this.commandPrefix = new CommandPrefix(".");
    }
    
    public void setCommandList(final ArrayList<Command> commandList) {
        this.commandList = commandList;
    }
    
    public ArrayList<Command> getCommandList() {
        return this.commandList;
    }
    
    public static CommandPrefix getCommandPrefix() {
        return CommandManager.INSTANCE.commandPrefix;
    }
    
    public void registry(final Command command) {
        this.commandList.add(command);
    }
    
    public void unregister(final Command command) {
        if (get(command.getClass()) != null) {
            this.commandList.remove(command);
        }
    }
    
    public static Command get(final Class<?> clazz) {
        for (final Command commands : CommandManager.INSTANCE.getCommandList()) {
            if (commands.getClass() == clazz) {
                return commands;
            }
        }
        return null;
    }
    
    public static Command get(final String _alias) {
        for (final Command commands : CommandManager.INSTANCE.getCommandList()) {
            for (final String alias : commands.getAlias()) {
                if (alias.equalsIgnoreCase(_alias)) {
                    return commands;
                }
            }
        }
        return null;
    }
    
    public String[] split(final String message) {
        return message.replaceFirst(this.commandPrefix.getPrefix(), "").split(" ");
    }
}
