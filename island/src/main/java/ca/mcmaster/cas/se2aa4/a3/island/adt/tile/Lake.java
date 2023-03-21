package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import ca.mcmaster.cas.se2aa4.a3.island.adt.profile.WhittakerProfile;

public class Lake implements BaseType{
    @Override
    public Biome calculateBiome(double altitude, double absorption, WhittakerProfile profile) { return Biome.LAKE; }

    @Override
    public boolean isLand() {
        return false;
    }
}