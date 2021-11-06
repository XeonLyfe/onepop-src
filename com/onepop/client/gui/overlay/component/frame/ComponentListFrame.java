// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.gui.overlay.component.frame;

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
import com.onepop.client.gui.module.module.widget.ModuleWidget;
import me.rina.turok.render.opengl.TurokShaderGL;
import java.awt.Color;
import me.rina.turok.render.opengl.TurokRenderGL;
import java.util.Iterator;
import com.onepop.client.gui.overlay.component.widget.ComponentWidget;
import com.onepop.api.component.Component;
import com.onepop.Onepop;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.api.gui.flag.Flag;
import com.onepop.api.gui.widget.Widget;
import java.util.ArrayList;
import me.rina.turok.util.TurokRect;
import com.onepop.client.gui.overlay.ComponentClickGUI;
import com.onepop.api.gui.frame.Frame;

public class ComponentListFrame extends Frame
{
    private ComponentClickGUI master;
    private int offsetX;
    private int offsetY;
    private int offsetWidth;
    private int offsetHeight;
    private int dragX;
    private int dragY;
    public TurokRect rectOffset;
    private ArrayList<Widget> loadedWidgetList;
    private boolean isButtonMouseLeftClicked;
    public Flag flagMouse;
    public Flag flagOffsetMouse;
    
    public ComponentListFrame(final ComponentClickGUI master, final String tag) {
        super(tag);
        this.master = master;
        this.rectOffset = new TurokRect("Offset", 0.0f, 0.0f);
        this.rect.setWidth(102.0f);
        this.offsetX = 1;
        this.offsetY = 1;
        this.offsetWidth = 112;
        this.offsetHeight = 2 + TurokFontManager.getStringHeight(this.master.fontComponentListFrame, this.rect.getTag()) + 2 + 2;
        this.init();
    }
    
    public void init() {
        this.loadedWidgetList = new ArrayList<Widget>();
        this.rect.setHeight((float)this.offsetHeight);
        for (final Component components : Onepop.getComponentManager().getComponentList()) {
            final ComponentWidget moduleWidget = new ComponentWidget(this.master, this, components);
            moduleWidget.setOffsetY((int)this.rect.getHeight());
            this.loadedWidgetList.add(moduleWidget);
            final TurokRect rect = this.rect;
            rect.height += moduleWidget.getRect().getHeight() + 1.0f;
        }
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
    
    public void setButtonMouseClickedLeft(final boolean mouseClickedLeft) {
        this.isButtonMouseLeftClicked = mouseClickedLeft;
    }
    
    public boolean isButtonMouseClickedLeft() {
        return this.isButtonMouseLeftClicked;
    }
    
    public void refresh() {
        this.rect.height = 0.0f;
        this.rect.setHeight((float)this.offsetHeight);
        for (final Widget widgets : this.loadedWidgetList) {
            if (widgets instanceof ComponentWidget) {
                final ComponentWidget moduleWidget = (ComponentWidget)widgets;
                moduleWidget.setOffsetY((int)this.rect.getHeight());
                if (moduleWidget.isWidgetOpened()) {
                    final TurokRect rect = this.rect;
                    rect.height += moduleWidget.getOffsetHeight();
                }
                else {
                    final TurokRect rect2 = this.rect;
                    rect2.height += moduleWidget.getRect().getHeight() + 1.0f;
                }
            }
        }
    }
    
    @Override
    public boolean verifyFocus(final int mx, final int my) {
        boolean verified = false;
        if (this.rect.collideWithMouse(this.master.getMouse())) {
            verified = true;
        }
        return verified;
    }
    
    @Override
    public void onScreenOpened() {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onScreenOpened();
        }
    }
    
