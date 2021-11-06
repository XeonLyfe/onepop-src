//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import com.onepop.client.module.player.ModuleSafeWalk;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.onepop.Onepop;
import com.onepop.client.event.entity.PushPlayerEvent;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    @Shadow
    public void moveEntity(final MoverType type, final double x, final double y, final double z) {
    }
    
    @Shadow
    public abstract boolean removeTag(final String p0);
    
    @Redirect(method = { "applyEntityCollision" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void onPushPlayer(final Entity entity, final double x, final double y, final double z) {
        final PushPlayerEvent event = new PushPlayerEvent();
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (!event.isCanceled()) {
            entity.motionX += x;
            entity.motionY += y;
            entity.motionZ += z;
        }
    }
    
    @Redirect(method = { "move" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z"))
    public boolean isSneaking(final Entity entity) {
        return ModuleSafeWalk.INSTANCE.isEnabled() || entity.isSneaking();
    }
}
