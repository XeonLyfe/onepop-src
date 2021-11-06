//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.misc;

import com.onepop.client.event.entity.AbstractHorseEvent;
import com.onepop.client.event.entity.PigEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.entity.Entity;
import com.onepop.api.ISLClass;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Entity Control", tag = "EntityControl", description = "Allows you mount to control animals without a saddle or carrot.", category = ModuleCategory.MISC)
public class ModuleEntityControl extends Module
{
    public static ValueBoolean settingPIG;
    public static ValueBoolean settingStrafeAirControl;
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (ModuleEntityControl.settingStrafeAirControl.getValue() && ModuleEntityControl.mc.player.getRidingEntity() != null) {
            final Entity riding = ModuleEntityControl.mc.player.getRidingEntity();
            float playerRotationYaw = ISLClass.mc.player.rotationYaw;
            final float playerRotationPitch = ISLClass.mc.player.rotationPitch;
            float playerForward = ISLClass.mc.player.movementInput.field_192832_b;
            float playerStrafe = ISLClass.mc.player.movementInput.moveStrafe;
            final float speed = (Math.sqrt(riding.motionX * riding.motionX + riding.motionZ * riding.motionZ) > 0.2872999906539917) ? ((float)Math.sqrt(riding.motionX * riding.motionX + riding.motionZ * riding.motionZ)) : 0.2873f;
            if (playerForward == 0.0 && playerStrafe == 0.0) {
                riding.motionX = 0.0;
                riding.motionZ = 0.0;
            }
            else {
                if (playerForward != 0.0 && playerStrafe != 0.0 && playerForward != 0.0) {
                    if (playerStrafe > 0.0) {
                        playerRotationYaw += ((playerForward > 0.0) ? -45 : 45);
                    }
                    else if (playerStrafe < 0.0) {
                        playerRotationYaw += ((playerForward > 0.0) ? 45 : -45);
                    }
                    playerStrafe = 0.0f;
                    if (playerForward > 0.0) {
                        playerForward = 1.0f;
                    }
                    else if (playerForward < 0.0f) {
                        playerForward = -1.0f;
                    }
                }
                riding.motionX = playerForward * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f)) + playerStrafe * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f));
                riding.motionZ = playerForward * speed * Math.sin(Math.toRadians(playerRotationYaw + 90.0f)) - playerStrafe * speed * Math.cos(Math.toRadians(playerRotationYaw + 90.0f));
            }
        }
    }
    
    @Listener
    public void onPig(final PigEvent event) {
        if (ModuleEntityControl.settingPIG.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @Listener
    public void onAbstractHorse(final AbstractHorseEvent event) {
        event.setCanceled(true);
    }
    
    static {
        ModuleEntityControl.settingPIG = new ValueBoolean("Pig <3", "Pig", "Pigs!!!! oink oink!", true);
        ModuleEntityControl.settingStrafeAirControl = new ValueBoolean("Strafe Air Control", "StrafeAirControl", "Control air with your entity!", true);
    }
}
