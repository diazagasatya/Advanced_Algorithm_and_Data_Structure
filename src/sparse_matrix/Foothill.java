package sparse_matrix;

// CIS 1C Assignment #2
// Instructor Solution Featuring clone()

// client -----------------------------------------------------

//------------------------------------------------------
public class Foothill
{
   final static int MAT_SIZE = 100000;
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      // 100000 x 100000 filled with 0
      int k;
      SparseMat<Double> mat
            = new SparseMat<Double>(MAT_SIZE, MAT_SIZE, 0.);

      System.out.println("-----Part A----");
      System.out.println("-----Show Sub Square Before Mutators----");
      mat.showSubSquare(0, 12);
      System.out.println();

      // test mutators
      for (k = 0; k < 10; k++)
      {
         mat.set(k, k, k*1.);
         mat.set(4, k, k*10.);
         mat.set(k, 4, -k*10.);
      }

      System.out.println("-----Show Sub Square After Mutators----");
      mat.showSubSquare(0, 12);

      // Testing purposes 1
      try {
         System.out.println("-----Accessing Mutators----");
         System.out.println("Row 4, Column 4 : " + mat.get(4,4));
         System.out.println("Row 9, Column 4 : " + mat.get(9,4));
         System.out.println("Row 11, Column 8 : " + mat.get(11,8));
         System.out.println("Row 100023, Column 32 : " + mat.get(100023,32));
      } catch (IndexOutOfBoundsException e) {
         System.out.println("Out-of-bound range accessor");
      }
      // Testing purposes 2
      System.out.println("-----Bounds Checking----");
      System.out.println("Out-of-bound row 100001 try : "
            + mat.set(100001,300,32.3));
      System.out.println("Out-of-bound column 100021 try : "
            + mat.set(10000,100021,13.3));
      System.out.println("Out-of-bound row -100 & column 100051 try : "
            + mat.set(-100,100051,17.3));
      System.out.println("-------End of Testing------");

      System.out.println("\n----PART B----");

      SparseMat<Double> mat2 = (SparseMat<Double>)mat.clone();

      for (k = 0; k < 10; k++)
      {
         mat.set(k, k, 1.);
         mat.set(4, k, 10.);
         mat.set(k, 4, -10.);
      }

      System.out.println("-----Show Sub Square mat 2nd mutation----");
      mat.showSubSquare(0, 12);
      System.out.println();
      System.out.println("-----Show Sub Square mat2 clone unchanged----");
      mat2.showSubSquare(0, 12);

      // Testing purposes 3
      try {
         System.out.println("-----Accessing Mutators----");
         System.out.println("Row 9, Column 4 : " + mat2.get(9,4));
         System.out.println("Row 8, Column 4 : " + mat2.get(8,4));
         System.out.println("Row 7, Column 4 : " + mat2.get(7,4));
         System.out.println("Row 100032, Column 22 : " + mat2.get(100032,22));
      } catch (IndexOutOfBoundsException e) {
         System.out.println("Out-of-bound range accessor");
      }
      // Testing purposes 4
      System.out.println("-----Bounds Checking----");
      System.out.println("Out-of-bound row 100101 try : "
            + mat2.set(100101,300,32.3));
      System.out.println("Out-of-bound column 110000 try : "
            + mat2.set(110000,100021,13.3));
      System.out.println("Out-of-bound row -99 & column 123003 try : "
            + mat2.set(-99,123003,17.3));
      System.out.println("-------End of Testing------");

   }
}

/*----------------------RUNS----------------------
-----Part A----
-----Show Sub Square Before Mutators----
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0

-----Show Sub Square After Mutators----
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   1.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   2.0   0.0 -20.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   3.0 -30.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0  10.0  20.0  30.0 -40.0  50.0  60.0  70.0  80.0  90.0   0.0   0.0
   0.0   0.0   0.0   0.0 -50.0   5.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -60.0   0.0   6.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -70.0   0.0   0.0   7.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -80.0   0.0   0.0   0.0   8.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -90.0   0.0   0.0   0.0   0.0   9.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
-----Accessing Mutators----
Row 4, Column 4 : -40.0
Row 9, Column 4 : -90.0
Row 11, Column 8 : 0.0
Out-of-bound range accessor
-----Bounds Checking----
Out-of-bound row 100001 try : false
Out-of-bound column 100021 try : false
Out-of-bound row -100 & column 100051 try : false
-------End of Testing------

----PART B----
-----Show Sub Square mat 2nd mutation----
   1.0   0.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   1.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   1.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   1.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
  10.0  10.0  10.0  10.0 -10.0  10.0  10.0  10.0  10.0  10.0   0.0   0.0
   0.0   0.0   0.0   0.0 -10.0   1.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -10.0   0.0   1.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -10.0   0.0   0.0   1.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -10.0   0.0   0.0   0.0   1.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   1.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0

-----Show Sub Square mat2 clone unchanged----
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   1.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   2.0   0.0 -20.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   3.0 -30.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0  10.0  20.0  30.0 -40.0  50.0  60.0  70.0  80.0  90.0   0.0   0.0
   0.0   0.0   0.0   0.0 -50.0   5.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -60.0   0.0   6.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -70.0   0.0   0.0   7.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -80.0   0.0   0.0   0.0   8.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0 -90.0   0.0   0.0   0.0   0.0   9.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0
-----Accessing Mutators----
Row 9, Column 4 : -90.0
Row 8, Column 4 : -80.0
Row 7, Column 4 : -70.0
Out-of-bound range accessor
-----Bounds Checking----
Out-of-bound row 100101 try : false
Out-of-bound column 110000 try : false
Out-of-bound row -99 & column 123003 try : false
-------End of Testing------

 ---------------------END OF RUN----------------*/