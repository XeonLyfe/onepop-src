//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3i;
import com.onepop.api.util.world.BlocksUtil;
import com.onepop.api.util.world.BlockUtil;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.util.entity.PlayerUtil;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Reverse Step", tag = "ReverseStep", description = "Step down blocks.", category = ModuleCategory.PLAYER)
public class ModuleReverseStep extends Module
{
    public static ValueBoolean settingPacket;
    public static ValueEnum settingMode;
    public static ValueBoolean settingOnlyHole;
    public static ValueNumber settingHoleHeight;
    
    @Override
    public void onSetting() {
        ModuleReverseStep.settingHoleHeight.setEnabled(ModuleReverseStep.settingOnlyHole.getValue());
    }
    
    @Listener
    public void onListen(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (!ModuleReverseStep.mc.player.onGround || ModuleReverseStep.mc.player.isOnLadder() || ModuleReverseStep.mc.player.isInWater() || ModuleReverseStep.mc.player.isInLava() || ModuleReverseStep.mc.player.movementInput.jump || ModuleReverseStep.mc.player.noClip) {
            return;
        }
        if (ModuleReverseStep.mc.player.field_191988_bg == 0.0f && ModuleReverseStep.mc.player.moveStrafing == 0.0f) {
            return;
        }
        if (ModuleReverseStep.settingOnlyHole.getValue()) {
            final BlockPos player = PlayerUtil.getBlockPos();
            boolean fall = ModuleReverseStep.settingHoleHeight.getValue().intValue() == 1 && this.isHole(player.add(0, -1, 0));
            if (ModuleReverseStep.settingHoleHeight.getValue().intValue() != 1) {
                for (int y = 0; y < ModuleReverseStep.settingHoleHeight.getValue().intValue() + 1; ++y) {
                    if (this.isHole(player.add(0, -y, 0)) || this.isHole(player)) {
                        fall = true;
                    }
                }
            }
            if (fall || this.isHole(player)) {
                this.doFall();
            }
        }
        else {
            this.doFall();
        }
    }
    
    public boolean isHole(final BlockPos position) {
        if (!BlockUtil.isAir(position)) {
            return false;
        }
        int count = 0;
        for (final BlockPos add : BlocksUtil.SURROUND) {
            final BlockPos added = position.add((Vec3i)add);
            if (BlockUtil.isAir(added)) {
                ++count;
            }
        }
        return count == 0;
    }
    
    public void doFall() {
        final boolean flag = ModuleReverseStep.settingPacket.getValue();
        switch (ModuleReverseStep.settingMode.getValue()) {
            case SMOOTH: {
                if (flag) {
                    final NetHandlerPlayClient connection = ModuleReverseStep.mc.player.connection;
                    final double posX = ModuleReverseStep.mc.player.posX;
                    final EntityPlayerSP player = ModuleReverseStep.mc.player;
                    final double posY = player.posY;
                    player.posY = posY - 1.0;
                    connection.sendPacket((Packet)new CPacketPlayer.Position(posX, posY, ModuleReverseStep.mc.player.posZ, ModuleReverseStep.mc.player.onGround));
                    break;
                }
                final EntityPlayerSP player2 = ModuleReverseStep.mc.player;
                --player2.motionY;
                break;
            }
            case NORMAL: {
                if (flag) {
                    final NetHandlerPlayClient connection2 = ModuleReverseStep.mc.player.connection;
                    final double posX2 = ModuleReverseStep.mc.player.posX;
                    final EntityPlayerSP player3 = ModuleReverseStep.mc.player;
                    connection2.sendPacket((Packet)new CPacketPlayer.Position(posX2, --player3.posY, ModuleReverseStep.mc.player.posZ, ModuleReverseStep.mc.player.onGround));
                    break;
                }
                final EntityPlayerSP player4 = ModuleReverseStep.mc.player;
                --player4.motionY;
                break;
            }
        }
    }
    
    static {
        ModuleReverseStep.settingPacket = new ValueBoolean("Packet", "Packet", "Sends packet together falls.", false);
        ModuleReverseStep.settingMode = new ValueEnum("Mode", "Mode", "The modes for you fall!", Mode.SMOOTH);
        ModuleReverseStep.settingOnlyHole = new ValueBoolean("Only Hole", "OnlyHole", "Step down only if you falling at one hole!", true);
        ModuleReverseStep.settingHoleHeight = new ValueNumber("Hole Height", "HoleHeight", "The height of falls to get down.", 1, 1, 4);
    }
    
    public enum Mode
    {
        SMOOTH, 
        NORMAL;
    }
}
