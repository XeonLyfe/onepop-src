//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.module;

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
import com.onepop.api.setting.value.ValueString;
import com.onepop.api.setting.value.ValueEnum;
import com.onepop.api.setting.value.ValueNumber;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import java.io.File;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;
import com.onepop.api.preset.management.PresetManager;
import com.onepop.api.util.chat.ChatUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import java.lang.annotation.Annotation;
import com.onepop.api.module.registry.Registry;
import com.onepop.Onepop;
import com.onepop.client.event.client.ModuleStatusEvent;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.ICamera;
import com.onepop.mixin.interfaces.IMinecraft;
import com.onepop.api.setting.value.ValueBoolean;
import com.onepop.api.setting.value.ValueBind;
import com.onepop.api.setting.Setting;
import java.util.ArrayList;
import com.onepop.api.module.impl.ModuleCategory;
import com.onepop.api.ISLClass;

public class Module implements ISLClass
{
    private String name;
    private String tag;
    private String description;
    private ModuleCategory category;
    private String status;
    private ArrayList<Setting> settingList;
    private ValueBind settingKeyBinding;
    private ValueBoolean settingArrayList;
    private ValueBoolean settingStatus;
    private ValueBoolean settingToggleMessage;
    public IMinecraft imc;
    public ICamera camera;
    
    public Module() {
        this.name = this.getRegistry().name();
        this.tag = this.getRegistry().tag();
        this.description = this.getRegistry().description();
        this.category = this.getRegistry().category();
        this.settingKeyBinding = new ValueBind("", "KeyBind", "Key bind to active or disable module.", -1);
        this.settingArrayList = new ValueBoolean("ArrayList", "ArrayList", "Enable on array list.", true);
        this.settingStatus = new ValueBoolean("Status", "Status", "Show status on array list.", true);
        this.settingToggleMessage = new ValueBoolean("Toggle Message", "ToggleMessage", "Alert if is toggled.", true);
        this.imc = (IMinecraft)Minecraft.getMinecraft();
        this.camera = (ICamera)new Frustum();
        this.registry(this.settingKeyBinding);
        this.registry(this.settingArrayList);
        this.registry(this.settingStatus);
        this.registry(this.settingToggleMessage);
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.settingStatus.getValue() ? this.status : null;
    }
    
    public void setCategory(final ModuleCategory category) {
        this.category = category;
    }
    
    public ModuleCategory getCategory() {
        return this.category;
    }
    
    public void setEnabled(final boolean enabled) {
        final ModuleStatusEvent event = new ModuleStatusEvent(this, enabled);
        Onepop.getPomeloEventManager().dispatchEvent(event);
        if (!event.isCanceled() && this.settingKeyBinding.getState() != enabled) {
            this.settingKeyBinding.setState(enabled);
            this.onReload();
        }
    }
    
    public boolean isEnabled() {
        return this.settingKeyBinding.getState();
    }
    
    public void setKeyCode(final int key) {
        this.settingKeyBinding.setKeyCode(key);
    }
    
    public int getKeyCode() {
        return this.settingKeyBinding.getKeyCode();
    }
    
    public void setSettingList(final ArrayList<Setting> settingList) {
        this.settingList = settingList;
    }
    
    public ArrayList<Setting> getSettingList() {
        return this.settingList;
    }
    
    public Registry getRegistry() {
        Registry details = null;
        if (this.getClass().isAnnotationPresent(Registry.class)) {
            details = this.getClass().getAnnotation(Registry.class);
        }
        return details;
    }
    
    public void registry(final Setting setting) {
        if (this.settingList == null) {
            this.settingList = new ArrayList<Setting>();
        }
        this.settingList.add(setting);
    }
    
    public void unregister(final Setting setting) {
        if (this.settingList == null) {
            this.settingList = new ArrayList<Setting>();
        }
        else if (this.get(setting.getClass()) != null) {
            this.settingList.remove(setting);
        }
    }
    
    public Setting get(final Class<?> clazz) {
        for (final Setting settings : this.settingList) {
            if (settings.getClass() == clazz) {
                return settings;
            }
        }
        return null;
    }
    
