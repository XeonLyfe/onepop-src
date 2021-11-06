// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.module.category;

import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.nio.file.OpenOption;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import java.io.File;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import com.google.gson.GsonBuilder;
import com.onepop.api.preset.management.PresetManager;
import me.rina.turok.render.opengl.TurokShaderGL;
import java.awt.Color;
import me.rina.turok.render.opengl.TurokRenderGL;
import me.rina.turok.hardware.mouse.TurokMouse;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import com.onepop.client.gui.module.module.container.ModuleScrollContainer;
import com.onepop.api.gui.widget.Widget;
import java.util.ArrayList;
import me.rina.turok.util.TurokRect;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.client.gui.module.ModuleClickGUI;
import com.onepop.api.gui.frame.Frame;

public class CategoryFrame extends Frame
{
    private ModuleClickGUI master;
    private ModuleCategory category;
    private int offsetX;
    private int offsetY;
    private int offsetWidth;
    private int offsetHeight;
    private float dragX;
    private float dragY;
    public TurokRect rectOffset;
    private int maximumModule;
    private ArrayList<Widget> loadedWidgetList;
    private boolean isButtonMouseLeftClicked;
    private boolean isAbleToScissor;
    public ModuleScrollContainer scrollContainer;
    public Flag flagOffsetMouse;
    public Flag flagMouse;
    
    public CategoryFrame(final ModuleClickGUI master, final ModuleCategory category) {
        super(category.getTag());
        this.master = master;
        this.category = category;
        this.offsetX = 2;
        this.offsetY = 1;
        this.offsetWidth = 102;
        this.offsetHeight = 2 + TurokFontManager.getStringHeight(this.master.fontFrameCategory, this.rect.getTag()) + 2 + 2;
        this.flagOffsetMouse = Flag.MOUSE_NOT_OVER;
        this.flagMouse = Flag.MOUSE_NOT_OVER;
        this.maximumModule = 12;
        this.isAbleToScissor = false;
        this.init();
    }
    
    public void init() {
        this.rect.setWidth((float)this.offsetWidth);
        this.loadedWidgetList = new ArrayList<Widget>();
        this.rect.setHeight((float)this.offsetHeight);
        this.scrollContainer = new ModuleScrollContainer(this.master, this);
        this.rectOffset = new TurokRect("flag", 0.0f, 0.0f);
    }
    
    public ModuleCategory getCategory() {
        return this.category;
    }
    
    public int getMaximumModule() {
        return this.maximumModule;
    }
    
    public boolean verify(final TurokMouse mouse) {
        boolean verified = false;
        if (this.rect.collideWithMouse(mouse)) {
            verified = true;
        }
        if (this.flagOffsetMouse == Flag.MOUSE_OVER) {
            verified = true;
        }
        return verified;
    }
    
    public void setOffsetX(final int offsetX) {
        this.offsetX = offsetX;
    }
    
    public int getOffsetX() {
        return this.offsetX;
    }
    
    public void setOffsetY(final int offsetY) {
        this.offsetY = offsetY;
    }
    
    public int getOffsetY() {
        return this.offsetY;
    }
    
    public void setOffsetWidth(final int offsetWidth) {
        this.offsetWidth = offsetWidth;
    }
    
    public int getOffsetWidth() {
        return this.offsetWidth;
    }
    
    public void setOffsetHeight(final int offsetHeight) {
        this.offsetHeight = offsetHeight;
    }
    
    public int getOffsetHeight() {
        return this.offsetHeight;
    }
    
    public void setAbleToScissor(final boolean ableToScissor) {
        this.isAbleToScissor = ableToScissor;
    }
    
    public boolean isAbleToScissor() {
        return this.isAbleToScissor;
    }
    
    @Override
    public void onScreenOpened() {
        this.scrollContainer.onScreenOpened();
    }
    
    @Override
    public void onCustomScreenOpened() {
        this.scrollContainer.onCustomScreenOpened();
    }
    
    @Override
    public void onScreenClosed() {
        this.isButtonMouseLeftClicked = false;
        this.scrollContainer.onScreenClosed();
    }
    
    @Override
    public void onCustomScreenClosed() {
        this.scrollContainer.onCustomScreenClosed();
    }
    
    @Override
    public void onKeyboardPressed(final char charCode, final int keyCode) {
        this.scrollContainer.onKeyboardPressed(charCode, keyCode);
    }
    
