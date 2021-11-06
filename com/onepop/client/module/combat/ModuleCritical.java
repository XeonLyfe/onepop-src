//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import com.onepop.api.util.network.PacketUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import com.onepop.api.util.entity.PlayerUtil;
import com.onepop.api.util.client.KeyUtil;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import com.onepop.api.ISLClass;
import net.minecraft.network.play.client.CPacketUseEntity;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Critical", tag = "Critical", description = "Critical hits.", category = ModuleCategory.COMBAT)
public class ModuleCritical extends Module
{
    public static ValueEnum settingMode;
    
    @Listener
    public void onListen(final PacketEvent.Send event) {
        if (!(event.getPacket() instanceof CPacketUseEntity)) {
            return;
        }
        final CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
        final boolean flag = ISLClass.mc.player.onGround && packet.getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)ISLClass.mc.world) instanceof EntityLivingBase;
        if (flag && !KeyUtil.isPressed(ISLClass.mc.gameSettings.keyBindJump)) {
            switch (ModuleCritical.settingMode.getValue()) {
                case JUMP: {
                    ModuleCritical.mc.player.jump();
                    break;
                }
                case PACKET: {
                    PacketUtil.send((Packet<?>)new CPacketPlayer.Position(PlayerUtil.getPos()[0], PlayerUtil.getPos()[1] + 0.10000000149011612, PlayerUtil.getPos()[2], false));
                    PacketUtil.send((Packet<?>)new CPacketPlayer.Position(PlayerUtil.getPos()[0], PlayerUtil.getPos()[1], PlayerUtil.getPos()[2], false));
                    break;
                }
                case LOWHOP: {
                    ModuleCritical.mc.player.jump();
                    final EntityPlayerSP player = ModuleCritical.mc.player;
                    player.motionY /= 2.0;
                    break;
                }
            }
        }
    }
    
    static {
        ModuleCritical.settingMode = new ValueEnum("Mode", "Mode", "Critical action mode.", Mode.PACKET);
    }
    
    public enum Mode
    {
        PACKET, 
        JUMP, 
        LOWHOP;
    }
}
