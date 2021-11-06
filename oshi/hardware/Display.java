// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.hardware;

import oshi.annotation.concurrent.Immutable;

@Immutable
public interface Display
{
    byte[] getEdid();
}
