//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import org.spongepowered.asm.mixin.injection.Redirect;
import com.onepop.client.module.client.ModuleTPSSync;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockProperties;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.Onepop;
import com.onepop.client.event.entity.PlayerDamageBlockEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP
{
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("INVOKE") }, cancellable = true)
    private void onPlayerDamageBlock(final BlockPos pos, final EnumFacing facing, final CallbackInfoReturnable<Boolean> info) {
        final PlayerDamageBlockEvent event = new PlayerDamageBlockEvent(pos, facing);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "onPlayerDamageBlock" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockProperties;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
    float getPlayerRelativeBlockHardness(final IBlockProperties iBlockProperties, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos) {
        return iBlockProperties.getPlayerRelativeBlockHardness(entityPlayer, world, blockPos) * (ModuleTPSSync.INSTANCE.isEnabled() ? (Onepop.getTPSManager().getTPS() / 20.0f) : 1.0f);
    }
}
