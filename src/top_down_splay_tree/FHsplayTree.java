package top_down_splay_tree;

import cs_1c.*;

/**
 * Top-Down Splaying Tree.
 * Created by diazagasatya on 10/30/17.
 */
public class FHsplayTree<E extends Comparable<? super E>>
      extends FHsearch_tree<E> {

   /**
    * Default constructor to super class.
    */
   public FHsplayTree() { super(); }


   // Public methods starts here------------------------
   /**
    * Splay Tree Insertion, where the insertion data will always be the root.
    * @param x           Generic data
    * @return Boolean         Insertion Validation
    */
   @Override
   public boolean insert(E x) {

      int compareResult;

      if(mRoot == null) {
         mSize++;
         mRoot = new FHs_treeNode<>(x, null, null);
         return true;
      }
      else {
         mRoot = splay(mRoot, x);
      }

      compareResult = x.compareTo(mRoot.data);

      if(compareResult < 0) {
         mSize++;
         mRoot = new FHs_treeNode<>(x, mRoot.lftChild, mRoot);
         return true;
      }
      else if (compareResult > 0) {
         mSize++;
         mRoot = new FHs_treeNode<>(x, mRoot, mRoot.rtChild);
         return true;
      }
      else
         return false;
   }

   /**
    * Splay Tree remove where the x value will be splay to the root and
    * be replaced by the left subtree's max as the new root.
    * @param x         Generic data
    * @return Boolean         Remove Validation
    */
   @Override
   public boolean remove(E x) {

      FHs_treeNode newNode;

      if(mRoot == null) {
         return false;
      }

      mRoot = splay(mRoot, x);

      if(x.compareTo(mRoot.data) != 0) {
         return false;
      }

      if(mRoot.lftChild == null) {
         mRoot = mRoot.rtChild;
      }
      else {
         newNode = mRoot.lftChild;
         // Bring max to the top of the list
         newNode = splay(newNode, x);
         newNode.rtChild = mRoot.rtChild;
         // Set the newNode
         mRoot = newNode;
      }
      return true;
   }

   /**
    * Show the current tree's root for debugging and proof.
    * @return data         Current Root's data
    */
   public E showRoot() {
      if(mRoot == null) {
         return null;
      }
      return mRoot.data;
   }

   // Private helper methods------------------------

   /***
    * Splay Algorithm that will make the requested data to become the root
    * of the tree. If tree does not contain requested data, the "closest" data
    * will be the root of the tree.
    * @param root         The tree's root for splaying
    * @param x         Generic data
    * @return root         New root splayed
    */
   protected FHs_treeNode<E> splay( FHs_treeNode<E> root, E x ) {
      // Initialize the rightTree, leftTree, rightTreeMin & leftTreeMax to null
      FHs_treeNode<E> rightTree = new FHs_treeNode<>();
      FHs_treeNode<E> leftTree = new FHs_treeNode<>();
      FHs_treeNode<E> rightTreeMin = new FHs_treeNode<>();
      FHs_treeNode<E> leftTreeMax = new FHs_treeNode<>();
      int comparisonResult;

      while(root != null) {

         comparisonResult = x.compareTo(root.data);

         if(comparisonResult < 0) {

            if(root.lftChild == null) {
               break;
            }

            // Zig-Zig condition, do a single left rotation at root
            if(x.compareTo(root.lftChild.data) < 0) {
               root = rotateWithLeftChild(root);
               // check new root's left child for x
               if(root.lftChild == null) {
                  break;
               }
            }
            // Add root to rightTree at it's minimum node
            if(rightTree.myRoot == null) {
               rightTree.myRoot = root;
               rightTreeMin = rightTree.myRoot;
            }
            else {
               rightTreeMin.lftChild = root;
               rightTreeMin = rightTreeMin.lftChild;
            }

            // Update the new working root to root's left child
            root = root.lftChild;
         }
         else if(comparisonResult > 0) {

            if(root.rtChild == null) {
               break;
            }

            // Zig-Zig condition, do a single right rotation at root
            if(x.compareTo(root.rtChild.data) > 0) {
               root = rotateWithRightChild(root);
               // check new root's right child for x
               if(root.rtChild == null) {
                  break;
               }
            }
            // Add root to leftTree at it's maximum node
            if(leftTree.myRoot == null) {
               leftTree.myRoot = root;
               leftTreeMax = leftTree.myRoot;
            }
            else {
               leftTreeMax.rtChild = root;
               leftTreeMax = leftTreeMax.rtChild;
            }

            // Update the new working root to root's right child
            root = root.rtChild;
         }
         else {
            break;
         }
      }

      // Reassemble
      if(leftTree.myRoot != null) {
         leftTreeMax.rtChild = root.lftChild;
         root.lftChild = leftTree.myRoot;
      }

      if(rightTree.myRoot != null) {
         rightTreeMin.lftChild = root.rtChild;
         root.rtChild = rightTree.myRoot;
      }
      //Update the master root
      mRoot = root;

      return root;
   }

   /**
    * A rotation of a tree node of it's left child, making it the root.
    * @param k2      Base node for rotation
    * @return k1      Left child of base node
    */
   protected FHs_treeNode<E> rotateWithLeftChild( FHs_treeNode<E> k2 ) {
      FHs_treeNode<E> k1 = k2.lftChild;
      k2.lftChild = k1.rtChild;
      k1.rtChild = k2;
      return k1;
   }

   /**
    * A rotation of a tree node of it's right child, making it the root.
    * @param k2       Base node for rotation
    * @return k1      Right child of base node
    */
   protected FHs_treeNode<E> rotateWithRightChild( FHs_treeNode<E> k2 ) {
      FHs_treeNode<E> k1 = k2.rtChild;
      k2.rtChild = k1.lftChild;
      k1.lftChild = k2;
      return k1;
   }

   /**
    * Splay the requested data to the root of the tree and return it.
    * @param root      Root tree
    * @param x         Generic data
    * @return root         Splayed x as root
    */
   @Override
   protected FHs_treeNode<E> find( FHs_treeNode<E> root, E x ) {

      if (root == null) {
         return null;
      }

      root = splay(root, x);

      if(root.data != x) {
         return null;
      }

      return root;
   }




}
