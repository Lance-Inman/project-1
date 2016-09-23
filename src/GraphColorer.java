package src;

import java.util.Scanner;

public class GraphColorer {

    private static void printGraph(Graph g) {
        for(Vertex v: g.vertices) {
            System.out.println("("+v.getX()+","+v.getY()+"):"+v.getColor());
            for(Vertex n: v.getNeighbors()) {
                System.out.println("--(" + n.getX() + "," + n.getY() + "):" + n.getColor());
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(10);
        int numColors = 4;
        Scanner keyboard = new Scanner(System.in);
        String input = "h";

        while (!input.equals("q")) {
            input = keyboard.next();
            switch (input) {
                case "h":
                    System.out.println("" +
                        "g -- generate a an uncolored graph of size n\n" +
                        "c -- set number of usable colors to n\n" +
                        "s -- solve graph g\n" +
                        "p -- print graph g\n" +
                        "h -- display help menu\n" +
                        "q -- exit the program");
                    break;

                case "g":
                    System.out.print("graph size:");
                    int n = keyboard.nextInt();
                    try {
                        g = new Graph(n);
                    } catch (NumberFormatException fne) {
                        System.out.println("Graph size must be an integer!");
                    }
                    break;

                case "c":
                    System.out.print("max colors:");
                    numColors = keyboard.nextInt();
                    break;

                case "s":
                    System.out.print("Choose a solver (Min-conflicts, Simple-backtracking, backtracking-Fc, Backtracking-mac, Genetic:");
                    input = keyboard.next();
                    switch(input) {
                        case "m":
                            try {
                                MinConflictsSolver mcs = new MinConflictsSolver(100000000);
                                g = mcs.solve(g, numColors);
                                System.out.println("Solved graph with Min-Conflicts in "+mcs.getNumDecisions()+" decisions");
                            } catch (UnsolvableGraphException uge) {
                                System.out.println("Can not solve graph with Min-Conflicts in less than 100,000,000 decisions");
                            }
                            break;
                        case "s":
                            SimpleBacktrack sb = new SimpleBacktrack();
                            g = sb.solve(g, numColors);
                            System.out.println("Solved graph with Simple Backtrack in "+sb.getNumDecisions()+" decisions");
                            if (g == null) {
                                System.out.println("Can not solve graph with Simple Backtrack");
                                g = new Graph(10);
                            }
                            break;
                        case "f":
                            FCBacktrack fcb = new FCBacktrack();
                            g = fcb.solve(g, numColors);
                            System.out.println("Solved graph with Backtrack with FC in "+fcb.getNumDecisions()+" decisions");
                            if (g == null) {
                                System.out.println("Can not solve graph with Backtracking with Forward Checking");
                                g = new Graph(10);
                            }
                            break;
                        case "b":
                            MACBacktrack macb = new MACBacktrack();
                            g = macb.solve(g, numColors);
                            System.out.println("Solved graph with Backtrack with MAC in "+macb.getNumDecisions()+" decisions");
                            if (g == null) {
                                System.out.println("Can not solve graph with MAC Backtrack");
                                g = new Graph(10);
                            }
                            break;
                        case "g":
                            GeneticSolver gs = new GeneticSolver(100000000);
                            try {
                                g = gs.solve(g, numColors);
                                System.out.println("Solved graph with a Genetic Algorithm in " + gs.getNumDecisions() + " decitions");
                            } catch (UnsolvableGraphException uge) {
                                System.out.println("Can not solve graph with Genetic Algorithm in less than 100,000,000 decisions");
                            }
                            break;
                    }
                    break;

                case "p":
                    if(g != null) {
                        printGraph(g);
                    } else {
                        System.out.println("No graph to print");
                    }
                    break;

            }
        }
        System.exit(0);
    }
}
