package org.terasology.FlowerPlugin;


import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generator.plugin.RegisterPlugin;

import java.util.Map.Entry;


@RegisterPlugin
public class FlowerRasterizer implements WorldRasterizer {
    private Block flower;

    @Override
    public void initialize() {
        flower = CoreRegistry.get(BlockManager.class).getBlock("Core:RedFlower");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        FlowerFacet flowerFacet = chunkRegion.getFacet(FlowerFacet.class);

        for (Entry<BaseVector3i, Flower> entry : flowerFacet.getWorldEntries().entrySet()) {
            // there should be a house here
            // create a couple 3d regions to help iterate through the cube shape, inside and out
            Vector3i centerFlowerPosition = new Vector3i(entry.getKey());
            int extent = entry.getValue().getExtent();
            centerFlowerPosition.add(0, extent, 0);
            Region3i walls = Region3i.createFromCenterExtents(centerFlowerPosition, extent);
            Region3i inside = Region3i.createFromCenterExtents(centerFlowerPosition, extent - 1);

            // loop through each of the positions in the cube, ignoring the inside
            for (Vector3i newBlockPosition : walls) {
                if (chunkRegion.getRegion().encompasses(newBlockPosition)
                        && !inside.encompasses(newBlockPosition)) {
                    chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), flower);
                }
            }
        }
    }
}