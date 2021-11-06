//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.command;

import java.util.Iterator;
import net.minecraft.client.network.NetworkPlayerInfo;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.Onepop;
import com.onepop.api.social.Social;
import me.rina.turok.util.TurokClass;
import com.onepop.api.social.type.SocialType;
import com.onepop.api.social.management.SocialManager;
import com.onepop.client.manager.network.PlayerServerManager;
import com.onepop.api.command.Command;

public class CommandSocial extends Command
{
    public CommandSocial() {
        super(new String[] { "friend", "enemy" }, "Command to add or remove friends/enemies.");
    }
    
    @Override
    public String setSyntax() {
        return "friend/enemy <list> | <add/new/put/set/forced> <name> | <rem/remove/del/delete/unset> <name>";
    }
    
    @Override
    public void onCommand(final String[] args) {
        String first = null;
        String second = null;
        if (args.length > 1) {
            first = args[1];
        }
        if (args.length > 2) {
            second = args[2];
        }
        if (args.length > 3) {
            this.splash();
            return;
        }
        if (first == null) {
            this.splash();
            return;
        }
        if (!this.verify(args[0], "friend", "enemy")) {
            this.splash();
            return;
        }
        if (this.verify(first, "add", "put", "set", "new", "forced")) {
            if (second == null) {
                this.splash();
                return;
            }
            final NetworkPlayerInfo player = PlayerServerManager.get(second);
            if (player == null && !first.equalsIgnoreCase("forced")) {
                this.splash("Is the player online?");
                return;
            }
            if (player != null && SocialManager.get(player.getGameProfile().getName()) != null) {
                this.splash("Player already at social list");
                return;
            }
            final Social user = new Social(first.equalsIgnoreCase("forced") ? second : player.getGameProfile().getName(), (SocialType)TurokClass.getEnumByName(SocialType.UNKNOWN, args[0].toUpperCase()));
            Onepop.getSocialManager().registry(user);
            this.splash("Added " + user.getName() + " " + args[0].toLowerCase());
        }
        else if (this.verify(first, "rem", "remove", "delete", "del", "unset")) {
            final Social social = SocialManager.get(second);
            if (social == null) {
                this.splash("Unknown friend or enemy");
                return;
            }
            this.splash("You removed " + social.getName());
            Onepop.getSocialManager().unregister(social);
        }
        else {
            if (this.verify(first, "list")) {
                final StringBuilder stringBuilder = new StringBuilder();
                if (second == null) {
                    if (Onepop.getSocialManager().getSocialList().isEmpty()) {
                        this.splash("Social list is empty!");
                        return;
                    }
                    this.splash("Type " + ChatFormatting.GREEN + "friend " + ChatFormatting.RED + "enemy");
                }
                else {
                    if (!this.verify(args[0], "friend", "enemy")) {
                        this.splash(ChatFormatting.RED + "friend/enemy");
                        return;
                    }
                    if (Onepop.getSocialManager().getSocialList().isEmpty()) {
                        this.splash("Social list is empty!");
                        return;
                    }
                }
                for (final Social social2 : Onepop.getSocialManager().getSocialList()) {
                    if (second != null) {
                        if (args[0].equalsIgnoreCase("friend") && social2.getType() == SocialType.ENEMY) {
                            continue;
                        }
                        if (args[0].equalsIgnoreCase("enemy") && social2.getType() == SocialType.FRIEND) {
                            continue;
                        }
                    }
                    final String name = "" + ((social2.getType() == SocialType.FRIEND) ? (ChatFormatting.GREEN + social2.getName()) : (ChatFormatting.RED + social2.getName())) + ChatFormatting.WHITE;
                    stringBuilder.append(name + "; ");
                }
                if (stringBuilder.length() == 0) {
                    this.splash("Social list is empty!");
                }
                else {
                    this.splash(stringBuilder.toString());
                }
                return;
            }
            this.splash();
        }
    }
}
