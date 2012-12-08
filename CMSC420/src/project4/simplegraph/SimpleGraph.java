/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: SimpleGraph.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */
package project4.simplegraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * TODO:  Add documentation for the generics T and V used in the graph
 *        as well as standardize and document the use of variable names u,
 *        dst, src, etc.  Also add more error detection/correction for when
 *        vertex objects or what not does not exist. 
 *        
 * @author jsmith
 *
 * @param <T> : The vertex name / identification type.
 * @param <V> : The type of data held in the vertex.
 */
public class SimpleGraph<T, V> {
  private HashMap<T, Vertex<V>> adjMatrix;
  
  public SimpleGraph() {
    adjMatrix = new HashMap<T, Vertex<V>>();
  }
  
  public void addVertex(T u, Vertex<V> v) throws DuplicateVertexException {
    if(!adjMatrix.containsKey(u))
      adjMatrix.put(u, v);
    else
      throw new DuplicateVertexException("Vertex already exist in matrix");
  }
  
  public Vertex<V>  getVertex(T u)  { return adjMatrix.get(u);         }
  public boolean    contains(T u)   { return adjMatrix.containsKey(u); }
  
  
  public boolean hasIndegree(T u) { 
    return adjMatrix.get(u).getIndegree() > 0 ? true:false;
  }
  
  public void addEdge(T src, T dst) throws VertexDoesNotExist {
    if(!adjMatrix.containsKey(src)||!adjMatrix.containsKey(dst))
      throw new VertexDoesNotExist();
    
    adjMatrix.get(src).addEdge(adjMatrix.get(dst));
    adjMatrix.get(dst).incrementIndegree();
  }
  
  public void removeEdge(T src, T dst) throws VertexDoesNotExist {
    if(!adjMatrix.containsKey(src)||!adjMatrix.containsKey(dst))
      throw new VertexDoesNotExist();
    
    adjMatrix.get(src).removeEdge(adjMatrix.get(dst));
    adjMatrix.get(dst).decrementIndegree();
  }
  
  public void clearVertexEdges(T u) { 
    // Notify each adjacent vertex they are no longer being followed.
    if(adjMatrix.containsKey(u)) {
      for(Vertex<V> v : adjMatrix.get(u).getAllEdges())
        v.decrementIndegree();
      
      adjMatrix.get(u).clearEdges(); 
    }
  }
  
  public void replaceVertex(T u, Vertex<V> v) { adjMatrix.put(u, v); }
  
  /**
   * My implementation from topsort() pseudocode found in (Weiss, 2007, p323)
   * 
   * TODO: Verify topsort() works correctly with input data with known outputs.
   */
  public Queue<Vertex<V>> topsort() throws CycleException {
    Queue<Vertex<V>> q = new LinkedList<Vertex<V>>();
    Queue<Vertex<V>> finalProduct = new LinkedList<Vertex<V>>();
    
    HashMap<T, Vertex<V>> adjCopy = new HashMap<T, Vertex<V>>();    
    int counter = 0;
    
    /** TODO: Figure out how to do this without making a deep copy. */
    // Make a deep copy of the matrix so we don't disturb the original state
    // And add the vertex to the queue if it's indegree is 0
    for(T u : adjMatrix.keySet()) {
      adjCopy.put(u, adjMatrix.get(u).clone());
      if(adjCopy.get(u).getIndegree() == 0)
        q.add(adjCopy.get(u));
    }
    
    while(!q.isEmpty()) {
      Vertex<V> v = q.poll();
      v.setTopSortNo(++counter);
      finalProduct.add(v);
      
      ArrayList<Vertex<V>> w = v.getAllEdges();
      for(Vertex<V> x : w) {
        x.decrementIndegree();
        if(x.getIndegree() == 0)
          q.add(x);
      }
    }
    
    if(counter != adjCopy.size())
      throw new CycleException();

    return finalProduct;
  }
  
  public int size() { return adjMatrix.size(); }
}
