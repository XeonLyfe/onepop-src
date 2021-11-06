// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.client.module.render.ModuleNoRender;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.BossInfo;
import net.minecraft.client.gui.GuiBossOverlay;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiBossOverlay.class })
public class MixinBossOverlay
{
    @Inject(method = { "render" }, at = { @At("HEAD") }, cancellable = true)
    public void onRender(final int x, final int y, final BossInfo info, final CallbackInfo ci) {
        final boolean flag = ModuleNoRender.INSTANCE.isEnabled() && ModuleNoRender.settingBossInfo.getValue();
        if (flag) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    public void onRenderBoosHealth(final CallbackInfo ci) {
        final boolean flag = ModuleNoRender.INSTANCE.isEnabled() && ModuleNoRender.settingBossInfo.getValue();
        if (flag) {
            ci.cancel();
        }
    }
}
