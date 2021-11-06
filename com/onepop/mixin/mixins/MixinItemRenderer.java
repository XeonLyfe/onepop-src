// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import com.onepop.client.module.render.ModuleNoRender;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.Onepop;
import com.onepop.client.event.render.EnumHandSideEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.EnumHandSide;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ItemRenderer.class })
public class MixinItemRenderer
{
    @Inject(method = { "transformSideFirstPerson" }, at = { @At("HEAD") })
    public void transformSideFirstPerson(final EnumHandSide hand, final float p_187459_2_, final CallbackInfo ci) {
        final EnumHandSideEvent event = new EnumHandSideEvent(hand);
        Onepop.getPomeloEventManager().dispatchEvent(event);
    }
    
    @Inject(method = { "transformFirstPerson" }, at = { @At("HEAD") })
    public void transformFirstPerson(final EnumHandSide hand, final float p_187453_2_, final CallbackInfo ci) {
        final EnumHandSideEvent event = new EnumHandSideEvent(hand);
        Onepop.getPomeloEventManager().dispatchEvent(event);
    }
    
    @Inject(method = { "renderFireInFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void onRenderFireInFirstPerson(final CallbackInfo ci) {
        final boolean flag = ModuleNoRender.INSTANCE.isEnabled() && ModuleNoRender.settingFire.getValue();
        if (flag) {
            ci.cancel();
        }
    }
}
