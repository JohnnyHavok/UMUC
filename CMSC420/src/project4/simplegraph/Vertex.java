/**
 * Author: Justin Smith
 * Course: CMSC420.6980
 * Date: Dec 4, 2010
 * Project: Project 4
 * File: Vertex.java
 * Development Environment:  Apple Mac OS X 10.6.5
 *                           Eclipse 3.6 / Java 1.6.0_22
 */
package simplegraph;

import java.util.ArrayList;

/**
 * TODO:  Build interface from class and document.
 * 
 * @author jsmith
 *
 * @param <V>
 */
public class Vertex<V> implements Cloneable {
  private int indegree, topSortNo;
  private V ref;
  
  private ArrayList<Vertex<V>> edges;
  
  public Vertex(V ref) {
    this.ref = ref;
    indegree = 0;
    topSortNo = -1;
    edges = new ArrayList<Vertex<V>>();
  }
  
  public void addEdge(Vertex<V> v) {
    if(!edges.contains(v))
      edges.add(v);
  }
  
  public void removeEdge(Vertex<V> v) {
    if(edges.contains(v))
      edges.remove(v);
  }
  
  public void clearEdges() { 
    edges = new ArrayList<Vertex<V>>(); 
  }
  
  public V getReference() { return ref; }
  
  public boolean containEdge(Vertex<V> v) { return edges.contains(v); }
  
  public ArrayList<Vertex<V>> getAllEdges() { return edges; }
  
  public void decrementIndegree()  { indegree--;  }
  public void incrementIndegree()  { indegree++;  }
  public void decrementTopSortNo() { topSortNo--; }
  public void incrementTopSortNo() { topSortNo++; }
  
  public int getEdgeCount() { return edges.size(); }
  public int getIndegree()  { return indegree;     }
  public int getTopSortNo() { return topSortNo;    }

  public void setIndegree(int indegree)   { this.indegree  = indegree;  }
  public void setTopSortNo(int topSortNo) { this.topSortNo = topSortNo; }
  
  @Override
  public Vertex<V> clone() {
    /**
     * Provides a deep copy of this vertex object.  Not sure if this is an 
     * appropriate use of the standard clone() abstract idea since most clone
     * methods in the Javadoc seem to say "shallow copy" 
     */
    Vertex<V> clone = new Vertex<V>(this.ref);
    clone.indegree = this.indegree;
    clone.topSortNo = this.topSortNo;
    clone.edges = new ArrayList<Vertex<V>>();
    
    for(Vertex<V> v : this.edges)
      clone.edges.add(v);
    
    return clone;
  }
}