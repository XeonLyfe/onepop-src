//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.chat;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.Onepop;

public class ChatUtil
{
    public static void print(final String message) {
        if (Onepop.getMinecraft().ingameGUI == null) {
            return;
        }
        final String formatedMessage = ChatFormatting.GRAY + message;
        Onepop.getMinecraft().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(ChatFormatting.GRAY + message));
    }
    
    public static void malloc(final String message) {
        if (Onepop.getMinecraft().ingameGUI == null) {
            return;
        }
        Onepop.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(message);
    }
    
    public static void message(final String message) {
        if (Onepop.getMinecraft().player == null) {
            return;
        }
        Onepop.getMinecraft().player.connection.sendPacket((Packet)new CPacketChatMessage(message));
    }
}
