package src;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

// ***********************************************************
// For this project, you need to write a “problem generator”
// whereby you will create several graphs of various sizes
// to solve instances of the graph-coloring problem. For
// your graph generator, scatter n points on the unit square
// (where n will be provided as input), select some point X
// at random and connect X by a straight line to the nearest
// point Y such that X is not already connected to Y and line
// crosses no other line. Repeat the previous step until no
// more connections are possible. The points represent
// regions on the map, and the lines connect neighbors.
// ***********************************************************

/**
 * The {@code Graph} class generates an uncolored graph
 * and connects vertices to their closes neighbor at
 * random until no more connections are possible.
 *
 * @author Lance Inman
 * @version 0.1.0
 * @since 2016-09-05
 */
public class Graph {
    ArrayList<Vertex> vertices;
    final int unitSize = 100;

    public Graph(int size) {
        vertices = new ArrayList<>();
        populateGraph(size);
        connectGraph();
    }

    private void populateGraph(int n) {
        Random rnd = new Random();
        for(int i = 0; i < n; i++) {
            Vertex v = new Vertex(rnd.nextInt(unitSize), rnd.nextInt(unitSize));
            vertices.add(v);
        }
    }

    private double distance(Vertex v1, Vertex v2) {
        return Math.sqrt(Math.pow((v1.getX() - v2.getX()), 2) + Math.pow((v1.getY() - v2.getY()), 2));
    }

    private boolean connect(Vertex v1, Vertex v2) {
        if((v1 != v2) && (!intersects(v1, v2))) {
            v1.neighbors.add(v2);
            v2.neighbors.add(v1);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    private void connectGraph() {
        Random rnd = new Random();
        ArrayList<Vertex> unconnected = (ArrayList<Vertex>) vertices.clone();
        Vertex v1 = unconnected.get(0);
        Vertex v2;
        double minDistance = 0;
        boolean done = false;

        while(unconnected.size() > 1) {
            try {
                v1 = unconnected.get(rnd.nextInt(unconnected.size()));
                v2 = closestVertex(v1, minDistance);
                connect(v1, v2);
                minDistance = distance(v1, v2);
            } catch (NullPointerException npe) {
                unconnected.remove(v1);
                minDistance = 0;
            }
        }
    }

    private Vertex closestVertex(Vertex v1, double minDistance) throws NullPointerException {
        double shortestDistance = vertices.size() * vertices.size();
        double distance;
        Vertex closestVertex = null;
        for(Vertex v2: vertices) {
            distance = distance(v1, v2);
            if((distance(v1, v2) > minDistance) && (distance < shortestDistance) && (v1.getNeighbors().contains(v2) == false)) {
                shortestDistance = distance;
                closestVertex = v2;
            }
        }

        if(closestVertex != null) {
            return closestVertex;
        } else {
            throw new NullPointerException();
        }
    }

    /** Checks if a line segment from v1 to v2 would
     * intersect an existing connection in the graph.
     *
     * @param v1 the first vertex in the connection
     * @param v2 the second vertex in the connection
     * @return Return true if a line segment from v1 to v2 would
     * intersect an existing connection in g, false otherwise.
     */
    private boolean intersects(Vertex v1, Vertex v2) {
        for(Vertex v3: vertices) {
            if(v1 != v3 && v2 != v3) {
                for(Vertex v4: v3.neighbors) {
                    if((v1 != v4 && v2 != v4) &&
                            Line2D.linesIntersect(
                            v1.getX(), v1.getY(),
                            v2.getX(), v2.getY(),
                            v3.getX(), v3.getY(),
                            v4.getX(), v4.getY())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
