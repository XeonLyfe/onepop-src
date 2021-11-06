// 
// Decompiled by Procyon v0.6-prerelease
// 

package oshi.util.platform.windows;

import oshi.util.ParseUtil;
import java.time.OffsetDateTime;
import oshi.util.Constants;
import com.sun.jna.platform.win32.COM.WbemcliUtil;
import oshi.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class WmiUtil
{
    public static final String OHM_NAMESPACE = "ROOT\\OpenHardwareMonitor";
    private static final String CLASS_CAST_MSG = "%s is not a %s type. CIM Type is %d and VT type is %d";
    
    private WmiUtil() {
    }
    
    public static <T extends Enum<T>> String queryToString(final WbemcliUtil.WmiQuery<T> query) {
        final T[] props = query.getPropertyEnum().getEnumConstants();
        final StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(props[0].name());
        for (int i = 1; i < props.length; ++i) {
            sb.append(',').append(props[i].name());
        }
        sb.append(" FROM ").append(query.getWmiClassName());
        return sb.toString();
    }
    
    public static <T extends Enum<T>> String getString(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        if (result.getCIMType((Enum)property) == 8) {
            return getStr((WbemcliUtil.WmiResult<Enum>)result, property, index);
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "String", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    public static <T extends Enum<T>> String getDateString(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        final OffsetDateTime dateTime = getDateTime(result, property, index);
        if (dateTime.equals(Constants.UNIX_EPOCH)) {
            return "";
        }
        return dateTime.toLocalDate().toString();
    }
    
    public static <T extends Enum<T>> OffsetDateTime getDateTime(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        if (result.getCIMType((Enum)property) == 101) {
            return ParseUtil.parseCimDateTimeToOffset(getStr(result, property, index));
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "DateTime", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    public static <T extends Enum<T>> String getRefString(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        if (result.getCIMType((Enum)property) == 102) {
            return getStr((WbemcliUtil.WmiResult<Enum>)result, property, index);
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "Reference", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    private static <T extends Enum<T>> String getStr(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        final Object o = result.getValue((Enum)property, index);
        if (o == null) {
            return "";
        }
        if (result.getVtType((Enum)property) == 8) {
            return (String)o;
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "String-mapped", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    public static <T extends Enum<T>> long getUint64(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        final Object o = result.getValue((Enum)property, index);
        if (o == null) {
            return 0L;
        }
        if (result.getCIMType((Enum)property) == 21 && result.getVtType((Enum)property) == 8) {
            return ParseUtil.parseLongOrDefault((String)o, 0L);
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "UINT64", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    public static <T extends Enum<T>> int getUint32(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        if (result.getCIMType((Enum)property) == 19) {
            return getInt((WbemcliUtil.WmiResult<Enum>)result, property, index);
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "UINT32", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    public static <T extends Enum<T>> long getUint32asLong(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        if (result.getCIMType((Enum)property) == 19) {
            return (long)getInt(result, property, index) & 0xFFFFFFFFL;
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "UINT32", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    public static <T extends Enum<T>> int getSint32(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        if (result.getCIMType((Enum)property) == 3) {
            return getInt((WbemcliUtil.WmiResult<Enum>)result, property, index);
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "SINT32", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    public static <T extends Enum<T>> int getUint16(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        if (result.getCIMType((Enum)property) == 18) {
            return getInt((WbemcliUtil.WmiResult<Enum>)result, property, index);
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "UINT16", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    private static <T extends Enum<T>> int getInt(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        final Object o = result.getValue((Enum)property, index);
        if (o == null) {
            return 0;
        }
        if (result.getVtType((Enum)property) == 3) {
            return (int)o;
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "32-bit integer", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
    
    public static <T extends Enum<T>> float getFloat(final WbemcliUtil.WmiResult<T> result, final T property, final int index) {
        final Object o = result.getValue((Enum)property, index);
        if (o == null) {
            return 0.0f;
        }
        if (result.getCIMType((Enum)property) == 4 && result.getVtType((Enum)property) == 4) {
            return (float)o;
        }
        throw new ClassCastException(String.format("%s is not a %s type. CIM Type is %d and VT type is %d", property.name(), "Float", result.getCIMType((Enum)property), result.getVtType((Enum)property)));
    }
}
