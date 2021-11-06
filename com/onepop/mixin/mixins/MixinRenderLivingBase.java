//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.onepop.client.event.render.RenderModelEntityLivingEvent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.Onepop;
import com.onepop.client.event.render.RenderNameEvent;
import com.onepop.api.event.impl.EventStage;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLivingBase;

@Mixin({ RenderLivingBase.class })
public class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    public MixinRenderLivingBase(final RenderManager renderManagerIn, final ModelBase modelBaseIn, final float shadowSizeIn) {
        super(renderManagerIn);
    }
    
    @Inject(method = { "renderName" }, at = { @At("HEAD") }, cancellable = true)
    public void onRenderNamePre(final T entity, final double x, final double y, final double z, final CallbackInfo ci) {
        final RenderNameEvent event = new RenderNameEvent(EventStage.PRE);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderName" }, at = { @At("RETURN") }, cancellable = true)
    public void onRenderNamePost(final T entity, final double x, final double y, final double z, final CallbackInfo ci) {
        final RenderNameEvent event = new RenderNameEvent(EventStage.POST);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "renderModel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderModel(final ModelBase modelBase, final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        final RenderModelEntityLivingEvent event = new RenderModelEntityLivingEvent((EntityLivingBase)entityIn, modelBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            return;
        }
        event.getModelBase().render(entityIn, event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
    }
    
    @Nullable
    protected ResourceLocation getEntityTexture(final T t) {
        return null;
    }
}
