//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.item;

import net.minecraft.init.Items;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ItemUtil
{
    public static final Item[] ALL_HELMETS;
    public static final Item[] ALL_CHEST_PLATES;
    public static final Item[] ALL_LEGGINGS;
    public static final Item[] ALL_BOOTS;
    public static final Item[] ALL_PICKAXES;
    public static final Item[] ALL_CLIENT_OFFHAND_ITEMS_USABLE;
    
    public static boolean contains(final Item[] list, final Item item) {
        boolean contains = false;
        for (int i = 0; i < list.length; ++i) {
            final Item items = list[i];
            if (items == item) {
                contains = true;
                break;
            }
        }
        return contains;
    }
    
    public static float getArmorLevel(final ItemStack firstArmor) {
        final float k = (float)((ItemArmor)firstArmor.getItem()).damageReduceAmount;
        final float l = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, firstArmor);
        return k + l;
    }
    
    static {
        ALL_HELMETS = new Item[] { (Item)Items.DIAMOND_HELMET, (Item)Items.IRON_HELMET, (Item)Items.GOLDEN_HELMET, (Item)Items.CHAINMAIL_HELMET, (Item)Items.LEATHER_HELMET };
        ALL_CHEST_PLATES = new Item[] { (Item)Items.DIAMOND_CHESTPLATE, (Item)Items.IRON_CHESTPLATE, (Item)Items.GOLDEN_CHESTPLATE, (Item)Items.CHAINMAIL_CHESTPLATE, (Item)Items.LEATHER_CHESTPLATE };
        ALL_LEGGINGS = new Item[] { (Item)Items.DIAMOND_LEGGINGS, (Item)Items.IRON_LEGGINGS, (Item)Items.GOLDEN_LEGGINGS, (Item)Items.CHAINMAIL_LEGGINGS, (Item)Items.LEATHER_LEGGINGS };
        ALL_BOOTS = new Item[] { (Item)Items.DIAMOND_BOOTS, (Item)Items.IRON_BOOTS, (Item)Items.GOLDEN_BOOTS, (Item)Items.CHAINMAIL_BOOTS, (Item)Items.LEATHER_BOOTS };
        ALL_PICKAXES = new Item[] { Items.DIAMOND_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.STONE_PICKAXE, Items.WOODEN_PICKAXE };
        ALL_CLIENT_OFFHAND_ITEMS_USABLE = new Item[] { Items.field_190929_cY, Items.GOLDEN_APPLE, Items.END_CRYSTAL, (Item)Items.BOW };
    }
}
