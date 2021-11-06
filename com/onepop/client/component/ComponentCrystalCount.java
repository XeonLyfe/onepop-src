//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import me.rina.turok.render.opengl.TurokGL;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.function.ToIntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import com.onepop.api.component.impl.ComponentSetting;
import com.onepop.api.component.Component;

public class ComponentCrystalCount extends Component
{
    ComponentSetting<Type> settingType;
    public int crystals;
    
    public ComponentCrystalCount() {
        super("Crystal Count", "CrystalCount", "Counts crystals for you!", StringType.USE);
        this.settingType = new ComponentSetting<Type>("Type", "Type", "The type.", Type.TEXT);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        this.crystals = ComponentCrystalCount.mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::func_190916_E).sum();
        if (ComponentCrystalCount.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            this.crystals += ComponentCrystalCount.mc.player.getHeldItemOffhand().func_190916_E();
        }
        if (this.settingType.getValue() == Type.TEXT) {
            final String count = "Crystals " + ChatFormatting.GRAY + this.crystals;
            this.render(count, 0.0f, 0.0f);
            this.rect.setWidth((float)this.getStringWidth(count));
            this.rect.setHeight((float)this.getStringHeight(count));
        }
        else {
            TurokGL.pushMatrix();
            GlStateManager.enableTexture2D();
            RenderHelper.enableGUIStandardItemLighting();
            final RenderItem renderItem = ComponentCrystalCount.mc.getRenderItem();
            renderItem.renderItemAndEffectIntoGUI(new ItemStack(Items.END_CRYSTAL), (int)this.rect.getX() + this.verifyDock(0.0f, 16.0f), (int)this.rect.getY());
            renderItem.renderItemOverlayIntoGUI(ComponentCrystalCount.mc.fontRendererObj, new ItemStack(Items.END_CRYSTAL), (int)this.rect.getX() + this.verifyDock(0.0f, 16.0f), (int)this.rect.getY(), (String)null);
            renderItem.zLevel = -5.0f;
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableTexture2D();
            TurokGL.popMatrix();
            final String formatted = "" + this.crystals;
            this.render(formatted, 17.0f, 6.0f);
            this.rect.setWidth((float)(17 + this.getStringWidth(formatted)));
            this.rect.setHeight(14.0f);
        }
    }
    
    public enum Type
    {
        TEXT, 
        IMAGE;
    }
}
