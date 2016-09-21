package graphcoloring;

import java.util.ArrayList;

/*
 * @author Ethan Peterson
 * @since Sep 19, 2016
 */
public class SimpleBacktrack implements IKColorings
{
    private int numDecisions;
    private int[] domainValues;
    private int curVertex;
    
    public SimpleBacktrack()
    {
        numDecisions = 0;
    }
    
    @Override
    public Graph color(Graph g, int kColors)
    {
        orderDomainValues(kColors); //sets the order in which the colors should be tried
        curVertex = 0;
        return backtrack(g);
    }
    
    private Graph backtrack(Graph g)
    {
        //checking if the assignment (graph coloring problem) is complete
        if(isColored(g))
            return g;
        
        //var <- Select-Unassigned-Variable(csp, assignment)
        Vertex currentVertex = g.getVertices().get(curVertex); //since this is simple backtracking, progression is done simply by iterating through the Graph's list of vertices
        
        //test each possible color at the vertex
        for(int color : domainValues)
        {
            //check if the color is valid in this spot
            if(checkColorOnNeighbors(color, currentVertex.getNeighbors()))
            {
                currentVertex.setColor(color); //sets the color of currentVertex to color
                incDecision(); //Decision was made to color the vertex. Count it.
                curVertex++; //allows the coloring to move to the next vertex
                
                Graph result = backtrack(g); //continues the coloring at the next vertex
                //return the result if there wasn't a failure
                if(result != null)
                    return result;
                else
                    curVertex--; //ensures curVertex is returned to what really is the current vertex. Problems would be caused without this line.
            }
            currentVertex.setColor(-1); //Uncolor the vertex. The color ended up not working.
            incDecision(); //The decision was made to backtrack. Count it.
        }
        
        return null; //null signifies failure
    }
    
    /*Checks if the color we are trying to color the current vertex with is valid.
     *Meaning: if the color is the current color of any vertex, return false.*/
    private boolean checkColorOnNeighbors(int color, ArrayList<Vertex> neighbors)
    {
        for(Vertex v : neighbors)
        {
            if(color == v.getColor())
                return false;
        }
        return true;
    }
    
    //initializes the domain of possible coloring values
    private void orderDomainValues(int kColors)
    {
        int[] domain = new int[kColors];
        
        for(int i = 0; i < domain.length; i++)
        {
            domain[i] = i;
        }
        
        domainValues = domain;
    }
    
    @Override
    public boolean isColored(Graph g)
    {
        for(Vertex v : g.getVertices())
        {
            if(v.getColor() == -1)
                return false;
        }
        
        return true;
    }
    
    @Override
    public int getNumDecisions()
    {
        return numDecisions;
    }
    
    private void incDecision()
    {
        numDecisions++;
    }
}
