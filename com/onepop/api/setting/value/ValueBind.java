// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.setting.value;

import com.onepop.api.setting.Setting;

public class ValueBind extends Setting
{
    private boolean state;
    private int keyCode;
    private InputType inputType;
    
    public ValueBind(final String name, final String tag, final String description, final int key) {
        super(name, tag, description);
        this.keyCode = key;
        this.inputType = InputType.KEYBOARD;
    }
    
    public void setInputType(final InputType inputType) {
        this.inputType = inputType;
    }
    
    public InputType getInputType() {
        return this.inputType;
    }
    
    public void setKeyCode(final int key) {
        this.keyCode = key;
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    public void setState(final boolean state) {
        this.state = state;
    }
    
    public boolean getState() {
        return this.state;
    }
    
    public enum InputType
    {
        MOUSE, 
        KEYBOARD;
    }
}
