package maximum_flow_problem;

import java.util.*;
import cs_1c.*;

public class FHflowGraph<E> {
   // the graph data is all here --------------------------
   protected HashSet< FHflowVertex<E> > vertexSet;

   // represents the start and end of the flow problem.
   protected FHflowVertex<E> startVert, endVert;

   // public graph methods --------------------------------
   public FHflowGraph ()
   {
      vertexSet = new HashSet< FHflowVertex<E> >();
   }

   public void addEdge(E source, E dest, double cost)
   {
      FHflowVertex<E> src, dst;

      // put both source and dest into vertex list(s) if not already there
      src = addToVertexSet(source);
      dst = addToVertexSet(dest);

      // add dest to source's adjacency list
      src.addToResAdjList(dst, cost); // forward edge
      dst.addToResAdjList(src,0); // reverse edge with 0 cost
      src.addToFlowAdjList(dst, 0); // cost should starts at 0
   }

   public void addEdge(E source, E dest, int cost)
   {
      addEdge(source,
            dest, (double)cost);
   }


   // adds vertex with x in it, and always returns ref to it
   public FHflowVertex<E> addToVertexSet(E x)
   {
      FHflowVertex<E> retVal, vert;
      boolean successfulInsertion;
      Iterator< FHflowVertex<E> > iter;

      // save sort key for client
      FHflowVertex.pushKeyType();
      FHflowVertex.setKeyType(FHflowVertex.KEY_ON_DATA);

      // build and insert vertex into master list
      retVal = new FHflowVertex<E>(x);
      successfulInsertion = vertexSet.add(retVal);

      if ( successfulInsertion )
      {
         FHflowVertex.popKeyType();  // restore client sort key
         return retVal;
      }

      // the vertex was already in the set, so get its ref
      for (iter = vertexSet.iterator(); iter.hasNext(); )
      {
         vert = iter.next();
         if (vert.equals(retVal))
         {
            FHflowVertex.popKeyType();  // restore client sort key
            return vert;
         }
      }

      FHflowVertex.popKeyType();  // restore client sort key
      return null;   // should never happen
   }

   public void clear()
   {
      vertexSet.clear();
   }


   // Printing methods ---------------------------------------------------
   public void showFlowAdjTable()
   {
      Iterator< FHflowVertex<E> > iter;

      System.out.println( "------------------------ ");
      for (iter = vertexSet.iterator(); iter.hasNext(); )
         (iter.next()).showFlowAdjList();
      System.out.println();
   }

   public void showResAdjTable()
   {
      Iterator< FHflowVertex<E> > iter;

      System.out.println( "------------------------ ");
      for (iter = vertexSet.iterator(); iter.hasNext(); )
         (iter.next()).showResAdjList();
      System.out.println();
   }

   // Initialization methods ---------------------------------------------
   public boolean setStartVert(E x) {

      Iterator<FHflowVertex<E>> iterator;
      FHflowVertex<E> tempVertex;

      if(x != null) {
         for(iterator = vertexSet.iterator(); iterator.hasNext(); ) {
            tempVertex = iterator.next();
            if(tempVertex.data.equals(x)) {
               startVert = tempVertex;
               return true;
            }
         }
      }

      return false;
   }

   public boolean setEndVert(E x) {

      Iterator<FHflowVertex<E>> iterator;
      FHflowVertex<E> tempVertex;

      if(x != null) {
         for(iterator = vertexSet.iterator(); iterator.hasNext(); ) {
            tempVertex = iterator.next();
            if(tempVertex.data.equals(x)) {
               endVert = tempVertex;
               return true;
            }
         }
      }

      return false;
   }

   // algorithms--------------------------------------------------------

   /**
    * Algorithm that seeks the maximum flow between two vertex in a network.
    * @return maxFlow      Maximum Flow
    */
   public double findMaxFlow() {

      // Loop until there are no more paths in the residual graph
      // from start to end
      double minCost;
      double maxFlow = 0;

      // find a possible path to end vertex from starting vertex
      while(establishNextFlowPath()) {
         // get the minimum flow on residual path
         minCost = getLimitingFlowOnResPath();

         maxFlow += minCost;

         // adjusting the residual and flow graphs
         adjustPathByCost(minCost);
      }

      return maxFlow;
   }

   /**
    * Helper method of main algorithm maximum flow,
    * uses the data and path just created to find the
    * limiting flow (minimum) of the residual path just found.
    * @return minCost       Smallest cost of flow
    */
   protected double getLimitingFlowOnResPath() {
      FHflowVertex<E> start, stop, vert, vertTwo;
      Stack< FHflowVertex<E> > pathStack = new Stack< FHflowVertex<E> >();
      double minCost, tempCost;
      start = startVert;
      stop = endVert;

      // Initialize mincost
      minCost = FHflowVertex.INFINITY;

      // trace path from endVert to startVert
      for (vert = stop; vert != start; vert = vert.nextInPath) {
         pathStack.push(vert);
      }
      pathStack.push(vert);

      while (true)
      {
         vert = pathStack.pop();
         vertTwo = pathStack.pop();
         tempCost = getCostOfResEdge(vert, vertTwo);

         if(tempCost < minCost) {
            minCost = tempCost;
         }

         if ( pathStack.empty() )
         {
            break;
         }
      }
      return minCost;
   }

