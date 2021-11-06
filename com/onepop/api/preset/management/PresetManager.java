// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.preset.management;

import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.nio.file.OpenOption;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.nio.file.attribute.FileAttribute;
import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import com.onepop.api.module.management.ModuleManager;
import com.onepop.Onepop;
import com.onepop.api.preset.impl.PresetState;
import java.util.Iterator;
import me.rina.turok.util.TurokGeneric;
import com.onepop.api.preset.Preset;
import java.util.ArrayList;
import com.onepop.api.ISLClass;

public class PresetManager implements ISLClass
{
    public static PresetManager INSTANCE;
    private ArrayList<Preset> presetList;
    private TurokGeneric<String> policyProtection;
    
    public PresetManager() {
        PresetManager.INSTANCE = this;
        this.presetList = new ArrayList<Preset>();
        this.policyProtection = new TurokGeneric<String>("default");
    }
    
    public void setPolicyProtection(final TurokGeneric<String> preset) {
        this.policyProtection = preset;
    }
    
    public TurokGeneric<String> getPolicyProtection() {
        return this.policyProtection;
    }
    
    public void registry(final Preset preset) {
        this.presetList.add(preset);
    }
    
    public void unregister(final Preset preset) {
        if (get(preset.getClass()) != null) {
            this.presetList.remove(preset);
        }
    }
    
    public void setPresetList(final ArrayList<Preset> presetList) {
        this.presetList = presetList;
    }
    
    public ArrayList<Preset> getPresetList() {
        return this.presetList;
    }
    
    public void setCurrent(final Preset preset) {
        for (final Preset presets : this.presetList) {
            preset.setCurrent(false);
        }
        for (final Preset presets : this.presetList) {
            if (preset.getName().equalsIgnoreCase(preset.getName())) {
                preset.setCurrent(true);
                break;
            }
        }
    }
    
    public static Preset current() {
        Preset preset = null;
        int countTarget = 0;
        int countTotally = 0;
        for (final Preset presets : PresetManager.INSTANCE.presetList) {
            if (presets.isCurrent() && presets.getState() == PresetState.OPERABLE) {
                preset = presets;
                ++countTarget;
            }
            ++countTotally;
        }
        return preset;
    }
    
    public static Preset get(final Class<?> clazz) {
        for (final Preset presets : PresetManager.INSTANCE.getPresetList()) {
            if (presets.getClass() == clazz) {
                return presets;
            }
        }
        return null;
    }
    
    public static Preset get(final String name) {
        for (final Preset presets : PresetManager.INSTANCE.getPresetList()) {
            if (presets.getName().equalsIgnoreCase(name)) {
                return presets;
            }
        }
        return null;
    }
    
    public static void shutdown() {
        refresh();
        Onepop.getModuleManager().onSave();
        Onepop.getSocialManager().onSave();
        Onepop.getComponentManager().onSaveList();
        PresetManager.INSTANCE.onSave();
    }
    
    public static void reload() {
        PresetManager.INSTANCE.onLoad();
        refresh();
        Onepop.getModuleManager().onLoad();
        Onepop.getSocialManager().onLoad();
        Onepop.getComponentManager().onLoadList();
        ModuleManager.refresh();
        ModuleManager.reload();
    }
    
    public static void refresh() {
        Preset preset = current();
        if (preset == null) {
            preset = get("Default");
            if (preset == null) {
                preset = new Preset("Default", "Default", "Unknown");
            }
            PresetManager.INSTANCE.setCurrent(preset);
        }
        final String path = "onepop/presets/" + preset.getPath();
        PresetManager.INSTANCE.policyProtection.setValue(Files.exists(Paths.get(path, new String[0]), new LinkOption[0]) ? preset.getPath() : "default");
    }
    
    public static String getPolicyProtectionValue() {
        return "onepop/presets/" + PresetManager.INSTANCE.getPolicyProtection().getValue();
    }
    
    @Override
    public void onSave() {
        try {
            final String pathFolder = "onepop//";
            final String pathFile = pathFolder + "Preset.json";
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
            final JsonArray mainJsonArray = new JsonArray();
            for (final Preset presets : this.presetList) {
                final JsonObject presetJson = new JsonObject();
                presetJson.add("name", (JsonElement)new JsonPrimitive(presets.getName()));
                presetJson.add("data", (JsonElement)new JsonPrimitive(presets.getData()));
                presetJson.add("path", (JsonElement)new JsonPrimitive(presets.getData()));
                presetJson.add("current", (JsonElement)new JsonPrimitive(Boolean.valueOf(presets.isCurrent())));
                mainJsonArray.add((JsonElement)presetJson);
            }
            mainJson.add("presets", (JsonElement)mainJsonArray);
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
            final String pathFolder = "onepop//";
            final String pathFile = pathFolder + "Preset.json";
            if (!Files.exists(Paths.get(pathFile, new String[0]), new LinkOption[0])) {
                return;
            }
            final InputStream file = Files.newInputStream(Paths.get(pathFile, new String[0]), new OpenOption[0]);
            final JsonParser jsonParser = new JsonParser();
            final JsonObject mainJson = jsonParser.parse((Reader)new InputStreamReader(file)).getAsJsonObject();
            if (mainJson.get("presets") == null) {
                file.close();
                return;
            }
            final JsonArray mainJsonArray = mainJson.get("presets").getAsJsonArray();
            for (final JsonElement element : mainJsonArray) {
                final JsonObject presetJson = element.getAsJsonObject();
                if (presetJson.get("name") != null) {
                    if (presetJson.get("path") == null) {
                        continue;
                    }
                    final Preset preset = new Preset(presetJson.get("name").getAsString(), presetJson.get("path").getAsString(), "");
                    if (presetJson.get("current") != null) {
                        preset.setCurrent(presetJson.get("current").getAsBoolean());
                    }
                    if (presetJson.get("data") != null) {
                        preset.setData(presetJson.get("data").getAsString());
                    }
                    this.registry(preset);
                }
            }
            file.close();
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
