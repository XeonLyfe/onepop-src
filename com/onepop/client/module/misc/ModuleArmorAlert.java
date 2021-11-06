//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import me.rina.turok.util.TurokMath;
import net.minecraft.init.Items;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import com.onepop.api.util.chat.ChatUtil;
import com.onepop.api.social.type.SocialType;
import com.onepop.api.social.management.SocialManager;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import java.awt.Color;
import com.onepop.client.module.client.ModuleHUD;
import me.rina.turok.render.font.management.TurokFontManager;
import me.rina.turok.util.TurokDisplay;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import com.onepop.api.setting.value.ValueString;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Armor Alert", tag = "ArmorAlert", description = "Alerts if armor low durability.", category = ModuleCategory.MISC)
public class ModuleArmorAlert extends Module
{
    public static ValueBoolean settingFriend;
    public static ValueNumber settingArmorPercentage;
    public static ValueNumber settingArmorQuantity;
    public static ValueEnum settingAlertMode;
    public static ValueString settingMessageInput;
    private boolean notified;
    private String staticText;
    private final List<EntityPlayer> confirmedMessage;
    
    public ModuleArmorAlert() {
        this.confirmedMessage = new ArrayList<EntityPlayer>();
    }
    
    @Override
    public void onSetting() {
        ModuleArmorAlert.settingMessageInput.setEnabled(ModuleArmorAlert.settingFriend.getValue());
    }
    
    @Override
    public void onRender2D() {
        final TurokDisplay display = new TurokDisplay(ModuleArmorAlert.mc);
        if (this.notified && ModuleArmorAlert.settingAlertMode.getValue() == Mode.RENDER) {
            TurokFontManager.render(this.staticText, (float)(display.getScaledWidth() / 2 - TurokFontManager.getStringWidth(this.staticText) / 2), 10.0f, true, new Color(ModuleHUD.settingRed.getValue().intValue(), ModuleHUD.settingGreen.getValue().intValue(), ModuleHUD.settingBlue.getValue().intValue()));
        }
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        final ItemStack currentBreakingArmor = this.getArmorBreaking((EntityPlayer)ModuleArmorAlert.mc.player);
        if (currentBreakingArmor == null && this.notified) {
            this.notified = false;
        }
        if (currentBreakingArmor != null && !this.notified) {
            this.staticText = "Your " + currentBreakingArmor.getDisplayName() + " is breaking!";
            switch (ModuleArmorAlert.settingAlertMode.getValue()) {
                case CHAT: {
                    this.print(this.staticText);
                    break;
                }
            }
            this.notified = true;
        }
        if (ModuleArmorAlert.settingFriend.getValue()) {
            for (final EntityPlayer entities : ModuleArmorAlert.mc.world.playerEntities) {
                if (entities != ModuleArmorAlert.mc.player && SocialManager.get(entities.getName()) != null) {
                    if (SocialManager.get(entities.getName()).getType() != SocialType.FRIEND) {
                        continue;
                    }
                    final ItemStack currentFriendBreakingArmor = this.getArmorBreaking(entities);
                    if (currentFriendBreakingArmor == null && this.confirmedMessage.contains(entities)) {
                        this.confirmedMessage.remove(entities);
                    }
                    if (currentFriendBreakingArmor == null || this.confirmedMessage.contains(entities)) {
                        continue;
                    }
                    final String formattedMessage = ModuleArmorAlert.settingMessageInput.getValue().replaceAll("<player>", entities.getName()).replaceAll("<armor>", currentFriendBreakingArmor.getDisplayName());
                    ChatUtil.message(formattedMessage);
                    this.confirmedMessage.add(entities);
                }
            }
        }
    }
    
    public ItemStack getArmorBreaking(final EntityPlayer player) {
        float mostDamagePercentage = 101.0f;
        ItemStack mostDamageItemStack = null;
        int equippedCount = 0;
        for (final ItemStack armors : player.inventory.armorInventory) {
            if (armors.getItem() == Items.field_190931_a) {
                continue;
            }
            ++equippedCount;
            final float percentage = TurokMath.amount((float)armors.getItemDamage(), (float)armors.getMaxDamage());
            if (percentage >= mostDamagePercentage) {
                continue;
            }
            mostDamagePercentage = percentage;
            mostDamageItemStack = armors;
        }
        return (equippedCount >= ModuleArmorAlert.settingArmorQuantity.getValue().intValue() && mostDamagePercentage != 101.0f && mostDamagePercentage <= ModuleArmorAlert.settingArmorPercentage.getValue().intValue()) ? mostDamageItemStack : null;
    }
    
    static {
        ModuleArmorAlert.settingFriend = new ValueBoolean("Friend", "Friend", "Alert friends!", true);
        ModuleArmorAlert.settingArmorPercentage = new ValueNumber("Armor Percentage", "ArmorPercentage", "The percentage of armor to alerts!", 25, 0, 100);
        ModuleArmorAlert.settingArmorQuantity = new ValueNumber("Armor Quantity", "ArmorQuantity", "The quantity of armor equipped to notify.", 4, 1, 4);
        ModuleArmorAlert.settingAlertMode = new ValueEnum("Mode Alert", "ModeAlert", "Modes for alert!", Mode.CHAT);
        ModuleArmorAlert.settingMessageInput = new ValueString("Message Input", "MessageInput", "The input message!", "/w <player> your <armor> is breaking!");
    }
    
    public enum Mode
    {
        CHAT, 
        NOTIFIER, 
        RENDER;
    }
}
