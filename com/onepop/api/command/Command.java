// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.command;

import com.onepop.api.util.chat.ChatUtil;
import com.onepop.Onepop;
import com.mojang.realmsclient.gui.ChatFormatting;

public class Command
{
    private String[] alias;
    private String description;
    
    public Command(final String[] alias, final String description) {
        this.alias = alias;
        this.description = description;
    }
    
    public void setAlias(final String[] alias) {
        this.alias = alias;
    }
    
    public String[] getAlias() {
        return this.alias;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void splash() {
        this.print(ChatFormatting.RED + this.setSyntax());
    }
    
    public void splash(final String splash) {
        this.print(splash);
    }
    
    public void print(final String message) {
        ChatUtil.print(Onepop.CHAT + message);
    }
    
    public boolean verify(final String argument, final String... possibles) {
        boolean isVerified = false;
        for (final String strings : possibles) {
            if (argument.equalsIgnoreCase(strings)) {
                isVerified = true;
                break;
            }
        }
        return isVerified;
    }
    
    public String setSyntax() {
        return null;
    }
    
    public void onCommand(final String[] args) {
    }
}
