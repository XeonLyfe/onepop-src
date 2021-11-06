//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Items;
import java.awt.Color;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import com.onepop.api.component.impl.ComponentSetting;
import com.onepop.api.component.Component;

public class ComponentInventory extends Component
{
    public static ComponentSetting<Integer> settingAlpha;
    
    public ComponentInventory() {
        super("Inventory", "Inventory", "Inventory preview.", StringType.NOT_USE);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        if (ComponentInventory.mc.player == null || ComponentInventory.mc.world == null) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        RenderHelper.enableGUIStandardItemLighting();
        final RenderItem renderItem = ComponentInventory.mc.getRenderItem();
        this.render(0, 0, this.rect.getWidth(), this.rect.getHeight(), new Color(0, 0, 0, ComponentInventory.settingAlpha.getValue()));
        for (int i = 0; i < 27; ++i) {
            final ItemStack itemStack = ComponentInventory.mc.player.inventory.getStackInSlot(i + 9);
            final int x = (int)(this.rect.getX() + i % 9 * 16);
            final int y = (int)(this.rect.getY() + i / 9 * 16);
            if (itemStack.getItem() != Items.field_190931_a) {
                renderItem.renderItemAndEffectIntoGUI(itemStack, x, y);
                renderItem.renderItemOverlayIntoGUI(ComponentInventory.mc.fontRendererObj, itemStack, x, y, (String)null);
            }
        }
        renderItem.zLevel = -5.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.popMatrix();
        this.rect.setWidth(144.0f);
        this.rect.setHeight(48.0f);
    }
    
    static {
        ComponentInventory.settingAlpha = new ComponentSetting<Integer>("Alpha", "Alpha", "The background alpha!", 100, 0, 255);
    }
}
