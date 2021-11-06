//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.onepop.api.component.impl.ComponentSetting;
import java.util.HashMap;
import com.onepop.api.component.Component;

public class ComponentWelcome extends Component
{
    public final HashMap<String, String> userWelcomeMap;
    private ComponentSetting<Boolean> settingCustom;
    
    public ComponentWelcome() {
        super("Welcome", "Welcome", "Really cool welcomes!!", StringType.USE);
        this.userWelcomeMap = new HashMap<String, String>();
        this.settingCustom = new ComponentSetting<Boolean>("Do you have 1POP beta?", "DoYouHave1POPBeta?", "If yes...", true);
        this.userWelcomeMap.put("SRRINA", "You'll make go to canada!");
        this.userWelcomeMap.put("CHEROSIN", "Stop uses burrow and anime pfp," + ChatFormatting.DARK_RED + " I love you!");
        this.userWelcomeMap.put("FLLMALL", "Uses the new auto crystal, and" + ChatFormatting.DARK_RED + " I love you!");
        this.userWelcomeMap.put("HEROGLAUCOP", "My godinho lindo!" + ChatFormatting.DARK_RED + " I love you!");
        this.userWelcomeMap.put("MATHEUS1300", "Nigger! and balls and " + ChatFormatting.DARK_RED + " I love you!");
    }
    
    @Override
    public void onRender(final float partialTicks) {
        final String welcomes = "Welcome to" + ChatFormatting.GOLD + " 1pop" + ChatFormatting.RESET + " " + ComponentWelcome.mc.player.getName() + " " + ((this.settingCustom.getValue() && this.userWelcomeMap.get(ComponentWelcome.mc.player.getName().toUpperCase()) != null) ? this.userWelcomeMap.get(ComponentWelcome.mc.player.getName().toUpperCase()) : "");
        this.render(welcomes, 0.0f, 0.0f);
        this.rect.setWidth((float)this.getStringWidth(welcomes));
        this.rect.setHeight((float)this.getStringHeight(welcomes));
    }
}
