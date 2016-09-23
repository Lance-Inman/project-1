package src;

import java.util.ArrayList;
import java.util.Random;

public class GeneticSolver {
    private int numTicks;
    private int maxTicks;
    private final int initialPopulationSize;
    private ArrayList<Graph> population;

    public GeneticSolver(int maxTicks) {
        numTicks = 0;
        this.maxTicks = maxTicks;
        initialPopulationSize = 100;
        population = new ArrayList<>();
    }

    public int getNumDecisions() {
        return numTicks;
    }

    private void tick() {
        numTicks++;
    }

    public Graph solve(Graph g, int numColors) throws UnsolvableGraphException{

        // Generate a random initial population
        for(int i = 0; i < initialPopulationSize; i++) {
            Graph newMember = randomlyColorGraph(g, numColors);
            population.add(newMember);
        }

        Graph candidate1;
        Graph candidate2;
        int thisFitness;
        Random rnd = new Random();

        while(numTicks < maxTicks) {

            // Search for a solution among population
            for(Graph p: population) {
                if(fitness(p) == 0) {
                    return p;
                }
            }

            // Find Candidate 1
            candidate1 = selectCandidate();

            // Find Candidate 2
            candidate2 = selectCandidate(candidate1);

            // Combine Candidate 1 and Candidate 2 and add it to the population
            Graph child = makeChild(candidate1, candidate2);

            // Mutate that cute little baby
            child = mutate(child, numColors, 3);

            // Add that messed-up baby to the gene pool...
            population.add(child);

            // Kill off the least fit
            int leastFitness = 0;
            Graph leastFit = population.get(0);
            for (Graph p : population) {
                thisFitness = fitness(p);
                if (thisFitness > leastFitness) {
                    tick();
                    leastFit = p;
                    leastFitness = thisFitness;
                }
            }
            tick();
            population.remove(leastFit);
        }

        throw new UnsolvableGraphException();
    }

    private int fitness(Graph g) {
        int score = 0;
        tick();
        for(Vertex v: g.vertices) {
            score += v.getConflicts();
        }
        return score;
    }

    private Graph selectCandidate() {
        int thisFitness;
        int minFitness = Integer.MAX_VALUE;
        Graph mostFit = population.get(0);
        for(Graph g: population) {
            thisFitness = fitness(g);
            if(thisFitness < minFitness) {
                minFitness = thisFitness;
                mostFit = g;
            }
        }
        tick();
        return mostFit;
    }

    private Graph selectCandidate(Graph otherCandidate) {
        int thisFitness;
        int minFitness = Integer.MAX_VALUE;
        Graph mostFit = population.get(0);
        for(Graph g: population) {
            thisFitness = fitness(g);
            if(thisFitness < minFitness && g != otherCandidate) {
                minFitness = thisFitness;
                mostFit = g;
            }
        }
        tick();
        return mostFit;
    }

    private Graph makeChild(Graph p1, Graph p2) {
        Random rnd = new Random();
        int mutationIndex = rnd.nextInt(p1.vertices.size());
        Graph child = new Graph(0);
        tick();
        for(int i = 0; i < mutationIndex; i++) {
            child.vertices.add(p1.vertices.get(i));
        }
        for(int i = mutationIndex; i < p2.vertices.size(); i++) {
            child.vertices.add(p2.vertices.get(i));
        }
        return child;
    }

    private Graph mutate(Graph g, int numColors, int numMutations) {
        Random rnd = new Random();
        for(int i = 0; i < numMutations; i++) {
            Vertex v = g.vertices.get(rnd.nextInt(g.vertices.size()));
            v.setColor(rnd.nextInt(numColors));
        }
        return g;
    }

    private Graph randomlyColorGraph(Graph g, int numColors) {
        int color = 0;
        Graph coloredGraph = g;
        for(Vertex v:coloredGraph.vertices) {
            v.setColor(color);
            tick();
            color = ++color % numColors;
        }
        return coloredGraph;
    }
}
