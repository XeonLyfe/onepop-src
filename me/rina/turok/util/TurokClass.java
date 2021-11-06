// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.util;

public class TurokClass
{
    public static Enum getEnumByName(final Enum _enum, final String name) {
        for (final Enum enums : (Enum[])_enum.getClass().getEnumConstants()) {
            if (enums.name().equalsIgnoreCase(name)) {
                return enums;
            }
        }
        return _enum;
    }
    
    public static boolean isAnnotationPreset(final Class clazz, final Class clazz1) {
        return clazz.isAnnotationPresent(clazz1);
    }
}
