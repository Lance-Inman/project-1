package src;

import java.util.ArrayList;

public class Vertex {
    private int x;
    private int y;
    private int color;
    private int conflicts;
    private ArrayList<Integer> possibleColor;
    ArrayList<Vertex> neighbors;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        color = -1;
        conflicts = 0;
        neighbors = new ArrayList<>();
        possibleColor = new ArrayList<>();
    }

    private void updateConflicts() {
        int updatedConflicts = 0;
        for(Vertex v: neighbors) {
            if(v.getColor() == color) {
                updatedConflicts++;
            }
        }
        conflicts = updatedConflicts;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public int getConflicts() {
        return conflicts;
    }

    public ArrayList<Integer> getPossibleColor() {
        return possibleColor;
    }

    public ArrayList<Vertex> getNeighbors() {
        return neighbors;
    }

    public void setColor(int newColor) {
        color = newColor;
        updateConflicts();
    }

    public void addNeighbor(Vertex v) {
        neighbors.add(v);
    }

    public String toString() {
        return "("+x+","+y+")";
    }

}
