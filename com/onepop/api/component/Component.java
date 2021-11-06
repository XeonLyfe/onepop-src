//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.component;

import java.io.InputStream;
import me.rina.turok.util.TurokClass;
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
import me.rina.turok.util.TurokDisplay;
import me.rina.turok.render.font.management.TurokFontManager;
import com.onepop.Onepop;
import com.onepop.client.module.client.ModuleHUD;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import com.onepop.api.component.impl.ComponentSetting;
import java.util.ArrayList;
import me.rina.turok.util.TurokRect;

public class Component
{
    private String name;
    private String description;
    public TurokRect rect;
    public int offsetX;
    public int offsetY;
    private boolean isEnabled;
    private boolean isDragging;
    private ArrayList<ComponentSetting> settingList;
    private int[] colorRGB;
    private int[] colorHUD;
    public Dock dock;
    public StringType type;
    public ComponentSetting<Boolean> customFont;
    public ComponentSetting<Boolean> shadowFont;
    public ComponentSetting<ColorMode> colorMode;
    public static final Minecraft mc;
    
    public Component(final String name, final String tag, final String description, final StringType type) {
        this.colorRGB = new int[] { 0, 0, 0 };
        this.colorHUD = new int[] { 0, 0, 0 };
        this.name = name;
        this.description = description;
        this.rect = new TurokRect(tag, 0.0f, 0.0f);
        this.dock = Dock.TOP_LEFT;
        this.type = type;
        if (this.type == StringType.USE) {
            this.registry(this.customFont = new ComponentSetting<Boolean>("Custom Font", "CustomFont", "Enable smooth font render.", false));
            this.registry(this.shadowFont = new ComponentSetting<Boolean>("Shadow Font", "ShadowFont", "Render shadow effect in font.", true));
            this.registry(this.colorMode = new ComponentSetting<ColorMode>("Color Mode", "ColorMode", "Color modes", ColorMode.HUD));
        }
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setTag(final String tag) {
        this.rect.setTag(tag);
    }
    
    public String getTag() {
        return this.rect.getTag();
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setEnabled(final boolean enabled) {
        this.isEnabled = enabled;
    }
    
    public boolean isEnabled() {
        return this.isEnabled;
    }
    
    public void setDragging(final boolean dragging) {
        this.isDragging = dragging;
    }
    
    public boolean isDragging() {
        return this.isDragging;
    }
    
    public ArrayList<ComponentSetting> getSettingList() {
        return this.settingList;
    }
    
    public void setSettingList(final ArrayList<ComponentSetting> settingList) {
        this.settingList = settingList;
    }
    
    public void setRect(final TurokRect rect) {
        this.rect = rect;
    }
    
    public TurokRect getRect() {
        return this.rect;
    }
    
    public void setDock(final Dock dock) {
        this.dock = dock;
    }
    
    public Dock getDock() {
        return this.dock;
    }
    
    public void setType(final StringType type) {
        this.type = type;
    }
    
    public StringType getType() {
        return this.type;
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
    
    public void registry(final ComponentSetting<?> setting) {
        if (this.settingList == null) {
            this.settingList = new ArrayList<ComponentSetting>();
        }
        this.settingList.add(setting);
    }
    
    public ComponentSetting<?> get(final Class clazz) {
        for (final ComponentSetting<?> settings : this.settingList) {
            if (settings.getClass() == clazz) {
                return settings;
            }
        }
        return null;
    }
    
    public ComponentSetting<?> get(final String tag) {
        for (final ComponentSetting<?> settings : this.settingList) {
            if (settings.getTag().equalsIgnoreCase(tag)) {
                return settings;
            }
        }
        return null;
    }
    
    public void render(final int x, final int y, final float w, final float h, final Color color) {
        final int realX = (int)(this.rect.getX() + this.offsetX);
        final int realY = (int)(this.rect.getY() + this.offsetY);
        Gui.drawRect(realX + this.verifyDock((float)x, w), realY + y, (int)(realX + this.verifyDock((float)x, w) + w), (int)(realY + y + h), color.getRGB());
    }
    
    public void render(final String string, final float x, final float y) {
        final int factor = 101 - ModuleHUD.settingSpeedHUE.getValue().intValue();
        if (this.type == StringType.USE) {
            final int realX = (int)(this.rect.getX() + this.offsetX);
            final int realY = (int)(this.rect.getY() + this.offsetY);
            Color color = new Color(0, 0, 0);
            if (this.colorMode.getValue() == ColorMode.RGB) {
                color = new Color(this.colorRGB[0], this.colorRGB[1], this.colorRGB[2]);
            }
            else if (this.colorMode.getValue() == ColorMode.HUD) {
                color = new Color(this.colorHUD[0], this.colorHUD[1], this.colorHUD[2]);
            }
            if (this.customFont.getValue()) {
                if (this.colorMode.getValue() == ColorMode.HUE) {
                    TurokFontManager.render(Onepop.getComponentManager().font, string, (float)(realX + this.verifyDock(x, (float)this.getStringWidth(string))), realY + y, this.shadowFont.getValue(), factor);
                }
                else {
                    TurokFontManager.render(Onepop.getComponentManager().font, string, (float)(realX + this.verifyDock(x, (float)this.getStringWidth(string))), realY + y, this.shadowFont.getValue(), color);
                }
            }
            else if (this.colorMode.getValue() == ColorMode.HUE) {
                TurokFontManager.render(string, (float)(realX + this.verifyDock(x, (float)this.getStringWidth(string))), realY + y, this.shadowFont.getValue(), factor);
            }
            else {
                TurokFontManager.render(string, (float)(realX + this.verifyDock(x, (float)this.getStringWidth(string))), realY + y, this.shadowFont.getValue(), color);
            }
        }
    }
    
    public int getStringWidth(final String string) {
        if (this.type != StringType.USE) {
            return 0;
        }
        if (this.customFont.getValue()) {
            return TurokFontManager.getStringWidth(Onepop.getComponentManager().font, string);
        }
        return Component.mc.fontRendererObj.getStringWidth(string);
    }
    
    public int getStringHeight(final String string) {
        if (this.type != StringType.USE) {
            return 0;
        }
        if (this.customFont.getValue()) {
            return TurokFontManager.getStringHeight(Onepop.getComponentManager().font, string) + 2;
        }
        return Component.mc.fontRendererObj.FONT_HEIGHT;
    }
    
    public int verifyDock(final float x, final float w) {
        int position = 0;
        if (this.dock == Dock.TOP_LEFT) {
            position = (int)x;
        }
        if (this.dock == Dock.TOP_RIGHT) {
            position = (int)(this.rect.getWidth() - w - this.offsetX - x);
        }
        if (this.dock == Dock.BOTTOM_LEFT) {
            position = (int)x;
        }
        if (this.dock == Dock.BOTTOM_RIGHT) {
            position = (int)(this.rect.getWidth() - w - this.offsetX - x);
        }
        return position;
    }
    
    public void cornerDetector() {
        final TurokDisplay display = new TurokDisplay(Component.mc);
        final int diff = 0;
        if (this.rect.getX() <= diff) {
            if (this.dock == Dock.TOP_RIGHT) {
                this.dock = Dock.TOP_LEFT;
            }
            else if (this.dock == Dock.BOTTOM_RIGHT) {
                this.dock = Dock.BOTTOM_LEFT;
            }
        }
        if (this.rect.getY() <= diff) {
            if (this.dock == Dock.BOTTOM_LEFT) {
                this.dock = Dock.TOP_LEFT;
            }
            else if (this.dock == Dock.BOTTOM_RIGHT) {
                this.dock = Dock.TOP_RIGHT;
            }
        }
        if (this.rect.getX() + this.rect.getWidth() >= display.getScaledWidth() - diff) {
            if (this.dock == Dock.TOP_LEFT) {
                this.dock = Dock.TOP_RIGHT;
            }
            else if (this.dock == Dock.BOTTOM_LEFT) {
                this.dock = Dock.BOTTOM_RIGHT;
            }
        }
        if (this.rect.getY() + this.rect.getHeight() >= display.getScaledHeight() - diff) {
            if (this.dock == Dock.TOP_LEFT) {
                this.dock = Dock.BOTTOM_LEFT;
            }
            else if (this.dock == Dock.BOTTOM_RIGHT) {
                this.dock = Dock.BOTTOM_RIGHT;
            }
        }
        float dx = this.rect.getX();
        float dy = this.rect.getY();
        final float w = this.rect.getWidth();
        final float h = this.rect.getHeight();
        if (dx <= diff) {
            dx = (float)diff;
        }
        if (dy <= diff) {
            dy = (float)diff;
        }
        if (dx >= display.getScaledWidth() - w - diff) {
            dx = display.getScaledWidth() - w - diff;
        }
        if (dy >= display.getScaledHeight() - h - diff) {
            dy = display.getScaledHeight() - h - diff;
        }
        this.rect.setX(dx);
        this.rect.setY(dy);
    }
    
    public void onSave() {
        try {
            final String pathFolder = PresetManager.getPolicyProtectionValue() + "/HUD/";
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
            mainJson.add("enabled", (JsonElement)new JsonPrimitive(Boolean.valueOf(this.isEnabled)));
            mainJson.add("x", (JsonElement)new JsonPrimitive((Number)this.rect.getX()));
            mainJson.add("y", (JsonElement)new JsonPrimitive((Number)this.rect.getY()));
            final JsonObject jsonSettingList = new JsonObject();
            for (final ComponentSetting<?> settings : this.settingList) {
                if (settings.getValue() instanceof Boolean) {
                    final ComponentSetting<Boolean> componentSetting = (ComponentSetting<Boolean>)settings;
                    jsonSettingList.add(settings.getTag(), (JsonElement)new JsonPrimitive(Boolean.valueOf(componentSetting.getValue())));
                }
                if (settings.getValue() instanceof Number) {
                    final ComponentSetting<Number> componentSetting2 = (ComponentSetting<Number>)settings;
                    jsonSettingList.add(settings.getTag(), (JsonElement)new JsonPrimitive((Number)componentSetting2.getValue()));
                }
                if (settings.getValue() instanceof Enum) {
                    final ComponentSetting<Enum> componentSetting3 = (ComponentSetting<Enum>)settings;
                    jsonSettingList.add(settings.getTag(), (JsonElement)new JsonPrimitive(componentSetting3.getValue().name()));
                }
            }
            mainJson.add("settings", (JsonElement)jsonSettingList);
            final String stringJson = gsonBuilder.toJson(new JsonParser().parse(mainJson.toString()));
            final OutputStreamWriter fileOutputStream = new OutputStreamWriter(new FileOutputStream(pathFile), "UTF-8");
            fileOutputStream.write(stringJson);
            fileOutputStream.close();
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    
    public void onLoad() {
        try {
            final String pathFolder = PresetManager.getPolicyProtectionValue() + "/HUD/";
            final String pathFile = pathFolder + this.rect.getTag() + ".json";
            if (!Files.exists(Paths.get(pathFile, new String[0]), new LinkOption[0])) {
                return;
            }
            final InputStream file = Files.newInputStream(Paths.get(pathFile, new String[0]), new OpenOption[0]);
            final JsonObject mainJson = new JsonParser().parse((Reader)new InputStreamReader(file)).getAsJsonObject();
            if (mainJson.get("enabled") != null) {
                this.setEnabled(mainJson.get("enabled").getAsBoolean());
            }
            if (mainJson.get("x") != null) {
                this.rect.setX((float)mainJson.get("x").getAsInt());
            }
            if (mainJson.get("y") != null) {
                this.rect.setY((float)mainJson.get("y").getAsInt());
            }
            if (mainJson.get("settings") != null) {
                final JsonObject jsonSettingList = mainJson.get("settings").getAsJsonObject();
                for (final ComponentSetting<?> settings : this.settingList) {
                    if (jsonSettingList.get(settings.getTag()) == null) {
                        continue;
                    }
                    if (settings.getValue() instanceof Boolean) {
                        final ComponentSetting<Boolean> componentSetting = (ComponentSetting<Boolean>)settings;
                        componentSetting.setValue(jsonSettingList.get(settings.getTag()).getAsBoolean());
                    }
                    if (settings.getValue() instanceof Integer) {
                        final ComponentSetting<Integer> componentSetting2 = (ComponentSetting<Integer>)settings;
                        componentSetting2.setValue(jsonSettingList.get(settings.getTag()).getAsInt());
                    }
                    if (settings.getValue() instanceof Double) {
                        final ComponentSetting<Double> componentSetting3 = (ComponentSetting<Double>)settings;
                        componentSetting3.setValue(jsonSettingList.get(settings.getTag()).getAsDouble());
                    }
                    if (settings.getValue() instanceof Float) {
                        final ComponentSetting<Float> componentSetting4 = (ComponentSetting<Float>)settings;
                        componentSetting4.setValue(jsonSettingList.get(settings.getTag()).getAsFloat());
                    }
                    if (!(settings.getValue() instanceof Enum)) {
                        continue;
                    }
                    final ComponentSetting<Enum> componentSetting5 = (ComponentSetting<Enum>)settings;
                    componentSetting5.setValue(TurokClass.getEnumByName(componentSetting5.getValue(), jsonSettingList.get(settings.getTag()).getAsString()));
                }
            }
            file.close();
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    
    public void onRender() {
        final float partialTicks = Component.mc.getRenderPartialTicks();
        final float[] currentColor360 = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int cycleColor = Color.HSBtoRGB(currentColor360[0], 1.0f, 1.0f);
        this.colorRGB = new int[] { cycleColor >> 16 & 0xFF, cycleColor >> 8 & 0xFF, cycleColor & 0xFF };
        this.colorHUD = new int[] { ModuleHUD.settingRed.getValue().intValue(), ModuleHUD.settingGreen.getValue().intValue(), ModuleHUD.settingBlue.getValue().intValue() };
        this.onRender(partialTicks);
    }
    
    public void onRender(final float partialTicks) {
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
    
    public enum ColorMode
    {
        HUD, 
        RGB, 
        HUE;
    }
    
    public enum Dock
    {
        TOP_LEFT, 
        TOP_RIGHT, 
        BOTTOM_LEFT, 
        BOTTOM_RIGHT;
    }
    
    public enum StringType
    {
        USE, 
        NOT_USE;
    }
}
