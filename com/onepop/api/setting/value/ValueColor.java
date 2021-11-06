// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.setting.value;

import java.awt.Color;
import com.onepop.api.setting.Setting;

public class ValueColor extends Setting
{
    private int r;
    private int g;
    private int b;
    private int a;
    
    public ValueColor(final String name, final String tag, final String description, final Color color) {
        super(name, tag, description);
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
        this.a = color.getAlpha();
    }
    
    public void setR(final int r) {
        this.r = r;
    }
    
    public int getR() {
        return this.r;
    }
    
    public void setG(final int g) {
        this.g = g;
    }
    
    public int getG() {
        return this.g;
    }
    
    public void setB(final int b) {
        this.b = b;
    }
    
    public int getB() {
        return this.b;
    }
    
    public void setA(final int a) {
        this.a = a;
    }
    
    public int getA() {
        return this.a;
    }
    
    public Color getColor() {
        return new Color(this.r, this.g, this.b, this.a);
    }
}
