// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.hardware.mouse;

import org.lwjgl.input.Mouse;

public class TurokMouse
{
    public static final int BUTTON_LEFT = 0;
    public static final int BUTTON_MIDDLE = 2;
    public static final int BUTTON_RIGHT = 3;
    private int scroll;
    private int x;
    private int y;
    
    public void setPos(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setCursorPos(final int x, final int y) {
        this.x = x;
        this.y = y;
        Mouse.setCursorPosition(this.x, this.y);
    }
    
    public int[] getPos() {
        return new int[] { this.x, this.y };
    }
    
    public void setCursorX(final int x) {
        Mouse.setCursorPosition(this.x = x, this.y);
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setCursorY(final int y) {
        this.y = y;
        Mouse.setCursorPosition(this.x, this.y);
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getScroll() {
        return -(Mouse.getDWheel() / 10);
    }
    
    public boolean hasWheel() {
        return Mouse.hasWheel();
    }
}
