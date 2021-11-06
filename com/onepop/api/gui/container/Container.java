// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.gui.container;

import me.rina.turok.util.TurokRect;
import com.onepop.api.gui.IScreenBasic;

public class Container implements IScreenBasic
{
    public TurokRect rect;
    
    public Container(final String tag) {
        this.rect = new TurokRect(tag, 0.0f, 0.0f);
    }
    
    @Override
    public boolean isEnabled() {
        return false;
    }
    
    @Override
    public TurokRect getRect() {
        return this.rect;
    }
    
    @Override
    public boolean verifyFocus(final int mx, final int my) {
        return false;
    }
    
    @Override
    public void onScreenOpened() {
    }
    
    @Override
    public void onCustomScreenOpened() {
    }
    
    @Override
    public void onScreenClosed() {
    }
    
    @Override
    public void onCustomScreenClosed() {
    }
    
    @Override
    public void onKeyboardPressed(final char charCode, final int keyCode) {
    }
    
    @Override
    public void onCustomKeyboardPressed(final char charCode, final int keyCode) {
    }
    
    @Override
    public void onMouseClicked(final int button) {
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
    }
    
    @Override
    public void onMouseReleased(final int button) {
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
    }
    
    @Override
    public void onRender() {
    }
    
    @Override
    public void onCustomRender() {
    }
    
    @Override
    public void onSave() {
    }
    
    @Override
    public void onLoad() {
    }
}
