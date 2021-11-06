//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import com.onepop.api.social.Social;
import com.onepop.api.social.type.SocialType;
import com.onepop.api.social.management.SocialManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.network.NetworkPlayerInfo;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.util.network.ServerUtil;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.render.opengl.TurokGL;
import net.minecraft.init.Items;
import java.util.Map;
import com.onepop.api.util.entity.EntityUtil;
import java.awt.Color;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import com.onepop.api.ISLClass;
import com.onepop.client.event.client.RunTickEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.event.impl.EventStage;
import com.onepop.client.event.render.RenderNameEvent;
import java.util.Iterator;
import me.rina.turok.util.TurokMath;
import net.minecraft.client.renderer.GlStateManager;
import com.onepop.api.util.client.NullUtil;
import com.onepop.Onepop;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import java.util.HashMap;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Name Tags", tag = "NameTags", description = "Better name tag.", category = ModuleCategory.RENDER)
public class ModuleNameTags extends Module
{
    public static ValueBoolean settingFriend;
    public static ValueBoolean settingEnemy;
    public static ValueBoolean settingPing;
    public static ValueBoolean settingName;
    public static ValueBoolean settingHealth;
    public static ValueBoolean settingMainHand;
    public static ValueBoolean settingOffhand;
    public static ValueBoolean settingArmor;
    public static ValueNumber settingBackgroundText;
    public static ValueNumber settingBackgroundItems;
    public static ValueBoolean settingShadow;
    public static ValueBoolean settingCustomFont;
    public static ValueBoolean settingSmartScale;
    public static ValueNumber settingScale;
    public static ValueNumber settingOffsetY;
    public static ValueNumber settingRange;
    private HashMap<String, ItemStack> itemStackMap;
    private ArrayList<EntityPlayer> entityToDraw;
    private int positionTagX;
    private int diffX;
    private float scaled;
    int CLEAR;
    int MASK;
    
    public ModuleNameTags() {
        this.itemStackMap = new HashMap<String, ItemStack>();
        this.entityToDraw = new ArrayList<EntityPlayer>();
        this.CLEAR = 256;
        this.MASK = 2929;
    }
    
    @Override
    public void onSetting() {
        Onepop.getWrapper().fontNameTags.setRenderingCustomFont(false);
    }
    
