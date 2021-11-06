//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.social.management;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.File;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import com.google.gson.GsonBuilder;
import java.util.Iterator;
import com.onepop.api.util.chat.ChatUtil;
import com.onepop.client.module.client.ModuleGeneral;
import com.onepop.api.social.type.SocialType;
import com.onepop.Onepop;
import com.onepop.api.social.Social;
import java.util.ArrayList;
import com.onepop.api.ISLClass;

public class SocialManager implements ISLClass
{
    public static SocialManager INSTANCE;
    private ArrayList<Social> socialList;
    
    public SocialManager() {
        SocialManager.INSTANCE = this;
        this.socialList = new ArrayList<Social>();
    }
    
    public void setSocialList(final ArrayList<Social> socialList) {
        this.socialList = socialList;
    }
    
    public ArrayList<Social> getSocialList() {
        return this.socialList;
    }
    
    public void registry(final Social social) {
        this.socialList.add(social);
        if (Onepop.getMinecraft().world != null && Onepop.getMinecraft().player.connection != null) {
            if (social.getType() == SocialType.FRIEND && ModuleGeneral.settingFriendedMessage.getValue()) {
                ChatUtil.message("/w " + social.getName() + " I just friended you on 1pop client!");
            }
            if (social.getType() == SocialType.ENEMY && ModuleGeneral.settingEnemyMesssage.getValue()) {
                ChatUtil.message("/w " + social.getName() + " Can't friend you!");
            }
        }
    }
    
    public void unregister(final Social social) {
        if (get(social.getClass()) != null) {
            this.socialList.remove(social);
        }
        if (Onepop.getMinecraft().world != null && Onepop.getMinecraft().player.connection != null) {
            if (social.getType() == SocialType.FRIEND && ModuleGeneral.settingFriendedMessage.getValue()) {
                ChatUtil.message("/w " + social.getName() + " I unfriended you!");
            }
            if (social.getType() == SocialType.ENEMY && ModuleGeneral.settingEnemyMesssage.getValue()) {
                ChatUtil.message("/w " + social.getName() + " we can still be friends!");
            }
        }
    }
    
    public static Social get(final Class<?> clazz) {
        for (final Social socials : SocialManager.INSTANCE.getSocialList()) {
            if (socials.getClass() == clazz) {
                return socials;
            }
        }
        return null;
    }
    
    public static Social get(final String name) {
        for (final Social socials : SocialManager.INSTANCE.getSocialList()) {
            if (socials.getName().equalsIgnoreCase(name)) {
                return socials;
            }
        }
        return null;
    }
    
    @Override
    public void onSave() {
        try {
            final String pathFolder = "onepop//";
            final String pathFile = pathFolder + "Social.json";
            final Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
            if (!Files.exists(Paths.get(pathFolder, new String[0]), new LinkOption[0])) {
                Files.createDirectories(Paths.get(pathFolder, new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            }
            if (Files.exists(Paths.get(pathFile, new String[0]), new LinkOption[0])) {
                final File file = new File(pathFile);
                file.delete();
            }
            Files.createFile(Paths.get(pathFile, new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
            final JsonParser jsonParser = new JsonParser();
            final JsonArray mainJson = new JsonArray();
            for (final Social socials : this.socialList) {
                final JsonObject socialJson = new JsonObject();
                socialJson.add("name", (JsonElement)new JsonPrimitive(socials.getName()));
                if (socials.getType() != null) {
                    socialJson.add("type", (JsonElement)new JsonPrimitive(socials.getType().toString()));
                }
                mainJson.add((JsonElement)socialJson);
            }
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
            final String pathFile = pathFolder + "Social.json";
            if (!Files.exists(Paths.get(pathFile, new String[0]), new LinkOption[0])) {
                return;
            }
            final JsonParser jsonParser = new JsonParser();
            final InputStream file = Files.newInputStream(Paths.get(pathFile, new String[0]), new OpenOption[0]);
            final JsonArray mainJson = jsonParser.parse((Reader)new InputStreamReader(file)).getAsJsonArray();
            for (final JsonElement element : mainJson) {
                final JsonObject socialJson = element.getAsJsonObject();
                if (socialJson.get("name") == null) {
                    continue;
                }
                final Social social = new Social(socialJson.get("name").getAsString());
                if (socialJson.get("type") == null) {
                    continue;
                }
                final SocialType enumRequested = (SocialType)TurokClass.getEnumByName(SocialType.UNKNOWN, socialJson.get("type").getAsString());
                social.setType((enumRequested != null) ? enumRequested : SocialType.UNKNOWN);
                Onepop.getSocialManager().registry(social);
            }
            file.close();
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
