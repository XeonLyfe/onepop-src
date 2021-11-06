// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.setting.value;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.onepop.api.setting.Setting;

public class ValueEnum extends Setting
{
    private final List<Enum<?>> valueList;
    private Enum<?> value;
    private int index;
    
    public ValueEnum(final String name, final String tag, final String description, final Enum<?> value) {
        super(name, tag, description);
        this.value = value;
        (this.valueList = new ArrayList<Enum<?>>()).addAll((Collection<? extends Enum<?>>)Arrays.asList(value.getDeclaringClass().getEnumConstants()));
        final int id = this.valueList.indexOf(this.value);
        this.index = ((id != -1) ? id : 0);
    }
    
    public void setValue(final Enum<?> p_Enum) {
        final int id = this.valueList.indexOf(p_Enum);
        this.index = ((id != -1) ? id : 0);
        this.value = this.valueList.get(this.index);
    }
    
    public Enum<?> getValue() {
        return this.value;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public List<Enum<?>> getValueList() {
        return this.valueList;
    }
    
    public void updateIndex() {
        if (this.index >= this.valueList.size() - 1) {
            this.index = 0;
        }
        else {
            ++this.index;
        }
    }
}
