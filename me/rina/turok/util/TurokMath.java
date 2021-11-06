//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.util;

import net.minecraft.util.math.Vec3d;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class TurokMath
{
    public static double PI;
    
    public static float amount(final float value, final float maximum) {
        final float h = (maximum - value) / maximum * 100.0f;
        return h;
    }
    
    public static float distancingValues(final float value, final float maximum, final float distance) {
        final float h = value * 100.0f / maximum;
        final float l = distance / 100.0f;
        return h * l;
    }
    
    public static int clamp(final int value, final int minimum, final int maximum) {
        return (value <= minimum) ? minimum : ((value >= maximum) ? maximum : value);
    }
    
    public static double clamp(final double value, final double minimum, final double maximum) {
        return (value <= minimum) ? minimum : ((value >= maximum) ? maximum : value);
    }
    
    public static float clamp(final float value, final float minimum, final float maximum) {
        return (value <= minimum) ? minimum : ((value >= maximum) ? maximum : value);
    }
    
    public static double round(final double vDouble) {
        BigDecimal decimal = new BigDecimal(vDouble);
        decimal = decimal.setScale(2, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }
    
    public static Vec3d lerp(final Vec3d a, final Vec3d b, final float ticks) {
        return new Vec3d(a.xCoord + (b.xCoord - a.xCoord) * ticks, a.yCoord + (b.yCoord - a.yCoord) * ticks, a.zCoord + (b.zCoord - a.zCoord) * ticks);
    }
    
    public static float lerp(final float a, final float b, final float ticks) {
        if (ticks == 1.0f || ticks == 5.0f) {
            return b;
        }
        return a + (b - a) * ticks;
    }
    
    public static TurokRect lerp(final TurokRect a, final TurokRect b, final float ticks) {
        if (ticks == 1.0f || ticks == 5.0f) {
            return a.copy(b);
        }
        a.x = serp(a.x, b.x, ticks);
        a.y = serp(a.y, b.y, ticks);
        a.width = serp(a.width, b.width, ticks);
        b.height = serp(a.height, b.height, ticks);
        return a;
    }
    
    public static double lerp(final double a, final double b, final float ticks) {
        return a + (b - a) * ticks;
    }
    
    public static float serp(final float a, final float b, final float ticks) {
        return lerp(a, b, ticks);
    }
    
    public static double serp(final double a, final double b, final float ticks) {
        return lerp(a, b, ticks);
    }
    
    public static int normalize(final int... value) {
        int normalizedValue = 0;
        int cachedValue = 0;
        for (int length = value.length, i = 0; i < length; ++i) {
            final int values = cachedValue = value[i];
            normalizedValue = values / cachedValue * cachedValue;
        }
        return normalizedValue;
    }
    
    public static double normalize(final double... value) {
        double normalizedValue = 0.0;
        double cachedValue = 0.0;
        for (int length = value.length, i = 0; i < length; ++i) {
            final double values = cachedValue = value[i];
            normalizedValue = values / cachedValue * cachedValue;
        }
        return normalizedValue;
    }
    
    public static float normalize(final float... value) {
        float normalizedValue = 0.0f;
        float cachedValue = 0.0f;
        for (int length = value.length, i = 0; i < length; ++i) {
            final float values = cachedValue = value[i];
            normalizedValue = values / cachedValue * cachedValue;
        }
        return normalizedValue;
    }
    
    public static int ceiling(final double value) {
        final int valueInt = (int)value;
        return (value >= valueInt) ? (valueInt + 1) : valueInt;
    }
    
    public static int ceiling(final float value) {
        final int valueInt = (int)value;
        return (value >= valueInt) ? (valueInt + 1) : valueInt;
    }
    
    public static double sqrt(final double a) {
        return Math.sqrt(a);
    }
    
    public static float sqrt(final float a) {
        return (float)Math.sqrt(a);
    }
    
    public static int sqrt(final int a) {
        return (int)Math.sqrt(a);
    }
    
    public static int min(final int value, final int minimum) {
        return Math.max(value, minimum);
    }
    
    public static float min(final float value, final float minimum) {
        return (value <= minimum) ? minimum : value;
    }
    
    public static double min(final double value, final double minimum) {
        return (value <= minimum) ? minimum : value;
    }
    
    public static int max(final int value, final int maximum) {
        return Math.min(value, maximum);
    }
    
    public static double max(final double value, final double maximum) {
        return (value >= maximum) ? maximum : value;
    }
    
    public static float max(final float value, final float maximum) {
        return (value >= maximum) ? maximum : value;
    }
    
    public static int negative(final int a) {
        return a - a - a;
    }
    
    public static double negative(final double a) {
        return a - a - a;
    }
    
    public static float negative(final float a) {
        return a - a - a;
    }
    
    public static int positive(final int a) {
        return (a > 0) ? (a + a + a) : a;
    }
    
    public static double positive(final double a) {
        return (a > 0.0) ? (a + a + a) : a;
    }
    
    public static float positive(final float a) {
        return (a > 0.0f) ? (a + a + a) : a;
    }
    
    static {
        TurokMath.PI = 3.1415998935699463;
    }
}
