package timing_matrix_multiplication;

import java.text.NumberFormat;
import java.util.*;


/**
 * Created by diazagasatya on 10/16/17.
 */
public class Foothill {
   // -------  main PART A --------------
   final static int MAT_SIZE = 100;

   // -------  proof of correctness --------------
   public static void main(String[] args) throws Exception {
      int r, randRow, randCol, arraySize;
      long startTime, stopTime;
      double randFrac, elapsedTime;
      double smallPercent;
      NumberFormat tidy = NumberFormat.getInstance(Locale.US);
      tidy.setMaximumFractionDigits(4);

      // non-sparse matrices
      double[][] mat, matAns;

      // allocate matrices
      mat = new double[MAT_SIZE][MAT_SIZE];
      matAns = new double[MAT_SIZE][MAT_SIZE];

      // generate small% of non-default values bet 0 and 1
      smallPercent = MAT_SIZE / 10. * MAT_SIZE;
      for (r = 0; r < smallPercent; r++) {
         // Allocate it to random position in matrix mat
         randRow = (int) (Math.random() * MAT_SIZE);
         randCol = (int) (Math.random() * MAT_SIZE);
         randFrac = Math.random(); // between 0-0.9
         mat[randRow][randCol] = randFrac;
      }

      // 10x10 submatrix in lower right
      matShow(mat, MAT_SIZE - 10, 10);

      startTime = System.nanoTime();
      matMult(mat, mat, matAns);
      stopTime = System.nanoTime();

      matShow(matAns, MAT_SIZE - 10, 10);

      System.out.println("\nSize = " + MAT_SIZE + " Mat. Mult. Elapsed Time: "
            + tidy.format((stopTime - startTime) / 1e9)
            + " seconds.");

      // Testing Matrix Multiplication
      double[][] mat1 = {{1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}};
      double[][] mat2 = {{1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}};
      double[][] result = new double[3][3];

      matMult(mat1, mat2, result);
      System.out.println("\nProof of Mat. Mult Works:");
      System.out.println("Example Matrix 1 & 2:");
      matShow(mat1, 0, mat1.length);
      System.out.println("          X");
      matShow(mat2, 0, mat2.length);
      System.out.println("          =");
      matShow(result, 0, result.length);

      // Question 1---------------------------------------
      // Find the smallest MAT_SIZE that gave you non-zero time
      elapsedTime = 0;
      System.out.println("\nCalculating... " +
            "\n1) This will be the smallest array size " +
            "that gives a non-zero time-------------");

      for (arraySize = MAT_SIZE; elapsedTime < 1; arraySize++) {

         // allocate new matrices
         mat = new double[arraySize][arraySize];
         matAns = new double[arraySize][arraySize];

         // generate small% of non-default values bet 0 and 1
         smallPercent = arraySize / 10. * arraySize;
         for (r = 0; r < smallPercent; r++) {
            // Allocate it to random position in matrix new size mat
            randRow = (int) (Math.random() * arraySize);
            randCol = (int) (Math.random() * arraySize);
            randFrac = Math.random(); // between 0-0.9
            mat[randRow][randCol] = randFrac;
         }

         // Time the Algorithm (Matrix Multiplication)
         startTime = System.nanoTime();
         matMult(mat, mat, matAns);
         stopTime = System.nanoTime();

         elapsedTime = (stopTime - startTime) / 1e9;

         if (elapsedTime >= 1) {
            System.out.println("\nSize = " + arraySize +
                  " Mat. Mult. Elapsed Time: "
                  + tidy.format(elapsedTime) + " seconds.");
         }
      }

      // Question 2---------------------------------------
      // What happened when you doubled M, tripled it, quadrupled it, etc?
      // Give several M values and their times in a table.
      // Maximum of 20% of total matrix increase in row/column size
      System.out.println("\n2) Calculating Mat. Mult of doubled, tripled & " +
            "quadrupled MAT_SIZE---------");
      int sizeMultiplication; // Starts of from doubling the size
      int maximumArraySize = 3000; // More than this will be inefficient

      for (sizeMultiplication = 2; sizeMultiplication < 5; sizeMultiplication++) {
         for (arraySize = MAT_SIZE; arraySize < maximumArraySize;
              arraySize *= sizeMultiplication) {
            // allocate new matrices
            mat = new double[arraySize][arraySize];
            matAns = new double[arraySize][arraySize];

            // generate small% of non-default values bet 0 and 1
            smallPercent = arraySize / 10. * arraySize;
            for (r = 0; r < smallPercent; r++) {
               // Allocate it to random position in matrix new size mat
               randRow = (int) (Math.random() * arraySize);
               randCol = (int) (Math.random() * arraySize);
               randFrac = Math.random(); // between 0-0.9
               mat[randRow][randCol] = randFrac;
            }

            // Time the Algorithm (Matrix Multiplication)
            startTime = System.nanoTime();
            matMult(mat, mat, matAns);
            stopTime = System.nanoTime();

            elapsedTime = (stopTime - startTime) / 1e9;

            System.out.print("\nSize = " + arraySize + " Mat. Mult. Elapsed Time: "
                  + tidy.format(elapsedTime) + " seconds.");
         }
      }
   }

