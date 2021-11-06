//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.rocan;

import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.render.opengl.TurokShaderGL;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.Clipboard;
import net.minecraft.util.ChatAllowedCharacters;
import java.io.IOException;
import java.awt.datatransfer.UnsupportedFlavorException;
import com.onepop.api.util.chat.ChatUtil;
import java.awt.datatransfer.DataFlavor;
import java.awt.Toolkit;
import org.lwjgl.input.Keyboard;
import org.apache.commons.lang3.StringUtils;
import java.awt.Color;
import me.rina.turok.util.TurokMath;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import me.rina.turok.hardware.mouse.TurokMouse;
import me.rina.turok.render.font.TurokFont;
import me.rina.turok.util.TurokTick;
import com.onepop.api.gui.IScreenBasic;
import com.onepop.api.gui.widget.Widget;

public class RocanEntryBox extends Widget implements IScreenBasic
{
    private Type type;
    private boolean isFocused;
    private boolean isScissored;
    private boolean isRendering;
    private boolean isSelected;
    private String text;
    private String save;
    private String split;
    private String postSplit;
    private String selected;
    private int splitIndex;
    private int selectedStartIndex;
    private int selectedEndIndex;
    private int lastKeyTyped;
    private float partialTicks;
    private float offsetX;
    private float offsetY;
    private float scroll;
    private final TurokTick delayCursor;
    public int[] colorBackground;
    public int[] colorBackgroundOutline;
    public int[] colorSelectedBackground;
    public int[] colorString;
    public int[] colorSelectedString;
    public float[] rectScissor;
    public float[] rectSelected;
    private boolean isMouseClickedLeft;
    private boolean isMouseClickedMiddle;
    private boolean isMouseClickedRight;
    private TurokFont fontRenderer;
    private TurokMouse mouse;
    public Flag flagMouse;
    
    public RocanEntryBox(final String name, final TurokFont fontRenderer, final TurokMouse mouse) {
        super(name);
        this.type = Type.TEXT;
        this.isRendering = true;
        this.delayCursor = new TurokTick();
        this.colorBackground = new int[] { 255, 255, 255, 255 };
        this.colorBackgroundOutline = new int[] { 255, 255, 255, 100 };
        this.colorSelectedBackground = new int[] { 0, 0, 190, 255 };
        this.colorString = new int[] { 0, 0, 0, 255 };
        this.colorSelectedString = new int[] { 255, 255, 255, 255 };
        this.rectScissor = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
        this.rectSelected = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.fontRenderer = fontRenderer;
        this.mouse = mouse;
        this.text = "";
        this.save = "";
    }
    
    public void setMouse(final TurokMouse mouse) {
        this.mouse = mouse;
    }
    
    public TurokMouse getMouse() {
        return this.mouse;
    }
    