   /**
    * Modify the costs of edges in both the residual graph and the flow graph.
    * @param cost      Smallest cost flow
    * @return boolean      Validation
    */
   protected boolean adjustPathByCost(double cost) {

      if(cost == 0) {
         return false;
      }

      // chase the path from the end to the start vertex
      FHflowVertex<E> start, stop, vert;
      Stack< FHflowVertex<E> > pathStack = new Stack< FHflowVertex<E> >();

      start = startVert;
      stop = endVert;

      // trace path from endVert to startVert
      for (vert = stop; vert != start; vert = vert.nextInPath)
         pathStack.push(vert);
      pathStack.push(vert);

      while(true) {
         vert = pathStack.pop();

         // re-adjust the cost based upon path found
         addCostToResEdge(vert.nextInPath, vert, -cost);
         addCostToResEdge(vert, vert.nextInPath, cost);
         addCostToFlowEdge(vert, vert.nextInPath, cost);

         if(pathStack.empty()) {
            break;
         }
      }

      return true;
   }

   /**
    * examines src's residual adjacency list to find dst
    * and then return the cost of the edge (src, dst).
    * @param src      Starting vertex
    * @param dst      A vertex in the network
    * @return costOfResidualEdge      Cost between two vertex
    */
   protected double getCostOfResEdge(FHflowVertex<E> src,
                                     FHflowVertex<E> dst) {

      Pair<FHflowVertex<E>, Double> findDist;
      Iterator<Pair<FHflowVertex<E>, Double>> iterator;
      double costOfResidualEdge = 0;

      for(iterator = src.resAdjList.iterator() ; iterator.hasNext(); ) {
          findDist = iterator.next();
          if(findDist.first == dst) {
             costOfResidualEdge = findDist.second;
          }
      }
      return costOfResidualEdge;
   }

   /**
    * Examines src's residual adjacency list to find dst and then
    * add/subtract cost to that edge.
    * @param src      Starting vertex
    * @param dst      A vertex in the network
    * @param cost      Smallest cost flow
    * @return boolean      Validation
    */
   protected boolean addCostToResEdge(FHflowVertex<E> src,
                                      FHflowVertex<E> dst, double cost) {

      if(src == null || dst == null) {
         return false;
      }

      Pair<FHflowVertex<E>, Double> findDist;
      Iterator<Pair<FHflowVertex<E>, Double>> iterator;

      // find dst and then add cost to that edge
      for(iterator = src.resAdjList.iterator() ; iterator.hasNext(); ) {
         findDist = iterator.next();
         if(findDist.first.data == dst.data) {
            findDist.second += cost;
         }
      }
      return true;
   }

   /**
    * Examines src's flow adjacency list to find dst and then adds
    * cost to that edge if not found subtract the flow.
    * @param src      Starting vertex
    * @param dst      A vertex in the network
    * @param cost      Smallest cost flow
    * @return boolean      Validation
    */
   protected boolean addCostToFlowEdge(FHflowVertex<E> src,
                                       FHflowVertex<E> dst, double cost) {

      boolean reverseEdge = true;

      if(src == null || dst == null) {
         return false;
      }

      Pair<FHflowVertex<E>, Double> findDist;
      Iterator<Pair<FHflowVertex<E>, Double>> iterator;

      for(iterator = dst.flowAdjList.iterator() ; iterator.hasNext(); ) {
         findDist = iterator.next();

         // find dst and then add cost to that edge
         if(findDist.first.data == src.data) {
            findDist.second += cost;
            reverseEdge = false;
         }
      }
      // if dst is not found in src's flowlist, update flow as reverse edge
      if(reverseEdge) {
         for (iterator = dst.flowAdjList.iterator(); iterator.hasNext(); ) {
            findDist = iterator.next();

            // find src and then subtract the flow with the cost
            if (findDist.first.data == src.data) {
               findDist.second -= cost;
            }
         }
      }
      return true;
   }

   /**
    * Find path that connects starting vertex and ending vertex defined.
    * @return boolean      Validation
    */
   protected boolean establishNextFlowPath() {
      FHflowVertex<E> w, s, v;
      Pair<FHflowVertex<E>, Double> edge;
      Iterator< FHflowVertex<E> > iter;
      Iterator< Pair<FHflowVertex<E>, Double> > edgeIter;
      Double costVW;
      Deque< FHflowVertex<E> > partiallyProcessedVerts
            = new LinkedList< FHflowVertex<E> >();

      s = startVert;
      if (s == null)
         return false;

      // initialize the vertex list and place the starting vert in p_p_v queue
      for (iter = vertexSet.iterator(); iter.hasNext(); ) {
         iter.next().dist = FHflowVertex.INFINITY;
      }

      s.dist = 0;
      partiallyProcessedVerts.addLast(s);

      // outer dijkstra loop
      while( !partiallyProcessedVerts.isEmpty() )
      {
         v = partiallyProcessedVerts.removeFirst();

         if(v.data == endVert.data) {
            return true;
         }

         // inner dijkstra loop: for each vert adj to v, lower its dist
         // to s if you can
         for (edgeIter = v.resAdjList.iterator(); edgeIter.hasNext(); )
         {
            edge = edgeIter.next();
            w = edge.first;
            costVW = edge.second;

            // skip if cost is equal to 0
            if(costVW == 0) {
               continue;
            }

            if ( v.dist + costVW < w.dist )
            {
               w.dist = v.dist + costVW;
               w.nextInPath = v;

               // w now has improved distance, so add w to PPV queue
               partiallyProcessedVerts.addLast(w);
            }
         }
      }
      return false;
   }
}
