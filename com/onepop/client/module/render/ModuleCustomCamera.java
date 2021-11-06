//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.render;

import com.onepop.client.event.render.PerspectiveEvent;
import net.minecraft.client.renderer.GlStateManager;
import me.rina.turok.render.opengl.TurokGL;
import com.onepop.client.event.render.EnumHandSideEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Custom Camera", tag = "CustomCamera", description = "Manage camera stuff and player hands.", category = ModuleCategory.RENDER)
public class ModuleCustomCamera extends Module
{
    public static ModuleCustomCamera INSTANCE;
    public static ValueNumber settingFieldOfView;
    public static ValueBoolean settingRatioChange;
    public static ValueNumber settingRatio;
    public static ValueBoolean settingNoCameraClip;
    public static ValueBoolean settingRight;
    public static ValueNumber settingRightX;
    public static ValueNumber settingRightY;
    public static ValueNumber settingRightZ;
    public static ValueNumber settingRightYaw;
    public static ValueNumber settingRightPitch;
    public static ValueNumber settingRightRoll;
    public static ValueNumber settingScaleRight;
    public static ValueBoolean settingLeft;
    public static ValueNumber settingLeftX;
    public static ValueNumber settingLeftY;
    public static ValueNumber settingLeftZ;
    public static ValueNumber settingLeftYaw;
    public static ValueNumber settingLeftPitch;
    public static ValueNumber settingLeftRoll;
    public static ValueNumber settingScaleLeft;
    
    public ModuleCustomCamera() {
        ModuleCustomCamera.INSTANCE = this;
    }
    
    @Override
    public void onSetting() {
        ModuleCustomCamera.settingRightX.setEnabled(ModuleCustomCamera.settingRight.getValue());
        ModuleCustomCamera.settingRightY.setEnabled(ModuleCustomCamera.settingRight.getValue());
        ModuleCustomCamera.settingRightZ.setEnabled(ModuleCustomCamera.settingRight.getValue());
        ModuleCustomCamera.settingRightYaw.setEnabled(ModuleCustomCamera.settingRight.getValue());
        ModuleCustomCamera.settingRightPitch.setEnabled(ModuleCustomCamera.settingRight.getValue());
        ModuleCustomCamera.settingRightRoll.setEnabled(ModuleCustomCamera.settingRight.getValue());
        ModuleCustomCamera.settingScaleRight.setEnabled(ModuleCustomCamera.settingRight.getValue());
        ModuleCustomCamera.settingLeftX.setEnabled(ModuleCustomCamera.settingLeft.getValue());
        ModuleCustomCamera.settingLeftY.setEnabled(ModuleCustomCamera.settingLeft.getValue());
        ModuleCustomCamera.settingLeftZ.setEnabled(ModuleCustomCamera.settingLeft.getValue());
        ModuleCustomCamera.settingLeftYaw.setEnabled(ModuleCustomCamera.settingLeft.getValue());
        ModuleCustomCamera.settingLeftPitch.setEnabled(ModuleCustomCamera.settingLeft.getValue());
        ModuleCustomCamera.settingLeftRoll.setEnabled(ModuleCustomCamera.settingLeft.getValue());
        ModuleCustomCamera.settingScaleLeft.setEnabled(ModuleCustomCamera.settingLeft.getValue());
        ModuleCustomCamera.settingRatio.setEnabled(ModuleCustomCamera.settingRatioChange.getValue());
    }
    
    @Listener
    public void onListenTickEvent(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        ModuleCustomCamera.mc.gameSettings.fovSetting = (float)ModuleCustomCamera.settingFieldOfView.getValue().intValue();
    }
    
