// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.util;

public class TurokGeneric<S>
{
    private S value;
    
    public TurokGeneric(final S value) {
        this.value = value;
    }
    
    public void setValue(final S value) {
        this.value = value;
    }
    
    public S getValue() {
        return this.value;
    }
}
