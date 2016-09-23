package src;

import java.util.ArrayList;

/*
 * @author Ethan Peterson
 * @since Sep 20, 2016
 */
public class FCBacktrack implements Solver
{
    private int numDecisions;
    private int[] domainValues;
    private int curVertex;
    
    @Override
    public Graph solve(Graph g, int kColors)
    {
        orderDomainValues(kColors); //sets the order in which the colors should be tried
        populateVertexDomainPossiblilities(g.getVertices());
        curVertex = 0;
        return fcBacktrack(g);
    }
    
    private Graph fcBacktrack(Graph g)
    {
        //checking if the assignment (graph coloring problem) is complete
        if(isColored(g))
            return g;
        
        //var <- Select-Unassigned-Variable(csp, assignment)
        Vertex currentVertex = g.getVertices().get(curVertex); //since this is simple backtracking, progression is done simply by iterating through the Graph's list of vertices
        
        //test each possible color at the vertex (which list comes from each Vertex's possible color list, the one that is trimmed by forward checking)
        for(int i = 0; i < currentVertex.getPossibleColor().size(); i++)
        {
            int color = currentVertex.getPossibleColor().get(i);
            //check if the color is valid in this spot
            if(checkColorOnNeighbors(color, currentVertex.getNeighbors()))
            {
                currentVertex.setColor(color); //sets the color of currentVertex to color
                incDecision(); //Decision was made to color the vertex. Count it.
                forwardCheck(currentVertex, color); //remove the selected color as a possiblility from all connected vertices
                curVertex++; //allows the coloring to move to the next vertex
                
                Graph result = fcBacktrack(g); //continues the coloring at the next vertex
                //return the result if there wasn't a failure
                if(result != null)
                    return result;
                else
                    curVertex--; //ensures curVertex is returned to what really is the current vertex. Problems would be caused without this line.
            }
            currentVertex.setColor(-1); //Uncolor the vertex. The color ended up not working.
            undoForwardCheck(currentVertex, color); //re-instate the previously removed color to neighbor verteces' list of possible colors
            incDecision(); //The decision was made to backtrack. Count it.
        }
        
        return null; //null signifies failure
    }
    
    /* The meat of this backtracking method.
     * Removes the color chosen for the current vertex (curV) from the list of possible colors of all connected vertices.
     * Forward checking prevents needless attempts at coloring, saving cycles at runtime.
     */
    private void forwardCheck(Vertex curV, Integer chosenColor)
    {
        for(Vertex v : curV.getNeighbors())
        {
            //if curV's color (chosenColor) exists in v's list of possible colors, remove it.
            if(v.getPossibleColor().contains(chosenColor))
                v.getPossibleColor().remove(chosenColor);
        }
    }
    
    private void undoForwardCheck(Vertex curV, Integer chosenColor)
    {
        for(Vertex v : curV.getNeighbors())
        {
            //if the color to be reinstated doesn't exist in the list of possible colors, reinstate it
            if(!v.getPossibleColor().contains(chosenColor))
                v.getPossibleColor().add(chosenColor);
        }
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
    
    //adds all color possibilities to the domain possiblilities list in each vertex
    private void populateVertexDomainPossiblilities(ArrayList<Vertex> graphVertices)
    {
        for(Vertex v : graphVertices)
        {
            ArrayList<Integer> listToPopulate = v.getPossibleColor();
            
            for(int color : domainValues)
            {
                listToPopulate.add(color);
            }
        }
    }
    
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
