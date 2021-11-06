// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.hardware.keyboard;

import org.lwjgl.input.Keyboard;

public class TurokKeyboard
{
    public static String toString(final int key) {
        return (key != -1) ? Keyboard.getKeyName(key) : "NONE";
    }
}
