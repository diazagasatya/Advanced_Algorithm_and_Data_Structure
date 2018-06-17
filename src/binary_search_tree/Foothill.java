package binary_search_tree;

// CIS 1C Assignment #4

import cs_1c.*;

class PrintObject<E> implements Traverser<E>
{
   public void visit(E x)
   {
      System.out.print( x + " ");
   }
}

//------------------------------------------------------
public class Foothill
{
   // -------  MAIN  --------------
   public static void main(String[] args) throws Exception
   {
      // PART A-----------------------------------------------------------
      System.out.println("\n======PART A========");
      int k;
      FHlazySearchTree<Integer> searchTree = new FHlazySearchTree<Integer>();
      PrintObject<Integer> intPrinter = new PrintObject<Integer>();

      searchTree.traverse(intPrinter);

      System.out.println("\ninitial size: " + searchTree.size() );
      searchTree.insert(50);
      searchTree.insert(20);
      searchTree.insert(30);
      searchTree.insert(70);
      searchTree.insert(10);
      searchTree.insert(60);

      System.out.println("After populating -- traversal and sizes: ");
      searchTree.traverse(intPrinter);
      System.out.println("\ntree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println("Collecting garbage on new tree - should be " +
            "no garbage." );
      searchTree.collectGarbage();
      System.out.println("tree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      // test assignment operator
      FHlazySearchTree<Integer> searchTree2
            = (FHlazySearchTree<Integer>)searchTree.clone();

      System.out.println("\n\nAttempting 1 removal: ");
      if (searchTree.remove(20))
         System.out.println("removed " + 20 );
      System.out.println("tree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println("Collecting Garbage - should clean 1 item. " );
      searchTree.collectGarbage();
      System.out.println("tree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println("Collecting Garbage again - no change expected. " );
      searchTree.collectGarbage();
      System.out.println("tree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      // test soft insertion and deletion:

      System.out.println("Adding 'hard' 22 - should see new sizes. " );
      searchTree.insert(22);
      searchTree.traverse(intPrinter);
      System.out.println("\ntree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println("\nAfter soft removal. " );
      searchTree.remove(22);
      searchTree.traverse(intPrinter);
      System.out.println("\ntree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println("Repeating soft removal. Should see no change. " );
      searchTree.remove(22);
      searchTree.traverse(intPrinter);
      System.out.println("\ntree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println("Soft insertion. Hard size should not change. " );
      searchTree.insert(22);
      searchTree.traverse(intPrinter);
      System.out.println("\ntree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println("\n\nAttempting 100 removals: " );
      for (k = 100; k > 0; k--)
      {
         if (searchTree.remove(k))
            System.out.println("removed " + k );
      }
      searchTree.collectGarbage();

      System.out.println("\nsearch_tree now:");
      searchTree.traverse(intPrinter);
      System.out.println("\ntree 1 size: " + searchTree.size()
            + "  Hard size: " + searchTree.sizeHard() );

      searchTree2.insert(500);
      searchTree2.insert(200);
      searchTree2.insert(300);
      searchTree2.insert(700);
      searchTree2.insert(100);
      searchTree2.insert(600);
      System.out.println("\nsearchTree2:\n" );
      searchTree2.traverse(intPrinter);
      System.out.println("\ntree 2 size: " + searchTree2.size()
            + "  Hard size: " + searchTree2.sizeHard() );

      // Testing purposes -----------------------------------------------------
      System.out.println("\n-----Testing findMin() & findMax() starts--------");
      // Hard-Empty tree findMin() and findMax() tests
      FHlazySearchTree<Integer> searchTree3 = new FHlazySearchTree<Integer>();

      try {
         System.out.println("\n-----Hard-Empty Tree--------");

         // Insert integer 10 times
         for(int a = 0; a < 10; a++) {
            searchTree3.insert(a);
         }

         // Hard remove 2 nodes
         searchTree3.remove(searchTree3.mSizeHard/2);
         searchTree3.remove(searchTree3.mSizeHard/4);
         searchTree3.collectGarbage();

         int dataFound;

         dataFound = searchTree3.findMin();
         System.out.println("searchTree3 minimum value is : " + dataFound);

         dataFound = searchTree3.findMax();
         System.out.println("searchTree3 maximum value is : " + dataFound);

      } catch (Exception e) {
         System.out.println("\nException catch, please check BST's size..." +
               " hard size: " + searchTree3.mSizeHard + " soft size: "
               + searchTree3.mSize);
      }

      // A Tree that has non-deleted stuff in it
      // We can use searchTree2 since it's the perfect example

      try {
         System.out.println("\n-----Non-Deleted Tree--------");
         int dataFound;

         dataFound = searchTree2.findMin();
         System.out.println("\nsearchTree2 minimum value is : " + dataFound);

         dataFound = searchTree2.findMax();
         System.out.println("searchTree2 maximum value is : " + dataFound);

      } catch (Exception e) {
         System.out.println("\nException catch, please check BST's size..." +
               " hard size: " + searchTree2.mSizeHard + " soft size: "
               + searchTree2.mSize);
      }

      // A tree that is completely empty but has all soft-deleted nodes
      // Remove all nodes softly in search tree 2 and test findMin() & findMax()
      try {
         System.out.println("\n-----Soft-Deleted Nodes Tree--------");
         int dataFound;

         System.out.println("\nsearchTree2 before soft deletions: ");
         searchTree2.traverse(intPrinter);

         System.out.println("\n\nAttempting 700 soft-removals: " );
         for (k = 700; k > 0; k--)
         {
            if (searchTree2.remove(k))
               System.out.println("removed " + k );
         }

         System.out.println("\nsearchTree2 after soft deletions: ");
         searchTree2.traverse(intPrinter);

         dataFound = searchTree2.findMin();
         System.out.println("\nsearchTree2 minimum value is : " + dataFound);

         dataFound = searchTree2.findMax();
         System.out.println("searchTree2 maximum value is : " + dataFound);

      } catch (Exception e) {
         System.out.println("\nException catch, please check BST's size..." +
               " soft-deleted nodes: " + searchTree2.mSizeHard + " soft size: "
         + searchTree2.mSize);
      }

      // Completely empty tree
      searchTree3 = new FHlazySearchTree<Integer>();

      try {

         System.out.println("\n-----Completely Empty Tree--------");
         int dataFound;

         dataFound = searchTree3.findMin();
         System.out.println("\nsearchTree3 minimum value is : " + dataFound);

         dataFound = searchTree3.findMax();
         System.out.println("searchTree3 maximum value is : " + dataFound);


      } catch (Exception e) {
         System.out.println("\nException catch, please check BST's size..." +
               " hard size: " + searchTree3.mSizeHard + " soft size: "
               + searchTree3.mSize);
      }

      // PART B-----------------------------------------------------------
      // Show that the generic does not break on the EBookEntry data set
      System.out.println("\n======PART B========");
      System.out.println("\n-----------Lazy Deletion with EBooks------------");
      FHlazySearchTree<EBookEntry> electronicBook = new FHlazySearchTree<>();
      PrintObject<EBookEntry> bookPrinter = new PrintObject<>();

      // EBookEntryReader object read data from the file
      EBookEntryReader bookInput = new EBookEntryReader("catalog-short4.txt");
      int numOfBooks;

      // Print out error if book reader class failed
      if (bookInput.readError()) {
         System.out.println("couldn't open " + bookInput.getFileName()
         + " for input.");
         return;
      }

      // Print out the number of books and file name
      System.out.println("\nFile name: " + bookInput.getFileName());
      System.out.println("Number of books: " + bookInput.getNumBooks());

      // Insert 20 Books to ADT BST for testing purposes
      numOfBooks = 10;
      System.out.println("Insert " + numOfBooks
            + " books inside BST for testing");
      for(int j = 0; j < numOfBooks; j++) {
         electronicBook.insert(bookInput.getBook(j));
      }

      System.out.println("After populating books -- traversal and sizes: ");
      electronicBook.traverse(bookPrinter);
      System.out.println("\nElectronic-Book size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard());

      System.out.println("Collecting garbage on new tree - should be " +
            "no garbage." );
      electronicBook.collectGarbage();
      System.out.println("Electronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      // Clone electronicBook to another Tree
      FHlazySearchTree<EBookEntry> electronicBookTwo
            = (FHlazySearchTree<EBookEntry>)electronicBook.clone();

      System.out.println("\n\n-----Attempting 3 removal--------");
      int firstRemoval = 3;
      for(int b = 0; b < firstRemoval; b++) {
         if (electronicBook.remove(bookInput.getBook(b)))
            System.out.println("removed: " + bookInput.getBook(b) );
      }
      System.out.println("Electronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      System.out.println("Collecting Garbage - should clean 3 item. " );
      electronicBook.collectGarbage();
      System.out.println("Electronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      System.out.println("Collecting Garbage again - no change expected. " );
      electronicBook.collectGarbage();
      System.out.println("Electronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      // Test soft insertion and deletion:
      System.out.println("\n\n-----Adding 'hard'--------");
      System.out.println("\nAdding:\n"
            + bookInput.getBook(bookInput.getNumBooks()/2) +
            "- should see new sizes. " );
      electronicBook.insert(bookInput.getBook(bookInput.getNumBooks()/2));
      electronicBook.traverse(bookPrinter);
      System.out.println("\nElectronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      System.out.println("\n\n-----Attempting Soft Removal--------");
      System.out.println("\nAfter soft removal. " );
      electronicBook.remove(bookInput.getBook(bookInput.getNumBooks()/2));
      electronicBook.traverse(bookPrinter);
      System.out.println("\nElectronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      System.out.println("\n\nRepeating soft removal. Should see no change. ");
      electronicBook.remove(bookInput.getBook(bookInput.getNumBooks()/2));
      electronicBook.traverse(bookPrinter);
      System.out.println("\nElectronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      System.out.println("\n\n-----Attempting Soft Insertion--------");
      System.out.println("Soft insertion. Hard size should not change. " );
      electronicBook.insert(bookInput.getBook(bookInput.getNumBooks()/2));
      electronicBook.traverse(bookPrinter);
      System.out.println("\nElectronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      System.out.println("\n\n-----Attempting 2 removal--------");
      System.out.println("\n\nAttempting 2 removals from the end: " );
      numOfBooks -= firstRemoval;
      for (k = numOfBooks; k > 5  ; k--)
      {
         if (electronicBook.remove(bookInput.getBook(k)))
            System.out.println("removed " + bookInput.getBook(k) );
      }
      electronicBook.collectGarbage();

      System.out.println("\nElectronic-Book tree now:");
      electronicBook.traverse(bookPrinter);
      System.out.println("\nElectronic-Book tree size: " + electronicBook.size()
            + "  Hard size: " + electronicBook.sizeHard() );

      // TEST CLONE NOW
      System.out.println("\nAdd 3 books to the clone");
      electronicBookTwo.insert(bookInput.getBook(400));
      electronicBookTwo.insert(bookInput.getBook(300));
      electronicBookTwo.insert(bookInput.getBook(200));

      System.out.println("\nElectronic-Book2 tree now\n" );
      electronicBookTwo.traverse(bookPrinter);
      System.out.println("\nElectronic-Book2  size: " + electronicBookTwo.size()
            + "  Hard size: " + electronicBookTwo.sizeHard() );

   }
}

/*----------------------------Run Starts-------------------------------------

======PART A========

initial size: 0
After populating -- traversal and sizes:
10 20 30 50 60 70
tree 1 size: 6  Hard size: 6
Collecting garbage on new tree - should be no garbage.
tree 1 size: 6  Hard size: 6


Attempting 1 removal:
removed 20
tree 1 size: 5  Hard size: 6
Collecting Garbage - should clean 1 item.
tree 1 size: 5  Hard size: 5
Collecting Garbage again - no change expected.
tree 1 size: 5  Hard size: 5
Adding 'hard' 22 - should see new sizes.
10 22 30 50 60 70
tree 1 size: 6  Hard size: 6

After soft removal.
10 30 50 60 70
tree 1 size: 5  Hard size: 6
Repeating soft removal. Should see no change.
10 30 50 60 70
tree 1 size: 5  Hard size: 6
Soft insertion. Hard size should not change.
10 22 30 50 60 70
tree 1 size: 6  Hard size: 6


Attempting 100 removals:
removed 70
removed 60
removed 50
removed 30
removed 22
removed 10

search_tree now:

tree 1 size: 0  Hard size: 0

searchTree2:

10 20 30 50 60 70 100 200 300 500 600 700
tree 2 size: 12  Hard size: 12

-----Testing findMin() & findMax() starts--------

-----Hard-Empty Tree--------
searchTree3 minimum value is : 0
searchTree3 maximum value is : 9

-----Non-Deleted Tree--------

searchTree2 minimum value is : 10
searchTree2 maximum value is : 700

-----Soft-Deleted Nodes Tree--------

searchTree2 before soft deletions:
10 20 30 50 60 70 100 200 300 500 600 700

Attempting 700 soft-removals:
removed 700
removed 600
removed 500
removed 300
removed 200
removed 100
removed 70
removed 60
removed 50
removed 30
removed 20
removed 10

searchTree2 after soft deletions:

Exception catch, please check BST's size... soft-deleted nodes: 12 soft size: 0

-----Completely Empty Tree--------

Exception catch, please check BST's size... hard size: 0 soft size: 0

======PART B========

-----------Lazy Deletion with EBooks------------

File name: catalog-short4.txt
Number of books: 4863
Insert 10 books inside BST for testing
After populating books -- traversal and sizes:
   # 28711  ---------------
   "Operas Every Child Should KnowDescriptions of the Text and Music of Some of the Most Famous Masterpieces"
   by Bacon, Mary Schell Hoke, 1870-1934
   re: Operas -- Stories, plots, etc.

    # 30168  ---------------
   "A History of Giggleswick SchoolFrom its Foundation, 1499 to 1912"
   by Bell, Edward Allen
   re: Giggleswick School (Giggleswick, England) -- History

    # 28547  ---------------
   "The Words of Jesus"
   by Macduff, John R. (John Ross), 1818-1895
   re: Devotional exercises

    # 29171  ---------------
   "The Carroll Girls"
   by Quiller-Couch, Mabel, 1866-1924
   re: Girls -- Conduct of life -- Juvenile fiction

    # 28546  ---------------
   "A History of England Principally in the Seventeenth Century, Volume I (of 6)"
   by Ranke, Leopold von, 1795-1886
   re: Great Britain -- History -- Stuarts, 1603-1714

    # 28805  ---------------
   "Dorothy's House Party"
   by Raymond, Evelyn, 1843-1910
   re: Orphans -- Juvenile fiction

    # 28548  ---------------
   "Gipsy Lifebeing an account of our Gipsies and their children, with suggestions for their improvement"
   by Smith, George, 1831-1895
   re: Romanies -- Great Britain

    # 30169  ---------------
   "The Story of the White Mouse"
   by Unknown
   re: Conduct of life -- Juvenile fiction

    # 28712  ---------------
   "The American Missionary — Volume 54, No. 3, October, 1900"
   by Various
   re: Congregational churches -- Missions -- Periodicals

    # 30170  ---------------
   "Lonesome Hearts"
   by Winterbotham, R. R. (Russell Robert), 1904-1971
   re: Science fiction


Electronic-Book size: 10  Hard size: 10
Collecting garbage on new tree - should be no garbage.
Electronic-Book tree size: 10  Hard size: 10


-----Attempting 3 removal--------
removed:    # 30170  ---------------
   "Lonesome Hearts"
   by Winterbotham, R. R. (Russell Robert), 1904-1971
   re: Science fiction


removed:    # 30169  ---------------
   "The Story of the White Mouse"
   by Unknown
   re: Conduct of life -- Juvenile fiction


removed:    # 28546  ---------------
   "A History of England Principally in the Seventeenth Century, Volume I (of 6)"
   by Ranke, Leopold von, 1795-1886
   re: Great Britain -- History -- Stuarts, 1603-1714


Electronic-Book tree size: 7  Hard size: 10
Collecting Garbage - should clean 3 item.
Electronic-Book tree size: 7  Hard size: 7
Collecting Garbage again - no change expected.
Electronic-Book tree size: 7  Hard size: 7


-----Adding 'hard'--------

Adding:
   # 5221  ---------------
   "The Satyricon — Volume 04 : Escape by Sea"
   by Petronius Arbiter, 20-66
   re: PA

- should see new sizes.
   # 28711  ---------------
   "Operas Every Child Should KnowDescriptions of the Text and Music of Some of the Most Famous Masterpieces"
   by Bacon, Mary Schell Hoke, 1870-1934
   re: Operas -- Stories, plots, etc.

    # 30168  ---------------
   "A History of Giggleswick SchoolFrom its Foundation, 1499 to 1912"
   by Bell, Edward Allen
   re: Giggleswick School (Giggleswick, England) -- History

    # 28547  ---------------
   "The Words of Jesus"
   by Macduff, John R. (John Ross), 1818-1895
   re: Devotional exercises

    # 5221  ---------------
   "The Satyricon — Volume 04 : Escape by Sea"
   by Petronius Arbiter, 20-66
   re: PA

    # 29171  ---------------
   "The Carroll Girls"
   by Quiller-Couch, Mabel, 1866-1924
   re: Girls -- Conduct of life -- Juvenile fiction

    # 28805  ---------------
   "Dorothy's House Party"
   by Raymond, Evelyn, 1843-1910
   re: Orphans -- Juvenile fiction

    # 28548  ---------------
   "Gipsy Lifebeing an account of our Gipsies and their children, with suggestions for their improvement"
   by Smith, George, 1831-1895
   re: Romanies -- Great Britain

    # 28712  ---------------
   "The American Missionary — Volume 54, No. 3, October, 1900"
   by Various
   re: Congregational churches -- Missions -- Periodicals


Electronic-Book tree size: 8  Hard size: 8


-----Attempting Soft Removal--------

After soft removal.
   # 28711  ---------------
   "Operas Every Child Should KnowDescriptions of the Text and Music of Some of the Most Famous Masterpieces"
   by Bacon, Mary Schell Hoke, 1870-1934
   re: Operas -- Stories, plots, etc.

    # 30168  ---------------
   "A History of Giggleswick SchoolFrom its Foundation, 1499 to 1912"
   by Bell, Edward Allen
   re: Giggleswick School (Giggleswick, England) -- History

    # 28547  ---------------
   "The Words of Jesus"
   by Macduff, John R. (John Ross), 1818-1895
   re: Devotional exercises

    # 29171  ---------------
   "The Carroll Girls"
   by Quiller-Couch, Mabel, 1866-1924
   re: Girls -- Conduct of life -- Juvenile fiction

    # 28805  ---------------
   "Dorothy's House Party"
   by Raymond, Evelyn, 1843-1910
   re: Orphans -- Juvenile fiction

    # 28548  ---------------
   "Gipsy Lifebeing an account of our Gipsies and their children, with suggestions for their improvement"
   by Smith, George, 1831-1895
   re: Romanies -- Great Britain

    # 28712  ---------------
   "The American Missionary — Volume 54, No. 3, October, 1900"
   by Various
   re: Congregational churches -- Missions -- Periodicals


Electronic-Book tree size: 7  Hard size: 8


Repeating soft removal. Should see no change.
   # 28711  ---------------
   "Operas Every Child Should KnowDescriptions of the Text and Music of Some of the Most Famous Masterpieces"
   by Bacon, Mary Schell Hoke, 1870-1934
   re: Operas -- Stories, plots, etc.

    # 30168  ---------------
   "A History of Giggleswick SchoolFrom its Foundation, 1499 to 1912"
   by Bell, Edward Allen
   re: Giggleswick School (Giggleswick, England) -- History

    # 28547  ---------------
   "The Words of Jesus"
   by Macduff, John R. (John Ross), 1818-1895
   re: Devotional exercises

    # 29171  ---------------
   "The Carroll Girls"
   by Quiller-Couch, Mabel, 1866-1924
   re: Girls -- Conduct of life -- Juvenile fiction

    # 28805  ---------------
   "Dorothy's House Party"
   by Raymond, Evelyn, 1843-1910
   re: Orphans -- Juvenile fiction

    # 28548  ---------------
   "Gipsy Lifebeing an account of our Gipsies and their children, with suggestions for their improvement"
   by Smith, George, 1831-1895
   re: Romanies -- Great Britain

    # 28712  ---------------
   "The American Missionary — Volume 54, No. 3, October, 1900"
   by Various
   re: Congregational churches -- Missions -- Periodicals


Electronic-Book tree size: 7  Hard size: 8


-----Attempting Soft Insertion--------
Soft insertion. Hard size should not change.
   # 28711  ---------------
   "Operas Every Child Should KnowDescriptions of the Text and Music of Some of the Most Famous Masterpieces"
   by Bacon, Mary Schell Hoke, 1870-1934
   re: Operas -- Stories, plots, etc.

    # 30168  ---------------
   "A History of Giggleswick SchoolFrom its Foundation, 1499 to 1912"
   by Bell, Edward Allen
   re: Giggleswick School (Giggleswick, England) -- History

    # 28547  ---------------
   "The Words of Jesus"
   by Macduff, John R. (John Ross), 1818-1895
   re: Devotional exercises

    # 5221  ---------------
   "The Satyricon — Volume 04 : Escape by Sea"
   by Petronius Arbiter, 20-66
   re: PA

    # 29171  ---------------
   "The Carroll Girls"
   by Quiller-Couch, Mabel, 1866-1924
   re: Girls -- Conduct of life -- Juvenile fiction

    # 28805  ---------------
   "Dorothy's House Party"
   by Raymond, Evelyn, 1843-1910
   re: Orphans -- Juvenile fiction

    # 28548  ---------------
   "Gipsy Lifebeing an account of our Gipsies and their children, with suggestions for their improvement"
   by Smith, George, 1831-1895
   re: Romanies -- Great Britain

    # 28712  ---------------
   "The American Missionary — Volume 54, No. 3, October, 1900"
   by Various
   re: Congregational churches -- Missions -- Periodicals


Electronic-Book tree size: 8  Hard size: 8


-----Attempting 2 removal--------


Attempting 2 removals from the end:
removed    # 28712  ---------------
   "The American Missionary — Volume 54, No. 3, October, 1900"
   by Various
   re: Congregational churches -- Missions -- Periodicals


removed    # 30168  ---------------
   "A History of Giggleswick SchoolFrom its Foundation, 1499 to 1912"
   by Bell, Edward Allen
   re: Giggleswick School (Giggleswick, England) -- History



Electronic-Book tree now:
   # 28711  ---------------
   "Operas Every Child Should KnowDescriptions of the Text and Music of Some of the Most Famous Masterpieces"
   by Bacon, Mary Schell Hoke, 1870-1934
   re: Operas -- Stories, plots, etc.

    # 28547  ---------------
   "The Words of Jesus"
   by Macduff, John R. (John Ross), 1818-1895
   re: Devotional exercises

    # 5221  ---------------
   "The Satyricon — Volume 04 : Escape by Sea"
   by Petronius Arbiter, 20-66
   re: PA

    # 29171  ---------------
   "The Carroll Girls"
   by Quiller-Couch, Mabel, 1866-1924
   re: Girls -- Conduct of life -- Juvenile fiction

    # 28805  ---------------
   "Dorothy's House Party"
   by Raymond, Evelyn, 1843-1910
   re: Orphans -- Juvenile fiction

    # 28548  ---------------
   "Gipsy Lifebeing an account of our Gipsies and their children, with suggestions for their improvement"
   by Smith, George, 1831-1895
   re: Romanies -- Great Britain


Electronic-Book tree size: 6  Hard size: 6

Add 3 books to the clone

Electronic-Book2 tree now

   # 28711  ---------------
   "Operas Every Child Should KnowDescriptions of the Text and Music of Some of the Most Famous Masterpieces"
   by Bacon, Mary Schell Hoke, 1870-1934
   re: Operas -- Stories, plots, etc.

    # 30168  ---------------
   "A History of Giggleswick SchoolFrom its Foundation, 1499 to 1912"
   by Bell, Edward Allen
   re: Giggleswick School (Giggleswick, England) -- History

    # 29688  ---------------
   "Home Range and Movements of the Eastern Cottontail in Kansas"
   by Janes, Donald W.
   re: Cottontails

    # 28084  ---------------
   "Malcolm Sage, Detective"
   by Jenkins, Herbert George, 1876-1923
   re: Detective and mystery stories

    # 28547  ---------------
   "The Words of Jesus"
   by Macduff, John R. (John Ross), 1818-1895
   re: Devotional exercises

    # 29890  ---------------
   "The Doctor's Family"
   by Oliphant, Mrs. (Margaret), 1828-1897
   re: Fiction

    # 29171  ---------------
   "The Carroll Girls"
   by Quiller-Couch, Mabel, 1866-1924
   re: Girls -- Conduct of life -- Juvenile fiction

    # 28546  ---------------
   "A History of England Principally in the Seventeenth Century, Volume I (of 6)"
   by Ranke, Leopold von, 1795-1886
   re: Great Britain -- History -- Stuarts, 1603-1714

    # 28805  ---------------
   "Dorothy's House Party"
   by Raymond, Evelyn, 1843-1910
   re: Orphans -- Juvenile fiction

    # 28548  ---------------
   "Gipsy Lifebeing an account of our Gipsies and their children, with suggestions for their improvement"
   by Smith, George, 1831-1895
   re: Romanies -- Great Britain

    # 30169  ---------------
   "The Story of the White Mouse"
   by Unknown
   re: Conduct of life -- Juvenile fiction

    # 28712  ---------------
   "The American Missionary — Volume 54, No. 3, October, 1900"
   by Various
   re: Congregational churches -- Missions -- Periodicals

    # 30170  ---------------
   "Lonesome Hearts"
   by Winterbotham, R. R. (Russell Robert), 1904-1971
   re: Science fiction


Electronic-Book2  size: 13  Hard size: 13

Process finished with exit code 0

-------------------------------Run Ends-------------------------------------*/
