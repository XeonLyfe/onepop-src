//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.player;

import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import com.onepop.api.util.client.KeyUtil;
import com.onepop.api.ISLClass;
import net.minecraft.client.gui.GuiChat;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Inventory Walk", tag = "InventoryWalk", description = "You can move while a GUI is open.", category = ModuleCategory.PLAYER)
public class ModuleInventoryWalk extends Module
{
    @Listener
    public void onListen(final ClientTickEvent event) {
        if (NullUtil.isPlayer()) {
            return;
        }
        if (ISLClass.mc.currentScreen instanceof GuiChat || ISLClass.mc.currentScreen == null) {
            return;
        }
        for (final KeyBinding keys : KeyUtil.ALL_MOVEMENT_KEY_BIND) {
            KeyUtil.press(keys, Keyboard.isKeyDown(keys.getKeyCode()));
        }
    }
}
