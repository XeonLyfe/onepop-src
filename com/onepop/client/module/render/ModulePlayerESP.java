//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import com.onepop.api.social.Social;
import com.onepop.api.social.type.SocialType;
import com.onepop.api.social.management.SocialManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.entity.Entity;
import me.rina.turok.render.opengl.TurokGL;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.render.RenderModelEntityLivingEvent;
import com.onepop.Onepop;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Player ESP", tag = "PlayerESP", description = "Chams!", category = ModuleCategory.RENDER)
public class ModulePlayerESP extends Module
{
    public static ValueBoolean settingEnemy;
    public static ValueBoolean settingFriend;
    public static ValueBoolean settingEveryone;
    public static ValueBoolean settingFrustumNoRender;
    public static ValueNumber settingScale;
    public static ValueNumber settingOffsetY;
    public static ValueEnum settingRenderMode;
    public static ValueBoolean settingOffset;
    public static ValueBoolean settingFriendColor;
    public static ValueNumber settingAlpha;
    public static ValueBoolean settingRGB;
    public static ValueNumber settingRed;
    public static ValueNumber settingGreen;
    public static ValueNumber settingBlue;
    public static ValueNumber settingLineSize;
    
    @Override
    public void onSetting() {
        if (ModulePlayerESP.settingRGB.getValue()) {
            ModulePlayerESP.settingRed.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[0]);
            ModulePlayerESP.settingGreen.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[1]);
            ModulePlayerESP.settingBlue.setValue(Onepop.getClientEventManager().getCurrentRGBColor()[2]);
        }
        ModulePlayerESP.settingLineSize.setEnabled(ModulePlayerESP.settingRenderMode.getValue() == Mode.SMOOTH || ModulePlayerESP.settingRenderMode.getValue() == Mode.OUTLINE);
    }
    
    @Listener
    public void onRenderModel(final RenderModelEntityLivingEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        if (this.isAcceptable(event.getEntityLivingBase())) {
            event.setCanceled(true);
            final float scale = ModulePlayerESP.settingScale.getValue().intValue() / 1000.0f;
            final float offset = -(ModulePlayerESP.settingOffsetY.getValue().intValue() / 1000.0f);
            TurokGL.pushMatrix();
            TurokGL.pushAttrib(1048575);
            switch (ModulePlayerESP.settingRenderMode.getValue()) {
                case SMOOTH: {
                    TurokGL.polygonMode(1028, 6913);
                    TurokGL.disable(3553);
                    TurokGL.disable(2896);
                    TurokGL.disable(2929);
                    TurokGL.enable(2848);
                    TurokGL.enable(3042);
                    TurokGL.enable(32823);
                    TurokGL.disable(3553);
                    TurokGL.disable(2896);
                    TurokGL.enable(3042);
                    TurokGL.blendFunc(770, 771);
                    TurokGL.lineSize(ModulePlayerESP.settingLineSize.getValue().floatValue());
                    TurokGL.color((float)ModulePlayerESP.settingRed.getValue().intValue(), (float)ModulePlayerESP.settingGreen.getValue().intValue(), (float)ModulePlayerESP.settingBlue.getValue().intValue(), (float)ModulePlayerESP.settingAlpha.getValue().intValue());
                    break;
                }
                case OUTLINE: {
                    TurokGL.polygonMode(1028, 6913);
                    TurokGL.disable(3553);
                    TurokGL.disable(2896);
                    TurokGL.disable(2929);
                    TurokGL.enable(2848);
                    TurokGL.enable(3042);
                    TurokGL.enable(32823);
                    TurokGL.disable(3553);
                    TurokGL.disable(2896);
                    TurokGL.enable(3042);
                    TurokGL.blendFunc(770, 771);
                    TurokGL.lineSize(ModulePlayerESP.settingLineSize.getValue().floatValue());
                    TurokGL.color((float)ModulePlayerESP.settingRed.getValue().intValue(), (float)ModulePlayerESP.settingGreen.getValue().intValue(), (float)ModulePlayerESP.settingBlue.getValue().intValue(), (float)ModulePlayerESP.settingAlpha.getValue().intValue());
                    break;
                }
                case LINE: {
                    TurokGL.polygonMode(1028, 6913);
                    TurokGL.enable(10754);
                    TurokGL.disable(3553);
                    TurokGL.disable(2896);
                    TurokGL.enable(3042);
                    TurokGL.blendFunc(770, 771);
                    TurokGL.lineSize(ModulePlayerESP.settingLineSize.getValue().floatValue());
                    TurokGL.color((float)ModulePlayerESP.settingRed.getValue().intValue(), (float)ModulePlayerESP.settingGreen.getValue().intValue(), (float)ModulePlayerESP.settingBlue.getValue().intValue(), (float)ModulePlayerESP.settingAlpha.getValue().intValue());
                    break;
                }
                case FILL: {
                    TurokGL.enable(32823);
                    TurokGL.disable(3553);
                    TurokGL.disable(2896);
                    TurokGL.enable(3042);
                    TurokGL.blendFunc(770, 771);
                    TurokGL.color((float)ModulePlayerESP.settingRed.getValue().intValue(), (float)ModulePlayerESP.settingGreen.getValue().intValue(), (float)ModulePlayerESP.settingBlue.getValue().intValue(), (float)ModulePlayerESP.settingAlpha.getValue().intValue());
                    break;
                }
                case SKIN: {
                    TurokGL.enable(32823);
                    TurokGL.disable(2896);
                    TurokGL.enable(3042);
                    TurokGL.blendFunc(770, 771);
                    TurokGL.color(255.0f, 255.0f, 255.0f, (float)ModulePlayerESP.settingAlpha.getValue().intValue());
                    break;
                }
            }
            TurokGL.translate(0.0f, offset, 0.0f);
            TurokGL.scale(scale, scale, scale);
            TurokGL.polygonOffset(1.0f, -1100000.0f);
            event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
            if (ModulePlayerESP.settingOffset.getValue()) {
                TurokGL.polygonOffset(1.0f, 1100000.0f);
            }
            TurokGL.enable(3553);
            TurokGL.enable(2896);
            TurokGL.enable(32823);
            TurokGL.disable(3042);
            TurokGL.color(255.0f, 255.0f, 255.0f, 255.0f);
            TurokGL.popAttrib();
            TurokGL.popMatrix();
            switch (ModulePlayerESP.settingRenderMode.getValue()) {
                case OUTLINE: {
                    TurokGL.pushMatrix();
                    TurokGL.pushAttrib(1048575);
                    TurokGL.enable(32823);
                    TurokGL.disable(2896);
                    TurokGL.enable(3042);
                    TurokGL.blendFunc(770, 771);
                    TurokGL.color(255.0f, 255.0f, 255.0f, (float)ModulePlayerESP.settingAlpha.getValue().intValue());
                    TurokGL.translate(0.0f, offset, 0.0f);
                    TurokGL.scale(scale, scale, scale);
                    TurokGL.polygonOffset(1.0f, -1100000.0f);
                    event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
                    if (ModulePlayerESP.settingOffset.getValue()) {
                        TurokGL.polygonOffset(1.0f, 1100000.0f);
                    }
                    TurokGL.enable(3553);
                    TurokGL.enable(2896);
                    TurokGL.enable(32823);
                    TurokGL.disable(3042);
                    TurokGL.color(255.0f, 255.0f, 255.0f, 255.0f);
                    TurokGL.popAttrib();
                    TurokGL.popMatrix();
                    break;
                }
            }
            if (ModulePlayerESP.settingFrustumNoRender.getValue() && ModulePlayerESP.settingRenderMode.getValue() != Mode.OUTLINE) {
                TurokGL.pushMatrix();
                TurokGL.translate(0.0f, offset, 0.0f);
                TurokGL.scale(scale, scale, scale);
                event.getModelBase().render((Entity)event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
                TurokGL.popMatrix();
            }
        }
    }
    
    public boolean isAcceptable(final EntityLivingBase entityLivingBase) {
        boolean isAccepted = false;
        if (!(entityLivingBase instanceof EntityPlayer)) {
            return false;
        }
        final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
        final Social social = SocialManager.get(entityPlayer.getName());
        if (social != null && social.getType() == SocialType.FRIEND && ModulePlayerESP.settingFriend.getValue()) {
            isAccepted = true;
        }
        if (social != null && social.getType() == SocialType.ENEMY && ModulePlayerESP.settingEnemy.getValue()) {
            isAccepted = true;
        }
        if (social == null && ModulePlayerESP.settingEveryone.getValue()) {
            isAccepted = true;
        }
        return isAccepted;
    }
    
    static {
        ModulePlayerESP.settingEnemy = new ValueBoolean("Enemy", "Enemy", "Enable for all your enemies!", false);
        ModulePlayerESP.settingFriend = new ValueBoolean("Friend", "Friend", "Enable for all yours friends!", true);
        ModulePlayerESP.settingEveryone = new ValueBoolean("Everyone", "Everyone", "Everyone!!!!!", true);
        ModulePlayerESP.settingFrustumNoRender = new ValueBoolean("Frustum No Render", "FrustumNoRender", "Disable ESP on frustum area!", true);
        ModulePlayerESP.settingScale = new ValueNumber("Scale", "Scale", "Scale of entity.", 1000, 0, 2000);
        ModulePlayerESP.settingOffsetY = new ValueNumber("Offset Y", "OffsetY", "Offset space for Y", 0, -2000, 2000);
        ModulePlayerESP.settingRenderMode = new ValueEnum("Render Mode", "RenderMode", "Type of render.", Mode.SMOOTH);
        ModulePlayerESP.settingOffset = new ValueBoolean("Offset", "Offset", "Offset.", true);
        ModulePlayerESP.settingFriendColor = new ValueBoolean("Friend Color", "PostFriendColor", "Set HUD color for friended players.", true);
        ModulePlayerESP.settingAlpha = new ValueNumber("Alpha", "Alpha", "Alpha value.", 100, 0, 255);
        ModulePlayerESP.settingRGB = new ValueBoolean("RGB", "RGB", "RGB effect.", false);
        ModulePlayerESP.settingRed = new ValueNumber("Red", "Red", "Color alpha.", 218, 0, 255);
        ModulePlayerESP.settingGreen = new ValueNumber("Green", "Green", "Color green.", 165, 0, 255);
        ModulePlayerESP.settingBlue = new ValueNumber("Blue", "Blue", "Color blue.", 32, 0, 255);
        ModulePlayerESP.settingLineSize = new ValueNumber("Line Size", "LineSize", "The line size.", 1.0f, 1.0f, 5.0f);
    }
    
    public enum Mode
    {
        SMOOTH, 
        FILL, 
        LINE, 
        OUTLINE, 
        SKIN;
    }
}
