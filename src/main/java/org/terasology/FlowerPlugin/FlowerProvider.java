package org.terasology.FlowerPlugin;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector2f;
import org.terasology.utilities.procedural.BrownianNoise;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.PerlinNoise;
import org.terasology.utilities.procedural.SubSampledNoise;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;

@RegisterPlugin
@Produces(SurfaceHeightFacet.class)
public class FlowerProvider implements FacetProvider {

    private Noise flowerNoise;
    private Noise perlinNoise;

    @Override
    public void setSeed(long seed) {
        perlinNoise = new SubSampledNoise(new PerlinNoise(seed), new Vector2f(0.01f, 0.01f), 1);
        flowerNoise = new SubSampledNoise(new BrownianNoise(perlinNoise), new Vector2f(0.01f, 0.01f), 1);
    }

    @Override
    public void process(GeneratingRegion region) {
        // Create our surface height facet (we will get into borders later)
        Border3D border = region.getBorderForFacet(SurfaceHeightFacet.class);
        SurfaceHeightFacet facet = new SurfaceHeightFacet(region.getRegion(), border);

        // loop through every position on our 2d array
        Rect2i processRegion = facet.getWorldRegion();
        for (BaseVector2i position : processRegion.contents()) {
            facet.setWorld(position, flowerNoise.noise(position.x(), position.y()) * 20);
        }

        // give our newly created and populated facet to the region
        region.setRegionFacet(SurfaceHeightFacet.class, facet);
    }
}