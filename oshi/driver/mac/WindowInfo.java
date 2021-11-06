// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.driver.mac;

import com.sun.jna.Pointer;
import java.awt.Rectangle;
import oshi.util.FormatUtil;
import com.sun.jna.PointerType;
import com.sun.jna.platform.mac.CoreFoundation;
import java.util.ArrayList;
import oshi.jna.platform.mac.CoreGraphics;
import oshi.software.os.OSDesktopWindow;
import java.util.List;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class WindowInfo
{
    private WindowInfo() {
    }
    
    public static List<OSDesktopWindow> queryDesktopWindows(final boolean visibleOnly) {
        final CoreFoundation.CFArrayRef windowInfo = CoreGraphics.INSTANCE.CGWindowListCopyWindowInfo(visibleOnly ? 17 : 0, 0);
        final int numWindows = windowInfo.getCount();
        final List<OSDesktopWindow> windowList = new ArrayList<OSDesktopWindow>();
        final CoreFoundation.CFStringRef kCGWindowIsOnscreen = CoreFoundation.CFStringRef.createCFString("kCGWindowIsOnscreen");
        final CoreFoundation.CFStringRef kCGWindowNumber = CoreFoundation.CFStringRef.createCFString("kCGWindowNumber");
        final CoreFoundation.CFStringRef kCGWindowOwnerPID = CoreFoundation.CFStringRef.createCFString("kCGWindowOwnerPID");
        final CoreFoundation.CFStringRef kCGWindowLayer = CoreFoundation.CFStringRef.createCFString("kCGWindowLayer");
        final CoreFoundation.CFStringRef kCGWindowBounds = CoreFoundation.CFStringRef.createCFString("kCGWindowBounds");
        final CoreFoundation.CFStringRef kCGWindowName = CoreFoundation.CFStringRef.createCFString("kCGWindowName");
        final CoreFoundation.CFStringRef kCGWindowOwnerName = CoreFoundation.CFStringRef.createCFString("kCGWindowOwnerName");
        try {
            for (int i = 0; i < numWindows; ++i) {
                Pointer result = windowInfo.getValueAtIndex(i);
                final CoreFoundation.CFDictionaryRef windowRef = new CoreFoundation.CFDictionaryRef(result);
                result = windowRef.getValue((PointerType)kCGWindowIsOnscreen);
                final boolean visible = result == null || new CoreFoundation.CFBooleanRef(result).booleanValue();
                if (!visibleOnly || visible) {
                    result = windowRef.getValue((PointerType)kCGWindowNumber);
                    final long windowNumber = new CoreFoundation.CFNumberRef(result).longValue();
                    result = windowRef.getValue((PointerType)kCGWindowOwnerPID);
                    final long windowOwnerPID = new CoreFoundation.CFNumberRef(result).longValue();
                    result = windowRef.getValue((PointerType)kCGWindowLayer);
                    final int windowLayer = new CoreFoundation.CFNumberRef(result).intValue();
                    result = windowRef.getValue((PointerType)kCGWindowBounds);
                    final CoreGraphics.CGRect rect = new CoreGraphics.CGRect();
                    CoreGraphics.INSTANCE.CGRectMakeWithDictionaryRepresentation(new CoreFoundation.CFDictionaryRef(result), rect);
                    final Rectangle windowBounds = new Rectangle(FormatUtil.roundToInt(rect.origin.x), FormatUtil.roundToInt(rect.origin.y), FormatUtil.roundToInt(rect.size.width), FormatUtil.roundToInt(rect.size.height));
                    result = windowRef.getValue((PointerType)kCGWindowName);
                    String windowName = cfPointerToString(result);
                    result = windowRef.getValue((PointerType)kCGWindowOwnerName);
                    final String windowOwnerName = cfPointerToString(result);
                    if (windowName.isEmpty()) {
                        windowName = windowOwnerName;
                    }
                    else {
                        windowName = windowName + "(" + windowOwnerName + ")";
                    }
                    windowList.add(new OSDesktopWindow(windowNumber, windowName, windowOwnerName, windowBounds, windowOwnerPID, windowLayer, visible));
                }
            }
        }
        finally {
            kCGWindowIsOnscreen.release();
            kCGWindowNumber.release();
            kCGWindowOwnerPID.release();
            kCGWindowLayer.release();
            kCGWindowBounds.release();
            kCGWindowName.release();
            kCGWindowOwnerName.release();
            windowInfo.release();
        }
        return windowList;
    }
    
    private static String cfPointerToString(final Pointer result) {
        if (result != null) {
            final CoreFoundation.CFStringRef cfs = new CoreFoundation.CFStringRef(result);
            if (CoreFoundation.INSTANCE.CFStringGetLength(cfs).intValue() > 0) {
                return cfs.stringValue();
            }
        }
        return "";
    }
}
