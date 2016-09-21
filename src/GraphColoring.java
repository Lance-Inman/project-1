/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphcoloring;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Ethan Peterson
 * @since 2016-09-19
 */
public class GraphColoring 
{   
    public static void main(String[] args)
    {
        //test algs with Austrailia coloring problem
        Vertex wa = new Vertex(0, 2),
               nt = new Vertex(1, 0),
               sa = new Vertex(1, 3),
               q = new Vertex(2, 1),
               nsw = new Vertex(3, 3),
               v = new Vertex(2, 4),
               t = new Vertex(2, 5);
        
        wa.getNeighbors().add(nt);
        wa.getNeighbors().add(sa);
        nt.getNeighbors().add(wa);
        nt.getNeighbors().add(q);
        nt.getNeighbors().add(sa);
        sa.getNeighbors().add(wa);
        sa.getNeighbors().add(nt);
        sa.getNeighbors().add(q);
        sa.getNeighbors().add(nsw);
        sa.getNeighbors().add(v);
        q.getNeighbors().add(nt);
        q.getNeighbors().add(sa);
        q.getNeighbors().add(nsw);
        nsw.getNeighbors().add(q);
        nsw.getNeighbors().add(sa);
        nsw.getNeighbors().add(v);
        v.getNeighbors().add(sa);
        v.getNeighbors().add(nsw);
        
        Graph g = new Graph();
        ArrayList<Vertex> vertices = g.getVertices();
        
        vertices.add(wa);
        vertices.add(nt);
        vertices.add(sa);
        vertices.add(q);
        vertices.add(nsw);
        vertices.add(v);
        vertices.add(t);
        
        IKColorings coloringMethod = new FCBacktrack();
        int kColors = 3;
        
        g = coloringMethod.color(g, kColors);
        System.out.println("Colored in " + coloringMethod.getNumDecisions() + " decisions.");
        printAustrailia(g);
    }
    
    public static void printAustrailia(Graph g)
    {
        System.out.println("PRINT AUSTRAILIA");
        
        System.out.println("-" + g.getVertices().get(1).getColor() + "--");
        System.out.println("--" + g.getVertices().get(3).getColor() + "-");
        System.out.println(g.getVertices().get(0).getColor() + "---");
        System.out.println("-" + g.getVertices().get(2).getColor() + "-" + g.getVertices().get(4).getColor());
        System.out.println("--" + g.getVertices().get(5).getColor() + "-");
        System.out.println("--" + g.getVertices().get(6).getColor() + "-");
    }
    
    /*
    public static void main(String[] args) {
        ProblemGenerator pg; //the problem generator
        List<Graph> graphs; //a List to hold the generated graphs
        IKColorings coloringMethod; //IKColorings instance that can hold any of the coloringMethods.
        int kColors; //the number of colors
        List<TestInfo> results; //list of observations made throughout the testing process
    }
    
    public static void colorGraphs(List<Graph> uncoloredGraphs, int kColors)
    {
        IKColorings coloringMethod;
        
        for(int i = 0; i < uncoloredGraphs.size(); i++)
        {            
            coloringMethod = getCurrentColoringMethod(i);
            colorGraph(uncoloredGraphs.get(i), coloringMethod, kColors);
        }
    }
    
    public static void getCurrentColoringMethod(int i)
    {
        switch
    }
    
    public static void colorGraph(Graph g, IKColorings coloringMethod, int kColors)
    {
        coloringMethod.color(g, kColors);
    } */
    
    
}
