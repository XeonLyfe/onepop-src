// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.software.common;

import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.Iterator;
import oshi.util.ParseUtil;
import java.util.List;
import java.util.ArrayList;
import oshi.util.FileUtil;
import java.net.UnknownHostException;
import java.net.InetAddress;
import org.slf4j.Logger;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.software.os.NetworkParams;

@ThreadSafe
public abstract class AbstractNetworkParams implements NetworkParams
{
    private static final Logger LOG;
    private static final String NAMESERVER = "nameserver";
    
    @Override
    public String getDomainName() {
        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        }
        catch (UnknownHostException e) {
            AbstractNetworkParams.LOG.error("Unknown host exception when getting address of local host: {}", (Object)e.getMessage());
            return "";
        }
    }
    
    @Override
    public String getHostName() {
        try {
            final String hn = InetAddress.getLocalHost().getHostName();
            final int dot = hn.indexOf(46);
            if (dot == -1) {
                return hn;
            }
            return hn.substring(0, dot);
        }
        catch (UnknownHostException e) {
            AbstractNetworkParams.LOG.error("Unknown host exception when getting address of local host: {}", (Object)e.getMessage());
            return "";
        }
    }
    
    @Override
    public String[] getDnsServers() {
        final List<String> resolv = FileUtil.readFile("/etc/resolv.conf");
        final String key = "nameserver";
        final int maxNameServer = 3;
        final List<String> servers = new ArrayList<String>();
        for (int i = 0; i < resolv.size() && servers.size() < maxNameServer; ++i) {
            final String line = resolv.get(i);
            if (line.startsWith(key)) {
                final String value = line.substring(key.length()).replaceFirst("^[ \t]+", "");
                if (value.length() != 0 && value.charAt(0) != '#' && value.charAt(0) != ';') {
                    final String val = value.split("[ \t#;]", 2)[0];
                    servers.add(val);
                }
            }
        }
        return servers.toArray(new String[0]);
    }
    
    protected static String searchGateway(final List<String> lines) {
        for (final String line : lines) {
            final String leftTrimmed = line.replaceFirst("^\\s+", "");
            if (leftTrimmed.startsWith("gateway:")) {
                final String[] split = ParseUtil.whitespaces.split(leftTrimmed);
                if (split.length < 2) {
                    return "";
                }
                return split[1].split("%")[0];
            }
        }
        return "";
    }
    
    @Override
    public String toString() {
        return String.format("Host name: %s, Domain name: %s, DNS servers: %s, IPv4 Gateway: %s, IPv6 Gateway: %s", this.getHostName(), this.getDomainName(), Arrays.toString(this.getDnsServers()), this.getIpv4DefaultGateway(), this.getIpv6DefaultGateway());
    }
    
    static {
        LOG = LoggerFactory.getLogger((Class)AbstractNetworkParams.class);
    }
}
