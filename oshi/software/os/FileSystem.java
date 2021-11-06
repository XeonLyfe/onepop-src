// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os;

import java.util.List;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface FileSystem
{
    List<OSFileStore> getFileStores();
    
    List<OSFileStore> getFileStores(final boolean p0);
    
    long getOpenFileDescriptors();
    
    long getMaxFileDescriptors();
}
