package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();
        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX(x).setY(y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY(y).build());
                vertices.add(Vertex.newBuilder().setX(x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
            }
        }


        // Distribute colors randomly. Vertices are immutable, need to enrich them
        List<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        // Creating segments
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i< vertices.size()-1; i++) {
            Vertex v1 = verticesWithColors.get(i);
            Vertex v2 = verticesWithColors.get(i + 1);

            //Extracting color value from vertices
            String[] rgb_string = v1.getProperties(0).getValue().split(",");
            int red1 = Integer.parseInt(rgb_string[0]);
            int green1 = Integer.parseInt(rgb_string[1]);
            int blue1 = Integer.parseInt(rgb_string[2]);
            rgb_string = v2.getProperties(0).getValue().split(",");
            int red2 = Integer.parseInt(rgb_string[0]);
            int green2 = Integer.parseInt(rgb_string[1]);
            int blue2 = Integer.parseInt(rgb_string[2]);

            //Avg colours
            int red_avg = (red1 + red2 / 2);
            int green_avg = (green1 + green2 / 2);
            int blue_avg = (blue1 + blue2 / 2);
            if (red_avg > 255) { red_avg = 255; }
            if (green_avg > 255) { green_avg = 255; }
            if (blue_avg > 255) { blue_avg = 255; }

            //Create new colorCode
            String colorCode = red_avg + "," + green_avg + "," + blue_avg;
            Property avg_color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            segments.add(Structs.Segment.newBuilder().setV1Idx(i).setV2Idx(i + 1).addProperties(avg_color).build());
        }

        for (Segment s : segments){
            System.out.println(s);
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

}
