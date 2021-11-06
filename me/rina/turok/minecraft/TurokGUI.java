//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.minecraft;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import me.rina.turok.render.opengl.TurokGL;
import me.rina.turok.util.TurokClass;
import me.rina.turok.render.opengl.TurokShaderGL;
import net.minecraft.client.Minecraft;
import me.rina.turok.util.TurokDisplay;
import me.rina.turok.hardware.mouse.TurokMouse;
import net.minecraft.client.gui.GuiScreen;

@GUI(name = "HDU editor")
public class TurokGUI extends GuiScreen
{
    private final String name;
    private final String author;
    protected TurokMouse mouse;
    protected TurokDisplay display;
    protected float partialTicks;
    
    public TurokGUI() {
        this.name = this.get().name();
        this.author = this.get().author();
        this.mouse = new TurokMouse();
        TurokShaderGL.init(this.display = new TurokDisplay(Minecraft.getMinecraft()), this.mouse);
        this.init();
    }
    
    public GUI get() {
        if (TurokClass.isAnnotationPreset(this.getClass(), GUI.class)) {
            return this.getClass().getAnnotation(GUI.class);
        }
        return null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setMouse(final TurokMouse mouse) {
        this.mouse = mouse;
    }
    
    public TurokMouse getMouse() {
        return this.mouse;
    }
    
    public void setDisplay(final TurokDisplay display) {
        this.display = display;
    }
    
    public TurokDisplay getDisplay() {
        return this.display;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    public void init() {
    }
    
    public void closeGUI() {
        this.onClose();
        this.mc.setIngameFocus();
        this.mc.displayGuiScreen((GuiScreen)null);
    }
    
    public boolean pauseGameWhenActive() {
        return false;
    }
    
    public void onOpen() {
    }
    
    public void onClose() {
    }
    
    public void onKeyboard(final char character, final int key) {
    }
    
    public void onMouseClicked(final int button) {
    }
    
    public void onMouseReleased(final int button) {
    }
    
    public void onRender() {
    }
    
    public boolean doesGuiPauseGame() {
        return this.pauseGameWhenActive();
    }
    
    public void initGui() {
        this.onOpen();
    }
    
    public void keyTyped(final char charCode, final int keyCode) {
        this.onKeyboard(charCode, keyCode);
    }
    
    public void mouseClicked(final int mousePositionX, final int mousePositionY, final int mouseButtonUp) {
        this.onMouseClicked(mouseButtonUp);
    }
    
    public void mouseReleased(final int mousePositionX, final int mousePositionY, final int mouseButtonDown) {
        this.onMouseReleased(mouseButtonDown);
    }
    
    public void drawScreen(final int mousePositionX, final int mousePositionY, final float partialTicks) {
        TurokShaderGL.init(this.display = new TurokDisplay(Minecraft.getMinecraft()), this.mouse);
        this.mouse.setPos(mousePositionX, mousePositionY);
        this.partialTicks = partialTicks;
        TurokGL.pushMatrix();
        TurokGL.translate((float)this.display.getWidth(), (float)this.display.getHeight());
        TurokGL.scale(0.5f, 0.5f, 0.5f);
        TurokGL.popMatrix();
        TurokGL.disable(3553);
        this.onRender();
        TurokGL.enable(3553);
        TurokGL.disable(3553);
        TurokGL.disable(3042);
        TurokGL.enable(3553);
        TurokGL.color(255.0f, 255.0f, 255.0f);
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GUI {
        String name() default "Random";
        
        String author() default "Random";
    }
}
