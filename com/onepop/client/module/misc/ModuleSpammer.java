//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import com.onepop.api.ISLClass;
import com.onepop.api.util.client.KeyUtil;
import me.rina.turok.util.TurokMath;
import com.onepop.api.util.entity.PlayerUtil;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.util.client.FlagBoolUtil;
import com.onepop.Onepop;
import com.onepop.api.setting.value.ValueString;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Spammer", tag = "Spammer", description = "Send a lot messages on chat.", category = ModuleCategory.MISC)
public class ModuleSpammer extends Module
{
    public static ValueNumber settingDelay;
    public static ValueNumber settingLimit;
    public static ValueBoolean settingAntiSpam;
    public static ValueEnum settingWalk;
    public static ValueString settingWalkText;
    public static ValueEnum settingJump;
    public static ValueString settingJumpText;
    private double[] lastWalkingPlayerPos;
    private String current;
    
    @Override
    public void onSetting() {
        Onepop.getSpammerManager().setDelay(ModuleSpammer.settingDelay.getValue().floatValue());
        Onepop.getSpammerManager().setLimit(ModuleSpammer.settingLimit.getValue().intValue());
        ModuleSpammer.settingWalkText.setEnabled(ModuleSpammer.settingWalk.getValue() == FlagBoolUtil.TRUE);
        ModuleSpammer.settingJumpText.setEnabled(ModuleSpammer.settingJump.getValue() == FlagBoolUtil.TRUE);
    }
    
    @Override
    public void onShutdown() {
        this.setDisabled();
    }
    
    @Listener
    public void onListen(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleSpammer.settingWalkText.isEnabled()) {
            this.verifyWalking();
        }
        if (ModuleSpammer.settingJumpText.isEnabled()) {
            this.verifyJump();
        }
    }
    
    public void verifyWalking() {
        if (this.lastWalkingPlayerPos == null) {
            this.lastWalkingPlayerPos = PlayerUtil.getLastTickPos();
        }
        if (PlayerUtil.getBPS() >= 4.0) {
            final int x = (int)(this.lastWalkingPlayerPos[0] - PlayerUtil.getPos()[0]);
            final int y = (int)(this.lastWalkingPlayerPos[1] - PlayerUtil.getPos()[1]);
            final int z = (int)(this.lastWalkingPlayerPos[2] - PlayerUtil.getPos()[2]);
            final int walkedBlocks = TurokMath.sqrt(x * x + y * y + z * z);
            if (walkedBlocks >= 2) {
                Onepop.getSpammerManager().send(ModuleSpammer.settingWalkText.getValue().replaceAll("<blocks>", "" + walkedBlocks) + this.getRandom());
            }
        }
        else {
            this.lastWalkingPlayerPos = PlayerUtil.getLastTickPos();
        }
    }
    
    public void verifyJump() {
        if (KeyUtil.isJumping() && PlayerUtil.getBPS() <= 4.0 && !ISLClass.mc.player.isInWater()) {
            Onepop.getSpammerManager().send(ModuleSpammer.settingJumpText.getValue() + this.getRandom());
        }
    }
    
    public String getRandom() {
        final int min = 0;
        final int max = 500;
        String random = "";
        if (ModuleSpammer.settingAntiSpam.getValue()) {
            random = " " + (int)(Math.random() * (max - min + 1) + min);
        }
        return random;
    }
    
    static {
        ModuleSpammer.settingDelay = new ValueNumber("Delay", "Delay", "Seconds delay to send message.", 0.5f, 0.5f, 10.0f);
        ModuleSpammer.settingLimit = new ValueNumber("Limit", "Limit", "The limit of messages in queue.", 3, 1, 6);
        ModuleSpammer.settingAntiSpam = new ValueBoolean("Anti-Spam", "AntiSpam", "Make anti spam server crazy.", true);
        ModuleSpammer.settingWalk = new ValueEnum("Walk", "Walk", "Spam blocks walked.", FlagBoolUtil.TRUE);
        ModuleSpammer.settingWalkText = new ValueString("Walk Text", "WalkText", "The custom text for spam.", "I just walked <blocks> blocks, thanks to Rocan!");
        ModuleSpammer.settingJump = new ValueEnum("Jump", "Jump", "Spam jump action.", FlagBoolUtil.TRUE);
        ModuleSpammer.settingJumpText = new ValueString("Jump Text", "JumpText", "The custom text for spam.", "I just jumped, thanks to Rocan!");
    }
}