    @Override
    public void onCustomScreenOpened() {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomScreenOpened();
        }
    }
    
    @Override
    public void onScreenClosed() {
        this.isButtonMouseLeftClicked = false;
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onScreenClosed();
        }
    }
    
    @Override
    public void onCustomScreenClosed() {
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomScreenClosed();
        }
    }
    
    @Override
    public void onKeyboardPressed(final char charCode, final int keyCode) {
    }
    
    @Override
    public void onCustomKeyboardPressed(final char charCode, final int keyCode) {
    }
    
    @Override
    public void onMouseReleased(final int button) {
        if (this.isButtonMouseLeftClicked) {
            this.isButtonMouseLeftClicked = false;
        }
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onMouseReleased(button);
        }
    }
    
    @Override
    public void onCustomMouseReleased(final int button) {
        this.master.moveFocusedFrameToTopMatrix();
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomMouseReleased(button);
        }
    }
    
    @Override
    public void onMouseClicked(final int button) {
        if (this.flagOffsetMouse == Flag.MOUSE_OVER && button == 0) {
            this.dragX = (int)(this.master.getMouse().getX() - this.rect.getX());
            this.dragY = (int)(this.master.getMouse().getY() - this.rect.getY());
            this.isButtonMouseLeftClicked = true;
        }
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onMouseClicked(button);
        }
    }
    
    @Override
    public void onCustomMouseClicked(final int button) {
        this.master.moveFocusedFrameToTopMatrix();
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
            this.rect.setX((float)(this.master.getMouse().getX() - this.dragX));
            this.rect.setY((float)(this.master.getMouse().getY() - this.dragY));
        }
        TurokShaderGL.drawOutlineRectFadingMouse(this.rect, 20, new Color(this.master.guiColor.background[0], this.master.guiColor.background[1], this.master.guiColor.background[2], 255));
        TurokFontManager.render(this.master.fontComponentListFrame, this.rect.getTag(), this.rect.getX() + this.offsetX, this.rect.getY() + this.offsetY, true, new Color(255, 255, 255));
        TurokShaderGL.drawSolidRectFadingMouse(this.rect.getX(), this.rectOffset.getY() + this.rectOffset.getHeight() - 2.0f, (float)(this.offsetX + TurokFontManager.getStringWidth(this.master.fontComponentListFrame, this.rect.getTag()) + 2), 1.0f, 50, new Color(this.master.guiColor.base[0], this.master.guiColor.base[1], this.master.guiColor.base[2], 255));
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onRender();
            if (widgets instanceof ModuleWidget) {
                final ModuleWidget moduleWidget = (ModuleWidget)widgets;
                moduleWidget.flagMouse = Flag.MOUSE_NOT_OVER;
            }
        }
    }
    
    @Override
    public void onCustomRender() {
        this.flagOffsetMouse = (this.rectOffset.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
        this.flagMouse = (this.rect.collideWithMouse(this.master.getMouse()) ? Flag.MOUSE_OVER : Flag.MOUSE_NOT_OVER);
        for (final Widget widgets : this.loadedWidgetList) {
            widgets.onCustomRender();
        }
    }
    
    @Override
    public void onSave() {
        try {
            final String pathFolder = "ONEPOPCLIENT/GUI/";
            final String pathFile = pathFolder + this.rect.getTag() + ".json";
            final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
            if (!Files.exists(Paths.get(pathFolder.toString(), new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get(pathFolder.toString(), new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (!Files.exists(Paths.get(pathFile.toString(), new String[0]), new LinkOption[0])) {
                Files.createFile(Paths.get(pathFile.toString(), new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            else {
                final File file = new File(pathFile.toString());
                file.delete();
            }
            final JsonObject mainJson = new JsonObject();
            mainJson.add("x", (JsonElement)new JsonPrimitive((Number)this.rect.getX()));
            mainJson.add("y", (JsonElement)new JsonPrimitive((Number)this.rect.getY()));
            final String stringJson = gsonBuilder.toJson(new JsonParser().parse(mainJson.toString()));
            final OutputStreamWriter fileOutputStream = new OutputStreamWriter(new FileOutputStream(pathFile.toString()), "UTF-8");
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
            final String pathFolder = "ONEPOPCLIENT/GUI/";
            final String pathFile = pathFolder + this.rect.getTag() + ".json";
            if (!Files.exists(Paths.get(pathFile.toString(), new String[0]), new LinkOption[0])) {
                return;
            }
            final InputStream file = Files.newInputStream(Paths.get(pathFile.toString(), new String[0]), new OpenOption[0]);
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
