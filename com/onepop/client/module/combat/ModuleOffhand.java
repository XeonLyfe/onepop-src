//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import com.onepop.api.util.item.SlotUtil;
import net.minecraft.entity.player.EntityPlayer;
import com.onepop.api.ISLClass;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import com.onepop.client.event.network.PacketEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueBind;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Offhand", tag = "Offhand", description = "Automatically set offhand item.", category = ModuleCategory.COMBAT)
public class ModuleOffhand extends Module
{
    public static ValueBoolean settingCustomBinds;
    public static ValueBind settingTotemBind;
    public static ValueBind settingGappleBind;
    public static ValueBind settingCrystalBind;
    public static ValueBind settingBowBind;
    public static ValueBind settingBedBind;
    public static ValueBind settingSplashBind;
    public static ValueBoolean settingSafe;
    public static ValueEnum settingOffhandMode;
    public static ValueEnum settingFindMode;
    public static ValueEnum settingNoFail;
    public static ValueNumber settingSmartTotem;
    private float currentHealth;
    
    @Override
    public void onSetting() {
        if (ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.TOTEM) {
            ModuleOffhand.settingSmartTotem.setEnabled(ModuleOffhand.settingNoFail.getValue() != NoFail.NONE);
        }
        else {
            ModuleOffhand.settingSmartTotem.setEnabled(false);
        }
        ModuleOffhand.settingTotemBind.setEnabled(ModuleOffhand.settingCustomBinds.getValue());
        ModuleOffhand.settingGappleBind.setEnabled(ModuleOffhand.settingCustomBinds.getValue());
        ModuleOffhand.settingCrystalBind.setEnabled(ModuleOffhand.settingCustomBinds.getValue());
        ModuleOffhand.settingBowBind.setEnabled(ModuleOffhand.settingCustomBinds.getValue());
        ModuleOffhand.settingBedBind.setEnabled(ModuleOffhand.settingCustomBinds.getValue());
        ModuleOffhand.settingSplashBind.setEnabled(ModuleOffhand.settingCustomBinds.getValue());
        if (ModuleOffhand.settingCustomBinds.getValue() && ModuleOffhand.settingTotemBind.getState() && ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.TOTEM) {
            ModuleOffhand.settingGappleBind.setState(false);
            ModuleOffhand.settingCrystalBind.setState(false);
            ModuleOffhand.settingBowBind.setState(false);
            ModuleOffhand.settingBedBind.setState(false);
            ModuleOffhand.settingSplashBind.setState(false);
            ModuleOffhand.settingOffhandMode.setValue(OffhandMode.TOTEM);
        }
        if (ModuleOffhand.settingCustomBinds.getValue() && ModuleOffhand.settingGappleBind.getState() && ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.GAPPLE) {
            ModuleOffhand.settingTotemBind.setState(false);
            ModuleOffhand.settingCrystalBind.setState(false);
            ModuleOffhand.settingBowBind.setState(false);
            ModuleOffhand.settingBedBind.setState(false);
            ModuleOffhand.settingSplashBind.setState(false);
            ModuleOffhand.settingOffhandMode.setValue(OffhandMode.GAPPLE);
        }
        if (ModuleOffhand.settingCustomBinds.getValue() && ModuleOffhand.settingCrystalBind.getState() && ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.CRYSTAL) {
            ModuleOffhand.settingTotemBind.setState(false);
            ModuleOffhand.settingGappleBind.setState(false);
            ModuleOffhand.settingBowBind.setState(false);
            ModuleOffhand.settingBedBind.setState(false);
            ModuleOffhand.settingSplashBind.setState(false);
            ModuleOffhand.settingOffhandMode.setValue(OffhandMode.CRYSTAL);
        }
        if (ModuleOffhand.settingCustomBinds.getValue() && ModuleOffhand.settingCrystalBind.getState() && ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.CRYSTAL) {
            ModuleOffhand.settingTotemBind.setState(false);
            ModuleOffhand.settingGappleBind.setState(false);
            ModuleOffhand.settingBowBind.setState(false);
            ModuleOffhand.settingBedBind.setState(false);
            ModuleOffhand.settingSplashBind.setState(false);
            ModuleOffhand.settingOffhandMode.setValue(OffhandMode.CRYSTAL);
        }
        if (ModuleOffhand.settingCustomBinds.getValue() && ModuleOffhand.settingBowBind.getState() && ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.BOW) {
            ModuleOffhand.settingTotemBind.setState(false);
            ModuleOffhand.settingGappleBind.setState(false);
            ModuleOffhand.settingCrystalBind.setState(false);
            ModuleOffhand.settingBedBind.setState(false);
            ModuleOffhand.settingSplashBind.setState(false);
            ModuleOffhand.settingOffhandMode.setValue(OffhandMode.BOW);
        }
        if (ModuleOffhand.settingCustomBinds.getValue() && ModuleOffhand.settingBedBind.getState() && ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.BED) {
            ModuleOffhand.settingTotemBind.setState(false);
            ModuleOffhand.settingGappleBind.setState(false);
            ModuleOffhand.settingCrystalBind.setState(false);
            ModuleOffhand.settingBowBind.setState(false);
            ModuleOffhand.settingSplashBind.setState(false);
            ModuleOffhand.settingOffhandMode.setValue(OffhandMode.BED);
        }
        if (ModuleOffhand.settingCustomBinds.getValue() && ModuleOffhand.settingSplashBind.getState() && ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.SPLASH) {
            ModuleOffhand.settingTotemBind.setState(false);
            ModuleOffhand.settingGappleBind.setState(false);
            ModuleOffhand.settingCrystalBind.setState(false);
            ModuleOffhand.settingBowBind.setState(false);
            ModuleOffhand.settingBedBind.setState(false);
            ModuleOffhand.settingOffhandMode.setValue(OffhandMode.SPLASH);
        }
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.doOffhandCalculate(ModuleOffhand.mc.player.getHealth());
    }
    
