//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "No Slow Down", tag = "NoSlowDown", description = "No slow down module.", category = ModuleCategory.PLAYER)
public class ModuleNoSlowDown extends Module
{
    @Listener
    public void onInputUpdateEvent(final InputUpdateEvent event) {
        if (ModuleNoSlowDown.mc.player.isHandActive() && !ModuleNoSlowDown.mc.player.isRiding()) {
            final MovementInput movementInput = event.getMovementInput();
            movementInput.moveStrafe *= 5.0f;
            final MovementInput movementInput2 = event.getMovementInput();
            movementInput2.field_192832_b *= 5.0f;
        }
    }
}
