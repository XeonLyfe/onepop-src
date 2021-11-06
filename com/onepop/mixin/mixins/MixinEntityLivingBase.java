// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.Onepop;
import com.onepop.client.event.entity.SetHealthEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityLivingBase.class })
public class MixinEntityLivingBase
{
    @Inject(method = { "setHealth" }, at = { @At("HEAD") })
    public void onSetHealth(final float health, final CallbackInfo ci) {
        final SetHealthEvent event = new SetHealthEvent(health);
        Onepop.getPomeloEventManager().dispatchEvent(event);
    }
}
