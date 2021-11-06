// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.util;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class LsofUtil
{
    private LsofUtil() {
    }
    
    public static Map<Integer, String> getCwdMap(final int pid) {
        final List<String> lsof = ExecutingCommand.runNative("lsof -F n -d cwd" + ((pid < 0) ? "" : (" -p " + pid)));
        final Map<Integer, String> cwdMap = new HashMap<Integer, String>();
        Integer key = -1;
        for (final String line : lsof) {
            if (line.isEmpty()) {
                continue;
            }
            switch (line.charAt(0)) {
                case 'p': {
                    key = ParseUtil.parseIntOrDefault(line.substring(1), -1);
                    continue;
                }
                case 'n': {
                    cwdMap.put(key, line.substring(1));
                    continue;
                }
            }
        }
        return cwdMap;
    }
    
    public static String getCwd(final int pid) {
        final List<String> lsof = ExecutingCommand.runNative("lsof -F n -d cwd -p " + pid);
        for (final String line : lsof) {
            if (!line.isEmpty() && line.charAt(0) == 'n') {
                return line.substring(1).trim();
            }
        }
        return "";
    }
    
    public static long getOpenFiles(final int pid) {
        final int openFiles = ExecutingCommand.runNative("lsof -p " + pid).size();
        return (openFiles > 0) ? (openFiles - 1L) : 0L;
    }
}
