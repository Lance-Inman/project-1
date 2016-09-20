package src;

public interface Solver {
    Graph solve(Graph g, int numColors);
    void tick();
    int getTicks();
}
