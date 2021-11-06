//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import com.onepop.client.module.misc.ModuleMultitask;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.client.event.client.RunTickEvent;
import com.onepop.Onepop;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.opengl.Display;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import com.onepop.mixin.interfaces.IMinecraft;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft implements IMinecraft
{
    @Shadow
    public EntityPlayerSP player;
    @Shadow
    public PlayerControllerMP playerController;
    
    @Redirect(at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"), method = { "createDisplay" })
    public void createDisplay(final String name) {
        Display.setTitle("1pop 2.0beta");
    }
    
    @Accessor
    @Override
    public abstract void setRightClickDelayTimer(final int p0);
    
    @Inject(method = { "runTick" }, at = { @At("HEAD") })
    private void onTick(final CallbackInfo callbackInfo) {
        Onepop.getPomeloEventManager().dispatchEvent(new RunTickEvent());
    }
    
    @Inject(method = { "shutdown" }, at = { @At("HEAD") })
    private void onShutDown(final CallbackInfo ci) {
        Onepop.shutdown();
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActive(final EntityPlayerSP player) {
        return !ModuleMultitask.INSTANCE.isEnabled() && this.player.isHandActive();
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    private boolean isHittingBlock(final PlayerControllerMP playerControllerMP) {
        return !ModuleMultitask.INSTANCE.isEnabled() && this.playerController.getIsHittingBlock();
    }
}
