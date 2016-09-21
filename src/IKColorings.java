/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphcoloring;

/*
 * @author Ethan Peterson
 * @since 2016-09-19
 */
public interface IKColorings 
{
    //each implementation will have a numDecisions int
    
    //this method will be given an uncolored Graph and return a colored one
    public Graph color(Graph g, int kColors);
    
    public boolean isColored(Graph g);
    
    //retrieves the numDecisions int (keeping track of the amount of decisions that were made in the coloring process
    public int getNumDecisions();
    
    //each class will also have a countDecision() method that simply increments the numDecisions int
}
