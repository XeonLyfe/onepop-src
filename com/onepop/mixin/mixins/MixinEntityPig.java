// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.Onepop;
import com.onepop.client.event.entity.PigEvent;
import com.onepop.api.event.impl.EventStage;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityPig.class })
public class MixinEntityPig
{
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    public void onPigSteered(final CallbackInfoReturnable<Boolean> cir) {
        final PigEvent event = new PigEvent(EventStage.PRE);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            cir.setReturnValue(true);
        }
    }
    
    @Inject(method = { "travel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntityPig;setAIMoveSpeed(F)V") }, cancellable = true)
    public void onPigTravel(final float strafe, final float vertical, final float forward, final CallbackInfo ci) {
        final PigEvent event = new PigEvent(EventStage.POST);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
