//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.manager.entity;

import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import java.util.Map;
import net.minecraft.entity.Entity;
import java.util.HashMap;
import com.onepop.api.manager.Manager;

public class EntityWorldManager extends Manager
{
    private HashMap<Integer, Entity> entitySavedList;
    
    public EntityWorldManager() {
        super("Entity World Manager", "Save or request entity from space abstract client.");
        this.entitySavedList = new HashMap<Integer, Entity>();
    }
    
    public void setEntitySavedList(final HashMap<Integer, Entity> entitySavedList) {
        this.entitySavedList = entitySavedList;
    }
    
    public HashMap<Integer, Entity> getEntitySavedList() {
        return this.entitySavedList;
    }
    
    public void saveEntity(final int entityId, final Entity entity) {
        this.entitySavedList.put(entityId, entity);
    }
    
    public Entity removeEntity(final int entityId) {
        final Entity entity = this.getEntity(entityId);
        if (entity != null) {
            this.entitySavedList.remove(entityId);
        }
        return entity;
    }
    
    public Entity getEntity(final int entityId) {
        return this.entitySavedList.get(entityId);
    }
    
    @Override
    public void onUpdateAll() {
        for (final Map.Entry<Integer, Entity> entities : new HashMap(this.entitySavedList).entrySet()) {
            final int id = entities.getKey();
            final Entity entity = entities.getValue();
            boolean isManageable = false;
            if (entity.isDead) {
                isManageable = true;
            }
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                if (entityLivingBase.getHealth() < 0.0f) {
                    isManageable = true;
                }
            }
            if (entity instanceof EntityPlayer && EntityWorldManager.mc.getConnection() != null) {
                final EntityPlayer entityPlayer = (EntityPlayer)entities;
                final NetworkPlayerInfo playerInfo = EntityWorldManager.mc.getConnection().getPlayerInfo(entityPlayer.getUniqueID());
                if (playerInfo == null) {
                    isManageable = true;
                }
            }
            if (isManageable) {
                this.entitySavedList.remove(id);
            }
        }
    }
}
