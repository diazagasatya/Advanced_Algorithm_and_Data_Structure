package timing_matrix_multiplication;

import cs_1c.*;
import java.util.*;

/**
 * Created by diazagasatya on 10/9/17.
 */
class SparseMat<E> implements Cloneable {

   protected int rowSize, columnSize;
   protected E defaultValue;
   protected FHarrayList<FHlinkedList<MatNode>> rows;

   /**
    * A default constructor that creates a FHArrayList of 5 rows
    * with an empty sparse matrix of linked list internally.
    */
   public SparseMat() {
      rowSize = 5;
      allocateEmptyMatrix();
   }

   /**
    * A constructor that stores rowSize, columnSize, defaultValue
    * and instantiate a new empty Sparse Matrix
    * @param numRows       Number of Rows
    * @param numCols       Number of Columns
    * @param defaultVal       subset_sum_problem.Default value given
    */
   public SparseMat(int numRows, int numCols, E defaultVal) {

      if(!(validIndex(numRows)) && (validIndex(numCols))) {
         return;
      }
      // Establishes the sizes and default value
      rowSize = numRows;
      columnSize = numCols;
      defaultValue = defaultVal;

      allocateEmptyMatrix();
   }

   /**
    * Allocate all the memory of an empty matrix
    */
   public void allocateEmptyMatrix() {
      // Create an array list of references of size numRows
      rows = new FHarrayList<>(rowSize);
      FHlinkedList<MatNode> list;

      // Initialize linked list in every rows
      for(int a = 0; a < rowSize; a++) {
         list = new FHlinkedList<>();
         MatNode newNode = new MatNode();
         list.add(newNode);
         rows.add(list);
      }
   }

   /**
    * An accessor that returns the object stored in row r and column c.
    * @param r       Row number
    * @param c       Column number
    * @return returnObject       Generic object of given row and column
    * @throws IndexOutOfBoundsException
    */
   public E get(int r, int c) throws IndexOutOfBoundsException {
      // Throws invalid indexes
      if(!(indexViolation(r,c))) {
         throw new IndexOutOfBoundsException();
      }
      FHlinkedList<MatNode> referenceRow = rows.get(r);
      ListIterator<MatNode> iteration;
      MatNode returnObject;

      for(iteration = referenceRow.listIterator(); iteration.hasNext(); ) {
         returnObject = iteration.next();
         if(returnObject.col == c) {
            return returnObject.data;
         }
      }
      return defaultValue;
   }

   /**
    * Validation for matrix row and column bounds
    * @param row       Row number
    * @param column       Column number
    * @return validation       Boolean validation value
    */
   public boolean indexViolation(int row, int column) {
      boolean validation = false;
      if(row > rowSize || column > columnSize) {
         return validation;
      }
      else if(row < 0 || column < 0) {
         return validation;
      }
      validation = true;
      return validation;
   }


   /**
    * A mutator that set the data value of an object by the given row and column index to a given data x.
    * @param r       Row number
    * @param c       Column number
    * @param x       Integer Value
    * @return validChange       Validity of mutator
    */
   public boolean set(int r, int c, E x) {
      if(!(indexViolation(r,c))) { // Throws invalid indexes
         return false;
      }
      FHlinkedList<MatNode> referenceRow = rows.get(r);
      ListIterator<MatNode> iteration = referenceRow.listIterator();
      MatNode currentObject;
      if(!(x.equals(defaultValue))) {
         while(iteration.hasNext()) {
            currentObject = iteration.next();
            if(currentObject.col == c && (currentObject.data != defaultValue)) {
               currentObject.data = x;
               return true;
            }
         }
         MatNode newNode = new MatNode(c,x);
         referenceRow.add(newNode);
         return true;
      }
      else if(x.equals(defaultValue)) {
         while(iteration.hasNext()) {
            currentObject = iteration.next();
            if(currentObject.col == c && (currentObject.data != defaultValue)) {
               referenceRow.remove(currentObject);
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Resetting the LinkedLists without changing the size of Sparse Matrix
    */
   public void clear() {
      for(int a = 0; a < rows.size(); a++) {
         rows.get(a).clear();
      }
   }

   /**
    * A display method from a given set of ranges
    * @param start       Starting row
    * @param size       Number of columns
    */
   public void showSubSquare(int start, int size) {
      int length = start + size - 1;
      String defaultValueString = String.format("%6.1f", defaultValue);
      Boolean internalCondition = false;
      MatNode currentNode;

      for(int a = start; a <= length; a++) {
         FHlinkedList<MatNode> referenceRow = rows.get(a);
         for(int b = start; b <= length; b++) {
            ListIterator<MatNode> iteration = referenceRow.listIterator();
            while(iteration.hasNext()) {
               currentNode = iteration.next();
               if(currentNode.col == b) {
                  if(currentNode.data == null) {
                     System.out.print(defaultValueString);
                     internalCondition = true;
                  }
                  else {
                     System.out.print(String.format("%6.1f", currentNode.data));
                     internalCondition = true;
                  }
               }
            }
            if(!internalCondition) {
               System.out.print(defaultValueString);
            }
            internalCondition = false;
         }
         System.out.println();
      }
   }

   /**
    * A deep copy of calling Sparse Matrix
    * @return copySparse       Sparse Matrix copy
    * @throws CloneNotSupportedException
    */
   protected Object clone()  {
      SparseMat<E> copySparse;
      int callingObjectSize = this.rowSize;
      MatNode currentOldNode, newNode;
      try {
         // Shallow copy & instantiate new ArrayList
         copySparse = (SparseMat<E>)super.clone();
         copySparse.rows = new FHarrayList<>(callingObjectSize);
         FHlinkedList<MatNode> list;
         for(int a = 0; a < callingObjectSize; a++) {
            list = new FHlinkedList<>();
            copySparse.rows.add(list);
         }
         for(int a = 0; a < callingObjectSize; a++) { // Deep copy of Nodes
            FHlinkedList<MatNode> referenceOldMat = this.rows.get(a);
            FHlinkedList<MatNode> referenceNewMat = copySparse.rows.get(a);
            ListIterator<MatNode> iteration = referenceOldMat.listIterator();
            while(iteration.hasNext()) {
               currentOldNode = iteration.next();
               newNode = (MatNode)currentOldNode.clone();
               referenceNewMat.add(newNode);
            }
         }
      }
      catch(CloneNotSupportedException e) {
         return null;
      }
      return copySparse;
   }

   /**
    * One object of this inner protected class represents
    * the column index and data value
    */
   protected class MatNode implements Cloneable {
      // protected enables us to safely make col/data public
      public int col;
      public E data;

      // we need a default constructor for lists
      MatNode() {
         col = 0;
         data = null;
      }

      MatNode(int cl, E dt) {
         col = cl;
         data = dt;
      }

      public Object clone() throws CloneNotSupportedException {
         // shallow copy
         MatNode newObject = (MatNode)super.clone();
         return newObject;
      }
   }

   /**
    * Testing the validity of index given from client is not < 0
    * @param index       Number of desired index
    * @return validity       Validity of index
    */
   public boolean validIndex(int index) {
      boolean validity = false;

      // First validation
      if(!((index < 1)))
         validity = true;

      return validity;
   }
}
