//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.entity.Entity;
import com.onepop.client.module.combat.ModuleBurrow;
import com.onepop.api.social.Social;
import com.onepop.api.social.type.SocialType;
import com.onepop.Onepop;
import com.onepop.api.social.management.SocialManager;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import com.onepop.api.util.item.SlotUtil;
import org.lwjgl.input.Mouse;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Middle Click", tag = "MiddleClick", description = "Middle click!", category = ModuleCategory.MISC)
public class ModuleMiddleClick extends Module
{
    public static ValueEnum settingMode;
    private boolean hasPress;
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        final int currentSlot = ModuleMiddleClick.mc.player.inventory.currentItem;
        if (Mouse.isButtonDown(2)) {
            if (((Mode)ModuleMiddleClick.settingMode.getValue()).getItem() != null) {
                final int slot = SlotUtil.findItemSlotFromHotBar(((Mode)ModuleMiddleClick.settingMode.getValue()).getItem());
                if (slot != -1) {
                    SlotUtil.setServerCurrentItem(slot);
                    ModuleMiddleClick.mc.playerController.processRightClick((EntityPlayer)ModuleMiddleClick.mc.player, (World)ModuleMiddleClick.mc.world, EnumHand.MAIN_HAND);
                    this.hasPress = true;
                }
            }
            else if (ModuleMiddleClick.settingMode.getValue() != Mode.BURROW) {
                final Entity pointed = ModuleMiddleClick.mc.getRenderManager().pointedEntity;
                if (pointed instanceof EntityPlayer && !this.hasPress) {
                    final Social social = SocialManager.get(pointed.getName());
                    if (ModuleMiddleClick.settingMode.getValue() == Mode.FRIEND) {
                        if (social == null) {
                            Onepop.getSocialManager().registry(new Social(pointed.getName(), SocialType.FRIEND));
                            this.print("Added " + pointed.getName() + " at friend list.");
                        }
                        else if (social.getType() == SocialType.FRIEND) {
                            Onepop.getSocialManager().unregister(social);
                            this.print("Removed " + pointed.getName() + " from friend list.");
                        }
                    }
                    else if (social == null) {
                        Onepop.getSocialManager().registry(new Social(pointed.getName(), SocialType.ENEMY));
                        this.print("Added " + pointed.getName() + " at enemy list.");
                    }
                    else if (social.getType() == SocialType.ENEMY) {
                        Onepop.getSocialManager().unregister(social);
                        this.print("Removed " + pointed.getName() + " from enemy list.");
                    }
                    this.hasPress = true;
                }
            }
            else if (!this.hasPress) {
                ModuleBurrow.INSTANCE.setEnabled();
                this.hasPress = true;
            }
        }
        else if (this.hasPress) {
            if (((Mode)ModuleMiddleClick.settingMode.getValue()).getItem() != null) {
                SlotUtil.setServerCurrentItem(currentSlot);
            }
            this.hasPress = false;
        }
    }
    
    static {
        ModuleMiddleClick.settingMode = new ValueEnum("Mode", "Mode", "Modes for middle click!", Mode.ENDER_PEARL);
    }
    
    public enum Mode
    {
        ENDER_PEARL(Items.ENDER_PEARL), 
        XP(Items.EXPERIENCE_BOTTLE), 
        FRIEND((Item)null), 
        ENEMY((Item)null), 
        BURROW((Item)null);
        
        Item item;
        
        private Mode(final Item item) {
            this.item = item;
        }
        
        public Item getItem() {
            return this.item;
        }
    }
}
