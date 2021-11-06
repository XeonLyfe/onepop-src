//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.component;

import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.init.Items;
import me.rina.turok.util.TurokDisplay;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import me.rina.turok.util.TurokMath;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import com.onepop.api.component.impl.ComponentSetting;
import com.onepop.api.component.Component;

public class ComponentArmor extends Component
{
    public static ComponentSetting<Boolean> settingHotbar;
    public static ComponentSetting<Boolean> settingReverse;
    private int offset;
    
    public ComponentArmor() {
        super("Armor", "Armor", "Armor preview.", StringType.NOT_USE);
    }
    
    @Override
    public void onRender(final float partialTicks) {
        if (ComponentArmor.mc.player == null || ComponentArmor.mc.world == null) {
            return;
        }
        final BlockPos selfPosition = new BlockPos(Math.floor(ComponentArmor.mc.player.posX), Math.floor(ComponentArmor.mc.player.posY), Math.floor(ComponentArmor.mc.player.posZ));
        this.offset = (int)TurokMath.serp((float)this.offset, (ComponentArmor.mc.world.getBlockState(selfPosition.up()).getBlock() == Blocks.WATER) ? 10.0f : 0.0f, partialTicks);
        this.render(0, 0, this.rect.getWidth(), this.rect.getHeight(), new Color(0, 0, 0, 0));
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        RenderHelper.enableGUIStandardItemLighting();
        final RenderItem renderItem = ComponentArmor.mc.getRenderItem();
        final TurokDisplay display = new TurokDisplay(ComponentArmor.mc);
        final int l = display.getScaledWidth() / 2;
        final int k = display.getScaledHeight() - 55 - this.offset;
        if (ComponentArmor.settingHotbar.getValue()) {
            this.rect.setX((float)(l + 8));
            this.rect.setY((float)k);
        }
        int x = (int)this.rect.getX();
        final int y = (int)this.rect.getY();
        if (ComponentArmor.settingReverse.getValue()) {
            for (int i = 3; i >= 0; --i) {
                final ItemStack itemStack = ComponentArmor.mc.player.inventory.armorItemInSlot(i);
                if (itemStack.getItem() != Items.field_190931_a) {
                    renderItem.renderItemAndEffectIntoGUI(itemStack, x, y);
                    renderItem.renderItemOverlayIntoGUI(ComponentArmor.mc.fontRendererObj, itemStack, x, y, (String)null);
                }
                x += 16;
            }
        }
        else {
            for (int i = 0; i < 4; ++i) {
                final ItemStack itemStack = ComponentArmor.mc.player.inventory.armorItemInSlot(i);
                if (itemStack.getItem() != Items.field_190931_a) {
                    renderItem.renderItemAndEffectIntoGUI(itemStack, x, y);
                    renderItem.renderItemOverlayIntoGUI(ComponentArmor.mc.fontRendererObj, itemStack, x, y, (String)null);
                }
                x += 16;
            }
        }
        renderItem.zLevel = -5.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.popMatrix();
        this.rect.setHeight(16.0f);
        this.rect.setWidth(64.0f);
    }
    
    static {
        ComponentArmor.settingHotbar = new ComponentSetting<Boolean>("Hotbar Position", "HotbarPosition", "Set the position in the hotbar.", true);
        ComponentArmor.settingReverse = new ComponentSetting<Boolean>("Reverse", "Reverse", "Reverse armor.", false);
    }
}
