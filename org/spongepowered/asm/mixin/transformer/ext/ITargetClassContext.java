// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin.transformer.ext;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public interface ITargetClassContext
{
    ClassInfo getClassInfo();
    
    ClassNode getClassNode();
}