    @Listener
    public void onListen(final PacketEvent.Receive event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (event.getPacket() instanceof SPacketUpdateHealth && ModuleOffhand.settingNoFail.getValue() == NoFail.PACKET) {
            final SPacketUpdateHealth packet = (SPacketUpdateHealth)event.getPacket();
            this.doOffhandCalculate(this.currentHealth = packet.getHealth());
        }
    }
    
    public void doOffhandCalculate(final float health) {
        if (ModuleOffhand.mc.currentScreen != null) {
            return;
        }
        if (ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.TOTEM) {
            if (ModuleOffhand.settingNoFail.getValue() != NoFail.NONE) {
                if (health <= ModuleOffhand.settingSmartTotem.getValue().intValue() && ModuleOffhand.settingOffhandMode.getValue() != OffhandMode.TOTEM) {
                    this.doOffhand(Items.field_190929_cY);
                    return;
                }
                if (health > ModuleOffhand.settingSmartTotem.getValue().intValue() && ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.GAPPLE) {
                    this.doOffhand(Items.GOLDEN_APPLE);
                    return;
                }
                if (health > ModuleOffhand.settingSmartTotem.getValue().intValue() && ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.CRYSTAL) {
                    this.doOffhand(Items.END_CRYSTAL);
                    return;
                }
                if (health > ModuleOffhand.settingSmartTotem.getValue().intValue() && ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.BOW) {
                    this.doOffhand((Item)Items.BOW);
                    return;
                }
                if (health > ModuleOffhand.settingSmartTotem.getValue().intValue() && ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.BED) {
                    this.doOffhand(Items.BED);
                    return;
                }
                if (health > ModuleOffhand.settingSmartTotem.getValue().intValue() && ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.SPLASH) {
                    this.doOffhand((Item)Items.SPLASH_POTION);
                }
            }
            else if (ModuleOffhand.settingNoFail.getValue() == NoFail.NONE) {
                if (ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.GAPPLE) {
                    this.doOffhand(Items.GOLDEN_APPLE);
                    return;
                }
                if (ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.CRYSTAL) {
                    this.doOffhand(Items.END_CRYSTAL);
                    return;
                }
                if (ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.BOW) {
                    this.doOffhand((Item)Items.BOW);
                    return;
                }
                if (ModuleOffhand.settingOffhandMode.getValue() == OffhandMode.BED) {
                    this.doOffhand(Items.BED);
                }
            }
        }
        else {
            this.doOffhand(Items.field_190929_cY);
        }
    }
    
