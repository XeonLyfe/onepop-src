//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.item;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import com.onepop.Onepop;

public class SlotUtil
{
    public static void setCurrentItem(final int slot) {
        Onepop.MC.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        Onepop.MC.player.inventory.currentItem = slot;
        Onepop.MC.playerController.updateController();
    }
    
    public static void setServerCurrentItem(final int slot) {
        Onepop.MC.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
    }
    
    public static int findFirstNotAirSlotFromInventory() {
        int slot = -1;
        for (int i = 9; i < 36; ++i) {
            final ItemStack stack = Onepop.MC.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.field_190931_a) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static Item getArmourItem(final int slot) {
        return Onepop.MC.player.inventory.armorItemInSlot(slot).getItem();
    }
    
    public static ItemStack getArmourItemStack(final int slot) {
        return Onepop.MC.player.inventory.armorItemInSlot(slot);
    }
    
    public static ItemArmor getArmourItemArmor(final int slot) {
        return (ItemArmor)Onepop.MC.player.inventory.armorItemInSlot(slot).getItem();
    }
    
    public static Item getItem(final int slot) {
        return Onepop.MC.player.inventory.getStackInSlot(slot).getItem();
    }
    
    public static ItemStack getItemStack(final int slot) {
        return Onepop.MC.player.inventory.getStackInSlot(slot);
    }
    
    public static boolean isAir(final int slot) {
        return Onepop.MC.player.inventory.getStackInSlot(slot).getItem() == Items.field_190931_a;
    }
    
    public static boolean isArmourSlotAir(final int slot) {
        return Onepop.MC.player.inventory.armorItemInSlot(slot).getItem() == Items.field_190931_a;
    }
    
    public static int getCurrentItemSlotHotBar() {
        final int slot = Onepop.MC.player.inventory.currentItem;
        return slot;
    }
    
    public static Item getCurrentItemHotBar() {
        final Item item = Onepop.MC.player.inventory.getStackInSlot(Onepop.MC.player.inventory.currentItem).getItem();
        return item;
    }
    
    public static int findItemSlot(final Item item) {
        int slot = -1;
        for (int i = 0; i < 36; ++i) {
            final Item items = Onepop.MC.player.inventory.getStackInSlot(i).getItem();
            if (items == item) {
                if (i < 9) {
                    i += 36;
                }
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static int findItemSlotFromInventory(final Item item) {
        int slot = -1;
        for (int i = 9; i < 36; ++i) {
            final Item items = Onepop.MC.player.inventory.getStackInSlot(i).getItem();
            if (items == item) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static int findItemSlotFromHotBar(final Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final Item items = Onepop.MC.player.inventory.getStackInSlot(i).getItem();
            if (items == item) {
                slot = i;
                break;
            }
        }
        return slot;
    }
}
