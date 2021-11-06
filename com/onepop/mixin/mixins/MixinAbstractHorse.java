// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.Onepop;
import com.onepop.client.event.entity.AbstractHorseEvent;
import com.onepop.api.event.impl.EventStage;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ AbstractHorse.class })
public class MixinAbstractHorse
{
    @Inject(method = { "canBeSteered" }, at = { @At("HEAD") }, cancellable = true)
    public void onAbstractHorseCanBeSteered(final CallbackInfoReturnable<Boolean> cir) {
        final AbstractHorseEvent event = new AbstractHorseEvent(EventStage.PRE);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            cir.setReturnValue(true);
        }
    }
    
    @Inject(method = { "isHorseSaddled" }, at = { @At("HEAD") }, cancellable = true)
    public void onSaddled(final CallbackInfoReturnable<Boolean> cir) {
        final AbstractHorseEvent event = new AbstractHorseEvent(EventStage.POST);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            cir.setReturnValue(true);
        }
    }
}
