// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.Label;
import java.util.Map;

public interface Textifiable
{
    void textify(final StringBuffer p0, final Map<Label, String> p1);
}