    public Setting get(final String tag) {
        for (final Setting settings : this.settingList) {
            if (settings.getTag().equalsIgnoreCase(tag)) {
                return settings;
            }
        }
        return null;
    }
    
    public void onInput(final int keyCode, final ValueBind.InputType inputType) {
        for (final Setting settings : this.settingList) {
            if (settings instanceof ValueBind) {
                final ValueBind settingValueBind = (ValueBind)settings;
                if (!settingValueBind.isEnabled() || settingValueBind.getInputType() != inputType || settingValueBind.getKeyCode() != keyCode) {
                    continue;
                }
                if (settingValueBind.getTag().equalsIgnoreCase(this.settingKeyBinding.getTag())) {
                    this.toggle();
                }
                else {
                    settingValueBind.setState(!settingValueBind.getState());
                }
            }
        }
    }
    
    public boolean shouldRenderOnArrayList() {
        return this.settingArrayList.getValue();
    }
    
    public void toggle() {
        this.setEnabled(!this.settingKeyBinding.getState());
    }
    
    public void onReload() {
        if (this.settingKeyBinding.getState()) {
            this.setEnabled();
        }
        else {
            this.setDisabled();
        }
    }
    
    public void setEnabled() {
        this.settingKeyBinding.setState(true);
        if (this.settingToggleMessage.getValue()) {
            this.print(ChatFormatting.GREEN + "Enabled");
        }
        this.onEnable();
        Onepop.getPomeloEventManager().addEventListener(this);
    }
    
    public void setDisabled() {
        this.settingKeyBinding.setState(false);
        if (this.settingToggleMessage.getValue()) {
            this.print(ChatFormatting.RED + "Disabled");
        }
        this.onDisable();
        Onepop.getPomeloEventManager().removeEventListener(this);
    }
    
    public void status(final String status) {
        this.status = status;
    }
    
