package timing_matrix_multiplication;

import cs_1c.*;

import java.util.ListIterator;

/**
 * Created by diazagasatya on 10/18/17.
 */
public class SparseMatWMult extends SparseMat<Double> {

   SparseMat<Double> sparseMatrix;

   // constructor ??
   public SparseMatWMult(int rowSize, int columnSize, Double defaultValue) {
      sparseMatrix = new SparseMat<>(rowSize,columnSize,defaultValue);
   }

   // multiply:
   public boolean matMult(SparseMatWMult matA, SparseMatWMult matB) {
      if (!(dimensionCompatibility(matA, matB))) {
         System.out.println("Sparse Matrix must at least have 1x1 size or more");
         return false;
      }
      MatNode currentNodeA, currentNodeB, currentNodeC;
      int matARowSize = matA.sparseMatrix.rowSize;
      int matBColumnSize = matB.sparseMatrix.columnSize;
      double result = 0;

      for (int a = 0; a < matARowSize; a++) {
         FHlinkedList<MatNode> referenceRowMatA = matA.rows.get(a);
         ListIterator<MatNode> iteratorMatA = referenceRowMatA.listIterator();
         while (iteratorMatA.hasNext()) {
            currentNodeA = iteratorMatA.next();
            System.out.println(currentNodeA.data);
            for (int b = 0; b < matBColumnSize; b++) {
               FHlinkedList<MatNode> referenceRowMatB = matB.rows.get(b);
               ListIterator<MatNode> iteratorMatB = referenceRowMatB.listIterator();
               FHlinkedList<MatNode> referenceRowResult = this.rows.get(b);
               ListIterator<MatNode> iteratorResult = referenceRowResult.listIterator();
               if (iteratorMatB.hasNext()) {
                  currentNodeB = iteratorMatB.next();
                  if (currentNodeA.col == b) {
                     System.out.println("reach here");
                     result += currentNodeA.data * currentNodeB.data;
                  }
               }
            }
         }
      }
      return true;
   }


   public boolean dimensionCompatibility(SparseMatWMult matA,SparseMatWMult matB) {
      return !(matA.rowSize < 1 || matB.rowSize < 1);
   }
}
