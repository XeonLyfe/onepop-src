//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.google.common.eventbus.Subscribe;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import com.onepop.api.ISLClass;
import net.minecraft.item.ItemBow;
import net.minecraft.init.Items;
import com.onepop.Onepop;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Fast Use", tag = "FastUse", description = "Fast use stuff.", category = ModuleCategory.MISC)
public class ModuleFastUse extends Module
{
    public static ValueBoolean exp;
    public static ValueBoolean place;
    public static ValueBoolean crystal;
    public static ValueBoolean bow;
    public static ValueBoolean pearl;
    public static ValueBoolean snowball;
    public static ValueBoolean all;
    
    @Subscribe
    @Listener
    public void onPacketSend(final PacketEvent.Send event) {
        if (ModuleFastUse.exp.getValue() && Onepop.MC.player != null && (Onepop.MC.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || Onepop.MC.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE)) {
            new Thread(() -> this.imc.setRightClickDelayTimer(0)).start();
        }
        if (ModuleFastUse.bow.getValue() && Onepop.MC.player != null && ISLClass.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && ISLClass.mc.player.isHandActive() && ISLClass.mc.player.getItemInUseMaxCount() >= 3) {
            new Thread(() -> {
                ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, ISLClass.mc.player.getHorizontalFacing()));
                ISLClass.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(ISLClass.mc.player.getActiveHand()));
                ISLClass.mc.player.stopActiveHand();
                return;
            }).start();
        }
        if (ModuleFastUse.place.getValue() && Onepop.MC.player != null && (Onepop.MC.player.getHeldItemMainhand().getItem() instanceof ItemBlock || Onepop.MC.player.getHeldItemOffhand().getItem() instanceof ItemBlock)) {
            new Thread(() -> this.imc.setRightClickDelayTimer(0)).start();
        }
        if (ModuleFastUse.crystal.getValue() && Onepop.MC.player != null && (Onepop.MC.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal || Onepop.MC.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal)) {
            new Thread(() -> this.imc.setRightClickDelayTimer(0)).start();
        }
        if (ModuleFastUse.pearl.getValue() && Onepop.MC.player != null && (Onepop.MC.player.getHeldItemMainhand().getItem() instanceof ItemEnderPearl || Onepop.MC.player.getHeldItemOffhand().getItem() instanceof ItemEnderPearl)) {
            new Thread(() -> this.imc.setRightClickDelayTimer(0)).start();
        }
        if (ModuleFastUse.snowball.getValue() && Onepop.MC.player != null && (Onepop.MC.player.getHeldItemMainhand().getItem() instanceof ItemSnowball || Onepop.MC.player.getHeldItemOffhand().getItem() instanceof ItemSnowball)) {
            new Thread(() -> this.imc.setRightClickDelayTimer(0)).start();
        }
        if (ModuleFastUse.all.getValue() && Onepop.MC.player != null) {
            Onepop.MC.player.getHeldItemMainhand().getItem();
            new Thread(() -> this.imc.setRightClickDelayTimer(0)).start();
            Onepop.MC.player.getHeldItemOffhand().getItem();
            new Thread(() -> this.imc.setRightClickDelayTimer(0)).start();
        }
    }
    
    static {
        ModuleFastUse.exp = new ValueBoolean("Experience Bottle", "ExperienceBottle", "Makes xp go brrrr", true);
        ModuleFastUse.place = new ValueBoolean("Block", "Block", "Makes xp go brrrr", false);
        ModuleFastUse.crystal = new ValueBoolean("End Crystal", "EndCrystal", "Makes xp go brrrr", true);
        ModuleFastUse.bow = new ValueBoolean("Bow", "Bow", "Makes xp go brrrr", true);
        ModuleFastUse.pearl = new ValueBoolean("Pearl", "Pearl", "Makes xp go brrrr", true);
        ModuleFastUse.snowball = new ValueBoolean("Snowballs", "snowballs", "Makes xp go brrrr", true);
        ModuleFastUse.all = new ValueBoolean("All", "All", "Makes xp go brrrr", false);
    }
}
