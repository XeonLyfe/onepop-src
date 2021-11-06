// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import com.onepop.api.component.Component;

public class ComponentRinaMessage extends Component
{
    private final String[] specialThanksAndMuchTexto;
    
    public ComponentRinaMessage() {
        super("Rina Message", "RinaMessage", "Rina message!", StringType.USE);
        this.specialThanksAndMuchTexto = new String[] { "Hi, thank you for buy my client!", "Im very happy, because, all this GUI, HUD & Auto Crystal,", " are very well made to wrong places...", "Now its on right place! Enjoy the client!!", "Its helping me to go live for Canada!", "Special thanks to Jake, Hero and all users and members of 1POP!" };
    }
    
    @Override
    public void onRender(final float partialTicks) {
        int stringWidth = 1;
        int stringHeight = 1;
        for (final String lines : this.specialThanksAndMuchTexto) {
            this.render(lines, 0.0f, (float)stringHeight);
            if (this.getStringWidth(lines) > stringWidth) {
                stringWidth = this.getStringWidth(lines);
            }
            stringHeight += this.getStringHeight(lines) + 1;
        }
        this.rect.setWidth((float)stringWidth);
        this.rect.setHeight((float)stringHeight);
    }
    
    public enum Mode
    {
        PIG, 
        CACHORRO_DO_HERO, 
        CACHORRO_DO_RINA;
    }
}
