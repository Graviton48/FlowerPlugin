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
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generation.facets.SeaLevelFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;

import java.util.Map.Entry;
//import java.util.Random;


@RegisterPlugin
public class FlowerRasterizer implements WorldRasterizerPlugin {
    private Block redflower;
    private Block yellowflower;
    private Block irisflower;
    private Block lavenderflower;

    @Override
    public void initialize() {
        redflower = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        yellowflower = CoreRegistry.get(BlockManager.class).getBlock("Core:YellowFlower");
        irisflower = CoreRegistry.get(BlockManager.class).getBlock("Core:Iris");
        lavenderflower = CoreRegistry.get(BlockManager.class).getBlock("Core:Lavender");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        FlowerFacet flowerFacet = chunkRegion.getFacet(FlowerFacet.class);

        for (Entry<BaseVector3i, Flower> entry : flowerFacet.getWorldEntries().entrySet()) {
            // there should be a house here
            // create a couple 3d regions to help iterate through the cube shape, inside and out
            BaseVector3i min = entry.getKey();
            BaseVector3i size = entry.getKey();
            Region3i tower = Region3i.createFromMinAndSize(min, size);
            // loop through each of the positions in the cube, ignoring the inside
            for (Vector3i newBlockPosition : tower) {
                SeaLevelFacet seaLevelFacet = chunkRegion.getFacet(SeaLevelFacet.class);
                int seaLevel = seaLevelFacet.getSeaLevel();
                if(newBlockPosition.y > seaLevel - 1) {
                    if (newBlockPosition.y > 120) {
                        if (chunkRegion.getRegion().encompasses(newBlockPosition)) {
                            chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), yellowflower);
                        }
                    }
                    else if (newBlockPosition.y <= 120 && newBlockPosition.y > 80) {
                        if (chunkRegion.getRegion().encompasses(newBlockPosition)) {
                            chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), redflower);
                        }
                    }
                    else if (newBlockPosition.y <= 80 && newBlockPosition.y > 40) {
                        if (chunkRegion.getRegion().encompasses(newBlockPosition)) {
                            chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), irisflower);
                        }
                    }
                    else if (newBlockPosition.y <= 40 && newBlockPosition.y > 0) {
                        if (chunkRegion.getRegion().encompasses(newBlockPosition)) {
                            chunk.setBlock(ChunkMath.calcBlockPos(newBlockPosition), lavenderflower);
                        }
                    }
                }
            }
        }
    }
}