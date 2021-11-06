//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.lwjgl.util.glu.Project;
import com.onepop.Onepop;
import com.onepop.client.event.render.PerspectiveEvent;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.client.module.render.ModuleNoRender;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import com.onepop.client.module.misc.ModuleNoEntityTrace;
import java.util.List;
import com.google.common.base.Predicate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.onepop.client.module.render.ModuleCustomCamera;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public class MixinEntityRenderer
{
    private final Minecraft mc;
    
    public MixinEntityRenderer() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @Redirect(method = { "orientCamera" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    public RayTraceResult rayTraceBlocks(final WorldClient world, final Vec3d start, final Vec3d end) {
        final boolean flag = ModuleCustomCamera.INSTANCE.isEnabled() && ModuleCustomCamera.settingNoCameraClip.getValue();
        return flag ? null : world.rayTraceBlocks(start, end);
    }
    
    @Redirect(method = { "getMouseOver" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
        final boolean flag = ModuleNoEntityTrace.INSTANCE.isEnabled() && ModuleNoEntityTrace.INSTANCE.doAccept();
        return flag ? new ArrayList<Entity>() : worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }
    
    @Inject(method = { "hurtCameraEffect" }, at = { @At("HEAD") }, cancellable = true)
    public void onHurtCameraEffect(final float partialTicks, final CallbackInfo ci) {
        final boolean flag = ModuleNoRender.INSTANCE.isEnabled() && ModuleNoRender.settingHurtEffectCamera.getValue();
        if (flag) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "setupFog" }, at = { @At("RETURN") }, cancellable = true)
    public void onSetupFrog(final int startCoords, final float partialTicks, final CallbackInfo ci) {
        final boolean flag = ModuleNoRender.INSTANCE.isEnabled() && ModuleNoRender.settingFog.getValue();
        if (flag) {
            GlStateManager.disableFog();
        }
    }
    
    @Redirect(method = { "setupCameraTransform" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onSetupCameraTransform(final float fovy, final float aspect, final float zNear, final float zFar) {
        final PerspectiveEvent event = new PerspectiveEvent(this.mc.displayWidth / (float)this.mc.displayHeight);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        Project.gluPerspective(fovy, event.getAspect(), zNear, zFar);
    }
    
    @Redirect(method = { "renderWorldPass" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onRenderWorldPass(final float fovy, final float aspect, final float zNear, final float zFar) {
        final PerspectiveEvent event = new PerspectiveEvent(this.mc.displayWidth / (float)this.mc.displayHeight);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        Project.gluPerspective(fovy, event.getAspect(), zNear, zFar);
    }
    
    @Redirect(method = { "renderCloudsCheck" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onRenderCloudsCheck(final float fovy, final float aspect, final float zNear, final float zFar) {
        final PerspectiveEvent event = new PerspectiveEvent(this.mc.displayWidth / (float)this.mc.displayHeight);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        Project.gluPerspective(fovy, event.getAspect(), zNear, zFar);
    }
}
