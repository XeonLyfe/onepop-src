//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.util.network.PacketUtil;
import com.onepop.api.tracker.impl.RightMouseClickTracker;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import com.onepop.api.ISLClass;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.util.SoundEvent;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import com.onepop.client.event.network.PacketEvent;
import com.onepop.api.tracker.Tracker;
import me.rina.turok.util.TurokTick;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Auto-Fish", tag = "AutoFish", description = "Automatically fish to you.", category = ModuleCategory.MISC)
public class ModuleAutoFish extends Module
{
    public static ValueNumber settingSplashDelay;
    public static ValueNumber settingPacketDelay;
    private Flag flag;
    private TurokTick tick;
    private Tracker tracker;
    
    public ModuleAutoFish() {
        this.flag = Flag.NoFishing;
        this.tick = new TurokTick();
        this.tracker = new Tracker("AutoFishTrack").inject();
    }
    
    @Override
    public void onSetting() {
        this.tracker.setDelay((float)ModuleAutoFish.settingPacketDelay.getValue().intValue());
    }
    
    @Override
    public void onShutdown() {
        this.setDisabled();
    }
    
    @Listener
    public void onListen(final PacketEvent.Receive event) {
        if (!(event.getPacket() instanceof SPacketSoundEffect)) {
            return;
        }
        final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
        final SoundEvent currentSoundEvent = packet.getSound();
        if (currentSoundEvent == SoundEvents.ENTITY_BOBBER_SPLASH) {
            this.flag = Flag.Splash;
        }
    }
    
    @Listener
    public void onListenClientTick(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ISLClass.mc.player.getHeldItemMainhand().getItem() != Items.FISHING_ROD) {
            this.print("No fishing rod at hand!");
            this.setDisabled();
            return;
        }
        if (this.flag == Flag.Splash) {
            if (this.tick.isPassedMS((float)ModuleAutoFish.settingSplashDelay.getValue().intValue())) {
                this.print("You fish!");
                this.tracker.send(new RightMouseClickTracker(EnumHand.MAIN_HAND));
                this.tracker.join(new RightMouseClickTracker(EnumHand.MAIN_HAND));
                this.flag = Flag.Fishing;
            }
        }
        else {
            this.tick.reset();
            if (this.flag == Flag.NoFishing && ISLClass.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                this.flag = Flag.Fishing;
            }
            if (this.flag == Flag.Fishing && ISLClass.mc.player.fishEntity.onGround) {
                this.print("You can't fish out of " + ChatFormatting.BLUE + "water" + ChatFormatting.WHITE + ".");
                this.setDisabled();
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.flag = Flag.NoFishing;
        this.tracker.register();
    }
    
    @Override
    public void onDisable() {
        if (this.flag == Flag.Fishing) {
            this.tracker.send(new RightMouseClickTracker(EnumHand.MAIN_HAND));
        }
        this.flag = Flag.NoFishing;
        this.tracker.unregister();
    }
    
    static {
        ModuleAutoFish.settingSplashDelay = new ValueNumber("Splash Delay", "SplashDelay", "The MS delay after the sound splash event.", 750, 1, 3000);
        ModuleAutoFish.settingPacketDelay = new ValueNumber("Packet Delay", "PacketDelay", "The MS delay for sending packet.", 500, 0, 3000);
    }
    
    public enum Flag
    {
        Splash, 
        Fishing, 
        NoFishing;
    }
}
