//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.manager.world;

import com.onepop.api.util.world.BlockUtil;
import net.minecraft.util.math.Vec3i;
import com.onepop.api.util.world.BlocksUtil;
import com.onepop.api.util.entity.PlayerUtil;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import com.onepop.api.manager.Manager;

public class BlockManager extends Manager
{
    public static BlockManager INSTANCE;
    private final ArrayList<BlockPos> playerSurroundBlockList;
    private World world;
    
    public BlockManager() {
        super("Block Manager", "Manages blocks in world.");
        this.playerSurroundBlockList = new ArrayList<BlockPos>();
        BlockManager.INSTANCE = this;
    }
    
    public ArrayList<BlockPos> getPlayerSurroundBlockList() {
        return this.playerSurroundBlockList;
    }
    
    public static ArrayList<BlockPos> getAirSurroundPlayer() {
        return BlockManager.INSTANCE.getPlayerSurroundBlockList();
    }
    
    @Override
    public void onUpdateAll() {
        this.world = (World)BlockManager.mc.world;
        if (this.world == null || BlockManager.mc.player == null) {
            return;
        }
        this.updateSurroundPlayerList();
    }
    
    public void updateSurroundPlayerList() {
        this.playerSurroundBlockList.clear();
        final BlockPos player = PlayerUtil.getBlockPos();
        for (final BlockPos add : BlocksUtil.FULL_SURROUND) {
            final BlockPos offset = player.add((Vec3i)add);
            final int diffY = offset.getY() - player.getY();
            Label_0161: {
                if (player.down() != offset || !BlockUtil.isAir(offset) || BlockUtil.isAir(offset.down())) {
                    if (diffY == -1) {
                        if (!BlockUtil.isAir(offset.up())) {
                            break Label_0161;
                        }
                        if (BlockUtil.isPlaceableExcludingBlackListAndEntity(offset.up())) {
                            break Label_0161;
                        }
                    }
                    if (diffY != 1 || !BlockUtil.isAir(offset.up())) {
                        if (BlockUtil.isAir(offset) && BlockUtil.isPlaceableExcludingBlackListAndEntity(offset)) {
                            this.playerSurroundBlockList.add(offset);
                        }
                    }
                }
            }
        }
    }
}
