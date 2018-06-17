package top_down_splay_tree;

// CIS 1C Assignment #5
// Instructor Solution Client

import cs_1c.*;

class PrintObject<E> implements Traverser<E>
{
   public void visit(E x)
   {
      System.out.print( x + " ");
   }
};

//------------------------------------------------------
public class Foothill
{
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      int k;
      FHsplayTree<Integer> searchTree = new FHsplayTree<Integer>();
      PrintObject<Integer> intPrinter = new PrintObject<Integer>();

      searchTree.traverse(intPrinter);

      System.out.println( "Initial size: " + searchTree.size() );
      for (k = 1; k <= 32; k++)
         searchTree.insert(k);
      System.out.println( "New size: " + searchTree.size() );

      System.out.println( "\nTraversal");
      searchTree.traverse(intPrinter);
      System.out.println();


      for (k = -1; k < 10; k++)
      {
         try
         {
            searchTree.find(k);
         }
         catch( Exception e )
         {
            System.out.println( " oops " );
         }
         System.out.println( "splay " + k + " --> root: "
               + searchTree.showRoot()
               + " height: " + searchTree.showHeight() );
      }

      System.out.println( "\n\nTesting for contains method ------------\n");
      for ( int c = 10; c > -3; c--) {

         try
         {
            // alternative to find() - different error return
            searchTree.contains(c);
         }
         catch ( Exception e )
         {
            System.out.println( " null found ");
         }
         System.out.println( "splay " + c + " --> root: "
               + searchTree.showRoot()
               + " height: " + searchTree.showHeight() );
      }

      System.out.println( "\n\nTesting for remove method ------------\n");
      for ( int d = 0; d < 30; d++) {

         try
         {
            searchTree.remove(d);
         }
         catch ( Exception e )
         {
            System.out.println(  d + " not found ");
         }
         System.out.println( "splay " + d + " --> root: "
               + searchTree.showRoot()
               + " height: " + searchTree.showHeight() );
      }
   }
}

/*----------------------------Paste of Run---------------------------------
Initial size: 0
Initial size: 0
New size: 32

Traversal
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32
 oops
splay -1 --> root: 1 height: 16
 oops
splay 0 --> root: 1 height: 16
splay 1 --> root: 1 height: 16
splay 2 --> root: 2 height: 9
splay 3 --> root: 3 height: 6
splay 4 --> root: 4 height: 6
splay 5 --> root: 5 height: 5
splay 6 --> root: 6 height: 6
splay 7 --> root: 7 height: 6
splay 8 --> root: 8 height: 7
splay 9 --> root: 9 height: 8


Testing for contains method ------------

splay 10 --> root: 10 height: 9
splay 9 --> root: 9 height: 8
splay 8 --> root: 8 height: 8
splay 7 --> root: 7 height: 9
splay 6 --> root: 6 height: 10
splay 5 --> root: 5 height: 11
splay 4 --> root: 4 height: 12
splay 3 --> root: 3 height: 13
splay 2 --> root: 2 height: 14
splay 1 --> root: 1 height: 15
splay 0 --> root: 1 height: 15
splay -1 --> root: 1 height: 15
splay -2 --> root: 1 height: 15


Testing for remove method ------------

splay 0 --> root: 1 height: 15
splay 1 --> root: 2 height: 14
splay 2 --> root: 3 height: 13
splay 3 --> root: 4 height: 12
splay 4 --> root: 5 height: 11
splay 5 --> root: 6 height: 10
splay 6 --> root: 7 height: 9
splay 7 --> root: 8 height: 8
splay 8 --> root: 9 height: 7
splay 9 --> root: 10 height: 6
splay 10 --> root: 13 height: 5
splay 11 --> root: 13 height: 5
splay 12 --> root: 13 height: 5
splay 13 --> root: 17 height: 4
splay 14 --> root: 15 height: 5
splay 15 --> root: 17 height: 4
splay 16 --> root: 17 height: 4
splay 17 --> root: 25 height: 3
splay 18 --> root: 21 height: 4
splay 19 --> root: 21 height: 4
splay 20 --> root: 21 height: 4
splay 21 --> root: 25 height: 3
splay 22 --> root: 23 height: 4
splay 23 --> root: 25 height: 3
splay 24 --> root: 25 height: 3
splay 25 --> root: 29 height: 2
splay 26 --> root: 27 height: 3
splay 27 --> root: 29 height: 2
splay 28 --> root: 29 height: 2
splay 29 --> root: 31 height: 1

Process finished with exit code 0

------------------------------End of Run---------------------------------*/