//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import com.onepop.Onepop;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import com.mojang.realmsclient.gui.ChatFormatting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import com.onepop.api.setting.value.ValueString;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Negro Module", tag = "FakePlayer", description = "Negro module, muscle!", category = ModuleCategory.MISC)
public class ModuleNegro extends Module
{
    public static ValueString settingName;
    private EntityOtherPlayerMP pedroperry;
    
    @Override
    public void onShutdown() {
        this.setDisabled();
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.pedroperry.inventory = ModuleNegro.mc.player.inventory;
        if (ModuleNegro.mc.player.isDead || ModuleNegro.mc.player.getHealth() < 0.0f) {
            this.setDisabled();
        }
    }
    
    @Override
    public void onEnable() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.print(ChatFormatting.YELLOW + "Reload module for apply new fake player name.");
        (this.pedroperry = new EntityOtherPlayerMP((World)ModuleNegro.mc.world, new GameProfile(UUID.fromString("498dc6ae-1084-4d7d-ab9f-0cf090fd336a"), ModuleNegro.settingName.getValue()))).copyLocationAndAnglesFrom((Entity)ModuleNegro.mc.player);
        this.pedroperry.rotationYawHead = ModuleNegro.mc.player.rotationYawHead;
        Onepop.getEntityWorldManager().saveEntity(-150, (Entity)this.pedroperry);
        ModuleNegro.mc.world.addEntityToWorld(-150, (Entity)this.pedroperry);
    }
    
    @Override
    public void onDisable() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        Onepop.getEntityWorldManager().removeEntity(-150);
        ModuleNegro.mc.world.removeEntityFromWorld(-150);
    }
    
    static {
        ModuleNegro.settingName = new ValueString("Name", "Name", "Customize name from fake player.", "Pedroperry");
    }
}
