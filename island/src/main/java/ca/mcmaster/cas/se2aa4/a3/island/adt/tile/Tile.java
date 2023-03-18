package ca.mcmaster.cas.se2aa4.a3.island.adt.tile;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.adt.profile.WhittakerProfile;

public class Tile {

    /*

    A Tile is composed of a Polygon as well as additional traits:
    - index (represents the index in the input Mesh's Polygon List)
    - altitude (average of its vertices and based on altimetric profile)
    - absorptionCoeff (based on soil absorption profile)
    - hasAquifer (true or false)
    - moisture (represents the outgoing moisture i.e. for a lake or tile with aquifer)
    - absorption (represents absorbed moisture from surroundings)
    - baseType (land or water; land can become water (lake), water cannot become land, only land can have a biome)
    - biome (ocean, lake or land (which has more varying biomes) )

     */

    private final Structs.Polygon foundation;
    private BaseType baseType;
    private Biome biome = Biome.OCEAN; //Ocean by default
    private double altitude = 0;
    private boolean hasAquifer = false;
    private double moisture = 0;
    private double absorption = 0;

    //Constructor
    public Tile(Structs.Polygon foundation, BaseType baseType){
        this.foundation = foundation;
        this.baseType = baseType;
    }

    //Called by default ColorAdder
    public Structs.Property getDefaultColor(){
        return Structs.Property.newBuilder().setKey(biome.getKey()).setValue(biome.getValue()).build();
    }

    /*
    //Called by altitude heatmap ColorAdder
    public Structs.Property getAltitudeColor(){}

    //Called by moisture heatmap ColorAdder
    public Structs.Property getMoistureColor(){}

    //Called by aquifer debug tool ColorAdder
    public Structs.Property getAquiferColor(){}
     */

    public Structs.Polygon getFoundationPolygon() { return foundation; }

    public double getMoisture() {
        return moisture;
    }

    public void setBaseType(BaseType baseType) { this.baseType = baseType; }

    //Called when assigning altimetric profile
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    //Called when creating aquifers
    public void setHasAquifer(boolean hasAquifer) {
        this.hasAquifer = hasAquifer;
    }

    //Called when a Tile is now a Lake or has an aquifer
    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    //Called when assigning soil absorption profile
    public void setAbsorption(double absorption) {
        this.absorption = absorption;
    }

    //Biome is calculated based on the baseType and the altitude, absorption and WhittakerProfile if it is a Land Tile
    public void calculateBiome(WhittakerProfile profile){
        biome = this.baseType.calculateBiome(altitude, absorption, profile);
    }

}