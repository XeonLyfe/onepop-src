//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.event.management;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.command.Command;
import com.onepop.api.util.chat.ChatUtil;
import com.onepop.api.command.management.CommandManager;
import net.minecraftforge.client.event.ClientChatEvent;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import com.onepop.api.setting.value.ValueBind;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import java.awt.Color;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.util.Iterator;
import com.onepop.client.module.client.ModuleHUD;
import com.onepop.api.component.Component;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import com.onepop.api.module.Module;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.Onepop;
import com.onepop.api.util.client.NullUtil;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class EventManager
{
    private float currentRender2DPartialTicks;
    private float currentRender3DPartialTicks;
    private int[] currentRGBColor;
    
    public EventManager() {
        this.currentRGBColor = new int[] { 0, 0, 0 };
    }
    
    protected void setCurrentRender2DPartialTicks(final float currentRender2DPartialTicks) {
        this.currentRender2DPartialTicks = currentRender2DPartialTicks;
    }
    
    public float getCurrentRender2DPartialTicks() {
        return this.currentRender2DPartialTicks;
    }
    
    protected void setCurrentRender3DPartialTicks(final float currentRender3DPartialTicks) {
        this.currentRender3DPartialTicks = currentRender3DPartialTicks;
    }
    
    public float getCurrentRender3DPartialTicks() {
        return this.currentRender3DPartialTicks;
    }
    
    private void setCurrentRGBColor(final int[] currentRGBColor) {
        this.currentRGBColor = currentRGBColor;
    }
    
    public int[] getCurrentRGBColor() {
        return this.currentRGBColor;
    }
    
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (event.isCanceled()) {
            return;
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (NullUtil.isPlayer()) {
            return;
        }
        Onepop.getPomeloEventManager().dispatchEvent(new ClientTickEvent());
        Onepop.getSpammerManager().onUpdateAll();
        Onepop.getTrackerManager().onUpdateAll();
        Onepop.getPlayerServerManager().onUpdateAll();
        Onepop.getHoleManager().onUpdateAll();
        Onepop.getRotationManager().onUpdateAll();
        Onepop.getBlockManager().onUpdateAll();
        Onepop.getBreakManager().onUpdateAll();
        Onepop.getCurrentItemPacketManager().onUpdateAll();
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent event) {
        if (Onepop.MC.player == null) {
            return;
        }
        this.setCurrentRender2DPartialTicks(event.getPartialTicks());
        RenderGameOverlayEvent.ElementType target = RenderGameOverlayEvent.ElementType.ALL;
        if (!Onepop.getMinecraft().player.isCreative() && Onepop.getMinecraft().player.getRidingEntity() instanceof AbstractHorse) {
            target = RenderGameOverlayEvent.ElementType.HEALTHMOUNT;
        }
        if (event.getType() != target) {
            return;
        }
        for (final Module modules : Onepop.getModuleManager().getModuleList()) {
            if (modules.isEnabled()) {
                modules.onRender2D();
                GL11.glPushMatrix();
                GL11.glEnable(3553);
                GL11.glEnable(3042);
                GlStateManager.enableBlend();
                GL11.glPopMatrix();
                GlStateManager.enableCull();
                GlStateManager.depthMask(true);
                GlStateManager.enableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.enableDepth();
            }
        }
        final boolean flag = !Onepop.getComponentClickGUI().isClosingGUI();
        for (final Component components : Onepop.getComponentManager().getComponentList()) {
            if (flag) {
                if (components.isEnabled() && ModuleHUD.settingRender.getValue()) {
                    components.onRender();
                    GL11.glPushMatrix();
                    GL11.glEnable(3553);
                    GL11.glEnable(3042);
                    GlStateManager.enableBlend();
                    GL11.glPopMatrix();
                    GlStateManager.enableCull();
                    GlStateManager.depthMask(true);
                    GlStateManager.enableTexture2D();
                    GlStateManager.enableBlend();
                    GlStateManager.enableDepth();
                }
                components.cornerDetector();
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        final float[] currentSystemCycle = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int currentColorCycle = Color.HSBtoRGB(currentSystemCycle[0], 1.0f, 1.0f);
        this.currentRGBColor = new int[] { currentColorCycle >> 16 & 0xFF, currentColorCycle >> 8 & 0xFF, currentColorCycle & 0xFF };
        this.setCurrentRender3DPartialTicks(event.getPartialTicks());
        Onepop.getWrapper().onUpdateColor();
        for (final Module modules : Onepop.getModuleManager().getModuleList()) {
            if (modules.isEnabled()) {
                modules.onRender3D();
            }
            modules.onSync();
            modules.onSetting();
        }
        Onepop.getComponentManager().onCornerDetectorComponentList(this.currentRender2DPartialTicks);
    }
    
    @SubscribeEvent
    public void onAttackingDamage(final LivingHurtEvent event) {
        Onepop.getPomeloEventManager().dispatchEvent(event);
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent event) {
        Onepop.getPomeloEventManager().dispatchEvent(event);
    }
    
    @SubscribeEvent
    public void onPlayerSPPushOutOfBlocksEvent(final PlayerSPPushOutOfBlocksEvent event) {
        Onepop.getPomeloEventManager().dispatchEvent(event);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            Onepop.getModuleManager().onInput(Keyboard.getEventKey(), ValueBind.InputType.KEYBOARD);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH, receiveCanceled = true)
    public void onMouse(final InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButtonState()) {
            Onepop.getModuleManager().onInput(Mouse.getEventButton(), ValueBind.InputType.MOUSE);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onChat(final ClientChatEvent event) {
        final String message = event.getMessage();
        final String currentPrefix = CommandManager.getCommandPrefix().getPrefix();
        if (message.startsWith(currentPrefix)) {
            event.setCanceled(true);
            ChatUtil.malloc(message);
            final String[] args = Onepop.getCommandManager().split(message);
            boolean isCommand = false;
            for (final Command commands : Onepop.getCommandManager().getCommandList()) {
                final Command commandRequested = CommandManager.get(args[0]);
                if (commandRequested != null) {
                    commandRequested.onCommand(args);
                    isCommand = true;
                    break;
                }
            }
            if (!isCommand) {
                ChatUtil.print(Onepop.CHAT + ChatFormatting.RED + "Unknown command. Try help for a list commands");
            }
        }
    }
}