    @Listener
    public void onEnumHandSideEvent(final EnumHandSideEvent event) {
        switch (event.getHandSide()) {
            case LEFT: {
                if (ModuleCustomCamera.settingLeft.getValue()) {
                    TurokGL.translate(ModuleCustomCamera.settingLeftX.getValue().floatValue() / 100.0f, ModuleCustomCamera.settingLeftY.getValue().floatValue() / 100.0f, ModuleCustomCamera.settingLeftZ.getValue().floatValue() / 100.0f);
                    TurokGL.scale(ModuleCustomCamera.settingScaleLeft.getValue().floatValue() / 10.0f, ModuleCustomCamera.settingScaleLeft.getValue().floatValue() / 10.0f, ModuleCustomCamera.settingScaleLeft.getValue().floatValue() / 10.0f);
                    GlStateManager.rotate(ModuleCustomCamera.settingLeftYaw.getValue().floatValue(), 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(ModuleCustomCamera.settingLeftPitch.getValue().floatValue(), 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(ModuleCustomCamera.settingLeftRoll.getValue().floatValue(), 0.0f, 0.0f, 1.0f);
                    break;
                }
                break;
            }
            case RIGHT: {
                if (ModuleCustomCamera.settingRight.getValue()) {
                    TurokGL.translate(ModuleCustomCamera.settingRightX.getValue().floatValue() / 100.0f, ModuleCustomCamera.settingRightY.getValue().floatValue() / 100.0f, ModuleCustomCamera.settingRightZ.getValue().floatValue() / 100.0f);
                    TurokGL.scale(ModuleCustomCamera.settingScaleRight.getValue().floatValue() / 10.0f, ModuleCustomCamera.settingScaleRight.getValue().floatValue() / 10.0f, ModuleCustomCamera.settingScaleRight.getValue().floatValue() / 10.0f);
                    GlStateManager.rotate(ModuleCustomCamera.settingRightYaw.getValue().floatValue(), 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(ModuleCustomCamera.settingRightPitch.getValue().floatValue(), 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate(ModuleCustomCamera.settingRightRoll.getValue().floatValue(), 0.0f, 0.0f, 1.0f);
                    break;
                }
                break;
            }
        }
    }
    
    @Listener
    public void onPerspectiveEvent(final PerspectiveEvent event) {
        if (ModuleCustomCamera.settingRatioChange.getValue()) {
            event.setAspect(ModuleCustomCamera.settingRatio.getValue().floatValue());
        }
        else {
            event.setAspect(ModuleCustomCamera.mc.displayWidth / (float)ModuleCustomCamera.mc.displayHeight);
        }
    }
    
    static {
        ModuleCustomCamera.settingFieldOfView = new ValueNumber("Field of View", "FieldOfView", "Field of view camera.", 130, 0, 180);
        ModuleCustomCamera.settingRatioChange = new ValueBoolean("Ratio Changer", "RatioChanger", "Allows you to change the ratio.", false);
        ModuleCustomCamera.settingRatio = new ValueNumber("Ratio", "Ratio", "The ratio for ratio changer.", ModuleCustomCamera.mc.displayWidth / ModuleCustomCamera.mc.displayHeight, 0.0, 3.0);
        ModuleCustomCamera.settingNoCameraClip = new ValueBoolean("No Camera Clip", "NoCameraClip", "No camera clip!", false);
        ModuleCustomCamera.settingRight = new ValueBoolean("Right", "Right", "Right", false);
        ModuleCustomCamera.settingRightX = new ValueNumber("Right X", "RightX", "Changes the x value.", 0.0, -50.0, 50.0);
        ModuleCustomCamera.settingRightY = new ValueNumber("Right Y", "RightY", "Changes the y value.", 0.0, -50.0, 50.0);
        ModuleCustomCamera.settingRightZ = new ValueNumber("Right Z", "RightZ", "Changes the z value.", 0.0, -50.0, 50.0);
        ModuleCustomCamera.settingRightYaw = new ValueNumber("Right Yaw", "RightYaw", "Changes the yaw of the item", 0, -100, 100);
        ModuleCustomCamera.settingRightPitch = new ValueNumber("Right Pitch", "RightPitch", "Changes the pitch of the item", 0, -100, 100);
        ModuleCustomCamera.settingRightRoll = new ValueNumber("Right Roll", "RightRoll", "Changes the roll of the item", 0, -100, 100);
        ModuleCustomCamera.settingScaleRight = new ValueNumber("Scale Right", "ScaleRight", "Changes the scale.", 10, 0, 50);
        ModuleCustomCamera.settingLeft = new ValueBoolean("Left", "Left", "Left", false);
        ModuleCustomCamera.settingLeftX = new ValueNumber("Left X", "LeftX", "Changes the x value.", 0.0, -50.0, 50.0);
        ModuleCustomCamera.settingLeftY = new ValueNumber("Left Y", "LeftY", "Changes the y value.", 0.0, -50.0, 50.0);
        ModuleCustomCamera.settingLeftZ = new ValueNumber("Left Z", "LeftZ", "Changes the z value.", 0.0, -50.0, 50.0);
        ModuleCustomCamera.settingLeftYaw = new ValueNumber("Left Yaw", "LeftYaw", "Changes the yaw of the item", 0, -100, 100);
        ModuleCustomCamera.settingLeftPitch = new ValueNumber("Left Pitch", "LeftPitch", "Changes the pitch of the item", 0, -100, 100);
        ModuleCustomCamera.settingLeftRoll = new ValueNumber("Left Roll", "LeftRoll", "Changes the roll of the item", 0, -100, 100);
        ModuleCustomCamera.settingScaleLeft = new ValueNumber("Scale Left", "ScaleLeft", "Changes the scale.", 10, 0, 50);
    }
}
