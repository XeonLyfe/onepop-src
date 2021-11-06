//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.command;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import com.onepop.api.util.chat.ChatUtil;
import com.onepop.Onepop;
import com.onepop.api.command.Command;

public class CommandCoords extends Command
{
    public CommandCoords() {
        super(new String[] { "coords", "c" }, "Copies your coordinates to the clipboard");
    }
    
    @Override
    public String setSyntax() {
        return "coords/c || coords/c name";
    }
    
    @Override
    public void onCommand(final String[] args) {
        if (args.length > 2) {
            this.splash();
            return;
        }
        final String coords = "X: " + Onepop.MC.player.getPosition().getX() + " Y: " + Onepop.MC.player.getPosition().getY() + " Z: " + Onepop.MC.player.getPosition().getZ();
        if (args.length > 1) {
            ChatUtil.message("/w " + args[1] + " " + coords);
            return;
        }
        final StringSelection stringSelection = new StringSelection(coords);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        this.print("Coordinates copied to clipboard");
    }
}
