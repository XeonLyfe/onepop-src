//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.util.entity.PlayerUtil;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.client.gui.GuiGameOver;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import me.rina.turok.util.TurokTick;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Respawn", tag = "AutoRespawn", description = "Automatically respawn after you die.", category = ModuleCategory.MISC)
public class ModuleAutoRespawn extends Module
{
    public static ValueNumber settingDelay;
    public static ValueBoolean settingDeathPosition;
    public static ValueBoolean settingSync;
    private boolean hasSentMessage;
    private final TurokTick tick;
    
    public ModuleAutoRespawn() {
        this.tick = new TurokTick();
    }
    
    @Listener
    public void onListen(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        final boolean flag = ModuleAutoRespawn.settingSync.getValue() ? (ModuleAutoRespawn.mc.player.getHealth() <= 0.0f) : (ModuleAutoRespawn.mc.currentScreen instanceof GuiGameOver);
        if (flag) {
            this.doRequest();
        }
        else {
            this.hasSentMessage = true;
            this.tick.reset();
        }
    }
    
    public void doRequest() {
        if (this.tick.isPassedMS(ModuleAutoRespawn.settingDelay.getValue().floatValue() * 1000.0f)) {
            if (ModuleAutoRespawn.settingDeathPosition.getValue()) {
                final double[] pos = PlayerUtil.getPos();
                final String position = "[" + (int)pos[0] + ", " + (int)pos[1] + ", " + (int)pos[2] + "]";
                if (this.hasSentMessage) {
                    this.print("You died at " + this.getColorBasedDimension() + position);
                    this.hasSentMessage = false;
                }
            }
            ModuleAutoRespawn.mc.player.respawnPlayer();
        }
    }
    
    public String getColorBasedDimension() {
        String string = "";
        if (PlayerUtil.getCurrentDimension() == PlayerUtil.Dimension.WORLD) {
            string = "" + ChatFormatting.GREEN;
        }
        if (PlayerUtil.getCurrentDimension() == PlayerUtil.Dimension.NETHER) {
            string = "" + ChatFormatting.RED;
        }
        if (PlayerUtil.getCurrentDimension() == PlayerUtil.Dimension.END) {
            string = "" + ChatFormatting.BLUE;
        }
        return string;
    }
    
    static {
        ModuleAutoRespawn.settingDelay = new ValueNumber("Delay", "Delay", "The seconds delay for respawn.", 0, 0, 10);
        ModuleAutoRespawn.settingDeathPosition = new ValueBoolean("Death Position", "DeathPosition", "Send the last position client side message after you die.", true);
        ModuleAutoRespawn.settingSync = new ValueBoolean("Sync", "Sync", "Respawn only if death GUI screen is shown.", true);
    }
}
