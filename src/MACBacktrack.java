package src;

import java.util.ArrayList;

/*
 * @author Ethan Peterson
 * @since Sep 20, 2016
 */
public class MACBacktrack implements Solver
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
        return macBacktrack(g);
    }
    
    private Graph macBacktrack(Graph g)
    {
        //checking if the assignment (graph coloring problem) is complete
        if(isColored(g))
            return g;
        
        //var <- Select-Unassigned-Variable(csp, assignment)
        Vertex currentVertex = g.getVertices().get(curVertex); //since this is simple backtracking, progression is done simply by iterating through the Graph's list of vertices
        
        //test each possible color at the vertex (which list comes from each Vertex's possible color list, the one that is trimmed by forward checking)
        for(Integer color : currentVertex.getPossibleColor())
        {
            //check if the color is valid in this spot
            if(checkColorOnNeighbors(color, currentVertex.getNeighbors()))
            {
                currentVertex.setColor(color); //sets the color of currentVertex to color
                incDecision(); //Decision was made to color the vertex. Count it.
                if(forwardCheckWithMAC(currentVertex, color)) //remove the selected color as a possiblility from all connected vertices. Immediately fail if a domain was reduced to 0
                {
                    curVertex++; //allows the coloring to move to the next vertex

                    Graph result = macBacktrack(g); //continues the coloring at the next vertex
                    //return the result if there wasn't a failure
                    if(result != null)
                        return result;
                    else
                        curVertex--; //ensures curVertex is returned to what really is the current vertex. Problems would be caused without this line.
                }
            }
            currentVertex.setColor(-1); //Uncolor the vertex. The color ended up not working.
            undoForwardCheck(currentVertex, color); //re-instate the previously removed color(s) to neighbor verteces' list of possible colors
            incDecision(); //The decision was made to backtrack. Count it.
        }
        
        return null; //null signifies failure
    }
    
    /* The meat of this backtracking method.
     * Removes the color chosen for the current vertex (curV) from the list of possible colors of all connected vertices.
     * Forward checking prevents needless attempts at coloring, saving cycles at runtime.
     * This forward checking method is modified to maintain arc consistancy. 
     * This means, if, by removing chosenColor from v's possible colors, v is made to have no more possible colors, we know to backtrack immediately.
     */
    private boolean forwardCheckWithMAC(Vertex curV, Integer chosenColor)
    {
        for(Vertex v : curV.getNeighbors())
        {
            //only do this if v is uncolored
            if(v.getColor() == -1)
            {
                //if curV's color (chosenColor) exists in v's list of possible colors, remove it.
                if(v.getPossibleColor().contains(chosenColor))
                    v.getPossibleColor().remove(chosenColor);

                //if there are no possible colors left to color v with, instantly backtrack. Going any farther is doomed to fail
                if(v.getPossibleColor().isEmpty())
                {
                    System.out.println("Vertex (" + v.getX() + "," + v.getY() + ") is no longer colorable. Backtrack.");
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private void undoForwardCheck(Vertex curV, Integer chosenColor)
    {
        for(Vertex v : curV.getNeighbors())
        {
            //if the color to be reinstated doesn't exist in the list of possible colors, reinstate it
            if(!v.getPossibleColor().contains(chosenColor) && v.getColor() == -1)
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
    
    public int getNumDecisions()
    {
        return numDecisions;
    }
    
    private void incDecision()
    {
        numDecisions++;
    }
}


//package graphcoloring;
//
//import java.util.ArrayList;
//
///*
// * @author Ethan Peterson
// * @since Sep 21, 2016
// */
//public class MACBacktrack implements IKColorings
//{
//    private int numDecisions;
//    private int[] domainValues;
//    private int curVertex;
//    
//    @Override
//    public Graph color(Graph g, int kColors)
//    {
//        orderDomainValues(kColors); //sets the order in which the colors should be tried
//        populateVertexDomainPossiblilities(g.getVertices());
//        curVertex = 0;
//        return macBacktrack(g);
//    }
//    
//    private Graph macBacktrack(Graph g)
//    {
//        ArrayList<Vertex> revisedVertices = new ArrayList<Vertex>();
//        ArrayList<Integer> revisedVerticesRemovedInt = new ArrayList<Integer>();
//        
//        //checking if the assignment (graph coloring problem) is complete
//        if(isColored(g))
//            return g;
//        
//        //var <- Select-Unassigned-Variable(csp, assignment)
//        Vertex currentVertex = g.getVertices().get(curVertex); //since this is simple backtracking, progression is done simply by iterating through the Graph's list of vertices
//        
//        //test each possible color at the vertex (which list comes from each Vertex's possible color list, the one that is trimmed by forward checking)
//        for(Integer color : currentVertex.getPossibleColor())
//        {
//            //check if the color is valid in this spot
//            if(checkColorOnNeighbors(color, currentVertex.getNeighbors()))
//            {
//                currentVertex.setColor(color); //sets the color of currentVertex to color
//                incDecision(); //Decision was made to color the vertex. Count it.
//                if(ac3(currentVertex, color, revisedVertices, revisedVerticesRemovedInt)) //remove the selected color as a possiblility from all connected vertices
//                {
//                    curVertex++; //allows the coloring to move to the next vertex
//
//                    Graph result = macBacktrack(g); //continues the coloring at the next vertex
//                    //return the result if there wasn't a failure
//                    if(result != null)
//                        return result;
//                    else
//                        curVertex--; //ensures curVertex is returned to what really is the current vertex. Problems would be caused without this line.
//                }
//            }
//            currentVertex.setColor(-1); //Uncolor the vertex. The color ended up not working.
//            restorePossibleColoringToVertices(revisedVertices, revisedVerticesRemovedInt); //re-instate the previously removed color to neighbor verteces' list of possible colors
//            incDecision(); //The decision was made to backtrack. Count it.
//        }
//        
//        return null; //null signifies failure
//    }
//    
//    /* The meat of this backtracking method.
//     * Removes the color chosen for the current vertex (Xi) from the list of possible colors of all connected vertices.
//     * Like forward checking, but it returns false if a neighbor's list of possible domains is reduced to {}.
//     */
//    private boolean ac3(Vertex Xi, int XiColoring, ArrayList<Vertex> revisedVertices, ArrayList<Integer> revisedVerticesRemovedInt)
//    {
//        ArrayList<Vertex> arcs = new ArrayList<Vertex>();
//        populateArcs(arcs, Xi.getNeighbors());
//        
//        ArrayList<Vertex> revisedVerticesAC3 = new ArrayList<Vertex>();
//        
//        Vertex Xj;
//        while(arcs != null && arcs.size() > 0)
//        {
//            Xj = arcs.remove(0); //removes and gets the first Vertex from arcs
//            if(Xj.getColor() != -1) //skip this Xj if it is already colored
//                continue;
//            
//            if(revise(XiColoring, Xj))
//            {
//                revisedVerticesAC3.add(Xj);
//                if(Xj.getPossibleColor().size() == 0)
//                {
//                    restorePossibleColoringToVertices(revisedVerticesAC3, XiColoring); //restore XiColoring as possible value for all affected vertices, since we are backtracking.
//                    return false; //coloring cannot be done on this vertex with the previous colorings. Backtrack.
//                }
//                
//                /* The ac3 pseudocode includes a line here about adding to the arcs queue. 
//                 * With how we are implementing the MAC for our graph coloring problem, I don't think it makes sense to do it.
//                 */
//            }
//        }
//        retainRevisedVertexInfo(revisedVertices, revisedVerticesRemovedInt, revisedVerticesAC3, XiColoring);
//        
//        return true;
//    }
//    
//    private void retainRevisedVertexInfo(ArrayList<Vertex> revisedVertices, ArrayList<Integer> revisedVerticesRemovedInt, ArrayList<Vertex> revisedVerticesAC3, int XiColoring)
//    {
//        for(Vertex v : revisedVerticesAC3)
//        {
//            revisedVertices.add(v);
//            revisedVerticesRemovedInt.add(XiColoring);
//        }
//    }
//    
//    /* Revises the domain of possible colors for Vertex Xj,
//     * removing Xi's coloring from that domain.
//     * If no removal occurred, return false.
//     */
//    private boolean revise(int XiColoring, Vertex Xj)
//    {
//        boolean revised = false;
//        
//        for(Integer possibleColor : Xj.getPossibleColor())
//        {
//            if(Xj.getPossibleColor().contains(possibleColor))
//            {
//                Xj.getPossibleColor().remove(possibleColor);
//                revised = true;
//            }            
//        }
//        
//        return revised;
//    }
//    
//    //if ac3 returns false, we must restore the previously removed colorings as possible colors for the affected vertices. Otherwise, the algorithm will fail
//    private void restorePossibleColoringToVertices(ArrayList<Vertex> revisedVertices, Integer XiColoring)
//    {
//        for(Vertex v : revisedVertices)
//        {
//            v.getPossibleColor().add(XiColoring);
//        }
//    }
//    
//    //if ac3 returns false, we must restore the previously removed colorings as possible colors for the affected vertices. Otherwise, the algorithm will fail
//    private void restorePossibleColoringToVertices(ArrayList<Vertex> revisedVertices, ArrayList<Integer> colors)
//    {
//        for(int i = 0; i < revisedVertices.size(); i++)
//        {
//            Vertex v = revisedVertices.remove(i);
//            Integer colorToReinstate = colors.remove(i);
//            v.getPossibleColor().add(colorToReinstate);
//        }
//    }
//    
//    //populates the arcs list in ac3 so that we may begin the arc consistency check
//    private void populateArcs(ArrayList<Vertex> arcs, ArrayList<Vertex> XiNeighbors)
//    {
//        for(Vertex v : XiNeighbors)
//        {
//            arcs.add(v);
//        }
//    }
//    
//    /*Checks if the color we are trying to color the current vertex with is valid.
//     *Meaning: if the color is the current color of any vertex, return false.*/
//    private boolean checkColorOnNeighbors(int color, ArrayList<Vertex> neighbors)
//    {
//        for(Vertex v : neighbors)
//        {
//            if(color == v.getColor())
//                return false;
//        }
//        return true;
//    }
//    
//    //initializes the domain of possible coloring values
//    private void orderDomainValues(int kColors)
//    {
//        int[] domain = new int[kColors];
//        
//        for(int i = 0; i < domain.length; i++)
//        {
//            domain[i] = i;
//        }
//        
//        domainValues = domain;
//    }
//    
//    //adds all color possibilities to the domain possiblilities list in each vertex
//    private void populateVertexDomainPossiblilities(ArrayList<Vertex> graphVertices)
//    {
//        for(Vertex v : graphVertices)
//        {
//            ArrayList<Integer> listToPopulate = v.getPossibleColor();
//            
//            for(int color : domainValues)
//            {
//                listToPopulate.add(color);
//            }
//        }
//    }
//    
//    @Override
//    public boolean isColored(Graph g)
//    {
//        for(Vertex v : g.getVertices())
//        {
//            if(v.getColor() == -1)
//                return false;
//        }
//        
//        return true;
//    }
//    
//    @Override
//    public int getNumDecisions()
//    {
//        return numDecisions;
//    }
//    
//    private void incDecision()
//    {
//        numDecisions++;
//    }
//}
