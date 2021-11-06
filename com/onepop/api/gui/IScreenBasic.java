// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.gui;

import me.rina.turok.util.TurokRect;

public interface IScreenBasic
{
    boolean isEnabled();
    
    TurokRect getRect();
    
    boolean verifyFocus(final int p0, final int p1);
    
    void onScreenOpened();
    
    void onCustomScreenOpened();
    
    void onScreenClosed();
    
    void onCustomScreenClosed();
    
    void onKeyboardPressed(final char p0, final int p1);
    
    void onCustomKeyboardPressed(final char p0, final int p1);
    
    void onMouseClicked(final int p0);
    
    void onCustomMouseClicked(final int p0);
    
    void onMouseReleased(final int p0);
    
    void onCustomMouseReleased(final int p0);
    
    void onRender();
    
    void onCustomRender();
    
    void onSave();
    
    void onLoad();
}
