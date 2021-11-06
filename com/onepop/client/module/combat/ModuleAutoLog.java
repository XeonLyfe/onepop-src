//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Log", tag = "AutoLog", description = "Automatically logs on combat...", category = ModuleCategory.COMBAT)
public class ModuleAutoLog extends Module
{
    public static ValueBoolean settingPacketKick;
    public static ValueNumber settingHealth;
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        final float health = ModuleAutoLog.mc.player.getHealth();
        if (health <= ModuleAutoLog.settingHealth.getValue().intValue() && health != 0.0f && !ModuleAutoLog.mc.player.isDead) {
            this.doLog();
            this.setDisabled();
        }
    }
    
    public void doLog() {
        if (ModuleAutoLog.settingPacketKick.getValue()) {
            ModuleAutoLog.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ModuleAutoLog.mc.player.posX, ModuleAutoLog.mc.player.posY + 50.0, ModuleAutoLog.mc.player.posZ, false));
        }
        ModuleAutoLog.mc.player.connection.getNetworkManager().closeChannel((ITextComponent)new TextComponentString("Auto Log!"));
    }
    
    static {
        ModuleAutoLog.settingPacketKick = new ValueBoolean("Packet Kick", "PacketKick", "Send packet before you log.", false);
        ModuleAutoLog.settingHealth = new ValueNumber("Health", "Health", "The health for log.", 6, 1, 20);
    }
}
