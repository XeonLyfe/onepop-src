// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.manager.impl;

import com.onepop.Onepop;
import net.minecraft.client.Minecraft;

public interface ManageStructure
{
    public static final Minecraft mc = Onepop.getMinecraft();
    
    void onUpdateAll();
}
