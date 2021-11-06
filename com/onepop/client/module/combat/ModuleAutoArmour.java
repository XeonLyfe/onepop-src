//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import java.util.Iterator;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import com.onepop.api.util.item.SlotUtil;
import com.onepop.api.util.item.ItemUtil;
import net.minecraft.init.Items;
import com.onepop.api.event.impl.EventStage;
import com.onepop.client.event.client.ClientTickEvent;
import me.rina.turok.util.TurokTick;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Armor", tag = "AutoArmor", description = "Auto equip armour, and no, the name module is not in british.", category = ModuleCategory.COMBAT)
public class ModuleAutoArmour extends Module
{
    private final TurokTick tick;
    
    public ModuleAutoArmour() {
        this.tick = new TurokTick();
    }
    
    @Listener
    public void onTick(final ClientTickEvent event) {
        if (ModuleAutoArmour.mc.player == null) {
            return;
        }
        if (event.getStage() == EventStage.PRE) {
            int selectedSlotId = -1;
            if (this.tick.isPassedMS(100.0f)) {
                if (ModuleAutoArmour.mc.player.inventory.armorItemInSlot(2).getItem() == Items.field_190931_a) {
                    for (final Item item : ItemUtil.ALL_CHEST_PLATES) {
                        final int slotId = SlotUtil.findItemSlot(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }
                if (ModuleAutoArmour.mc.player.inventory.armorItemInSlot(1).getItem() == Items.field_190931_a) {
                    for (final Item item : ItemUtil.ALL_LEGGINGS) {
                        final int slotId = SlotUtil.findItemSlot(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }
                if (ModuleAutoArmour.mc.player.inventory.armorItemInSlot(0).getItem() == Items.field_190931_a) {
                    for (final Item item : ItemUtil.ALL_BOOTS) {
                        final int slotId = SlotUtil.findItemSlot(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }
                if (ModuleAutoArmour.mc.player.inventory.armorItemInSlot(3).getItem() == Items.field_190931_a) {
                    for (final Item item : ItemUtil.ALL_HELMETS) {
                        final int slotId = SlotUtil.findItemSlot(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }
                if (selectedSlotId != -1) {
                    if (selectedSlotId < 9) {
                        selectedSlotId += 36;
                    }
                    ModuleAutoArmour.mc.playerController.windowClick(0, selectedSlotId, 0, ClickType.QUICK_MOVE, (EntityPlayer)ModuleAutoArmour.mc.player);
                    this.tick.reset();
                }
            }
        }
    }
    
    public void doEquipArmor(final int slot, final int armorInSlot) {
        ModuleAutoArmour.mc.playerController.windowClick(ModuleAutoArmour.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)ModuleAutoArmour.mc.player);
        ModuleAutoArmour.mc.playerController.windowClick(ModuleAutoArmour.mc.player.inventoryContainer.windowId, 5 + armorInSlot, 0, ClickType.PICKUP, (EntityPlayer)ModuleAutoArmour.mc.player);
        ModuleAutoArmour.mc.playerController.windowClick(ModuleAutoArmour.mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, (EntityPlayer)ModuleAutoArmour.mc.player);
        ModuleAutoArmour.mc.playerController.updateController();
    }
    
    public int findBestArmor(final int slotInArmor, final Item[] type) {
        final ArrayList<Integer> armourSlotFoundList = new ArrayList<Integer>();
        for (int i = 9; i < 36; ++i) {
            final ItemStack itemStack = SlotUtil.getItemStack(i);
            if (ItemUtil.contains(type, itemStack.getItem())) {
                armourSlotFoundList.add(i);
            }
        }
        if (armourSlotFoundList.isEmpty()) {
            return -1;
        }
        float bestArmourValue = 0.0f;
        int flag = -1;
        for (final int slot : armourSlotFoundList) {
            final ItemStack itemStack2 = SlotUtil.getItemStack(slot);
            if (itemStack2 != ItemStack.field_190927_a && itemStack2.getItem() != Items.field_190931_a) {
                if (!(itemStack2.getItem() instanceof ItemArmor)) {
                    continue;
                }
                final float k = (float)((ItemArmor)itemStack2.getItem()).damageReduceAmount;
                final float l = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, itemStack2);
                if (k + l < bestArmourValue) {
                    continue;
                }
                bestArmourValue = k + l;
                flag = slot;
            }
        }
        return flag;
    }
}
