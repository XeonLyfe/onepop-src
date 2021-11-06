//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import java.util.Iterator;
import java.util.function.Function;
import java.util.Comparator;
import com.onepop.client.component.ComponentWelcome;
import com.onepop.client.component.ComponentWatermark;
import com.onepop.client.component.ComponentTPS;
import com.onepop.client.component.ComponentTotemCount;
import com.onepop.client.component.ComponentPing;
import com.onepop.client.component.ComponentInventory;
import com.onepop.client.component.ComponentGoldenAppleCount;
import com.onepop.client.component.ComponentFPS;
import com.onepop.client.component.ComponentCrystalCount;
import com.onepop.client.component.ComponentCoordinates;
import com.onepop.client.component.ComponentArrayList;
import com.onepop.api.component.Component;
import com.onepop.client.component.ComponentArmor;
import com.onepop.client.command.CommandSettings;
import com.onepop.client.command.CommandVanish;
import com.onepop.client.command.CommandSocial;
import com.onepop.client.command.CommandCoords;
import com.onepop.client.command.CommandToggle;
import com.onepop.api.command.Command;
import com.onepop.client.command.CommandPrefix;
import com.onepop.client.module.combat.ModuleBurrow;
import com.onepop.client.module.exploit.ModuleCancelPackets;
import com.onepop.client.module.exploit.ModuleExtraSlots;
import com.onepop.client.module.exploit.ModuleNoServerRotate;
import com.onepop.client.module.misc.ModuleFastUse;
import com.onepop.client.module.misc.ModuleBlink;
import com.onepop.client.module.misc.ModuleBuildHeight;
import com.onepop.client.module.misc.ModuleSearch;
import com.onepop.client.module.misc.ModuleEntityControl;
import com.onepop.client.module.misc.ModuleNegro;
import com.onepop.client.module.misc.ModuleNoEntityTrace;
import com.onepop.client.module.misc.ModuleAutoEat;
import com.onepop.client.module.misc.ModuleTimer;
import com.onepop.client.module.misc.ModuleAntiAFK;
import com.onepop.client.module.misc.ModuleSpammer;
import com.onepop.client.module.misc.ModuleChatSuffix;
import com.onepop.client.module.misc.ModuleAutoFish;
import com.onepop.client.module.misc.ModuleAutoRespawn;
import com.onepop.client.module.misc.ModuleMultitask;
import com.onepop.client.module.player.ModuleFreecam;
import com.onepop.client.module.misc.ModuleMiddleClick;
import com.onepop.client.module.misc.ModuleArmorAlert;
import com.onepop.client.module.misc.ModuleAntiHunger;
import com.onepop.client.module.misc.ModulePortalGUI;
import com.onepop.client.module.misc.ModuleAutoMine;
import com.onepop.client.module.misc.ModuleAutoKillMessage;
import com.onepop.client.module.player.ModuleLongJump;
import com.onepop.client.module.player.ModuleSprint;
import com.onepop.client.module.player.ModuleNoFall;
import com.onepop.client.module.player.ModuleNoSlowDown;
import com.onepop.client.module.player.ModuleStrafe;
import com.onepop.client.module.player.ModuleReverseStep;
import com.onepop.client.module.player.ModuleStep;
import com.onepop.client.module.player.ModuleVelocity;
import com.onepop.client.module.player.ModuleInventoryWalk;
import com.onepop.client.module.player.ModuleAutoWalk;
import com.onepop.client.module.player.ModuleSafeWalk;
import com.onepop.client.module.player.ModuleCreativeFly;
import com.onepop.client.module.player.ModuleElytraFlight;
import com.onepop.client.module.player.ModuleBetterMine;
import com.onepop.client.module.render.ModuleStorageESP;
import com.onepop.client.module.render.ModuleBurrowESP;
import com.onepop.client.module.render.ModuleNoRender;
import com.onepop.client.module.render.ModulePlayerESP;
import com.onepop.client.module.render.ModuleSkeletonESP;
import com.onepop.client.module.render.ModuleNameTags;
import com.onepop.client.module.render.ModuleCustomCamera;
import com.onepop.client.module.render.ModuleFullBright;
import com.onepop.client.module.render.ModuleHoleESP;
import com.onepop.client.module.render.ModuleBlockHighlight;
import com.onepop.client.module.combat.ModuleSelfTrap;
import com.onepop.client.module.combat.ModuleSelfWeb;
import com.onepop.client.module.combat.ModuleAutoWeb;
import com.onepop.client.module.combat.ModuleBedAura;
import com.onepop.client.module.combat.ModuleAutoLog;
import com.onepop.client.module.combat.ModuleHoleFiller;
import com.onepop.client.module.combat.ModuleCritical;
import com.onepop.client.module.combat.ModuleKillAura;
import com.onepop.client.module.combat.ModuleSurround;
import com.onepop.client.module.combat.ModuleQuiver;
import com.onepop.client.module.combat.ModuleAutoArmour;
import com.onepop.client.module.combat.ModuleOffhand;
import com.onepop.client.module.combat.ModuleAutoCrystal;
import com.onepop.client.module.combat.ModuleAutoTrap;
import com.onepop.client.module.client.ModuleGeneral;
import com.onepop.client.module.client.ModuleRPC;
import com.onepop.client.module.client.ModuleAntiCheat;
import com.onepop.client.module.client.ModuleTPSSync;
import com.onepop.client.module.client.ModuleDeveloper;
import com.onepop.client.module.client.ModuleHUD;
import com.onepop.api.module.Module;
import team.stiff.pomelo.impl.annotated.AnnotatedEventManager;
import com.onepop.client.rpc.RPC;
import com.onepop.client.Wrapper;
import com.onepop.client.gui.overlay.ComponentClickGUI;
import com.onepop.client.gui.module.ModuleClickGUI;
import com.onepop.client.manager.network.TPSManager;
import com.onepop.client.manager.network.CurrentItemPacketManager;
import com.onepop.client.manager.world.BreakManager;
import com.onepop.client.manager.world.BlockManager;
import com.onepop.client.manager.network.RotationManager;
import com.onepop.client.manager.world.HoleManager;
import com.onepop.client.manager.entity.EntityWorldManager;
import com.onepop.client.manager.network.PlayerServerManager;
import com.onepop.client.manager.chat.SpammerManager;
import com.onepop.api.component.management.ComponentManager;
import com.onepop.api.preset.management.PresetManager;
import com.onepop.api.social.management.SocialManager;
import com.onepop.api.command.management.CommandManager;
import com.onepop.api.module.management.ModuleManager;
import com.onepop.api.tracker.management.TrackerManager;
import team.stiff.pomelo.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "onepop", name = "1pop", version = "2.0beta")
public class Onepop
{
    @Mod.Instance
    public static Onepop INSTANCE;
    public static final String NAME = "1pop";
    public static final String VERSION = "2.0beta";
    public static final String PATH_CONFIG = "onepop/";
    public static final String CHAT;
    public static final Minecraft MC;
    private EventManager pomeloEventManager;
    private TrackerManager trackerManager;
    private ModuleManager moduleManager;
    private com.onepop.api.event.management.EventManager clientEventManager;
    private CommandManager commandManager;
    private SocialManager socialManager;
    private PresetManager presetManager;
    private ComponentManager componentManager;
    private SpammerManager spammerManager;
    private PlayerServerManager playerServerManager;
    private EntityWorldManager entityWorldManager;
    private HoleManager holeManager;
    private RotationManager rotationManager;
    private BlockManager blockManager;
    private BreakManager breakManager;
    private CurrentItemPacketManager currentItemPacketManager;
    private TPSManager tpsManager;
    private ModuleClickGUI moduleClickGUI;
    private ComponentClickGUI componentClickGUI;
    private Wrapper wrapper;
    private RPC discordRPC;
    
