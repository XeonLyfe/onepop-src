// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.linux.proc;

import oshi.util.Memoizer;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.List;
import oshi.util.ExecutingCommand;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class UserGroupInfo
{
    private static final Supplier<Map<String, String>> usersIdMap;
    private static final Supplier<Map<String, String>> groupsIdMap;
    
    private UserGroupInfo() {
    }
    
    public static String getUser(final String userId) {
        return (String)UserGroupInfo.usersIdMap.get().getOrDefault((Object)userId, (Object)"unknown");
    }
    
    public static String getGroupName(final String groupId) {
        return (String)UserGroupInfo.groupsIdMap.get().getOrDefault((Object)groupId, (Object)"unknown");
    }
    
    private static Map<String, String> getUserMap() {
        final HashMap<String, String> userMap = new HashMap<String, String>();
        final List<String> passwd = ExecutingCommand.runNative("getent passwd");
        for (final String entry : passwd) {
            final String[] split = entry.split(":");
            if (split.length > 2) {
                final String userName = split[0];
                final String uid = split[2];
                userMap.putIfAbsent(uid, userName);
            }
        }
        return userMap;
    }
    
    private static Map<String, String> getGroupMap() {
        final Map<String, String> groupMap = new HashMap<String, String>();
        final List<String> group = ExecutingCommand.runNative("getent group");
        for (final String entry : group) {
            final String[] split = entry.split(":");
            if (split.length > 2) {
                final String groupName = split[0];
                final String gid = split[2];
                groupMap.putIfAbsent(gid, groupName);
            }
        }
        return groupMap;
    }
    
    static {
        usersIdMap = Memoizer.memoize(UserGroupInfo::getUserMap, TimeUnit.MINUTES.toNanos(1L));
        groupsIdMap = Memoizer.memoize(UserGroupInfo::getGroupMap, TimeUnit.MINUTES.toNanos(1L));
    }
}
