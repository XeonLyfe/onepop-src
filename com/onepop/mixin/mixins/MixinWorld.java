//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.onepop.Onepop;
import com.onepop.client.event.entity.PushWatterEvent;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ World.class })
public class MixinWorld
{
    @Redirect(method = { "handleMaterialAcceleration" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z"))
    public boolean onPushWatter(final Entity entity) {
        final PushWatterEvent event = new PushWatterEvent();
        Onepop.getPomeloEventManager().dispatchEvent(event);
        return event.isCanceled() || entity.isPushedByWater();
    }
}
