//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.client.manager.world;

import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3i;
import com.onepop.Onepop;
import com.onepop.api.util.world.BlockUtil;
import com.onepop.api.util.world.BlocksUtil;
import com.onepop.api.util.entity.PlayerUtil;
import me.rina.turok.util.TurokMath;
import com.onepop.api.util.client.NullUtil;
import java.util.Iterator;
import com.onepop.api.util.math.PositionUtil;
import net.minecraft.util.math.BlockPos;
import java.util.Comparator;
import java.util.ArrayList;
import com.onepop.api.manager.Manager;

public class HoleManager extends Manager
{
    public static final int UNSAFE = 0;
    public static final int SAFE = 1;
    private ArrayList<Hole> holeList;
    private float range;
    
    public HoleManager() {
        super("Hole Manager", "Client calculate holes and place in a list.");
        this.range = 10.0f;
        this.holeList = new ArrayList<Hole>();
    }
    
    public void setHoleList(final ArrayList<Hole> holeList) {
        this.holeList = holeList;
    }
    
    public ArrayList<Hole> getHoleList() {
        this.holeList.sort(Comparator.comparingDouble(hole -> HoleManager.mc.player.getDistanceSq(hole.getPosition())));
        return this.holeList;
    }
    
    public Hole getHole(final BlockPos position) {
        for (final Hole holes : this.holeList) {
            if (PositionUtil.collideBlockPos(holes.getPosition(), position)) {
                return holes;
            }
        }
        return null;
    }
    
    public boolean isHole(final BlockPos position) {
        return this.getHole(position) != null;
    }
    
    public void setRange(final float range) {
        this.range = range;
    }
    
    public float getRange() {
        return this.range;
    }
    
    @Override
    public void onUpdateAll() {
        if (NullUtil.isPlayerWorld()) {
            return;
        }
        this.holeList.clear();
        final int r = TurokMath.ceiling(this.range);
        final List<BlockPos> sphereList = BlocksUtil.getSphereList(PlayerUtil.getBlockPos(), (float)r, r, false, true);
        for (final BlockPos blocks : sphereList) {
            if (!BlockUtil.isAir(blocks)) {
                continue;
            }
            if (!BlockUtil.isAir(blocks.add(0, 1, 0))) {
                continue;
            }
            if (!BlockUtil.isAir(blocks.add(0, 2, 0))) {
                continue;
            }
            boolean isHole = true;
            int countBedrock = 0;
            for (final BlockPos _blocks : BlocksUtil.SURROUND) {
                final Block block = Onepop.MC.world.getBlockState(blocks.add((Vec3i)_blocks)).getBlock();
                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    isHole = false;
                    break;
                }
                if (block == Blocks.BEDROCK) {
                    ++countBedrock;
                }
            }
            if (!isHole) {
                continue;
            }
            this.holeList.add(new Hole(blocks, (int)((countBedrock == 5) ? 1 : 0)));
        }
    }
    
    public static class Hole
    {
        public final BlockPos position;
        private final int type;
        
        public Hole(final BlockPos position, final int type) {
            this.position = position;
            this.type = type;
        }
        
        public BlockPos getPosition() {
            return this.position;
        }
        
        public int getType() {
            return this.type;
        }
    }
}
