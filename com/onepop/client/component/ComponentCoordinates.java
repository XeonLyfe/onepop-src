//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import net.minecraft.util.EnumFacing;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.component.impl.ComponentSetting;
import com.onepop.api.component.Component;

public class ComponentCoordinates extends Component
{
    public static ComponentSetting<Direction> settingDirection;
    public static ComponentSetting<Placement> settingPlacement;
    
    public ComponentCoordinates() {
        super("Coordinates", "Coordinates", "Shows current player coordinates.", StringType.USE);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        if (ComponentCoordinates.mc.player == null || ComponentCoordinates.mc.world == null) {
            return;
        }
        final String x = String.format("%.1f", ComponentCoordinates.mc.player.posX);
        final String y = String.format("%.1f", ComponentCoordinates.mc.player.posY);
        final String z = String.format("%.1f", ComponentCoordinates.mc.player.posZ);
        final float value = ComponentCoordinates.mc.world.getBiome(ComponentCoordinates.mc.player.getPosition()).getBiomeName().equals("Hell") ? 8.0f : 0.125f;
        final String xNether = String.format("%.1f", ComponentCoordinates.mc.player.posX * value);
        final String zNether = String.format("%.1f", ComponentCoordinates.mc.player.posZ * value);
        final String coordinatesFlat = this.getDirection() + "XYZ " + ChatFormatting.GRAY + x + ", " + y + ", " + z + ChatFormatting.RESET + " [" + ChatFormatting.GRAY + xNether + ", " + zNether + ChatFormatting.RESET + "]";
        final String directionStacked = this.getDirection();
        final String coordinatesStackedX = "X " + ChatFormatting.GRAY + x + ChatFormatting.RESET + " [" + ChatFormatting.GRAY + xNether + ChatFormatting.RESET + "]";
        final String coordinatesStackedY = "X " + ChatFormatting.GRAY + y;
        final String coordinatesStackedZ = "X " + ChatFormatting.GRAY + z + ChatFormatting.RESET + " [" + ChatFormatting.GRAY + zNether + ChatFormatting.RESET + "]";
        Label_0649: {
            switch (ComponentCoordinates.settingPlacement.getValue()) {
                case Flat: {
                    this.render(coordinatesFlat, 0.0f, 0.0f);
                    this.rect.setWidth((float)this.getStringWidth(coordinatesFlat));
                    this.rect.setHeight((float)this.getStringHeight(coordinatesFlat));
                    break;
                }
                case Stacked: {
                    this.render(directionStacked, 0.0f, 0.0f);
                    this.render(coordinatesStackedX, 0.0f, 10.0f);
                    this.render(coordinatesStackedY, 0.0f, 20.0f);
                    this.render(coordinatesStackedZ, 0.0f, 30.0f);
                    this.rect.setWidth((float)this.getStringWidth(coordinatesStackedX));
                    switch (ComponentCoordinates.settingDirection.getValue()) {
                        case XZ: {
                            this.rect.setHeight(40.0f);
                            break Label_0649;
                        }
                        case NSWE: {
                            this.rect.setHeight(40.0f);
                            break Label_0649;
                        }
                        case NONE: {
                            this.rect.setHeight(30.0f);
                            break Label_0649;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public String getDirection() {
        String the = "";
        switch (ComponentCoordinates.settingDirection.getValue()) {
            case XZ: {
                the = this.getFaceDirection(true, false);
                break;
            }
            case NSWE: {
                the = this.getFaceDirection(false, true);
                break;
            }
        }
        return the;
    }
    
    public String getFaceDirection(final boolean xz, final boolean nswe) {
        final EnumFacing facing = ComponentCoordinates.mc.getRenderViewEntity().getHorizontalFacing();
        String value = "Invalid";
        final String l = ChatFormatting.RESET + "[" + ChatFormatting.GRAY;
        final String r = ChatFormatting.RESET + "]";
        switch (facing) {
            case NORTH: {
                value = (xz ? (l + "-Z" + r) : (nswe ? (l + "N" + r) : ("North " + l + "-Z" + r)));
                break;
            }
            case SOUTH: {
                value = (xz ? (l + "+Z" + r) : (nswe ? (l + "S" + r) : ("South " + l + "+Z" + r)));
                break;
            }
            case WEST: {
                value = (xz ? (l + "-X" + r) : (nswe ? (l + "W" + r) : ("West " + l + "-X" + r)));
                break;
            }
            case EAST: {
                value = (xz ? (l + "+X" + r) : (nswe ? (l + "E" + r) : ("East " + l + "+X" + r)));
                break;
            }
        }
        return value + " ";
    }
    
    static {
        ComponentCoordinates.settingDirection = new ComponentSetting<Direction>("Direction", "Direction", "Direction of the player.", Direction.XZ);
        ComponentCoordinates.settingPlacement = new ComponentSetting<Placement>("Mode", "mode", "Mode of display", Placement.Flat);
    }
    
    public enum Direction
    {
        NSWE, 
        XZ, 
        NONE;
    }
    
    public enum Placement
    {
        Flat, 
        Stacked;
    }
}
