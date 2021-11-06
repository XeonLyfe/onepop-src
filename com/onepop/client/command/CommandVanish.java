//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.command;

import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.Onepop;
import com.onepop.api.command.Command;

public class CommandVanish extends Command
{
    public CommandVanish() {
        super(new String[] { "v", "vanish", "riding", "ride" }, "Vanish mount/unmount.");
    }
    
    @Override
    public String setSyntax() {
        return "v/vanish/riding/ride <mount/on/dismount/off>";
    }
    
    @Override
    public void onCommand(final String[] args) {
        String first = null;
        if (args.length > 1) {
            first = args[1];
        }
        if (args.length > 2 || first == null) {
            this.splash();
            return;
        }
        final Minecraft mc = Onepop.getMinecraft();
        final boolean flag = mc.player.isRiding() || mc.player.getRidingEntity() != null;
        if (this.verify(first, "mount", "on")) {
            if (flag) {
                this.splash(ChatFormatting.RED + "Are you riding?");
            }
            else {
                final Entity entity = Onepop.getEntityWorldManager().getEntity(250);
                if (entity == null) {
                    this.splash(ChatFormatting.RED + "Were you riding?");
                    return;
                }
                mc.player.startRiding(entity);
                this.splash("Mounted");
                Onepop.getEntityWorldManager().removeEntity(250);
            }
        }
        if (this.verify(first, "dismount", "off")) {
            if (!flag) {
                this.splash(ChatFormatting.RED + "Are you riding?");
                return;
            }
            Onepop.getEntityWorldManager().saveEntity(250, mc.player.getRidingEntity());
            mc.player.dismountRidingEntity();
            this.splash("Dismounted");
        }
    }
}