    public void setType(final Type type) {
        this.type = type;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public void setFontRenderer(final TurokFont fontRenderer) {
        this.fontRenderer = fontRenderer;
    }
    
    public TurokFont getFontRenderer() {
        return this.fontRenderer;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setSave(final String save) {
        this.save = save;
    }
    
    public String getSave() {
        return this.save;
    }
    
    public void setFocused(final boolean focused) {
        this.isFocused = focused;
    }
    
    public boolean isFocused() {
        return this.isFocused;
    }
    
    public void setScissored(final boolean scissored) {
        this.isScissored = scissored;
    }
    
    public boolean isScissored() {
        return this.isScissored;
    }
    
    public void setRendering(final boolean rendering) {
        this.isRendering = rendering;
    }
    
    public boolean isRendering() {
        return this.isRendering;
    }
    
    public void setSelected(final boolean selected) {
        this.isSelected = selected;
    }
    
    public boolean isSelected() {
        return this.isSelected;
    }
    
    public boolean isMouseClickedLeft() {
        return this.isMouseClickedLeft;
    }
    
    public boolean isMouseClickedMiddle() {
        return this.isMouseClickedMiddle;
    }
    
    public void setMouseClickedRight(final boolean mouseClickedRight) {
        this.isMouseClickedRight = mouseClickedRight;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public void updateSplit() {
        this.split = "|";
        this.delayCursor.reset();
    }
    
    public void resetSplit() {
        this.split = "";
        this.delayCursor.reset();
    }
    
    public int getSplitByMouse(final int x, final int y) {
        int split = this.text.isEmpty() ? 0 : this.text.length();
        if (this.text.isEmpty() || this.flagMouse == Flag.MOUSE_NOT_OVER) {
            return split;
        }
        final float diffOffset = this.rect.getX() + 2.0f + this.scroll;
        float w = 0.0f;
        int count = 0;
        for (final String characters : this.text.split("")) {
            final float charWidth = (float)TurokFontManager.getStringWidth(this.fontRenderer, characters);
            final float offset = diffOffset + w;
            if (x >= offset && x <= offset + charWidth) {
                split = TurokMath.clamp(count, 0, this.text.length());
                break;
            }
            w += TurokFontManager.getStringWidth(this.fontRenderer, characters);
            ++count;
        }
        return TurokMath.clamp(split, 0, this.text.length());
    }
    
    public void doRenderSplit(final float x, final float y) {
        if (!this.isRendering) {
            return;
        }
        if (this.delayCursor.isPassedMS(500.0f)) {
            TurokFontManager.render(this.fontRenderer, "|", x, y, false, new Color(this.colorString[0], this.colorString[1], this.colorString[2], this.colorString[3]));
        }
        else {
            TurokFontManager.render(this.fontRenderer, this.split, x, y, false, new Color(this.colorString[0], this.colorString[1], this.colorString[2], this.colorString[3]));
        }
        if (this.delayCursor.isPassedMS(1000.0f)) {
            this.delayCursor.reset();
        }
    }
    
    public void doMouseOver(final TurokMouse mouse) {
        if (!this.isRendering) {
            return;
        }
        this.flagMouse = (this.rect.collideWithMouse(mouse) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
    }
    
    public void doMouseScroll() {
        if (!this.isRendering) {
            return;
        }
        final float stringWidth = (float)TurokFontManager.getStringWidth(this.fontRenderer, this.text);
        final float maximumPositionText = 0.0f;
        final float minimumPositionText = this.rect.getWidth() - stringWidth - 4.5f;
        final boolean isScrollLimit = stringWidth + 2.0f >= this.rect.getWidth();
        if (this.isFocused && this.flagMouse == Flag.MOUSE_OVER && this.mouse.hasWheel() && isScrollLimit) {
            this.scroll -= this.mouse.getScroll();
        }
        if (this.scroll <= minimumPositionText) {
            this.scroll = minimumPositionText;
        }
        if (this.scroll >= maximumPositionText) {
            this.scroll = maximumPositionText;
        }
    }
    
    @Override
    public void onScreenClosed() {
        if (!this.isRendering) {
            return;
        }
    }
    
    @Override
    public void onCustomScreenClosed() {
        if (!this.isRendering) {
            return;
        }
    }
    
    @Override
    public void onScreenOpened() {
        if (!this.isRendering) {
            return;
        }
    }
    
    @Override
    public void onCustomScreenOpened() {
        if (!this.isRendering) {
            return;
        }
    }
    
    @Override
    public void onKeyboardPressed(final char character, final int key) {
        if (!this.isRendering) {
            return;
        }
        String cache = this.text;
        int mov = this.splitIndex;
        if (this.isFocused) {
            if (key == 1) {
                this.setFocused(false);
                this.text = this.save;
            }
            else if (key == 28) {
                this.setFocused(false);
            }
            else if (key == 205) {
                if (cache.isEmpty() || mov == cache.length() || mov < 0) {
                    return;
                }
                this.lastKeyTyped = key;
                this.updateSplit();
                ++mov;
            }
            else if (key == 203) {
                if (cache.isEmpty() || mov <= 0) {
                    return;
                }
                this.lastKeyTyped = key;
                this.updateSplit();
                --mov;
            }
            else if (key == 207) {
                if (cache.isEmpty() || TurokFontManager.getStringWidth(this.fontRenderer, cache) <= 4.5f + this.rect.getWidth()) {
                    return;
                }
                this.scroll = this.rect.getWidth() - TurokFontManager.getStringWidth(this.fontRenderer, cache) - 4.5f;
                mov = cache.length();
            }
            else if (key == 199) {
                if (cache.isEmpty()) {
                    return;
                }
                this.scroll = 0.0f;
                mov = 0;
            }
            else if (key == 211) {
                if (cache.isEmpty() || mov == cache.length() || mov < 0) {
                    return;
                }
                final String first = cache.substring(mov);
                String second = "";
                for (int i = 0; i < mov; ++i) {
                    second += Character.toString(cache.charAt(i));
                }
                cache = second + first.substring(1);
                this.lastKeyTyped = key;
                this.updateSplit();
            }
            else if (key == 14) {
                if (cache.isEmpty() || mov <= 0 || mov > cache.length()) {
                    return;
                }
                if (mov < cache.length()) {
                    final String first = cache.substring(mov);
                    String second = "";
                    for (int i = 0; i < mov; ++i) {
                        second += Character.toString(cache.charAt(i));
                    }
                    cache = StringUtils.chop(second) + first;
                }
                else {
                    cache = StringUtils.chop(cache);
                }
                this.lastKeyTyped = key;
                this.updateSplit();
                --mov;
            }
            else if (Keyboard.isKeyDown(29) && Keyboard.isKeyDown(47)) {
                final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                final Transferable content = clipboard.getContents(null);
                if (content != null && content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        final String copied = content.getTransferData(DataFlavor.stringFlavor).toString();
                        if (mov < cache.length()) {
                            final String first2 = cache.substring(mov);
                            String second2 = "";
                            for (int j = 0; j < mov; ++j) {
                                second2 += Character.toString(cache.charAt(j));
                            }
                            cache = second2 + copied + first2;
                        }
                        else {
                            cache += copied;
                        }
                        this.lastKeyTyped = key;
                        this.updateSplit();
                        mov += copied.length();
                    }
                    catch (UnsupportedFlavorException | IOException ex) {
                        final Exception ex2;
                        final Exception exc = ex2;
                        ChatUtil.print("Exception: " + exc);
                        exc.printStackTrace();
                    }
                }
            }
            else if (ChatAllowedCharacters.isAllowedCharacter(character)) {
                final String c = (key == 57) ? " " : Character.toString(character);
                if (mov < cache.length()) {
                    final String first3 = cache.substring(mov);
                    String second3 = "";
                    for (int k = 0; k < mov; ++k) {
                        second3 += Character.toString(cache.charAt(k));
                    }
                    cache = second3 + c + first3;
                }
                else {
                    cache += c;
                }
                this.lastKeyTyped = key;
                this.updateSplit();
                ++mov;
            }
            this.splitIndex = TurokMath.clamp(mov, 0, cache.length());
            if (!this.text.equals(cache)) {
                this.text = cache;
            }
        }
    }
    
    @Override
    public void onCustomKeyboardPressed(final char character, final int key) {
        if (!this.isRendering) {}
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (!this.isRendering) {
            return;
        }
        if (this.isMouseClickedLeft) {
            this.resetSplit();
            this.isMouseClickedLeft = false;
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        if (!this.isRendering) {
            return;
        }
    }
    
    @Override
    public void onMouseClicked(final int button) {
        if (!this.isRendering) {
            return;
        }
        if (this.flagMouse == Flag.MOUSE_OVER && button == 0) {
            this.updateSplit();
            this.isMouseClickedLeft = true;
            this.splitIndex = this.getSplitByMouse(this.mouse.getX(), this.mouse.getY());
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        if (!this.isRendering) {
            return;
        }
    }
    
    @Override
    public void onRender() {
        if (!this.isRendering) {
            return;
        }
        if (this.isScissored) {
            TurokShaderGL.pushScissor();
            TurokShaderGL.drawScissor(this.rectScissor[0], this.rectScissor[1], this.rectScissor[2], this.rectScissor[3]);
        }
        this.offsetY = (this.rect.getHeight() - TurokFontManager.getStringHeight(this.fontRenderer, this.text)) / 2.0f;
        this.scroll = TurokMath.lerp(this.scroll, this.scroll + this.offsetX, this.partialTicks);
        final float x = this.rect.getX() + 2.0f + this.scroll;
        final float y = this.rect.getY() + this.offsetY;
        int w = 0;
        int i = 1;
        final int k = 1;
        if (this.isFocused) {
            TurokRenderGL.color(this.colorBackground[0], this.colorBackground[1], this.colorBackground[2], this.colorBackground[3]);
            TurokRenderGL.drawSolidRect(this.rect);
            if (this.colorBackgroundOutline[3] > 0) {
                TurokRenderGL.color(this.colorBackgroundOutline[0], this.colorBackgroundOutline[1], this.colorBackgroundOutline[2], this.colorBackgroundOutline[3]);
                TurokRenderGL.drawOutlineRect(this.rect);
            }
            if (this.lastKeyTyped != -1 && !Keyboard.isKeyDown(this.lastKeyTyped)) {
                this.resetSplit();
                this.lastKeyTyped = -1;
            }
        }
        else {
            this.resetSplit();
            this.save = this.text;
        }
        if (this.text.isEmpty()) {
            if (this.isFocused) {
                this.doRenderSplit(x - 0.5f, y);
            }
        }
        else {
            for (final String c : this.text.split("")) {
                final String characters = c;
                final int charWidth = TurokFontManager.getStringWidth(this.fontRenderer, c);
                if (i >= this.selectedStartIndex && i <= this.selectedEndIndex && this.isSelected && this.isFocused) {
                    TurokRenderGL.color(this.colorSelectedBackground[0], this.colorSelectedBackground[1], this.colorSelectedBackground[2], this.colorSelectedBackground[3]);
                    TurokRenderGL.drawSolidRect(x + w, y, (float)charWidth, this.rect.getHeight());
                    TurokFontManager.render(this.fontRenderer, characters, x + w, y, false, new Color(this.colorSelectedString[0], this.colorSelectedString[1], this.colorSelectedString[2], this.colorSelectedString[3]));
                }
                else {
                    TurokFontManager.render(this.fontRenderer, c, x + w, y, false, new Color(this.colorString[0], this.colorString[1], this.colorString[2], this.colorString[3]));
                    if (this.isFocused) {
                        if (this.splitIndex == 0) {
                            this.doRenderSplit(x - 0.5f, y);
                        }
                        else if (i == this.splitIndex) {
                            this.doRenderSplit(x + w + (charWidth - 1.1f), y);
                        }
                    }
                }
                w += charWidth;
                ++i;
            }
        }
        if (this.isScissored) {
            TurokShaderGL.popScissor();
        }
    }
    
    @Override
    public void onCustomRender() {
    }
    
    public enum Type
    {
        TEXT, 
        DOUBLE;
    }
}
