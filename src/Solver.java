package src;

public interface Solver {
    Graph solve(Graph g, int numColors) throws UnsolvableGraphException;
    int getNumDecisions();
}
