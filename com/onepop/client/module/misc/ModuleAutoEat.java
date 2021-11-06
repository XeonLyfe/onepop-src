//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import net.minecraft.item.ItemFood;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.item.Item;
import com.onepop.api.util.item.SlotUtil;
import com.onepop.api.ISLClass;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Eat", tag = "AutoEat", description = "Find an food in hot bar if is hunger and automatically eat.", category = ModuleCategory.MISC)
public class ModuleAutoEat extends Module
{
    public static ValueEnum settingMode;
    public static ValueNumber settingFood;
    public static ValueNumber settingFoodFill;
    public static ValueBoolean settingOnlyGoldenApple;
    public static ValueNumber settingHealth;
    public static ValueNumber settingHealthFill;
    private boolean isToEat;
    private boolean isReturned;
    private int oldSlot;
    private int newSlot;
    
    @Override
    public void onSetting() {
        ModuleAutoEat.settingFood.setEnabled(ModuleAutoEat.settingMode.getValue() == Mode.FOOD);
        ModuleAutoEat.settingFoodFill.setEnabled(ModuleAutoEat.settingMode.getValue() == Mode.FOOD);
        ModuleAutoEat.settingHealth.setEnabled(ModuleAutoEat.settingMode.getValue() == Mode.HEALTH);
        ModuleAutoEat.settingHealthFill.setEnabled(ModuleAutoEat.settingMode.getValue() == Mode.HEALTH);
        ModuleAutoEat.settingOnlyGoldenApple.setEnabled(ModuleAutoEat.settingMode.getValue() == Mode.HEALTH);
        if (ModuleAutoEat.settingHealthFill.getValue().intValue() <= ModuleAutoEat.settingHealth.getValue().intValue()) {
            ModuleAutoEat.settingHealthFill.setValue(ModuleAutoEat.settingHealth.getValue());
        }
        if (ModuleAutoEat.settingFoodFill.getValue().intValue() <= ModuleAutoEat.settingFood.getValue().intValue()) {
            ModuleAutoEat.settingFoodFill.setValue(ModuleAutoEat.settingFood.getValue());
        }
    }
    
    @Override
    public void onShutdown() {
        this.setDisabled();
    }
    
    @Listener
    public void onListen(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ISLClass.mc.player.isCreative()) {
            return;
        }
        if (this.isToEat) {
            final boolean flagO = this.newSlot == 40;
            final boolean flagM = this.newSlot != 40;
            if (ModuleAutoEat.settingMode.getValue() == Mode.HEALTH && !ModuleAutoEat.settingOnlyGoldenApple.getValue() && ISLClass.mc.player.getFoodStats().getFoodLevel() == 20) {
                this.isToEat = false;
            }
            this.isReturned = true;
            if (!flagO) {
                final Item item = SlotUtil.getItemStack(this.newSlot).getItem();
                if (this.doAccept(item)) {
                    ISLClass.mc.player.inventory.currentItem = this.newSlot;
                    this.doEat();
                }
                else {
                    this.newSlot = this.findFoodSlot();
                    this.isToEat = (this.newSlot == -1);
                }
            }
            if (!flagM) {
                final Item item = SlotUtil.getItemStack(this.newSlot).getItem();
                if (this.doAccept(item)) {
                    this.doEat();
                }
                else {
                    this.newSlot = this.findFoodSlot();
                    this.isToEat = (this.newSlot == -1);
                }
            }
        }
        else if (this.isReturned) {
            ISLClass.mc.gameSettings.keyBindUseItem.pressed = false;
            ISLClass.mc.player.inventory.currentItem = this.oldSlot;
            this.isReturned = false;
        }
        else {
            this.oldSlot = ISLClass.mc.player.inventory.currentItem;
        }
        final int slot = this.findFoodSlot();
        if (slot != -1) {
            final Item item2 = SlotUtil.getItem(slot);
            switch (ModuleAutoEat.settingMode.getValue()) {
                case HEALTH: {
                    this.isToEat = (ISLClass.mc.player.getHealth() <= ModuleAutoEat.settingHealth.getValue().intValue() && (ISLClass.mc.player.getFoodStats().getFoodLevel() != 20 || ModuleAutoEat.settingOnlyGoldenApple.getValue()));
                    break;
                }
                case FOOD: {
                    this.isToEat = (ISLClass.mc.player.getFoodStats().getFoodLevel() <= ModuleAutoEat.settingFood.getValue().intValue());
                    break;
                }
            }
        }
    }
    
    public void doEat() {
        final EnumHand hand = this.doAccept(ISLClass.mc.player.getHeldItemOffhand().getItem()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        ISLClass.mc.gameSettings.keyBindUseItem.pressed = true;
        ISLClass.mc.playerController.processRightClick((EntityPlayer)ISLClass.mc.player, (World)ISLClass.mc.world, hand);
    }
    
    public int findFoodSlot() {
        int slot = -1;
        if (this.doAccept(ISLClass.mc.player.getHeldItemOffhand().getItem())) {
            return 40;
        }
        for (int i = 0; i < 9; ++i) {
            final Item items = ISLClass.mc.player.inventory.getStackInSlot(i).getItem();
            if (this.doAccept(items)) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public boolean doAccept(final Item item) {
        boolean isAccepted = false;
        if (item == Items.GOLDEN_APPLE && ModuleAutoEat.settingOnlyGoldenApple.isEnabled() && ModuleAutoEat.settingOnlyGoldenApple.getValue()) {
            isAccepted = true;
        }
        else if (item instanceof ItemFood && (!ModuleAutoEat.settingOnlyGoldenApple.isEnabled() || (ModuleAutoEat.settingOnlyGoldenApple.isEnabled() && !ModuleAutoEat.settingOnlyGoldenApple.getValue())) && (item != Items.CHORUS_FRUIT || item != Items.ROTTEN_FLESH || item != Items.POISONOUS_POTATO || item != Items.SPIDER_EYE)) {
            isAccepted = true;
        }
        return isAccepted;
    }
    
    static {
        ModuleAutoEat.settingMode = new ValueEnum("Mode", "Mode", "Modes to verify if eats or no!", Mode.HEALTH);
        ModuleAutoEat.settingFood = new ValueNumber("Food", "Food", "Food stats to start eat!", 10, 1, 20);
        ModuleAutoEat.settingFoodFill = new ValueNumber("Fill", "FoodFill", "Stop eating in.", 20, 1, 20);
        ModuleAutoEat.settingOnlyGoldenApple = new ValueBoolean("Only Golden Apple", "OnlyGoldenApple", "Only golden eat!", false);
        ModuleAutoEat.settingHealth = new ValueNumber("Health", "Health", "Health to start eat.", 20, 1, 20);
        ModuleAutoEat.settingHealthFill = new ValueNumber("Fill", "HealthFill", "Stop eating in.", 20, 1, 20);
    }
    
    public enum Mode
    {
        HEALTH, 
        FOOD;
    }
}
