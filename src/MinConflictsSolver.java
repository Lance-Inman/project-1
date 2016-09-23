package src;

import java.util.ArrayList;
import java.util.Random;

public class MinConflictsSolver implements Solver{
    private int numTicks;
    private int maxTicks = 10000;

    private void tick() {
        numTicks++;
    }

    public int getNumDecisions() {
        return numTicks;
    }

    public void setMaxTicks(int newMax) {
        maxTicks = newMax;
    }

    public Graph solve(Graph g, int numColors) throws UnsolvableGraphException{
        Graph coloredGraph = g;
        ArrayList<Vertex> conflicts = new ArrayList<>();
        int color = 0;
        Random rnd = new Random();

        // Randomly color each vertex and add conflicting vertices to conflicts
        for (Vertex v: coloredGraph.vertices) {
            v.setColor(color);
            color = ++color % numColors;
            if(v.getConflicts() > 0) {
                conflicts.add(v);
            }
            tick();
        }

        while(numTicks < maxTicks && conflicts.size() > 0) {
            Vertex v = conflicts.get(rnd.nextInt(conflicts.size()));
            int minColor = v.getColor();
            int minConflicts = v.getConflicts();
            for(int c = 0; c < numColors; c++) {
                v.setColor(c);
                tick();
                if (v.getConflicts() < minConflicts) {
                    minColor = v.getColor();
                    minConflicts = v.getConflicts();
                }
            }
            v.setColor(minColor);
            //System.out.println("Set ("+v.getX()+","+v.getY()+") to "+minColor+". conflicts.size()="+conflicts.size());
            tick();

            if(v.getConflicts() == 0) {
                conflicts.remove(v);
            }
        }

        if(conflicts.size() == 0) {
            return coloredGraph;
        } else {
            throw new UnsolvableGraphException();
        }

    }
}
