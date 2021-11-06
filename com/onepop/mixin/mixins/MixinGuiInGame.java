// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.client.module.render.ModuleNoRender;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiIngame.class })
public class MixinGuiInGame
{
    @Inject(method = { "renderPotionEffects" }, at = { @At("HEAD") }, cancellable = true)
    public void onRenderPotionEffects(final ScaledResolution resolution, final CallbackInfo ci) {
        final boolean flag = ModuleNoRender.INSTANCE.isEnabled() && ModuleNoRender.settingPotionIcons.getValue();
        if (flag) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderPumpkinOverlay" }, at = { @At("HEAD") }, cancellable = true)
    public void onRenderPumpkin(final ScaledResolution scaledRes, final CallbackInfo ci) {
        final boolean flag = ModuleNoRender.INSTANCE.isEnabled() && ModuleNoRender.settingPumpkin.getValue();
        if (flag) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderPortal" }, at = { @At("HEAD") }, cancellable = true)
    public void onRenderPortal(final float timeInPortal, final ScaledResolution scaledRes, final CallbackInfo ci) {
        final boolean flag = ModuleNoRender.INSTANCE.isEnabled() && ModuleNoRender.settingPortal.getValue();
        if (flag) {
            ci.cancel();
        }
    }
}
