// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.event.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import com.onepop.api.event.Event;

public class RenderModelEntityLivingEvent extends Event
{
    private EntityLivingBase entityLivingBase;
    private ModelBase modelBase;
    private float limbSwing;
    private float limbSwingAmount;
    private float ageInTicks;
    private float netHeadYaw;
    private float headPitch;
    private float scaleFactor;
    
    public RenderModelEntityLivingEvent(final EntityLivingBase entityLivingBase, final ModelBase modelBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleFactor) {
        this.entityLivingBase = entityLivingBase;
        this.modelBase = modelBase;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
        this.scaleFactor = scaleFactor;
    }
    
    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }
    
    public ModelBase getModelBase() {
        return this.modelBase;
    }
    
    public void setModelBase(final ModelBase modelBase) {
        this.modelBase = modelBase;
    }
    
    public void setLimbSwing(final float limbSwing) {
        this.limbSwing = limbSwing;
    }
    
    public float getLimbSwing() {
        return this.limbSwing;
    }
    
    public void setLimbSwingAmount(final float limbSwingAmount) {
        this.limbSwingAmount = limbSwingAmount;
    }
    
    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }
    
    public void setAgeInTicks(final float ageInTicks) {
        this.ageInTicks = ageInTicks;
    }
    
    public float getAgeInTicks() {
        return this.ageInTicks;
    }
    
    public void setNetHeadYaw(final float netHeadYaw) {
        this.netHeadYaw = netHeadYaw;
    }
    
    public float getNetHeadYaw() {
        return this.netHeadYaw;
    }
    
    public void setHeadPitch(final float headPitch) {
        this.headPitch = headPitch;
    }
    
    public float getHeadPitch() {
        return this.headPitch;
    }
    
    public void setScaleFactor(final float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
    
    public float getScaleFactor() {
        return this.scaleFactor;
    }
}
