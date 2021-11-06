//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.module.combat;

import com.onepop.api.social.Social;
import java.util.Iterator;
import com.onepop.api.social.type.SocialType;
import com.onepop.api.social.management.SocialManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.monster.IMob;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import com.onepop.client.manager.network.RotationManager;
import com.onepop.api.util.math.RotationUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import com.onepop.api.util.network.PacketUtil;
import com.onepop.api.ISLClass;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import com.onepop.api.util.entity.EntityUtil;
import com.onepop.api.util.client.NullUtil;
import com.onepop.client.event.client.ClientTickEvent;
import net.minecraft.entity.Entity;
import com.onepop.api.setting.value.ValueNumber;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.module.registry.Registry;
import com.onepop.api.module.Module;

@Registry(name = "Kill Aura", tag = "KillAura", description = "Make you hit any entity close of you.", category = ModuleCategory.COMBAT)
public class ModuleKillAura extends Module
{
    public static ValueBoolean settingOnlySword;
    public static ValueEnum settingRotation;
    public static ValueBoolean settingPlayer;
    public static ValueBoolean settingMob;
    public static ValueBoolean settingAnimal;
    public static ValueBoolean settingVehicles;
    public static ValueBoolean settingProjectiles;
    public static ValueBoolean settingOffhandItem;
    public static ValueNumber settingRange;
    public static ValueEnum settingTargetMode;
    private Entity target;
    
    @Listener
    public void onListen(final ClientTickEvent event) {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        final EntityPlayer currentPlayer = ModuleKillAura.settingPlayer.getValue() ? EntityUtil.getTarget(ModuleKillAura.settingRange.getValue().floatValue(), false, false) : null;
        if (currentPlayer == null) {
            this.target = this.doFind();
        }
        else {
            this.target = (Entity)currentPlayer;
        }
        this.status((this.target != null) ? this.target.getName() : "");
        if (this.target != null && !this.target.isDead) {
            final boolean flag = !ModuleKillAura.settingOnlySword.getValue() || ModuleKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword;
            if (ModuleKillAura.mc.player.getCooledAttackStrength(0.0f) >= 1.0f && flag) {
                final ItemStack offhand = ModuleKillAura.mc.player.getHeldItemOffhand();
                if (offhand.getItem() == Items.SHIELD && ModuleKillAura.settingOffhandItem.getValue()) {
                    PacketUtil.send((Packet<?>)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, ISLClass.mc.player.getHorizontalFacing()));
                }
                final float[] rotates = RotationUtil.getBreakRotation(new Vec3d(this.target.posX, this.target.posY, this.target.posZ));
                RotationManager.task(ModuleKillAura.settingRotation.getValue(), rotates);
                ModuleKillAura.mc.playerController.attackEntity((EntityPlayer)ISLClass.mc.player, this.target);
                ModuleKillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
    
    public boolean doVerify(final Entity entity) {
        boolean isVerified = false;
        if (entity instanceof IMob && ModuleKillAura.settingMob.getValue()) {
            isVerified = true;
        }
        if (entity instanceof IAnimals && !(entity instanceof IMob) && ModuleKillAura.settingAnimal.getValue()) {
            isVerified = true;
        }
        if ((entity instanceof EntityBoat || entity instanceof EntityMinecart || entity instanceof EntityMinecartContainer) && ModuleKillAura.settingVehicles.getValue()) {
            isVerified = true;
        }
        if ((entity instanceof EntityShulkerBullet || entity instanceof EntityFireball) && ModuleKillAura.settingProjectiles.getValue()) {
            isVerified = true;
        }
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            if (entityLivingBase.isDead || entityLivingBase.getHealth() < 0.0f) {
                isVerified = false;
            }
        }
        return isVerified;
    }
    
    public Entity doFind() {
        Entity entity = null;
        float range = ModuleKillAura.settingRange.getValue().floatValue();
        for (final Entity entities : ISLClass.mc.world.loadedEntityList) {
            if (entities != null) {
                if (entities == ModuleKillAura.mc.player) {
                    continue;
                }
                if (!this.doVerify(entities)) {
                    continue;
                }
                final Social social = SocialManager.get(entities.getName());
                final float distance = ModuleKillAura.mc.player.getDistanceToEntity(entities);
                if (social != null && social.getType() == SocialType.ENEMY && distance <= range) {
                    entity = entities;
                    range = distance;
                }
                else {
                    if (distance > range) {
                        continue;
                    }
                    entity = entities;
                    range = distance;
                }
            }
        }
        return entity;
    }
    
    static {
        ModuleKillAura.settingOnlySword = new ValueBoolean("Only Sword", "OnlySword", "Only sword to hit.", false);
        ModuleKillAura.settingRotation = new ValueEnum("Rotation", "Rotation", "Rotation for strict servers!", RotationManager.Rotation.NONE);
        ModuleKillAura.settingPlayer = new ValueBoolean("Player", "Player", "Hit entity players.", true);
        ModuleKillAura.settingMob = new ValueBoolean("Mob", "Mob", "Hit entity mobs.", true);
        ModuleKillAura.settingAnimal = new ValueBoolean("Animal", "Animal", "Hit entity animal.", true);
        ModuleKillAura.settingVehicles = new ValueBoolean("Vehicle", "Vehicle", "Hit entity vehicles.", true);
        ModuleKillAura.settingProjectiles = new ValueBoolean("Projectile", "Projectile", "Hit entity projectiles.", true);
        ModuleKillAura.settingOffhandItem = new ValueBoolean("Offhand Item", "OffhandItem", "Enable use item while aura is hitting.", true);
        ModuleKillAura.settingRange = new ValueNumber("Range", "Range", "Range for target.", 4.0f, 1.0f, 6.0f);
        ModuleKillAura.settingTargetMode = new ValueEnum("Target Mode", "TargetMode", "Modes for get target.", TargetMode.FOCUSED);
    }
    
    public enum TargetMode
    {
        FOCUSED, 
        CLOSET;
    }
}
