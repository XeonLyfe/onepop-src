//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import java.util.Random;
import java.util.List;
import java.nio.file.Path;
import java.io.IOException;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.FileWriter;
import java.nio.file.Files;
import com.onepop.api.util.file.FileUtil;
import java.nio.file.Paths;
import com.onepop.api.social.Social;
import java.util.Iterator;
import com.onepop.api.util.chat.ChatUtil;
import com.onepop.api.social.type.SocialType;
import com.onepop.api.social.management.SocialManager;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import com.onepop.client.event.network.PacketEvent;
import net.minecraft.entity.player.EntityPlayer;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto Kill Message", tag = "AutoKillMessage", description = "AutoKillMessage", category = ModuleCategory.MISC)
public class ModuleAutoKillMessage extends Module
{
    public static ValueNumber settingRangeOut;
    private EntityPlayer targetPlayer;
    private String[] killMessages;
    private boolean sendMessage;
    
    @Override
    public void onSetting() {
    }
    
    @Override
    public void onEnable() {
        this.doLoadMessageKill();
    }
    
    @Override
    public void onDisable() {
    }
    
    @Listener
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketDestroyEntities && this.targetPlayer != null) {
            final SPacketDestroyEntities packets = (SPacketDestroyEntities)event.getPacket();
            for (final int entityIds : packets.getEntityIDs()) {
                final Entity entity = ModuleAutoKillMessage.mc.world.getEntityByID(entityIds);
                if (entity instanceof EntityPlayer) {
                    final EntityPlayer player = (EntityPlayer)entity;
                    if (player.getHealth() <= 0.0f) {
                        if (entityIds == this.targetPlayer.getEntityId()) {
                            this.sendMessage = true;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        EntityPlayer onTarget = null;
        for (final EntityPlayer entities : ModuleAutoKillMessage.mc.world.playerEntities) {
            if (entities == ModuleAutoKillMessage.mc.player) {
                continue;
            }
            if (ModuleAutoKillMessage.mc.player.getDistanceToEntity((Entity)entities) >= ModuleAutoKillMessage.settingRangeOut.getValue().floatValue()) {
                continue;
            }
            final Social social = SocialManager.get(entities.getName());
            if (social != null && social.getType() == SocialType.FRIEND) {
                if (social.getType() == SocialType.FRIEND) {
                    continue;
                }
                if (social.getType() == SocialType.ENEMY) {
                    onTarget = entities;
                    break;
                }
            }
            onTarget = entities;
        }
        if (this.sendMessage && this.targetPlayer != null) {
            final String message = this.chooseMessageKill().replaceAll("<player>", this.targetPlayer.getName());
            if (!message.isEmpty()) {
                ChatUtil.message(message);
            }
            this.targetPlayer = null;
            this.sendMessage = false;
        }
        else {
            this.targetPlayer = onTarget;
        }
    }
    
    public void doLoadMessageKill() {
        final String name = "onepop/KillMessages.txt";
        final Path path = Paths.get("onepop/KillMessages.txt", new String[0]);
        try {
            FileUtil.createFolderIfNeeded(Paths.get("onepop/", new String[0]));
            FileUtil.createFileIfNeeded(path);
            final List<String> loadedFileLines = Files.readAllLines(path);
            if (loadedFileLines.isEmpty()) {
                final FileWriter file = new FileWriter("onepop/KillMessages.txt");
                for (final String messagesByDefault : new String[] { "Good game <player>!", "Good game <player>, buy onepop!" }) {
                    file.write(messagesByDefault + "\r\n");
                }
                file.close();
                this.print(ChatFormatting.YELLOW + "File is empty, enable module again for set default messages.");
                this.setDisabled();
                return;
            }
            this.killMessages = new String[loadedFileLines.size()];
            int index = 0;
            for (final String lines : loadedFileLines) {
                if (lines.isEmpty()) {
                    continue;
                }
                this.killMessages[index] = lines;
                ++index;
            }
        }
        catch (IOException exc) {
            this.print(ChatFormatting.RED + "A error occurred in messages kill load file.");
            return;
        }
        this.print(ChatFormatting.GREEN + "Successfully loaded messages kill!");
    }
    
    public String chooseMessageKill() {
        if (this.killMessages.length == 0) {
            this.print(ChatFormatting.RED + "The data length of kill messages are 0, or the file is corrupted.");
            return "";
        }
        return this.killMessages[new Random().nextInt(this.killMessages.length)];
    }
    
    static {
        ModuleAutoKillMessage.settingRangeOut = new ValueNumber("Range Out", "RangeOut", "Removes target from client if is out of range.", 13.0f, 19.0f, 1.0f);
    }
}
