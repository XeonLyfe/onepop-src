// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.mixin.mixins;

import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import com.onepop.Onepop;
import com.onepop.client.event.network.PacketEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    public void onSendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        final PacketEvent event = new PacketEvent.Send(packet);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    public void onReceivePacket(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        final PacketEvent event = new PacketEvent.Receive(packet);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
}
