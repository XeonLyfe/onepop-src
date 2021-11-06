//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import com.onepop.client.manager.network.RotationManager;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.item.ItemBow;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Quiver", tag = "Quiver", description = "Automatically quivers arrow to your self.", category = ModuleCategory.COMBAT)
public class ModuleQuiver extends Module
{
    public static ValueBoolean settingManual;
    public static ValueEnum settingMode;
    
    @Override
    public void onSetting() {
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        final boolean withBowHandEquipped = ModuleQuiver.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow;
        switch (ModuleQuiver.settingMode.getValue()) {
            case TOGGLE: {
                if (withBowHandEquipped && ModuleQuiver.mc.player.getItemInUseMaxCount() >= 3) {
                    this.doQuiver();
                    this.setDisabled();
                    break;
                }
                break;
            }
            case SMART: {
                if (withBowHandEquipped && ModuleQuiver.mc.player.isHandActive() && ModuleQuiver.mc.player.getItemInUseMaxCount() >= 3) {
                    this.doQuiver();
                    break;
                }
                break;
            }
        }
    }
    
    public void doQuiver() {
        final float[] rotates = { ModuleQuiver.mc.player.rotationYaw, -90.0f };
        final RotationManager.Rotation rotate = ModuleQuiver.settingManual.getValue() ? RotationManager.Rotation.MANUAL : RotationManager.Rotation.SEND;
        RotationManager.task(rotate, rotates);
        ModuleQuiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, ModuleQuiver.mc.player.getHorizontalFacing()));
        ModuleQuiver.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        RotationManager.task(RotationManager.Rotation.REL, rotates);
    }
    
    static {
        ModuleQuiver.settingManual = new ValueBoolean("Manual", "Manual", "Quivers manually.", false);
        ModuleQuiver.settingMode = new ValueEnum("Mode", "Mode", "Modes for quiver.", Mode.SMART);
    }
    
    public enum Mode
    {
        TOGGLE, 
        SMART;
    }
}
