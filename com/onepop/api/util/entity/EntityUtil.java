//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.entity;

import me.rina.turok.util.TurokMath;
import com.onepop.api.social.Social;
import com.onepop.client.module.client.ModuleHUD;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import com.onepop.api.social.type.SocialType;
import com.onepop.api.social.management.SocialManager;
import com.onepop.Onepop;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import com.onepop.api.util.world.BlockUtil;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.util.world.BlocksUtil;
import net.minecraft.entity.player.EntityPlayer;

public class EntityUtil
{
    public static boolean isEntityPlayerSurrounded(final EntityPlayer entityPlayer) {
        for (final BlockPos add : BlocksUtil.FULL_SURROUND) {
            final BlockPos offset = new BlockPos(Math.floor(entityPlayer.posX), Math.floor(entityPlayer.posY), Math.floor(entityPlayer.posZ)).add((Vec3i)add);
            if (BlockUtil.isAir(offset)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isEntityPlayerNaked(final EntityPlayer entityPlayer) {
        boolean isPlayer = true;
        for (int i = 0; i < 4; ++i) {
            final Item item = ((ItemStack)entityPlayer.inventory.armorInventory.get(i)).getItem();
            if (item != Items.field_190931_a) {
                isPlayer = false;
                break;
            }
        }
        return isPlayer;
    }
    
    public static Vec3d eye(final Entity entity) {
        return new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
    }
    
    public static EntityPlayer getTarget(final float range, final boolean unsafe, final boolean naked) {
        final Minecraft mc = Onepop.getMinecraft();
        EntityPlayer target = null;
        float distance = range;
        for (final EntityPlayer entities : mc.world.playerEntities) {
            if (!entities.isDead && entities.getHealth() >= 0.0f && entities != mc.player) {
                if (SocialManager.get(entities.getName()) != null && SocialManager.get(entities.getName()).getType() == SocialType.FRIEND) {
                    continue;
                }
                if (mc.player.getDistanceToEntity((Entity)entities) > range) {
                    continue;
                }
                if (SocialManager.get(entities.getName()) != null && SocialManager.get(entities.getName()).getType() == SocialType.ENEMY) {
                    return entities;
                }
                if (naked && isEntityPlayerNaked(entities)) {
                    continue;
                }
                final boolean flag = isEntityPlayerSurrounded(entities);
                final float diff = mc.player.getDistanceToEntity((Entity)entities);
                if (unsafe && !flag) {
                    return entities;
                }
                if (diff >= distance) {
                    continue;
                }
                target = entities;
                distance = diff;
            }
        }
        return target;
    }
    
    public static Color getColor(final EntityPlayer player, final Color color) {
        final Social user = SocialManager.get(player.getName());
        if (user != null && user.getType() == SocialType.FRIEND) {
            return new Color(ModuleHUD.settingRed.getValue().intValue(), ModuleHUD.settingGreen.getValue().intValue(), ModuleHUD.settingBlue.getValue().intValue(), 255);
        }
        return color;
    }
    
    public static Vec3d getInterpolatedLinearVec(final Entity entity, final float ticks) {
        return new Vec3d(TurokMath.lerp(entity.lastTickPosX, entity.posX, ticks), TurokMath.lerp(entity.lastTickPosY, entity.posY, ticks), TurokMath.lerp(entity.lastTickPosZ, entity.posZ, ticks));
    }
}
