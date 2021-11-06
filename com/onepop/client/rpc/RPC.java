//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.rpc;

import java.util.Objects;
import net.minecraft.client.multiplayer.ServerData;
import com.onepop.client.module.client.ModuleRPC;
import club.minnced.discord.rpc.DiscordEventHandlers;
import net.minecraft.client.Minecraft;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordRPC;

public class RPC
{
    public final DiscordRPC discordRPC;
    public DiscordRichPresence discordPresence;
    public final Minecraft mc;
    public String detailOption1;
    public String detailOption2;
    public String detailOption3;
    public String detailOption4;
    public String stateOption1;
    public String stateOption2;
    public String stateOption3;
    public String stateOption4;
    
    public RPC() {
        this.mc = Minecraft.getMinecraft();
        this.discordRPC = DiscordRPC.INSTANCE;
        this.discordPresence = new DiscordRichPresence();
        this.detailOption1 = "";
        this.detailOption2 = "";
        this.detailOption3 = "";
        this.detailOption4 = "";
        this.stateOption1 = "";
        this.stateOption2 = "";
        this.stateOption3 = "";
        this.stateOption4 = "";
    }
    
    public void stop() {
        this.discordRPC.Discord_Shutdown();
    }
    
    public void run() {
        this.discordPresence = new DiscordRichPresence();
        final DiscordEventHandlers handler_ = new DiscordEventHandlers();
        this.discordRPC.Discord_Initialize("519692561443717130", handler_, true, "");
        this.discordPresence.largeImageText = "1pop 2.0beta";
        this.discordPresence.largeImageKey = "discord";
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (this.mc.world == null) {
                        this.detailOption1 = "";
                        this.detailOption2 = "sleeping on da 1pop.club";
                        this.stateOption1 = "zzz";
                    }
                    else {
                        this.detailOption1 = "";
                        if (this.mc.isIntegratedServerRunning()) {
                            this.detailOption2 = "1popping some nn's";
                            this.stateOption1 = "get 1popped kiddo";
                        }
                        else if (ModuleRPC.showName.getValue()) {
                            this.detailOption2 = "1popping at " + Objects.requireNonNull(this.mc.getCurrentServerData()).serverIP;
                            this.stateOption1 = this.mc.player.getName() + " 1pop.club hittin' p100";
                        }
                        else {
                            this.detailOption2 = "1popping at " + Objects.requireNonNull(this.mc.getCurrentServerData()).serverIP;
                            this.stateOption1 = "1pop.club hittin' p100";
                        }
                    }
                    final String detail = this.detailOption1 + this.detailOption2 + this.detailOption3 + this.detailOption4;
                    final String state = this.stateOption1 + this.stateOption2 + this.stateOption3 + this.stateOption4;
                    this.discordRPC.Discord_RunCallbacks();
                    this.discordPresence.details = detail;
                    this.discordPresence.state = state;
                    this.discordRPC.Discord_UpdatePresence(this.discordPresence);
                }
                catch (Exception exc) {
                    exc.printStackTrace();
                }
                try {
                    Thread.sleep(4000L);
                }
                catch (InterruptedException exc_) {
                    exc_.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }
    
    public String set(final String presume) {
        return " " + presume;
    }
}