    public void doOffhand(final Item item) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleOffhand.mc.currentScreen instanceof GuiInventory || ModuleOffhand.mc.currentScreen instanceof GuiContainerCreative) {
            return;
        }
        if (ModuleOffhand.mc.player.getHeldItemOffhand().getItem() == item) {
            return;
        }
        final int slot = this.doFindSlot(item);
        if (slot != -1) {
            this.doOffhandUpdate(slot);
        }
        else if (item != Items.field_190929_cY && ModuleOffhand.settingSafe.getValue()) {
            final int slotTotems = this.doFindSlot(Items.field_190929_cY);
            if (slotTotems != -1 && ModuleOffhand.mc.player.getHeldItemOffhand().getItem() != Items.field_190929_cY) {
                this.doOffhandUpdate(slotTotems);
            }
        }
    }
    
    public void doOffhandUpdate(final int slot) {
        ModuleOffhand.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ISLClass.mc.player);
        ModuleOffhand.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)ISLClass.mc.player);
        ModuleOffhand.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ISLClass.mc.player);
        ModuleOffhand.mc.playerController.updateController();
    }
    
    public int doFindSlot(final Item item) {
        int slot = -1;
        switch (ModuleOffhand.settingFindMode.getValue()) {
            case FULL: {
                slot = SlotUtil.findItemSlot(item);
                break;
            }
            case INVENTORY: {
                slot = SlotUtil.findItemSlotFromInventory(item);
                break;
            }
            case HOT_BAR: {
                slot = ((SlotUtil.findItemSlotFromHotBar(item) != -1) ? (SlotUtil.findItemSlotFromHotBar(item) + 36) : -1);
                break;
            }
        }
        return slot;
    }
    
    static {
        ModuleOffhand.settingCustomBinds = new ValueBoolean("Custom Binds", "CustomBinds", "Enable custom keybinds for offhand!", true);
        ModuleOffhand.settingTotemBind = new ValueBind("Totem Bind", "TotemBind", "Sets totem bind.", -1);
        ModuleOffhand.settingGappleBind = new ValueBind("Gapple Bind", "GappleBind", "Sets gapple bind.", -1);
        ModuleOffhand.settingCrystalBind = new ValueBind("Crystal Bind", "CrystalBind", "Sets crystal bind.", -1);
        ModuleOffhand.settingBowBind = new ValueBind("Bow Bind", "BowBind", "Sets bow bind.", -1);
        ModuleOffhand.settingBedBind = new ValueBind("Bed Bind", "BedBind", "Sets bed bind.", -1);
        ModuleOffhand.settingSplashBind = new ValueBind("Splash Bind", "SplashBind", "Sets splash potion bind.", -1);
        ModuleOffhand.settingSafe = new ValueBoolean("Safe", "Safe", "Safe use for others modes not totem!", false);
        ModuleOffhand.settingOffhandMode = new ValueEnum("Offhand Mode", "OffhandMode", "Modes for items in your offhand.", OffhandMode.TOTEM);
        ModuleOffhand.settingFindMode = new ValueEnum("Find Mode", "FindMode", "Modes to find the item(s).", FindMode.FULL);
        ModuleOffhand.settingNoFail = new ValueEnum("No Fail", "NoFail", "No fails totem.", NoFail.HEALTH);
        ModuleOffhand.settingSmartTotem = new ValueNumber("Smart Totem", "SmartTotem", "Automatically sets and remove at life", 20, 1, 20);
    }
    
    public enum FindMode
    {
        INVENTORY, 
        HOT_BAR, 
        FULL;
    }
    
    public enum OffhandMode
    {
        TOTEM, 
        CRYSTAL, 
        GAPPLE, 
        BOW, 
        BED, 
        SPLASH;
    }
    
    public enum NoFail
    {
        PACKET, 
        HEALTH, 
        NONE;
    }
}
