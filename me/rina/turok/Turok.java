// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok;

public class Turok
{
    public static String AUTHOR;
    public static String VERSION;
    public static String NAME;
    
    public static String getAuthor() {
        return Turok.AUTHOR;
    }
    
    public static String getVersion() {
        return Turok.VERSION;
    }
    
    public static String getName() {
        return Turok.NAME;
    }
    
    static {
        Turok.AUTHOR = "SrRina";
        Turok.VERSION = "6.0.6";
        Turok.NAME = "Turok Framework";
    }
}