   /**
    * A display method for the dynamic matrices to show square sub-matrix.
    * @param matA         Matrix Display
    * @param start         Anchor Point
    * @param size         Size of Square sub-matrix
    */
   public static void matShow(double[][] matA, int start, int size) {
      int length = start + size;

      for(int a = start; a < length; a++) {
         for(int b = start; b < length; b++) {
            System.out.print(String.format("%-8.2f", matA[a][b]));
         }
         System.out.println();
      }
   }

   /**
    * A Matrix Multiplication for Square Matrices.
    * @param matA         Matrix One
    * @param matB         Matrix Two
    * @param matC         Product Matrix
    */
   public static void matMult( double[][] matA,  double[][] matB,
                               double[][] matC) {

      if(!validSquareMatrix(matA, matB)) {
         throw new ArrayIndexOutOfBoundsException();
      }

      int rowSizeMatA = matA.length;
      int columnSizeMatB = matB.length; // Assuming matrix are square
      int rowSizeMatB = matB.length;
      double sum = 0;

      for(int a = 0; a < rowSizeMatA; a++) {
         for(int b = 0; b < columnSizeMatB; b++) {
            for(int c = 0; c < rowSizeMatB; c++) {
               sum += matA[a][c] * matB[c][b];
            }
            matC[a][b] = sum;
            sum = 0;
         }
      }
   }

   /**
    * Square Matrix Validation by checking that the first rows
    * of mat A and mat B are the same.
    * @param matA         Matrix One
    * @param matB         Matrix Two
    * @return Boolean
    */
   private static boolean validSquareMatrix(double[][] matA, double[][] matB) {

      // Assuming matA and matB are square
      int firstRow = 1;
      int columnSize = matB.length;
      double valueA, valueB;

      for(int a = 0; a < firstRow; a++) {
         for(int b = 0; b < columnSize; b++) {
            valueA = matA[a][b];
            valueB = matB[a][b];
            if(valueA == valueB) {
               continue;
            }
            else
               return false;
         }
      }
      return true;
   }
}

/*---------------------------Paste of 1st Run-------------------------------
0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.48    0.00    0.00
0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00
0.00    0.00    0.22    0.00    0.00    0.00    0.00    0.00    0.00    0.00
0.00    0.00    0.00    0.96    0.81    0.00    0.00    0.00    0.00    0.00
0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.32    0.00    0.00
0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00
0.88    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00
0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00
0.00    0.00    0.00    0.00    0.25    0.39    0.00    0.00    0.00    0.00
0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00    0.00
0.00    0.10    1.06    0.17    0.78    0.00    0.00    0.00    0.00    0.00
0.17    0.00    0.00    0.00    0.39    0.13    0.00    1.03    0.00    0.00
0.00    0.73    0.05    0.45    0.00    0.00    0.00    0.00    0.36    0.00
0.55    0.00    0.02    0.93    1.59    0.00    0.00    1.41    0.48    0.25
0.00    0.70    0.00    0.42    0.00    0.43    0.50    0.10    0.00    0.26
0.10    0.00    0.22    0.40    0.13    0.00    0.03    0.15    0.19    0.00
0.00    0.59    1.00    0.00    0.00    0.00    0.00    0.58    0.00    0.83
0.00    0.03    0.00    0.13    0.00    0.00    0.00    0.25    0.56    0.24
0.00    0.14    0.36    0.00    0.57    0.00    0.04    0.08    0.00    0.00
0.15    0.13    0.12    0.08    0.00    0.00    0.00    0.00    0.27    0.00

Size = 100 Mat. Mult. Elapsed Time: 0.0059 seconds.

Proof of Mat. Mult Works:
Example Matrix 1 & 2:
1.00    2.00    3.00
4.00    5.00    6.00
7.00    8.00    9.00
          X
1.00    2.00    3.00
4.00    5.00    6.00
7.00    8.00    9.00
          =
30.00   36.00   42.00
66.00   81.00   96.00
102.00  126.00  150.00

Calculating...
1) This will be the smallest array size that gives a non-zero time-------------

Size = 586 Mat. Mult. Elapsed Time: 1.0124 seconds.

2) Calculating Mat. Mult of doubled, tripled & quadrupled MAT_SIZE---------

Size = 100 Mat. Mult. Elapsed Time: 0.001 seconds.
Size = 200 Mat. Mult. Elapsed Time: 0.0114 seconds.
Size = 400 Mat. Mult. Elapsed Time: 0.1619 seconds.
Size = 800 Mat. Mult. Elapsed Time: 4.7545 seconds.
Size = 1600 Mat. Mult. Elapsed Time: 72.1761 seconds.
Size = 100 Mat. Mult. Elapsed Time: 0.001 seconds.
Size = 300 Mat. Mult. Elapsed Time: 0.0551 seconds.
Size = 900 Mat. Mult. Elapsed Time: 9.1804 seconds.
Size = 2700 Mat. Mult. Elapsed Time: 450.8642 seconds.
Size = 100 Mat. Mult. Elapsed Time: 0.0011 seconds.
Size = 400 Mat. Mult. Elapsed Time: 0.1456 seconds.
Size = 1600 Mat. Mult. Elapsed Time: 68.8366 seconds.

-------------------------------End of 1st run--------------------------------*/
