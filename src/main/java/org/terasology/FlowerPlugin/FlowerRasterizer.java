package org.terasology.FlowerPlugin;


import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;
import java.util.Random;






@RegisterPlugin
public class FlowerRasterizer implements WorldRasterizerPlugin{
    private Block iris;
    private Block yellowflower;
    private Block lavender;
    private Block redflower;

    @Facet(SurfaceHeightFacet.class)

    //@Requires(SurfaceHeightFacet)


    @Override
    public void initialize(){
        iris = CoreRegistry.get(BlockManager.class).getBlock("Core:Iris");
        yellowflower = CoreRegistry.get(BlockManager.class).getBlock("Core:YellowFlower");
        lavender = CoreRegistry.get(BlockManager.class).getBlock("Core:Lavender");
        redflower = CoreRegistry.get(BlockManager.class).getBlock("Core:RedFlower");

    }








    @Override
    public void generateChunk (CoreChunk chunk, Region chunkRegion){
    	Random random = new Random();
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        for (Vector3i position : chunkRegion.getRegion()) {
            float surfaceHeight = surfaceHeightFacet.getWorld(position.x, position.z);
            if (random.nextInt(4) == 0)
            {
            	if(position.y < surfaceHeight + 1 && position.y > surfaceHeight && surfaceHeight < 25){
                    chunk.setBlock(ChunkMath.calcBlockPos(position), lavender);
                }
            }
            else if (random.nextInt(4) == 1)
            {
            	if(position.y < surfaceHeight + 1 && position.y > surfaceHeight && surfaceHeight < 25){
                    chunk.setBlock(ChunkMath.calcBlockPos(position), iris);
                }
            }
            else if (random.nextInt(4) == 2)
            {
            	if(position.y < surfaceHeight + 1 && position.y > surfaceHeight && surfaceHeight < 25){
                    chunk.setBlock(ChunkMath.calcBlockPos(position), yellowflower);
                }
            }
            else if (random.nextInt(4) == 3)
            {
            	if(position.y < surfaceHeight + 1 && position.y > surfaceHeight && surfaceHeight >= 25){
                    chunk.setBlock(ChunkMath.calcBlockPos(position), redflower);
                }
            }
            
        }
    }
}
