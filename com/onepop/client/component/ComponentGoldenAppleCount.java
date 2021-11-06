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

public class ComponentGoldenAppleCount extends Component
{
    private final ComponentSetting<Type> settingType;
    public int goldenApples;
    
    public ComponentGoldenAppleCount() {
        super("Golden Apple Count", "GoldenAppleCount", "Counts golden apples for you!", StringType.USE);
        this.settingType = new ComponentSetting<Type>("Type", "Type", "The type.", Type.TEXT);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        this.goldenApples = ComponentGoldenAppleCount.mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::func_190916_E).sum();
        if (ComponentGoldenAppleCount.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            this.goldenApples += ComponentGoldenAppleCount.mc.player.getHeldItemOffhand().func_190916_E();
        }
        if (this.settingType.getValue() == Type.TEXT) {
            final String count = "Golden Apples " + ChatFormatting.GRAY + this.goldenApples;
            this.render(count, 0.0f, 0.0f);
            this.rect.setWidth((float)this.getStringWidth(count));
            this.rect.setHeight((float)this.getStringHeight(count));
        }
        else {
            TurokGL.pushMatrix();
            GlStateManager.enableTexture2D();
            RenderHelper.enableGUIStandardItemLighting();
            final RenderItem renderItem = ComponentGoldenAppleCount.mc.getRenderItem();
            renderItem.renderItemAndEffectIntoGUI(new ItemStack(Items.GOLDEN_APPLE), (int)this.rect.getX() + this.verifyDock(0.0f, 16.0f), (int)this.rect.getY());
            renderItem.renderItemOverlayIntoGUI(ComponentGoldenAppleCount.mc.fontRendererObj, new ItemStack(Items.GOLDEN_APPLE), (int)this.rect.getX() + this.verifyDock(0.0f, 16.0f), (int)this.rect.getY(), (String)null);
            renderItem.zLevel = -5.0f;
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableTexture2D();
            TurokGL.popMatrix();
            final String formatted = "" + this.goldenApples;
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
