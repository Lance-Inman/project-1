package src;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/************************************************************
 * For this project, you need to write a “problem generator”
 * whereby you will create several graphs of varioussizes
 * to solve instances of the graph-coloring problem. For
 * your graph generator, scatter n points on the unit square
 * (where n will be provided as input), select some point X
 * at random and connectXby a straight lineto the nearest
 * point Y such thatXis not already connected to Y and line
 * crosses no other line. Repeat the previous step until no
 * more connections are possible. The points represent
 * regions on the map, and the lines connect neighbors.
*************************************************************/

/**
 * The {@code ProblemGenerator} class
 *
 * @author Lance Inman
 * @version 0.1.0
 * @since 2016-09-05
 */
public class ProblemGenerator {
    final int graphSize = 100;
    ArrayList<Vertex> graph = new ArrayList<>();

    private class Vertex {
        int x;
        int y;
        ArrayList<Vertex> neighbors;

        public Vertex(int x, int y) {
            this.x = x;
            this.y = y;
            neighbors = new ArrayList<>();
        }
    }

    public static void main (String[]args) {
        Scanner keyboard = new Scanner(System.in);
        String input = "h";
        System.out.println("Welcome");

        while(input.equalsIgnoreCase("q")) {
            if(input.equalsIgnoreCase("h")) {

            } else {

            }
        }

        System.exit(0);
    }

    /**
     * Generates n vertices at random x and y values within g and adds
     * each vertex to g.
     *
     * @param g the graph of vertices
     * @param n the number of vertices to be generated
     */
    public void generatePoints(ArrayList<Vertex> g, int n) {
        Random rnd = new Random();
        for(int i = 0; i < n; i++) {
            Vertex v = new Vertex(rnd.nextInt(graphSize), rnd.nextInt(graphSize));
            g.add(v);
        }
    }

    /**
     * Generates n connection between two random vertices in g given the line
     * segment between v1 and v2 does not intersect an existing connection.
     * Neighbor lists in v1 and v2 will be updated to store the new connection.
     *
     * @param g the graph of vertices
     * @param n the number of connections to be generated
     */
    public void generateConnections(ArrayList<Vertex> g, int n) {
        Random rnd = new Random();

        int connectionsMade = 0;
        while(connectionsMade < n) {
            Vertex v1 = g.get(rnd.nextInt(g.size()));
            Vertex v2 = g.get(rnd.nextInt(g.size()));

            if(intersects(g, v1, v2) == false) {
                v1.neighbors.add(v2);
                v2.neighbors.add(v1);
                connectionsMade++;
            } else {

            }
        }

    }

    /** Checks if a line segment from v1 to v2 would
     * intersect an existing connection in g.
     *
     * @param g the graph of vertices
     * @param v1 the first vertex in the connection
     * @param v2 the second vertex in the connection
     * @return Return true if a line segment from v1 to v2 would
     * intersect an existing connection in g, false otherwise.
     */
    public boolean intersects(ArrayList<Vertex> g, Vertex v1, Vertex v2) {
        /* REMOVE FOLLOWING LINE AFTER EDITTING THIS FUNCTION */
        return false;
    }
}
