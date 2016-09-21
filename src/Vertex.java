package graphcoloring;

import java.util.ArrayList;

/*
 * @author Ethan Peterson
 * @since Sep 19, 2016
 */
public class Vertex 
{
    private int x;
    private int y;
    private int color;
    private ArrayList<Integer> possibleColor;
    private Vertex closestNeighbor;
    private boolean connectedToClosestNeighbor;
    private ArrayList<Vertex> neighbors;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        connectedToClosestNeighbor = false;
        neighbors = new ArrayList<>();
        color = -1;
        possibleColor = new ArrayList<>();
    }

    public int getColor() {
        return color;
    }
    
    public ArrayList<Integer> getPossibleColor()
    {
        return possibleColor;
    }
    
    public void setColor(int newColor) {
        color = newColor;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public boolean getConnectedToClosestNeighbor()
    {
        return connectedToClosestNeighbor;
    }
    
    public void setConnectedToClosestNeighbor(boolean b)
    {
        connectedToClosestNeighbor = b;
    }
    
    public Vertex getClosestNeighbor()
    {
        return closestNeighbor;
    }
    
    public void setClosestNeighbor(Vertex v)
    {
        closestNeighbor = v;
    }
    
    public ArrayList<Vertex> getNeighbors()
    {
        return neighbors;
    }
}
