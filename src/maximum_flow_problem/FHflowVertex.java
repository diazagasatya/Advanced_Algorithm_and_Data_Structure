package maximum_flow_problem;

import java.util.*;
import cs_1c.*;

// --- assumes definition of simple class Pair<E, F> in cis27a

// --- FHflowVertex class --------------------------------------------------
public class FHflowVertex<E>
{
   public static Stack<Integer> keyStack = new Stack<Integer>();
   public static final int KEY_ON_DATA = 0, KEY_ON_DIST = 1;
   public static int keyType = KEY_ON_DATA;
   public static final double INFINITY = Double.MAX_VALUE;
   public HashSet< Pair<FHflowVertex<E>, Double> > flowAdjList
         = new HashSet< Pair<FHflowVertex<E>, Double> >();
   public HashSet< Pair<FHflowVertex<E>, Double> > resAdjList
         = new HashSet< Pair<FHflowVertex<E>, Double> >();
   public E data;
   public double dist;
   public FHflowVertex<E> nextInPath;  // for client-specific info

   public FHflowVertex( E x )
   {
      data = x;
      dist = INFINITY;
      nextInPath = null;
   }
   public FHflowVertex() { this(null); }

   public void addToFlowAdjList(FHflowVertex<E> neighbor, double cost)
   {
      flowAdjList.add( new Pair<FHflowVertex<E>, Double> (neighbor, cost) );
   }

   public void addToResAdjList(FHflowVertex<E> neighbor, double cost)
   {
      resAdjList.add( new Pair<FHflowVertex<E>, Double> (neighbor, cost) );
   }

   public boolean equals(Object rhs)
   {
      FHflowVertex<E> other = (FHflowVertex<E>)rhs;
      switch (keyType)
      {
         case KEY_ON_DIST:
            return (dist == other.dist);
         case KEY_ON_DATA:
            return (data.equals(other.data));
         default:
            return (data.equals(other.data));
      }
   }

   public int hashCode()
   {
      switch (keyType)
      {
         case KEY_ON_DIST:
            Double d = dist;
            return (d.hashCode());
         case KEY_ON_DATA:
            return (data.hashCode());
         default:
            return (data.hashCode());
      }
   }

   public void showResAdjList()
   {
      Iterator< Pair<FHflowVertex<E>, Double> > iter ;
      Pair<FHflowVertex<E>, Double> pair;

      System.out.print( "Adj List for " + data + ": ");
      for (iter = resAdjList.iterator(); iter.hasNext(); )
      {
         pair = iter.next();
         System.out.print( pair.first.data + "("
               + String.format("%3.1f", pair.second)
               + ") " );
      }
      System.out.println();
   }


   public void showFlowAdjList()
   {
      Iterator< Pair<FHflowVertex<E>, Double> > iter ;
      Pair<FHflowVertex<E>, Double> pair;

      System.out.print( "Adj List for " + data + ": ");
      for (iter = flowAdjList.iterator(); iter.hasNext(); )
      {
         pair = iter.next();
         System.out.print( pair.first.data + "("
               + String.format("%3.1f", pair.second)
               + ") " );
      }
      System.out.println();
   }

   public static boolean setKeyType( int whichType )
   {
      switch (whichType)
      {
         case KEY_ON_DATA:
         case KEY_ON_DIST:
            keyType = whichType;
            return true;
         default:
            return false;
      }
   }
   public static void pushKeyType() { keyStack.push(keyType); }
   public static void popKeyType() { keyType = keyStack.pop(); }
}
