// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.util;

import org.slf4j.LoggerFactory;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import org.slf4j.Logger;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class ExecutingCommand
{
    private static final Logger LOG;
    private static final String[] DEFAULT_ENV;
    
    private ExecutingCommand() {
    }
    
    private static String[] getDefaultEnv() {
        final PlatformEnum platform = SystemInfo.getCurrentPlatform();
        if (platform == PlatformEnum.WINDOWS) {
            return new String[] { "LANGUAGE=C" };
        }
        if (platform != PlatformEnum.UNKNOWN) {
            return new String[] { "LC_ALL=C" };
        }
        return null;
    }
    
    public static List<String> runNative(final String cmdToRun) {
        final String[] cmd = cmdToRun.split(" ");
        return runNative(cmd);
    }
    
    public static List<String> runNative(final String[] cmdToRunWithArgs) {
        return runNative(cmdToRunWithArgs, ExecutingCommand.DEFAULT_ENV);
    }
    
    public static List<String> runNative(final String[] cmdToRunWithArgs, final String[] envp) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmdToRunWithArgs, envp);
        }
        catch (SecurityException | IOException ex) {
            final Exception ex2;
            final Exception e = ex2;
            ExecutingCommand.LOG.trace("Couldn't run command {}: {}", (Object)Arrays.toString(cmdToRunWithArgs), (Object)e.getMessage());
            return new ArrayList<String>(0);
        }
        final ArrayList<String> sa = new ArrayList<String>();
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.defaultCharset()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sa.add(line);
            }
            p.waitFor();
        }
        catch (IOException e2) {
            ExecutingCommand.LOG.trace("Problem reading output from {}: {}", (Object)Arrays.toString(cmdToRunWithArgs), (Object)e2.getMessage());
            return new ArrayList<String>(0);
        }
        catch (InterruptedException ie) {
            ExecutingCommand.LOG.trace("Interrupted while reading output from {}: {}", (Object)Arrays.toString(cmdToRunWithArgs), (Object)ie.getMessage());
            Thread.currentThread().interrupt();
        }
        return sa;
    }
    
    public static String getFirstAnswer(final String cmd2launch) {
        return getAnswerAt(cmd2launch, 0);
    }
    
    public static String getAnswerAt(final String cmd2launch, final int answerIdx) {
        final List<String> sa = runNative(cmd2launch);
        if (answerIdx >= 0 && answerIdx < sa.size()) {
            return sa.get(answerIdx);
        }
        return "";
    }
    
    static {
        LOG = LoggerFactory.getLogger((Class)ExecutingCommand.class);
        DEFAULT_ENV = getDefaultEnv();
    }
}
