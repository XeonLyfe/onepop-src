//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.client;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.init.Blocks;
import com.onepop.api.util.world.BlockUtil;
import net.minecraft.util.math.RayTraceResult;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Developer", tag = "Developer", description = "Test stuff!", category = ModuleCategory.CLIENT)
public class ModuleDeveloper extends Module
{
    @Listener
    public void onListenClientTickEvent(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        final RayTraceResult obj = ModuleDeveloper.mc.objectMouseOver;
        if (obj != null && obj.typeOfHit == RayTraceResult.Type.BLOCK && BlockUtil.getBlock(obj.getBlockPos()) != Blocks.AIR) {
            this.print("" + ModuleDeveloper.mc.player.getDistance((double)obj.getBlockPos().x, (double)obj.getBlockPos().y, (double)obj.getBlockPos().z));
        }
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
    }
}