    @Override
    public void onCustomKeyboardPressed(final char charCode, final int keyCode) {
        this.scrollContainer.onCustomKeyboardPressed(charCode, keyCode);
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (this.isButtonMouseLeftClicked) {
            this.isButtonMouseLeftClicked = false;
        }
        this.scrollContainer.onMouseReleased(button);
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        this.master.matrixMoveFocusedFrameToLast();
        this.scrollContainer.onCustomMouseReleased(button);
    }
    
    @Override
    public void onMouseClicked(final int button) {
        if (this.flagOffsetMouse == Flag.MOUSE_OVER && button == 0) {
            this.dragX = this.master.getMouse().getX() - this.rect.getX();
            this.dragY = this.master.getMouse().getY() - this.rect.getY();
            this.isButtonMouseLeftClicked = true;
        }
        this.scrollContainer.onMouseClicked(button);
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        this.master.matrixMoveFocusedFrameToLast();
        this.scrollContainer.onCustomMouseClicked(button);
    }
    
    @Override
    public void onRender() {
        this.rectOffset.setX(this.rect.getX());
        this.rectOffset.setY(this.rect.getY());
        this.rectOffset.setWidth(this.rect.getWidth());
        this.rectOffset.setHeight((float)this.offsetHeight);
        TurokRenderGL.color(this.master.guiColor.background[0], this.master.guiColor.background[1], this.master.guiColor.background[2], this.master.guiColor.background[3]);
        TurokRenderGL.drawSolidRect(this.rect);
        if (this.isButtonMouseLeftClicked) {
            this.rect.setX(this.master.getMouse().getX() - this.dragX);
            this.rect.setY(this.master.getMouse().getY() - this.dragY);
        }
        TurokShaderGL.drawSolidRectFadingMouse(this.rect.getX(), this.rectOffset.getY() + this.rectOffset.getHeight() - 2.0f, (float)(this.offsetX + TurokFontManager.getStringWidth(this.master.fontFrameCategory, this.rect.getTag()) + 2), 1.0f, 50, new Color(this.master.guiColor.base[0], this.master.guiColor.base[1], this.master.guiColor.base[2], 255));
        TurokShaderGL.drawOutlineRectFadingMouse(this.rect, 20, new Color(this.master.guiColor.background[0], this.master.guiColor.background[1], this.master.guiColor.background[2], 255));
        TurokFontManager.render(this.master.fontFrameCategory, this.rect.getTag(), this.rect.getX() + this.offsetX, this.rect.getY() + this.offsetY, true, new Color(255, 255, 255));
        this.scrollContainer.onRender();
    }
    
    @Override
    public void onCustomRender() {
        this.flagOffsetMouse = (this.rectOffset.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
        this.scrollContainer.onCustomRender();
    }
    
    @Override
    public void onSave() {
        try {
            final String pathFolder = PresetManager.getPolicyProtectionValue() + "/GUI/";
            final String pathFile = pathFolder + this.rect.getTag() + ".json";
            final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
            if (!Files.exists(Paths.get(pathFolder, new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get(pathFolder, new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (Files.exists(Paths.get(pathFile, new String[0]), new LinkOption[0])) {
                final File file = new File(pathFile);
                file.delete();
            }
            Files.createFile(Paths.get(pathFile, new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            final JsonObject mainJson = new JsonObject();
            mainJson.add("x", (JsonElement)new JsonPrimitive((Number)this.rect.getX()));
            mainJson.add("y", (JsonElement)new JsonPrimitive((Number)this.rect.getY()));
            final String stringJson = gsonBuilder.toJson(new JsonParser().parse(mainJson.toString()));
            final OutputStreamWriter fileOutputStream = new OutputStreamWriter(new FileOutputStream(pathFile), "UTF-8");
            fileOutputStream.write(stringJson);
            fileOutputStream.close();
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    
    @Override
    public void onLoad() {
        try {
            final String pathFolder = PresetManager.getPolicyProtectionValue() + "/GUI/";
            final String pathFile = pathFolder + this.rect.getTag() + ".json";
            if (!Files.exists(Paths.get(pathFile, new String[0]), new LinkOption[0])) {
                return;
            }
            final InputStream file = Files.newInputStream(Paths.get(pathFile, new String[0]), new OpenOption[0]);
            final JsonObject mainJson = new JsonParser().parse((Reader)new InputStreamReader(file)).getAsJsonObject();
            if (mainJson.get("x") != null) {
                this.rect.setX((float)mainJson.get("x").getAsInt());
            }
            if (mainJson.get("y") != null) {
                this.rect.setY((float)mainJson.get("y").getAsInt());
            }
            file.close();
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