    public Onepop() {
        this.pomeloEventManager = new AnnotatedEventManager();
    }
    
    public void onRegistry() {
        this.pomeloEventManager.addEventListener(this.rotationManager);
        this.pomeloEventManager.addEventListener(this.breakManager);
        this.pomeloEventManager.addEventListener(this.tpsManager);
        this.moduleManager.registry(new com.onepop.client.module.client.ModuleClickGUI());
        this.moduleManager.registry(new ModuleHUD());
        this.moduleManager.registry(new ModuleDeveloper());
        this.moduleManager.registry(new ModuleTPSSync());
        this.moduleManager.registry(new ModuleAntiCheat());
        this.moduleManager.registry(new ModuleRPC());
        this.moduleManager.registry(new ModuleGeneral());
        this.moduleManager.registry(new ModuleAutoTrap());
        this.moduleManager.registry(new ModuleAutoCrystal());
        this.moduleManager.registry(new ModuleOffhand());
        this.moduleManager.registry(new ModuleAutoArmour());
        this.moduleManager.registry(new ModuleQuiver());
        this.moduleManager.registry(new ModuleSurround());
        this.moduleManager.registry(new ModuleKillAura());
        this.moduleManager.registry(new ModuleCritical());
        this.moduleManager.registry(new ModuleHoleFiller());
        this.moduleManager.registry(new ModuleAutoLog());
        this.moduleManager.registry(new ModuleBedAura());
        this.moduleManager.registry(new ModuleAutoWeb());
        this.moduleManager.registry(new ModuleSelfWeb());
        this.moduleManager.registry(new ModuleSelfTrap());
        this.moduleManager.registry(new ModuleBlockHighlight());
        this.moduleManager.registry(new ModuleHoleESP());
        this.moduleManager.registry(new ModuleFullBright());
        this.moduleManager.registry(new ModuleCustomCamera());
        this.moduleManager.registry(new ModuleNameTags());
        this.moduleManager.registry(new ModuleSkeletonESP());
        this.moduleManager.registry(new ModulePlayerESP());
        this.moduleManager.registry(new ModuleNoRender());
        this.moduleManager.registry(new ModuleBurrowESP());
        this.moduleManager.registry(new ModuleStorageESP());
        this.moduleManager.registry(new ModuleBetterMine());
        this.moduleManager.registry(new ModuleElytraFlight());
        this.moduleManager.registry(new ModuleCreativeFly());
        this.moduleManager.registry(new ModuleSafeWalk());
        this.moduleManager.registry(new ModuleAutoWalk());
        this.moduleManager.registry(new ModuleInventoryWalk());
        this.moduleManager.registry(new ModuleVelocity());
        this.moduleManager.registry(new ModuleStep());
        this.moduleManager.registry(new ModuleReverseStep());
        this.moduleManager.registry(new ModuleStrafe());
        this.moduleManager.registry(new ModuleNoSlowDown());
        this.moduleManager.registry(new ModuleNoFall());
        this.moduleManager.registry(new ModuleSprint());
        this.moduleManager.registry(new ModuleLongJump());
        this.moduleManager.registry(new ModuleAutoKillMessage());
        this.moduleManager.registry(new ModuleAutoMine());
        this.moduleManager.registry(new ModulePortalGUI());
        this.moduleManager.registry(new ModuleAntiHunger());
        this.moduleManager.registry(new ModuleArmorAlert());
        this.moduleManager.registry(new ModuleMiddleClick());
        this.moduleManager.registry(new ModuleFreecam());
        this.moduleManager.registry(new ModuleMultitask());
        this.moduleManager.registry(new ModuleAutoRespawn());
        this.moduleManager.registry(new ModuleAutoFish());
        this.moduleManager.registry(new ModuleChatSuffix());
        this.moduleManager.registry(new ModuleSpammer());
        this.moduleManager.registry(new ModuleAntiAFK());
        this.moduleManager.registry(new ModuleTimer());
        this.moduleManager.registry(new ModuleAutoEat());
        this.moduleManager.registry(new ModuleNoEntityTrace());
        this.moduleManager.registry(new ModuleNegro());
        this.moduleManager.registry(new ModuleEntityControl());
        this.moduleManager.registry(new ModuleSearch());
        this.moduleManager.registry(new ModuleBuildHeight());
        this.moduleManager.registry(new ModuleBlink());
        this.moduleManager.registry(new ModuleFastUse());
        this.moduleManager.registry(new ModuleNoServerRotate());
        this.moduleManager.registry(new ModuleExtraSlots());
        this.moduleManager.registry(new ModuleCancelPackets());
        this.moduleManager.registry(new ModuleBurrow());
        this.commandManager.registry(new CommandPrefix());
        this.commandManager.registry(new CommandToggle());
        this.commandManager.registry(new CommandCoords());
        this.commandManager.registry(new CommandSocial());
        this.commandManager.registry(new CommandVanish());
        this.commandManager.registry(new CommandSettings());
        this.componentManager.registry(new ComponentArmor());
        this.componentManager.registry(new ComponentArrayList());
        this.componentManager.registry(new ComponentCoordinates());
        this.componentManager.registry(new ComponentCrystalCount());
        this.componentManager.registry(new ComponentFPS());
        this.componentManager.registry(new ComponentGoldenAppleCount());
        this.componentManager.registry(new ComponentInventory());
        this.componentManager.registry(new ComponentPing());
        this.componentManager.registry(new ComponentTotemCount());
        this.componentManager.registry(new ComponentTPS());
        this.componentManager.registry(new ComponentWatermark());
        this.componentManager.registry(new ComponentWelcome());
        this.moduleManager.getModuleList().sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getName));
        this.componentManager.getComponentList().sort(Comparator.comparing((Function<? super Component, ? extends Comparable>)Component::getName));
    }
    
    public void onInitClient() {
        (this.moduleClickGUI = new ModuleClickGUI()).init();
        (this.componentClickGUI = new ComponentClickGUI()).init();
        PresetManager.reload();
    }
    
    public static void shutdown() {
        for (final Module modules : Onepop.INSTANCE.moduleManager.getModuleList()) {
            modules.onShutdown();
        }
        PresetManager.shutdown();
    }
    
    @Mod.EventHandler
    public void onClientStarted(final FMLInitializationEvent event) {
        this.trackerManager = new TrackerManager();
        this.moduleManager = new ModuleManager();
        this.clientEventManager = new com.onepop.api.event.management.EventManager();
        this.commandManager = new CommandManager();
        this.socialManager = new SocialManager();
        this.presetManager = new PresetManager();
        this.componentManager = new ComponentManager();
        this.spammerManager = new SpammerManager();
        this.playerServerManager = new PlayerServerManager();
        this.entityWorldManager = new EntityWorldManager();
        this.holeManager = new HoleManager();
        this.rotationManager = new RotationManager();
        this.blockManager = new BlockManager();
        this.breakManager = new BreakManager();
        this.discordRPC = new RPC();
        this.currentItemPacketManager = new CurrentItemPacketManager();
        this.tpsManager = new TPSManager();
        this.wrapper = new Wrapper();
        MinecraftForge.EVENT_BUS.register((Object)this.clientEventManager);
        MinecraftForge.EVENT_BUS.register((Object)this.commandManager);
        this.onRegistry();
        this.onInitClient();
        if (ModuleRPC.INSTANCE.isEnabled()) {
            this.discordRPC.run();
        }
        Runtime.getRuntime().addShutdownHook(new Thread("Nigger Shutdown Hook") {
            @Override
            public void run() {
                Onepop.shutdown();
            }
        });
    }
    
    public static EventManager getPomeloEventManager() {
        return Onepop.INSTANCE.pomeloEventManager;
    }
    
    public static TrackerManager getTrackerManager() {
        return Onepop.INSTANCE.trackerManager;
    }
    
    public static ModuleManager getModuleManager() {
        return Onepop.INSTANCE.moduleManager;
    }
    
    public static com.onepop.api.event.management.EventManager getClientEventManager() {
        return Onepop.INSTANCE.clientEventManager;
    }
    
    public static CommandManager getCommandManager() {
        return Onepop.INSTANCE.commandManager;
    }
    
    public static SocialManager getSocialManager() {
        return Onepop.INSTANCE.socialManager;
    }
    
    public static PresetManager getPresetManager() {
        return Onepop.INSTANCE.presetManager;
    }
    
    public static ComponentManager getComponentManager() {
        return Onepop.INSTANCE.componentManager;
    }
    
    public static ModuleClickGUI getModuleClick() {
        return Onepop.INSTANCE.moduleClickGUI;
    }
    
    public static ComponentClickGUI getComponentClickGUI() {
        return Onepop.INSTANCE.componentClickGUI;
    }
    
    public static SpammerManager getSpammerManager() {
        return Onepop.INSTANCE.spammerManager;
    }
    
    public static PlayerServerManager getPlayerServerManager() {
        return Onepop.INSTANCE.playerServerManager;
    }
    
    public static EntityWorldManager getEntityWorldManager() {
        return Onepop.INSTANCE.entityWorldManager;
    }
    
    public static HoleManager getHoleManager() {
        return Onepop.INSTANCE.holeManager;
    }
    
    public static RotationManager getRotationManager() {
        return Onepop.INSTANCE.rotationManager;
    }
    
    public static BlockManager getBlockManager() {
        return Onepop.INSTANCE.blockManager;
    }
    
    public static BreakManager getBreakManager() {
        return Onepop.INSTANCE.breakManager;
    }
    
    public static RPC getRPC() {
        return Onepop.INSTANCE.discordRPC;
    }
    
    public static CurrentItemPacketManager getCurrentItemPacketManager() {
        return Onepop.INSTANCE.currentItemPacketManager;
    }
    
    public static TPSManager getTPSManager() {
        return Onepop.INSTANCE.tpsManager;
    }
    
    public static Wrapper getWrapper() {
        return Onepop.INSTANCE.wrapper;
    }
    
    public static Minecraft getMinecraft() {
        return Onepop.MC;
    }
    
    static {
        CHAT = ChatFormatting.GRAY + "1pop" + " " + ChatFormatting.WHITE;
        MC = Minecraft.getMinecraft();
    }
}
