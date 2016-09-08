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
 * The {@code ProblemGenerator} class generates a graph and
 * connects verticies to their closes neighbor at random
 * until no more connections are possible.
 *
 * @author Lance Inman
 * @version 0.1.0
 * @since 2016-09-05
 */
public class ProblemGenerator {

    private class Graph {
        ArrayList<Vertex> vertices;

        private class Vertex {
            int x;
            int y;
            Vertex closestNeighbor;
            boolean connectedToClosestNeighbor;
            ArrayList<Vertex> neighbors;

            public Vertex(int x, int y) {
                this.x = x;
                this.y = y;
                connectedToClosestNeighbor = false;
                neighbors = new ArrayList<>();
            }
        }

        public Graph(int size) {
            populateGraph(size);
            populateNeighbors();
            connectClosestNeighbors();
        }

        public void populateGraph(int n) {
            Random rnd = new Random();
            for(int i = 0; i < n; i++) {
                Vertex v = new Vertex(rnd.nextInt(n), rnd.nextInt(n));
                vertices.add(v);
            }
        }

        public double distance(Vertex v1, Vertex v2) {
            return Math.sqrt(Math.pow((v1.x - v2.x), 2) + Math.pow((v1.y - v2.y), 2));
        }

        /** Finds the closest neighbor to each vertex in the graph.
         */
        public void populateNeighbors() {
            for(int i = 0; i < vertices.size(); i++) {
                Vertex v1 = vertices.get(i);
                Vertex v1Neighbor = null;
                double shortestDist = -1;

                for(int j = 0; j < vertices.size(); j++) {
                    if(j != i) {
                        Vertex v2 = vertices.get(j);
                        if(distance(v1, v2) < shortestDist || shortestDist == -1) {
                            v1Neighbor = v2;
                            shortestDist = distance(v1, v2);
                        }
                    }
                }

                v1.closestNeighbor = v1Neighbor;
            }
        }

        public boolean connect(Vertex v1, Vertex v2) {
            if(!intersects(v1, v2)) {
                v1.neighbors.add(v2);
                v2.neighbors.add(v1);

                if (v2 == v1.closestNeighbor) {
                    v1.connectedToClosestNeighbor = true;
                }
                if (v1 == v2.closestNeighbor) {
                    v2.connectedToClosestNeighbor = true;
                }
                return true;
            } else {
                return false;
            }
        }

        public void connectClosestNeighbors() {
            for(int i = 0; i < vertices.size(); i++) {
                Vertex v = vertices.get(i);
                if(v.neighbors.contains(v.closestNeighbor)) {
                    v.connectedToClosestNeighbor = true;
                } else {
                    connect(v, v.closestNeighbor);
                }
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
        public boolean intersects(Vertex v1, Vertex v2) {
        /* REMOVE FOLLOWING LINE AFTER EDITTING THIS FUNCTION */
            return false;
        }
    }

    public void main (String[]args) {
        Scanner keyboard = new Scanner(System.in);
        String input = "h";
        System.out.println("Welcome");

        while(input.equalsIgnoreCase("q")) {
            if(input.equalsIgnoreCase("h")) {
                System.out.println("Type g X to generate a graph of size X. Type q to quit.");
            } else if (input.equalsIgnoreCase("g")){
                input = keyboard.next();
                Graph g = new Graph(Integer.parseInt(input));
            }
            input = keyboard.next();
        }

        System.exit(0);
    }
}
