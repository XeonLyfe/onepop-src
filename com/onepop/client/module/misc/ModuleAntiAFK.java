//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.entity.PlayerUtil;
import com.onepop.api.util.client.KeyUtil;
import net.minecraft.util.EnumHand;
import com.onepop.api.ISLClass;
import me.rina.turok.util.TurokMath;
import com.onepop.Onepop;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import me.rina.turok.util.TurokTick;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Anti-AFK", tag = "AntiAFK", description = "Make you no get kicked by server.", category = ModuleCategory.MISC)
public class ModuleAntiAFK extends Module
{
    public static ValueBoolean settingRotate;
    private TurokTick tick;
    private float angle;
    
    public ModuleAntiAFK() {
        this.tick = new TurokTick();
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
        final float ms = 3500.0f;
        if (this.tick.isPassedMS(ms / 2.0f)) {
            this.angle = TurokMath.serp(this.angle, 0.0f, Onepop.getClientEventManager().getCurrentRender3DPartialTicks());
            ISLClass.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
        else {
            this.angle = TurokMath.serp(this.angle, 90.0f, Onepop.getClientEventManager().getCurrentRender3DPartialTicks());
        }
        if (this.tick.isPassedMS(ms)) {
            this.tick.reset();
        }
        if (ISLClass.mc.player.onGround) {
            KeyUtil.press(ISLClass.mc.gameSettings.keyBindJump, true);
        }
        else {
            KeyUtil.press(ISLClass.mc.gameSettings.keyBindJump, false);
        }
        if (ModuleAntiAFK.settingRotate.getValue()) {
            PlayerUtil.setPitch(this.angle);
        }
    }
    
    static {
        ModuleAntiAFK.settingRotate = new ValueBoolean("Rotate", "Rotate", "Rotate camera.", true);
    }
}
