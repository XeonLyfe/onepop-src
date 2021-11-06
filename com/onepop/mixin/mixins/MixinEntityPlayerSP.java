//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.onepop.Onepop;
import com.onepop.client.event.entity.PlayerMoveEvent;
import net.minecraft.entity.MoverType;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import com.onepop.mixin.interfaces.IEntityPlayerSP;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends MixinEntityPlayer implements IEntityPlayerSP
{
    @Override
    public void moveEntity(final MoverType type, final double x, final double y, final double z) {
        final PlayerMoveEvent event = new PlayerMoveEvent(type, x, y, z);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        super.moveEntity(event.getType(), event.getX(), event.getY(), event.getZ());
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    public void onPushByBlocks(final double x, final double y, final double z, final CallbackInfoReturnable<Boolean> cir) {
    }
}
