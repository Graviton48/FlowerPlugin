package org.terasology.FlowerPlugin;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.SparseObjectFacet3D;

public class FlowerFacet extends SparseObjectFacet3D<Flower> {

    public FlowerFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}
