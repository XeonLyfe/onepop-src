//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import com.onepop.api.util.chat.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.Onepop;
import net.minecraft.entity.Entity;
import com.onepop.api.ISLClass;
import com.onepop.client.event.client.ClientTickEvent;
import java.util.ArrayList;
import java.util.List;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Visual Range", tag = "VisualRange", description = "Get players on visual range.", category = ModuleCategory.RENDER)
public class ModuleVisualRange extends Module
{
    private List<String> people;
    
    @Override
    public void onEnable() {
        this.people = new ArrayList<String>();
    }
    
    @Listener
    public void onUpdate(final ClientTickEvent event) {
        if (ISLClass.mc.world == null | ISLClass.mc.world == null) {
            return;
        }
        final List<String> players = new ArrayList<String>();
        final List<EntityPlayer> playerEntities = ISLClass.mc.world.playerEntities;
        for (final Entity e : playerEntities) {
            if (e.getName().equals(Onepop.MC.player.getName())) {
                continue;
            }
            players.add(e.getName());
        }
        if (players.size() > 0) {
            for (final String name : players) {
                if (!this.people.contains(name)) {
                    ChatUtil.print(ChatFormatting.GRAY + name + ChatFormatting.RESET + "Is now your visual range");
                }
                this.people.add(name);
            }
        }
    }
}