    public void print(final String message) {
        ChatUtil.print(ChatFormatting.GRAY + this.name + " " + ChatFormatting.WHITE + message);
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void onRender2D() {
    }
    
    public void onRender3D() {
    }
    
    public void onShutdown() {
    }
    
    public void onSetting() {
    }
    
    public void onSync() {
        this.settingStatus.setEnabled(this.settingArrayList.getValue());
    }
    
    @Override
    public void onSave() {
        try {
            final String pathFolder = PresetManager.getPolicyProtectionValue() + "/module/" + this.category.name().toLowerCase() + "/";
            final String pathFile = pathFolder + this.tag + ".json";
            final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
            final JsonParser jsonParser = new JsonParser();
            if (!Files.exists(Paths.get(pathFolder, new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get(pathFolder, new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (Files.exists(Paths.get(pathFile, new String[0]), new LinkOption[0])) {
                final File file = new File(pathFile);
                file.delete();
            }
            Files.createFile(Paths.get(pathFile, new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            final JsonObject mainJson = new JsonObject();
            final JsonObject jsonSettingList = new JsonObject();
            for (final Setting settings : this.settingList) {
                if (settings instanceof ValueBoolean) {
                    final ValueBoolean settingValueBoolean = (ValueBoolean)settings;
                    jsonSettingList.add(settingValueBoolean.getTag(), (JsonElement)new JsonPrimitive(Boolean.valueOf(settingValueBoolean.getValue())));
                }
                if (settings instanceof ValueNumber) {
                    final ValueNumber settingValueNumber = (ValueNumber)settings;
                    jsonSettingList.add(settingValueNumber.getTag(), (JsonElement)new JsonPrimitive(settingValueNumber.getValue()));
                }
                if (settings instanceof ValueEnum) {
                    final ValueEnum settingValueEnum = (ValueEnum)settings;
                    jsonSettingList.add(settingValueEnum.getTag(), (JsonElement)new JsonPrimitive(settingValueEnum.getValue().name()));
                }
                if (settings instanceof ValueString) {
                    final ValueString settingValueString = (ValueString)settings;
                    jsonSettingList.add(settingValueString.getTag(), (JsonElement)new JsonPrimitive(settingValueString.getValue()));
                }
                if (settings instanceof ValueBind) {
                    final ValueBind settingValueBind = (ValueBind)settings;
                    final JsonObject valueBindObject = new JsonObject();
                    valueBindObject.add("key", (JsonElement)new JsonPrimitive((Number)settingValueBind.getKeyCode()));
                    valueBindObject.add("state", (JsonElement)new JsonPrimitive(Boolean.valueOf(settingValueBind.getState())));
                    valueBindObject.add("type", (JsonElement)new JsonPrimitive(settingValueBind.getInputType().toString()));
                    jsonSettingList.add(settingValueBind.getTag(), (JsonElement)valueBindObject);
                }
            }
            mainJson.add("settings", (JsonElement)jsonSettingList);
            final String stringJson = gsonBuilder.toJson(jsonParser.parse(mainJson.toString()));
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
            final String pathFolder = PresetManager.getPolicyProtectionValue() + "/module/" + this.category.name().toLowerCase() + "/";
            final String pathFile = pathFolder + this.tag + ".json";
            if (!Files.exists(Paths.get(pathFile, new String[0]), new LinkOption[0])) {
                return;
            }
            final JsonParser jsonParser = new JsonParser();
            final InputStream file = Files.newInputStream(Paths.get(pathFile, new String[0]), new OpenOption[0]);
            final JsonObject mainJson = jsonParser.parse((Reader)new InputStreamReader(file)).getAsJsonObject();
            if (mainJson.get("settings") != null) {
                final JsonObject jsonSettingList = mainJson.get("settings").getAsJsonObject();
                for (final Setting settings : this.settingList) {
                    if (jsonSettingList.get(settings.getTag()) == null) {
                        continue;
                    }
                    if (settings instanceof ValueBoolean) {
                        final ValueBoolean settingValueBoolean = (ValueBoolean)settings;
                        settingValueBoolean.setValue(jsonSettingList.get(settings.getTag()).getAsBoolean());
                    }
                    if (settings instanceof ValueNumber) {
                        final ValueNumber settingValueNumber = (ValueNumber)settings;
                        if (settingValueNumber.getValue() instanceof Float) {
                            settingValueNumber.setValue(jsonSettingList.get(settings.getTag()).getAsFloat());
                        }
                        if (settingValueNumber.getValue() instanceof Double) {
                            settingValueNumber.setValue(jsonSettingList.get(settings.getTag()).getAsDouble());
                        }
                        if (settingValueNumber.getValue() instanceof Integer) {
                            settingValueNumber.setValue(jsonSettingList.get(settings.getTag()).getAsInt());
                        }
                    }
                    if (settings instanceof ValueEnum) {
                        final ValueEnum settingValueEnum = (ValueEnum)settings;
                        settingValueEnum.setValue(TurokClass.getEnumByName(settingValueEnum.getValue(), jsonSettingList.get(settings.getTag()).getAsString()));
                    }
                    if (settings instanceof ValueBind) {
                        final ValueBind settingValueBind = (ValueBind)settings;
                        final JsonObject valueBindObject = jsonSettingList.get(settingValueBind.getTag()).getAsJsonObject();
                        if (valueBindObject.get("key") != null) {
                            settingValueBind.setKeyCode(valueBindObject.get("key").getAsInt());
                        }
                        if (valueBindObject.get("state") != null) {
                            settingValueBind.setState(valueBindObject.get("state").getAsBoolean());
                        }
                        if (valueBindObject.get("type") != null) {
                            settingValueBind.setInputType((ValueBind.InputType)TurokClass.getEnumByName(settingValueBind.getInputType(), valueBindObject.get("type").getAsString()));
                        }
                    }
                    if (!(settings instanceof ValueString)) {
                        continue;
                    }
                    final ValueString settingValueString = (ValueString)settings;
                    settingValueString.setValue(jsonSettingList.get(settings.getTag()).getAsString());
                }
            }
            file.close();
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
