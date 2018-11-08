package org.terasology.FlowerPlugin;


import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizerPlugin;;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;
import java.lang.Math;






@RegisterPlugin
public class FlowerRasterizer implements WorldRasterizerPlugin{
    private Block iris;
    private Block yellowflower;

    @Facet(SurfaceHeightFacet.class)

    //@Requires(SurfaceHeightFacet)


    public static double getRandomNumber(){
        double x = Math.random() * (4 - 1);
        return x;
    }


    @Override
    public void initialize(){
        iris = CoreRegistry.get(BlockManager.class).getBlock("Core:Iris");
        yellowflower = CoreRegistry.get(BlockManager.class).getBlock("Core:YellowFlower");

    }

    @Override
    public void generateChunk (CoreChunk chunk, Region chunkRegion){
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        for (Vector3i position : chunkRegion.getRegion()) {
            float surfaceHeight = surfaceHeightFacet.getWorld(position.x, position.z);
            if (position.y < surfaceHeight + 1 && position.y > surfaceHeight) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), yellowflower);
            }

        }
    }
}
