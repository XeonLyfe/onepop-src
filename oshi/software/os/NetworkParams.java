// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.os;

import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface NetworkParams
{
    String getHostName();
    
    String getDomainName();
    
    String[] getDnsServers();
    
    String getIpv4DefaultGateway();
    
    String getIpv6DefaultGateway();
}
