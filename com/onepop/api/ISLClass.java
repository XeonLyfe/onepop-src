// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api;

import com.onepop.Onepop;
import net.minecraft.client.Minecraft;

public interface ISLClass
{
    public static final Minecraft mc = Onepop.getMinecraft();
    
    void onSave();
    
    void onLoad();
}
