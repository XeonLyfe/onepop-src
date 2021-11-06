//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import java.util.Iterator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import com.onepop.api.ISLClass;
import com.onepop.client.event.client.ClientTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "No Render", tag = "NoRender", description = "No render specified elements on Minecraft.", category = ModuleCategory.RENDER)
public class ModuleNoRender extends Module
{
    public static ModuleNoRender INSTANCE;
    public static ValueBoolean settingBossInfo;
    public static ValueBoolean settingCrossHair;
    public static ValueBoolean settingPotionIcons;
    public static ValueBoolean settingPortal;
    public static ValueBoolean settingPumpkin;
    public static ValueBoolean settingHurtEffectCamera;
    public static ValueBoolean settingFire;
    public static ValueBoolean settingFog;
    public static ValueBoolean settingRain;
    public static ValueBoolean settingFloorDroppedItem;
    public static ValueBoolean settingCustomWorldTime;
    public static ValueNumber settingWorldTime;
    public static ValueBoolean settingEffects;
    
    public ModuleNoRender() {
        ModuleNoRender.INSTANCE = this;
    }
    
    @Override
    public void onSetting() {
        ModuleNoRender.settingWorldTime.setEnabled(ModuleNoRender.settingCustomWorldTime.getValue());
    }
    
    @Listener
    public void onListenPacketEvent(final PacketEvent.Receive event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (event.getPacket() instanceof SPacketTimeUpdate && ModuleNoRender.settingCustomWorldTime.getValue()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityEffect && ModuleNoRender.settingEffects.getValue()) {
            final SPacketEntityEffect packet = (SPacketEntityEffect)event.getPacket();
            if (packet.getEntityId() == ModuleNoRender.mc.player.getEntityId() && (packet.getEffectId() == 9 || packet.getEffectId() == 15)) {
                event.setCanceled(true);
            }
        }
    }
    
    @Listener
    public void onListenClientTickEvent(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ISLClass.mc.player.isPotionActive(MobEffects.NAUSEA)) {
            ISLClass.mc.player.removePotionEffect(MobEffects.NAUSEA);
        }
        if (ModuleNoRender.settingFloorDroppedItem.getValue()) {
            for (final Entity entities : ISLClass.mc.world.loadedEntityList) {
                if (entities instanceof EntityItem) {
                    final EntityItem entityItem = (EntityItem)entities;
                    entityItem.setDead();
                }
            }
        }
        if (ISLClass.mc.world.isRaining() && ModuleNoRender.settingRain.getValue()) {
            ISLClass.mc.world.setRainStrength(0.0f);
        }
        if (ModuleNoRender.settingCustomWorldTime.getValue()) {
            ISLClass.mc.world.setWorldTime((long)ModuleNoRender.settingWorldTime.getValue().intValue());
        }
    }
    
    @SubscribeEvent
    public void onOverlay(final RenderGameOverlayEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS && ModuleNoRender.settingCrossHair.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    static {
        ModuleNoRender.settingBossInfo = new ValueBoolean("Boss Info", "BossInfo", "No render boss info.", true);
        ModuleNoRender.settingCrossHair = new ValueBoolean("Cross Hair", "CrossHair", "No render split cross hair.", false);
        ModuleNoRender.settingPotionIcons = new ValueBoolean("Potion Icons", "PotionIcons", "No render potion icons.", true);
        ModuleNoRender.settingPortal = new ValueBoolean("Portal", "Portal", "No render portal... ?", false);
        ModuleNoRender.settingPumpkin = new ValueBoolean("Pumpkin", "Pumpkin", "Disables pumpkin render.", true);
        ModuleNoRender.settingHurtEffectCamera = new ValueBoolean("Hurt Effect Camera", "HurtEffectCamera", "Disables hurt effect in camera.", true);
        ModuleNoRender.settingFire = new ValueBoolean("Fire", "Fire", "No render fire.", true);
        ModuleNoRender.settingFog = new ValueBoolean("Fog", "Fog", "No render fog.", false);
        ModuleNoRender.settingRain = new ValueBoolean("Rain", "Rain", "No render rain.", true);
        ModuleNoRender.settingFloorDroppedItem = new ValueBoolean("Floor Dropped Item", "FloorDroppedItem", "No render dropped item.", true);
        ModuleNoRender.settingCustomWorldTime = new ValueBoolean("Custom World Time", "CustomWorldTime", "Customize world time.", false);
        ModuleNoRender.settingWorldTime = new ValueNumber("World Time", "WorldTime", "Set world time.", 1000, 0, 23000);
        ModuleNoRender.settingEffects = new ValueBoolean("Effects", "Effects", "Effects potion!", false);
    }
}
