package binary_search_tree;

import cs_1c.*;
import java.util.*;


public class FHlazySearchTree<E extends Comparable< ? super E > >
      implements Cloneable
{
   int mSizeHard;
   protected int mSize;
   protected FHlazySTNode<E> mRoot;
   protected ArrayList<E> garbageCollection; // data reference to hard remove

   public FHlazySearchTree() { clear(); }
   public boolean empty() { return (mSize == 0); }
   public int size() { return mSize; }
   public void clear() {
      mSize = 0; mRoot = null;
      garbageCollection = new ArrayList<>(); }
   public int showHeight() { return findHeight(mRoot, -1); }


   /**
    * Hard removing all nodes that are flagged "deleted".
    * @return Boolean         Validation of successful hard removal
    */
   public boolean collectGarbage() {

      // The real size with the "deleted" and un-deleted nodes
      int oldSize = mSizeHard;

      // Look for the data that was softly deleted in the BST
      if(mSize < mSizeHard) {
         for(int a = 0; a < garbageCollection.size(); a++) {
            mRoot = collectGarbage(mRoot, garbageCollection.get(a));
            mRoot = removeHard(mRoot, garbageCollection.get(a));
         }
      }
      // Clear out garbage collection for re-use
      garbageCollection = new ArrayList<>();
      // Should return true if "deleted" nodes are hard removed correctly
      return (oldSize != mSizeHard);
   }

   public E findMin()
   {
      if (mRoot == null)
         throw new NoSuchElementException();
      return findMin(mRoot).data;
   }

   public E findMax()
   {
      if (mRoot == null)
         throw new NoSuchElementException();
      return findMax(mRoot).data;
   }

   public E find( E x )
   {
      FHlazySTNode<E> resultNode;
      resultNode = find(mRoot, x);
      if (resultNode == null)
         throw new NoSuchElementException();
      return resultNode.data;
   }
   public boolean contains(E x)  { return find(mRoot, x) != null; }

   public boolean insert( E x )
   {
      int oldSize = mSize;
      mRoot = insert(mRoot, x);
      return (mSize != oldSize);
   }

   /**
    * A public remove method that calls the recursive private soft remove().
    * @param x         Data remove request
    * @return Boolean      Success validation of remove method
    */
   public boolean remove( E x )
   {
      int oldSize = mSize;
      remove(mRoot, x);
      return (mSize != oldSize);
   }

   public < F extends Traverser<? super E > >
   void traverse(F func)
   {
      traverse(func, mRoot);
   }

   public Object clone() throws CloneNotSupportedException
   {
      FHlazySearchTree<E> newObject = (FHlazySearchTree<E>)super.clone();
      newObject.clear();  // can't point to other's data

      newObject.mRoot = cloneSubtree(mRoot);
      newObject.mSize = mSize;

      return newObject;
   }

   // private helper methods ----------------------------------------
   /**
    * Recursively transversing the tree and returning the node that is
    * flagged "deleted".
    * @param root      Current Root to Traverse
    * @param x      The flagged node's data to remove
    * @return root      x's Node Object
    */
   protected FHlazySTNode<E> collectGarbage(FHlazySTNode<E> root, E x) {
      int comparisonResult;

      if(root == null) {
         return null;
      }
      // Traverse the BST looking for data x
      comparisonResult = x.compareTo(root.data);
      if (comparisonResult < 0) {
         root.lftChild = collectGarbage(root.lftChild, x);
      }
      else if (comparisonResult > 0) {
         root.rtChild = collectGarbage(root.rtChild, x);
      }
      // root data x found, return it for hard remove
      return root;

   }

   /**
    * Traversing the current root given to find Node's object that
    * contains value x, and hard removing it.
    * @param root      Current Root to Traverse
    * @param x      The flagged node's data to remove
    * @return root   The newly connected Node's Object
    */
   protected FHlazySTNode<E> removeHard(FHlazySTNode<E> root, E x) {

      int compareResult;

      // If root is null return silently
      if (root == null) {
         return null;
      }

      compareResult = x.compareTo(root.data);
      if (compareResult < 0) {
         root.lftChild = removeHard(root.lftChild, x);
      }
      else if (compareResult > 0) {
         root.rtChild = removeHard(root.rtChild , x);
      }
      // found the node
      else if (root.lftChild != null && root.rtChild != null)
      {
         root.data = findMin(root.rtChild).data;
         // Make sure to change the deleted flag
         root.deleted = false;
         // Remove the old node of root right child's minimum
         root.rtChild = removeHard(root.rtChild, root.data);
      }
      else
      {
         root =
               (root.lftChild != null)? root.lftChild : root.rtChild;
         mSizeHard--;
      }

      return root;
   }

   protected FHlazySTNode<E> findMin( FHlazySTNode<E> root )
   {
      if (root == null)
         return null;
      if (root.lftChild == null) {
         if (root.deleted == false) {
            return root;
         }
         // If current node "deleted"
         else {
            // And there's a right child
            if(root.rtChild != null) {
               return findMin(root.rtChild);
            }
            // If there's no right child
            return root.myRoot;
         }
      }
      return findMin(root.lftChild);
   }

   protected FHlazySTNode<E> findMax( FHlazySTNode<E> root )
   {
      if (root == null)
         return null;
      if (root.rtChild == null) {
         if (root.deleted == false) {
            return root;
         }
         // If current node "deleted" return parent's node
         return root.myRoot;
      }

      return findMax(root.rtChild);
   }

   protected FHlazySTNode<E> insert( FHlazySTNode<E> root, E x )
   {
      int compareResult;  // avoid multiple calls to compareTo()

      if (root == null)
      {
         mSize++;
         mSizeHard++;
         return new FHlazySTNode<E>(x, null, null);
      }

      compareResult = x.compareTo(root.data);
      if ( compareResult < 0 )
         root.lftChild = insert(root.lftChild, x);
      else if ( compareResult > 0 )
         root.rtChild = insert(root.rtChild, x);
      else {
         // This root data == x
         if(root.deleted == true) {
            root.deleted = false;
            mSize++;
            // Remove this root data in garbage collection ArrayList
            if(garbageCollection.contains(root.data)) {
               garbageCollection.remove(root.data);
            }
         }
      }

      return root;
   }

   /**
    * Lazy removal by simply finding the node in the BST and flag the deleted
    * member as true.
    * @param root         Current Tree Node reference
    * @param x         Data of the searched node to remove
    */
   protected void remove( FHlazySTNode<E> root, E x  )
   {
      int compareResult;  // avoid multiple calls to compareTo()

      if (root == null) // do a silent return
         return;

      // Look for x in the BST
      compareResult = x.compareTo(root.data);
      if ( compareResult < 0 )
         remove(root.lftChild, x);
      else if ( compareResult > 0 )
         remove(root.rtChild, x);
      // Found the node
      else
      {
         root.deleted = true;
         // Add root to the garbage collection array list for hard remove(s)
         if(!(garbageCollection.contains(root.data))) {
            garbageCollection.add(root.data);
            mSize--;
         }
      }
   }


   protected <F extends Traverser<? super E>>
   void traverse(F func, FHlazySTNode<E> treeNode)
   {
      if (treeNode == null)
         return;

      traverse(func, treeNode.lftChild);
      // If tree node is deleted don't print
      if(treeNode.deleted == false) {
         func.visit(treeNode.data);
      }
      traverse(func, treeNode.rtChild);
   }

   protected FHlazySTNode<E> find( FHlazySTNode<E> root, E x )
   {
      int compareResult;  // avoid multiple calls to compareTo()

      if (root == null)
         return null;

      compareResult = x.compareTo(root.data);
      if (compareResult < 0)
         return find(root.lftChild, x);
      if (compareResult > 0)
         return find(root.rtChild, x);
      else { // Found
         if(root.deleted = true) { // "Not really here"
            return null;
         }
      }
      return root; // found and not "deleted"
   }

   protected FHlazySTNode<E> cloneSubtree(FHlazySTNode<E> root)
   {
      FHlazySTNode<E> newNode;
      if (root == null)
         return null;

      // If the root node is deleted
      if (root.deleted == true) {
         if (root.lftChild != null) {
            return cloneSubtree(root.lftChild);
         }
         else if (root.rtChild != null) {
            return cloneSubtree(root.rtChild);
         }
         else // both right child and left child is null
            return null; // can't clone "deleted" subtree
      }

      // does not set myRoot which must be done by caller
      newNode = new FHlazySTNode<E>
            (
                  root.data,
                  cloneSubtree(root.lftChild),
                  cloneSubtree(root.rtChild)
            );
      return newNode;
   }

   protected int findHeight( FHlazySTNode<E> treeNode, int height )
   {
      int leftHeight, rightHeight;
      if (treeNode == null)
         return height;
      height++;
      leftHeight = findHeight(treeNode.lftChild, height);
      rightHeight = findHeight(treeNode.rtChild, height);
      return (leftHeight > rightHeight)? leftHeight : rightHeight;
   }

   // Accessor methods ---------------------------------------------------

   /**
    * Accessor of hard size
    * @return mSizeHard         Number of "hard" nodes
    */
   public int sizeHard() { return mSizeHard; }

   class FHlazySTNode<E extends Comparable< ? super E > >
   {
      // use public access so the tree or other classes can access members
      public boolean deleted = false;
      public FHlazySTNode<E> lftChild, rtChild;
      public E data;
      public FHlazySTNode<E> myRoot;  // needed to test for certain error

      public FHlazySTNode( E d, FHlazySTNode<E> lft, FHlazySTNode<E> rt )
      {
         lftChild = lft;
         rtChild = rt;
         data = d;
      }

      public FHlazySTNode()
      {
         this(null, null, null);
      }

      // function stubs -- for use only with AVL Trees when we extend
      public int getHeight() { return 0; }
      boolean setHeight(int height) { return true; }
   }

}


