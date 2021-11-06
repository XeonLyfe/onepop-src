// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.service;

public interface IMixinServiceBootstrap
{
    String getName();
    
    String getServiceClassName();
    
    void bootstrap();
}
