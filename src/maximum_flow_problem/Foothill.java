package maximum_flow_problem;

// --------------------------------------------------------------------
// CIS 1C Assignment #9

public class Foothill
{
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      double finalFlow;

      // build graph
      FHflowGraph<String> myG = new FHflowGraph<String>();

      myG.addEdge("s","a", 3);    myG.addEdge("s","b", 2);
      myG.addEdge("a","b", 1);    myG.addEdge("a","c", 3);
      myG.addEdge("a","d", 4);
      myG.addEdge("b","d", 2);
      myG.addEdge("c","t", 2);
      myG.addEdge("d","t", 3);

      // show the original flow graph
      myG.showResAdjTable();
      myG.showFlowAdjTable();

      myG.setStartVert("s");
      myG.setEndVert("t");
      finalFlow = myG.findMaxFlow();

      System.out.println("Final flow: " + finalFlow);

      myG.showResAdjTable();
      myG.showFlowAdjTable();
   }
}

/*--------------------------Run Starts Here------------------------------
------------------------
Adj List for a: b(1.0) s(0.0) c(3.0) d(4.0)
Adj List for b: a(0.0) s(0.0) d(2.0)
Adj List for s: a(3.0) b(2.0)
Adj List for c: a(0.0) t(2.0)
Adj List for d: a(0.0) b(0.0) t(3.0)
Adj List for t: c(0.0) d(0.0)

------------------------
Adj List for a: b(0.0) c(0.0) d(0.0)
Adj List for b: d(0.0)
Adj List for s: a(0.0) b(0.0)
Adj List for c: t(0.0)
Adj List for d: t(0.0)
Adj List for t:

Final flow: 5.0
------------------------
Adj List for a: b(1.0) s(3.0) c(1.0) d(3.0)
Adj List for b: a(0.0) s(2.0) d(0.0)
Adj List for s: a(0.0) b(0.0)
Adj List for c: a(2.0) t(0.0)
Adj List for d: a(1.0) b(2.0) t(0.0)
Adj List for t: c(2.0) d(3.0)

------------------------
Adj List for a: b(0.0) c(2.0) d(1.0)
Adj List for b: d(2.0)
Adj List for s: a(3.0) b(2.0)
Adj List for c: t(2.0)
Adj List for d: t(3.0)
Adj List for t:


Process finished with exit code 0

----------------------------Run Ends Here------------------------------*/
