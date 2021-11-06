//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.world;

import com.onepop.api.util.math.PositionUtil;
import java.util.ArrayList;
import com.onepop.Onepop;
import java.util.List;
import net.minecraft.util.math.BlockPos;

public class BlocksUtil
{
    public static final BlockPos[] BURROW;
    public static final BlockPos[] SURROUND;
    public static final BlockPos[] FULL_SURROUND;
    
    public static List<BlockPos> getSphereList(final float range) {
        final BlockPos selfPos = new BlockPos(Onepop.MC.player.posX, Onepop.MC.player.posY, Onepop.MC.player.posZ);
        final List<BlockPos> sphereList = new ArrayList<BlockPos>();
        for (int x = (int)(selfPos.x - range); x <= selfPos.x + range; ++x) {
            for (int z = (int)(selfPos.z - range); z <= selfPos.z + range; ++z) {
                for (int y = (int)(selfPos.y - range); y <= selfPos.y + range; ++y) {
                    final double dist = (selfPos.x - x) * (selfPos.x - x) + (selfPos.z - z) * (selfPos.z - z) + (selfPos.y - y) * (selfPos.y - y);
                    if (dist < range * range) {
                        sphereList.add(new BlockPos(x, y, z));
                    }
                }
            }
        }
        return sphereList;
    }
    
    public static List<BlockPos> getSphereList(final BlockPos blockPos, final float r, final int h, final boolean hollow, final boolean sphere) {
        final List<BlockPos> sphereList = new ArrayList<BlockPos>();
        final int cx = blockPos.x;
        final int cy = blockPos.y;
        final int cz = blockPos.z;
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos spheres = new BlockPos(x, y, z);
                        sphereList.add(spheres);
                    }
                }
            }
        }
        return sphereList;
    }
    
    public static boolean contains(final BlockPos position, final BlockPos[] list) {
        for (final BlockPos positions : list) {
            if (PositionUtil.collideBlockPos(positions, position)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        BURROW = new BlockPos[] { new BlockPos(0, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1) };
        SURROUND = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1) };
        FULL_SURROUND = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(1, -1, 0), new BlockPos(0, -1, 1), new BlockPos(-1, -1, 0), new BlockPos(0, -1, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1) };
    }
}