    @Override
    public void onRender3D() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        ModuleNameTags.mc.mcProfiler.startSection("onepop-nametags");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        final float partialTicks = Onepop.getClientEventManager().getCurrentRender3DPartialTicks();
        for (final EntityPlayer entities : this.entityToDraw) {
            final float x = (float)TurokMath.lerp(entities.prevPosX, entities.posX, partialTicks);
            final float y = (float)TurokMath.lerp(entities.prevPosY, entities.posY, partialTicks);
            final float z = (float)TurokMath.lerp(entities.prevPosZ, entities.posZ, partialTicks);
            this.doNameTags(entities, x, y, z, partialTicks);
        }
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        ModuleNameTags.mc.mcProfiler.endSection();
    }
    
    @Listener
    public void onListenRenderNameEvent(final RenderNameEvent event) {
        if (event.getStage() == EventStage.PRE) {
            event.setCanceled(true);
        }
    }
    
    @Listener
    public void onTick(final RunTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.entityToDraw.clear();
        for (final EntityPlayer entities : ISLClass.mc.world.playerEntities) {
            if (entities != null) {
                if (entities == ModuleNameTags.mc.player) {
                    continue;
                }
                if (entities.isDead) {
                    continue;
                }
                if (entities.getHealth() < 0.0f) {
                    continue;
                }
                if (ISLClass.mc.player.getDistanceToEntity((Entity)entities) >= ModuleNameTags.settingRange.getValue().intValue()) {
                    continue;
                }
                if (!this.doAccept(entities)) {
                    continue;
                }
                this.entityToDraw.add(entities);
            }
        }
    }
    
    public void doNameTags(final EntityPlayer entity, final double x, final double y, final double z, final float partialTicks) {
        if (ModuleNameTags.mc.getRenderManager().options == null) {
            return;
        }
        this.itemStackMap.clear();
        if (ModuleNameTags.settingMainHand.getValue()) {
            this.itemStackMap.put("a", entity.getHeldItemMainhand());
        }
        if (ModuleNameTags.settingArmor.getValue()) {
            this.itemStackMap.put("b", (ItemStack)entity.inventory.armorInventory.get(0));
            this.itemStackMap.put("c", (ItemStack)entity.inventory.armorInventory.get(1));
            this.itemStackMap.put("d", (ItemStack)entity.inventory.armorInventory.get(2));
            this.itemStackMap.put("e", (ItemStack)entity.inventory.armorInventory.get(3));
        }
        if (ModuleNameTags.settingOffhand.getValue()) {
            this.itemStackMap.put("f", entity.getHeldItemOffhand());
        }
        final float playerViewX = ISLClass.mc.getRenderManager().playerViewX;
        final float playerViewY = ISLClass.mc.getRenderManager().playerViewY;
        final boolean flag = ISLClass.mc.getRenderManager().options.thirdPersonView == 2;
        final double height = entity.height + ModuleNameTags.settingOffsetY.getValue().intValue() / 100.0 - (entity.isSneaking() ? 0.25f : 0.0f);
        final double referencedX = x - ISLClass.mc.getRenderManager().renderPosX;
        final double referencedY = y + height - ISLClass.mc.getRenderManager().renderPosY;
        final double referencedZ = z - ISLClass.mc.getRenderManager().renderPosZ;
        this.doScale((EntityLivingBase)entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate(referencedX, referencedY, referencedZ);
        GlStateManager.rotate(-playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((flag ? -1.0f : 1.0f) * playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(this.scaled, this.scaled, this.scaled);
        GlStateManager.scale(-0.025f, -0.025f, 0.025f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        final Color color = EntityUtil.getColor(entity, new Color(190, 190, 190));
        this.doDrawText(entity, color);
        int diff = 0;
        for (final Map.Entry<String, ItemStack> entry : this.itemStackMap.entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getValue().getItem() == Items.field_190931_a) {
                    continue;
                }
                diff += 16;
            }
        }
        if (ModuleNameTags.settingBackgroundItems.getValue().intValue() != 0) {
            TurokGL.pushMatrix();
            TurokRenderGL.color(0, 0, 0, ModuleNameTags.settingBackgroundItems.getValue().intValue());
            TurokRenderGL.drawRoundedRect((float)(-((diff + 2) / 2)), -20.0f, (float)(diff + 2), 17.0f, 2.0f);
            TurokGL.popMatrix();
        }
        int positionItems = -(diff / 2);
        GlStateManager.enableTexture2D();
        RenderHelper.enableGUIStandardItemLighting();
        for (final Map.Entry<String, ItemStack> entry2 : this.itemStackMap.entrySet()) {
            if (entry2.getValue() != null) {
                if (entry2.getValue().getItem() == Items.field_190931_a) {
                    continue;
                }
                this.doRenderItem(entry2.getValue(), positionItems, -16);
                positionItems += 16;
            }
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    public void doDrawText(final EntityPlayer entity, final Color color) {
        String ping = "";
        String name = "";
        String health = "";
        int diff = 0;
        if (ModuleNameTags.settingPing.getValue()) {
            final NetworkPlayerInfo playerInfo = ModuleNameTags.mc.player.connection.getPlayerInfo(entity.getUniqueID());
            ping = "[" + ((playerInfo != null) ? ("" + ServerUtil.getPing(playerInfo) + "ms") : "nNms") + "] ";
        }
        if (ModuleNameTags.settingName.getValue()) {
            name = entity.getName() + " ";
        }
        if (ModuleNameTags.settingHealth.getValue()) {
            health = "" + (int)entity.getHealth();
        }
        diff = TurokFontManager.getStringWidth(Onepop.getWrapper().fontNameTags, ping + name + health);
        if (ModuleNameTags.settingBackgroundText.getValue().intValue() != 0) {
            TurokGL.pushMatrix();
            TurokRenderGL.color(0, 0, 0, ModuleNameTags.settingBackgroundText.getValue().intValue());
            TurokRenderGL.drawRoundedRect((float)(-((diff + 2) / 2)), -1.0f, (float)(diff + 2), 10.0f, 2.0f);
            TurokGL.popMatrix();
        }
        int x = -(diff / 2);
        if (ModuleNameTags.settingPing.getValue()) {
            final NetworkPlayerInfo playerInfo2 = ModuleNameTags.mc.player.connection.getPlayerInfo(entity.getUniqueID());
            final String cache = ChatFormatting.GRAY + "[" + ChatFormatting.RESET + "" + ((playerInfo2 != null) ? ("" + ServerUtil.getPing(playerInfo2) + "ms") : "nNms") + ChatFormatting.GRAY + "] ";
            final int ms = (playerInfo2 != null) ? ServerUtil.getPing(playerInfo2) : 0;
            Color colorPing = new Color(0, 255, 0);
            if (ms >= 100) {
                colorPing = new Color(255, TurokMath.clamp(255 - (int)TurokMath.distancingValues((float)ms, 150.0f, 255.0f), 0, 255), 0);
            }
            else {
                colorPing = new Color(TurokMath.clamp((int)TurokMath.distancingValues((float)ms, 100.0f, 255.0f), 0, 255), 255, 0);
            }
            TurokFontManager.render(Onepop.getWrapper().fontNameTags, cache, (float)x, 0.0f, ModuleNameTags.settingShadow.getValue(), colorPing);
            x += TurokFontManager.getStringWidth(Onepop.getWrapper().fontNameTags, ping);
        }
        if (ModuleNameTags.settingName.getValue()) {
            TurokFontManager.render(Onepop.getWrapper().fontNameTags, name, (float)x, 0.0f, ModuleNameTags.settingShadow.getValue(), color);
            x += TurokFontManager.getStringWidth(Onepop.getWrapper().fontNameTags, name);
        }
        if (ModuleNameTags.settingHealth.getValue()) {
            TurokFontManager.render(Onepop.getWrapper().fontNameTags, health, (float)x, 0.0f, ModuleNameTags.settingShadow.getValue(), new Color(255, TurokMath.clamp((int)TurokMath.distancingValues(entity.getHealth(), 20.0f, 255.0f), 0, 255), 0));
            x += TurokFontManager.getStringWidth(Onepop.getWrapper().fontNameTags, health);
        }
    }
    
    public void doRenderItem(final ItemStack item, final int x, final int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(this.CLEAR);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        ModuleNameTags.mc.getRenderItem().zLevel = -200.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        ModuleNameTags.mc.getRenderItem().renderItemAndEffectIntoGUI(item, x, y / 2 - 12);
        ModuleNameTags.mc.getRenderItem().renderItemOverlays(ModuleNameTags.mc.fontRendererObj, item, x, y / 2 - 12 + 2);
        ModuleNameTags.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    public void doScale(final EntityLivingBase entity) {
        final float distance = ISLClass.mc.player.getDistanceToEntity((Entity)entity);
        final float scaling = (float)(distance / 8.0f * Math.pow(1.258925437927246, ModuleNameTags.settingScale.getValue().intValue() / 100.0f));
        final boolean flag = distance <= 8.0f;
        if (!ModuleNameTags.settingSmartScale.getValue()) {
            this.scaled = ModuleNameTags.settingScale.getValue().intValue() / 100.0f;
        }
        else {
            this.scaled = (flag ? 1.5f : scaling);
        }
    }
    
    public boolean doAccept(final EntityPlayer entity) {
        boolean isAccepted = false;
        final Social social = SocialManager.get(entity.getName());
        if (social != null) {
            if (social.getType() == SocialType.FRIEND && ModuleNameTags.settingFriend.getValue()) {
                isAccepted = true;
            }
            if (social.getType() == SocialType.ENEMY && ModuleNameTags.settingEnemy.getValue()) {
                isAccepted = true;
            }
        }
        else {
            isAccepted = true;
        }
        return isAccepted;
    }
    
    static {
        ModuleNameTags.settingFriend = new ValueBoolean("Friend", "Friend", "Allows render friends name tag.", true);
        ModuleNameTags.settingEnemy = new ValueBoolean("Enemy", "Enemy", "Allows render enemies name tag.", false);
        ModuleNameTags.settingPing = new ValueBoolean("Ping", "Ping", "Show ping player.", true);
        ModuleNameTags.settingName = new ValueBoolean("Name", "Name", "Draws name.", true);
        ModuleNameTags.settingHealth = new ValueBoolean("Health", "Health", "Draws health!", true);
        ModuleNameTags.settingMainHand = new ValueBoolean("Main Hand", "MainHand", "Render item main hand.", true);
        ModuleNameTags.settingOffhand = new ValueBoolean("Offhand", "Offhand", "Render item offhand.", true);
        ModuleNameTags.settingArmor = new ValueBoolean("Armor", "Armor", "Render armor!", true);
        ModuleNameTags.settingBackgroundText = new ValueNumber("Background Text", "BackgroundText", "Changes background alpha text!", 150, 0, 255);
        ModuleNameTags.settingBackgroundItems = new ValueNumber("Background Items", "BackgroundItems", "Changes background alpha items!", 150, 0, 255);
        ModuleNameTags.settingShadow = new ValueBoolean("Shadow", "Shadow", "String shadow.", true);
        ModuleNameTags.settingCustomFont = new ValueBoolean("Custom Font", "CustomFont", "Set custom font to render.", true);
        ModuleNameTags.settingSmartScale = new ValueBoolean("Smart Scale", "SmartScale", "Automatically scale if you are close of entity.", true);
        ModuleNameTags.settingScale = new ValueNumber("Scale", "Scale", "The scale of render.", 25, 1, 1000);
        ModuleNameTags.settingOffsetY = new ValueNumber("Offset Y", "OffsetY", "Offset y to render.", 10, 0, 100);
        ModuleNameTags.settingRange = new ValueNumber("Range", "Range", "Distance to capture players.", 200, 0, 200);
    }
}
