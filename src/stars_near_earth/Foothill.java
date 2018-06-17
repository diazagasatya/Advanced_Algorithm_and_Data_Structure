package stars_near_earth;

// Test file for StarNearEarth project.  See Read Me file for details
// CS 1C, Foothill College, Michael Loceff, creator

import cs_1c.*;

//------------------------------------------------------
public class Foothill
{
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      String outString;
      int k, arraySize, row, col;
      double maxX, minX, maxY, minY, maxZ, minZ,
            xRange, yRange, zRange,
            xConversion, yConversion, zConversion;
      final int NUM_COLS = 70;
      final int NUM_ROWS = 35;

      StarNearEarthReader  starInput
            = new StarNearEarthReader("nearest_stars.txt");

      if (starInput.readError())
      {
         System.out.println("couldn't open " + starInput.getFileName()
               + " for input.");
         return;
      }

      // do this just to see if our read went well
      System.out.println(starInput.getFileName());
      System.out.println(starInput.getNumStars());

      // create an array of objects for our own use:
      arraySize = starInput.getNumStars();
      SNE_Analyzer[] starArray = new SNE_Analyzer[arraySize];
      for (k = 0; k < arraySize; k++)
         starArray[k] =  new SNE_Analyzer( starInput.getStar(k) );

      // display cartesian coords
      for ( k = 0; k < arraySize; k++ )
         System.out.println( starArray[k].getNameCommon() + " "
               + starArray[k].coordToString());

      // now for the graphing
      // get max and min coords for scaling
      maxX = minX = maxY = minY = maxZ = minZ = 0;
      for (k = 0; k < arraySize; k++)
      {
         starArray[k].calcCartCoords();

         // Initialize min values
         if(k == 0) {
            minX = starArray[k].getX();
            minY = starArray[k].getY();
            minZ = starArray[k].getZ();
         }

         // Get max X coordinate
         if(starArray[k].getX() > maxX) {
            maxX = starArray[k].getX();
         }
         // Get min X coordinate
         if(starArray[k].getX() < minX) {
            minX = starArray[k].getX();
         }

         // Get max Y coordinate
         if(starArray[k].getY() > maxY) {
            maxY = starArray[k].getY();
         }
         // Get min Y coordinate
         if(starArray[k].getY() < minY) {
            minY = starArray[k].getY();
         }

         // Get max Y coordinate
         if(starArray[k].getZ() > maxZ) {
            maxZ = starArray[k].getZ();
         }
         // Get min Y coordinate
         if(starArray[k].getZ() < minZ) {
            minZ = starArray[k].getZ();
         }
      }

      xRange = maxX - minX;
      yRange = maxY - minY;
      zRange = maxZ - minZ;

      // form 50 x 25 grid for display: x-y projection
      xConversion = 50; // Not needed
      yConversion = 25; // Not needed

      // Graphing x-y starts here ------------------------------------------
      SparseMat<Character> starMap
            = new SparseMat<Character>(NUM_ROWS, NUM_COLS, ' ');

      for (k = 0; k < arraySize; k++)
      {
         row = (int) ((starArray[k].getY() - minY) * (NUM_ROWS - 0) /
               (int) yRange ) % NUM_ROWS;
         col = (int) ((starArray[k].getX() - minX) * (NUM_COLS - 0) /
               (int) xRange ) % NUM_COLS;


         int rankingStar = starArray[k].getRank() + 48;
         char rank = (char) rankingStar;

         if ( starArray[k].getRank() < 10) {
            starMap.set(row, col, rank);
         }
         else {
            starMap.set(row, col, '*');
         }

      }

      System.out.println("\n\n===================================" +
            "===================================" +
            "\n" + "===================================" +
            "===================================");
      System.out.println("                " +
            "PLOTTED GRAPH OF STARS NEAR EARTH \n" +
            "                  on Cartesian Coordinates x & y");
      System.out.println("===================================" +
            "===================================" +
            "\n" + "===================================" +
            "===================================");

      // set sun at center
      row = (int) ((0 - minY) * (NUM_ROWS - 0) / (int) yRange) % NUM_ROWS;
      col = (int) ((0 - minX) * (NUM_COLS - 0) / (int) xRange) % NUM_COLS;

      starMap.set( row, col, 'S' );

      for (row = 0; row < NUM_ROWS; row++)
      {
         outString = "";
         // inner loop that builds outString not shown
         for (col = 0; col < NUM_COLS; col++) {
            outString += starMap.get(row,col);
         }
         System.out.println( outString );
      }

      // graphing x-z starts here ------------------------------------------

      // set all starmap to blank
      starMap = new SparseMat<Character>(NUM_ROWS, NUM_COLS, ' ');

      System.out.println("\n\n===================================" +
            "===================================" +
            "\n" + "===================================" +
            "===================================");
      System.out.println("                " +
            "PLOTTED GRAPH OF STARS NEAR EARTH \n" +
            "                  on Cartesian Coordinates z & x");
      System.out.println("===================================" +
            "===================================" +
            "\n" + "===================================" +
            "===================================");

      for (k = 0; k < arraySize; k++)
      {
         row = (int) ((starArray[k].getX() - minX) * (NUM_ROWS - 0) /
               (int) xRange ) % NUM_ROWS;
         col = (int) ((starArray[k].getZ() - minZ) * (NUM_COLS - 0) /
               (int) zRange ) % NUM_COLS;


         int rankingStar = starArray[k].getRank() + 48;
         char rank = (char) rankingStar;

         if ( starArray[k].getRank() < 10) {
            starMap.set(row, col, rank);
         }
         else {
            starMap.set(row, col, '*');
         }

      }

      // set sun at center
      row = (int) ((0 - minY) * (NUM_ROWS - 0) / (int) yRange) % NUM_ROWS;
      col = (int) ((0 - minX) * (NUM_COLS - 0) / (int) xRange) % NUM_COLS;

      starMap.set( row, col, 'S' );

      for (row = 0; row < NUM_ROWS; row++)
      {
         outString = "";
         // inner loop that builds outString not shown
         for (col = 0; col < NUM_COLS; col++) {
            outString += starMap.get(row,col);
         }
         System.out.println( outString );
      }

      // graphing y-z starts here ------------------------------------------

      // set all starmap to blank
      starMap = new SparseMat<Character>(NUM_ROWS, NUM_COLS, ' ');

      System.out.println("\n\n===================================" +
            "===================================" +
            "\n" + "===================================" +
            "===================================");
      System.out.println("                " +
            "PLOTTED GRAPH OF STARS NEAR EARTH \n" +
            "                  on Cartesian Coordinates y & z");
      System.out.println("===================================" +
            "===================================" +
            "\n" + "===================================" +
            "===================================");

      for (k = 0; k < arraySize; k++)
      {
         row = (int) ((starArray[k].getY() - minY) * (NUM_ROWS - 0) /
               (int) yRange ) % NUM_ROWS;
         col = (int) ((starArray[k].getZ() - minZ) * (NUM_COLS - 0) /
               (int) zRange ) % NUM_COLS;


         int rankingStar = starArray[k].getRank() + 48;
         char rank = (char) rankingStar;

         if ( starArray[k].getRank() < 10) {
            starMap.set(row, col, rank);
         }
         else {
            starMap.set(row, col, '*');
         }

      }

      // set sun at center
      row = (int) ((0 - minY) * (NUM_ROWS - 0) / (int) yRange) % NUM_ROWS;
      col = (int) ((0 - minX) * (NUM_COLS - 0) / (int) xRange) % NUM_COLS;

      starMap.set( row, col, 'S' );

      for (row = 0; row < NUM_ROWS; row++)
      {
         outString = "";
         // inner loop that builds outString not shown
         for (col = 0; col < NUM_COLS; col++) {
            outString += starMap.get(row,col);
         }
         System.out.println( outString );
      }
   }
}

/* ---------------- Sample Run ----------------
nearest_stars.txt
100
Proxima Centauri , (-1.546293280034828, -1.1834782404918829, -3.769340040640834)
Barnard's Star , (-0.05705414787937229, -5.943384488171159, 0.4879614221562781)
Wolf 359 , (-7.430302789267513, 2.113656766488175, 0.9505361784930765)
Lalande 21185 , (-6.506350814781059, 1.6423328463047275, 4.870045869580979)
Sirius , (-1.6085327082288747, 8.062124198742048, -2.4689451595593694)
BL Ceti , (7.541004909725272, 3.4771158493041323, -2.6901813997746356)
Ross 154 , (1.9099886146032934, -8.648134906359745, -3.912868986439883)
Ross 248 , (7.380576219119017, -0.5841043880942511, 7.193455565565409)
epsilon Eridani , (6.213552194969109, 8.314647528356762, -1.7292347753939203)
Lacaille 9352 , (8.465652851065244, -2.0376331496846167, -6.292257314440043)
Ross 128 , (-10.903212529140005, 0.5841352454014845, -0.15331293994618092)
EZ Aquarii A , (10.189088106645116, -3.7816986689656082, -2.9736119767530935)
Procyon , (-4.767886129714378, 10.306837767247032, 1.0384929439632595)
61 Cygni A , (6.4749347722201795, -6.0972262993806945, 7.137944689593691)
(no common name) , (1.0811476193702916, -5.726377170903597, 9.944846268362785)
GX Andromedae , (8.332607632236641, 0.6692059317846895, 8.0791177026848)
epsilon Indi A , (5.656607365319962, -3.1565141480371026, -9.893750489540375)
DX Cancri , (-6.421284440357058, 8.382256018324014, 5.328465548795745)
tau Ceti , (10.272861338713964, 5.014108887048788, -3.264361210391229)
Henry et al. 1997, Henry et al. 2006 , (5.0270147874277376, 6.9180342435230635, -8.407314275653606)
YZ Ceti , (11.027734120516028, 3.6097456681732085, -3.547299532183627)
Luyten's Star , (-4.583705711867041, 11.431141134068273, 1.1264337022874917)
Henry et al. 2006 , (8.723330934708033, 8.206090333905973, 3.634488298604819)
Henry et al. 2006 , (1.0786249896945848, -5.4123281146108955, -11.296783448636157)
Kapteyn's Star , (1.8909071773962967, 8.832829004761322, -9.038744292306433)
AX Microscopii , (7.599194224620635, -6.533454296377602, -8.077083111235929)
Kruger 60 A , (6.468708349056162, -2.7463589818019685, 11.114719559664739)
Jao et al. 2005, Costa et al. 2005 , (-9.606320412439063, 3.1104734721544656, -8.4531790945074)
Ross 614 A , (-1.7048784625589772, 13.22471249406481, -0.655388931298056)
Wolf 1061 , (-5.144282640562471, -12.465524323172, -3.029767485501764)
van Maanen's Star , (13.684614957343356, 2.9806012852368355, 1.3210962139524127)
(no common name) , (11.309466455844786, 0.2665223486435166, -8.635851872247539)
Wolf 424 A , (-13.987450701280986, -2.0457428763211327, 2.244232931350303)
TZ Arietis , (12.235263669852651, 7.07946394099081, 3.277077319600396)
(no common name) , (-0.5609298130342589, -5.43186888823961, 13.749622315239206)
(no common name) , (-13.811493816260295, 4.474304898912551, -2.910822815474845)
(no common name) , (-1.3800687637070208, -10.025940913445059, -10.81319246794408)
G 208-044 A , (5.0448998087446695, -9.301225533588186, 10.367498578410379)
WD 1142-645 , (-6.3908250472502015, 0.39927714683417503, -13.633228850848031)
(no common name) , (15.175636605573494, 0.4448795613026582, -2.009402827417076)
Ross 780 , (14.245086117283032, -4.269214343620348, -3.7805310325634776)
Henry et al. 2006 , (-7.112570453908373, 2.4369109081847693, -13.681678017946801)
(no common name) , (-11.156616725511448, 2.7059411532073794, 10.904347387429333)
(no common name) , (-9.167177171191707, 4.702864906889732, 12.043869531948207)
(no common name) , (-13.578271901860228, 6.360527338109189, 5.418779089988099)
(no common name) , (8.470209146694986, -6.292444091514149, -12.14219662433262)
(no common name) , (7.589544870148619, 10.797160901372031, -9.388862673050967)
Costa et al. 2005 , (7.976647982678315, 7.639314940065456, -11.850010166755983)
(no common name) , (-1.1686202977228328, -11.631033289348316, -11.4150338286823)
omicron 2 Eridani , (7.1684465143935014, 14.578910676825814, -2.1829072585159004)
EV Lacertae , (11.186085948138413, -3.698541797166734, 11.510861990193677)
70 Ophiuchi A , (0.39543574371950474, -16.625708817467245, 0.726099416134548)
Altair , (7.683130842751442, -14.636793912766256, 2.579289341649488)
EI Cancri , (-11.26537595427916, 11.440407807448155, 5.7684327245105145)
Henry et al. 2006 , (-0.003723165882916918, 17.065735050691273, 0.8067897698606545)
Henry et al. 2006 , (4.317946004675097, 16.681244266392785, -2.0993409605432016)
(no common name) , (-3.4370913507857135, 0.18489281092269277, 17.211934005749015)
Wolf 498 , (-15.324727080737995, -7.616986495610075, 4.550748562763216)
(no common name) , (11.711376213592777, -12.498642742714075, -5.228329182383282)
Stein 2051 , (3.517279200086466, 8.617038240360827, 15.475898871073841)
(no common name) , (-3.5980925301995783, 14.755305304262334, 9.964317221306036)
(no common name) , (2.399164126762291, -15.313455773234473, 10.06534250378411)
Wolf 1453 , (2.3090260090928707, 18.439563962063094, -1.194327093213436)
(no common name) , (8.137112423089073, 16.558068150668703, -3.1149709733393154)
sigma Draconis , (2.561187790112302, -6.008252627524452, 17.61982181100295)
(no common name) , (-0.8057337269792348, 17.46337877122848, -7.01520123005842)
(no common name) , (-0.6009689461966576, -10.241253370968245, -15.991598416745335)
Wolf 1055 , (6.256723160371619, -17.93745546497709, 1.718492983576037)
Ross 47 , (1.4544758133589524, 18.63680669724297, 4.140630968420611)
(no common name) , (-12.781469976081276, -12.501981591309868, -7.01237908279098)
Jao et al. 2005 , (4.665866587983771, -12.681454032050423, -13.77812405192475)
(no common name) , (19.319434892163613, -0.9110814217838443, 0.8109998542497681)
eta Cassiopei A , (10.083051501574305, 2.1938524846330867, 16.39592295884205)
(no common name) , (-8.75306249337614, -11.636820944417469, -12.781419105703112)
(no common name) , (18.60283885128522, 1.2573417601436143, -5.393632944069242)
Ross 882 , (-8.569445684330617, 17.441019688009863, 1.2064202013656653)
36 Ophiuchi A , (-3.3708011877066837, -17.082461549090617, -8.720264759886762)
(no common name) , (8.63472438820302, -13.400609626408311, -11.625292624112227)
82 Eridani , (9.286741477362405, 11.059338378781042, -13.499667228475726)
(no common name) , (-0.33678248902910657, -6.480292739939325, 18.712520596182145)
delta Pavonis , (4.283522662568179, -6.807606820840908, -18.220683012395934)
QY Aurigae A , (-4.710766798243793, 14.936855806464763, 12.471333711812747)
HN Librae , (-15.28833797040959, -12.189964128335564, -4.341807458363677)
(no common name) , (-14.17252652138047, 10.14965059144213, 9.869267046683166)
(no common name) , (-9.147183640081225, 8.066691930271183, 16.001815933909718)
(no common name) , (7.869865111406449, -11.90323011118884, -14.351474282554207)
Wolf 562 , (-13.082264980277813, -15.512778538820603, -2.751689785477283)
EQ Pegasi , (19.296365472144878, -2.3806919935336524, 7.052437729911085)
Henry et al. 2006 , (-13.671751957898799, 13.626093342730766, 7.734028900268257)
Henry et al. 2006 , (-16.07646311658482, -2.7492034530838394, -12.918522539223567)
(no common name) , (-3.0247249065911155, -14.26591724438214, 14.925989802585287)
(no common name) , (-13.199816381359483, -12.817596871551537, -9.850686875131904)
(no common name) , (-5.968577439414323, -14.64381399890091, 13.681140963310675)
WD 0552-041 , (0.4441472242748373, 20.984711355742135, -1.5308286302026246)
Wolf 630 A , (-5.787224715866445, -20.00661791630325, -3.0518155029454337)
(no common name) , (11.289692816090245, -2.333702210595376, 17.866835369596355)
Jao et al. 2005 , (-6.342090626234481, 4.327632686344061, -19.882305735256153)
GL Virginis , (-20.861389800148043, -1.733447817764015, 4.116538927214568)
(no common name) , (-5.030065120118541, -11.48603181526226, 17.45276292887482)
Ross 104 , (-19.340529158657702, 5.176249783415439, 8.429745096734456)


======================================================================
======================================================================
                PLOTTED GRAPH OF STARS NEAR EARTH
                  on Cartesian Coordinates x & y
======================================================================
======================================================================
                          *          *
                                               *
                              *      *
             *
                          *             *        *
                               *                   *
         *   **            *                *            *
                     *     *      *               *
                                  **
                                       7     *
         *
                                   *        *    * *
                                   *2 * *      *
                                                             *
                                              *       * *
        *   *                                  *   *    *
                                 1               8
                         *    *     S                   *      *
                 *       4                         *                 *
                *      3*                             *
                   *                             6     *    *
            *       *    *                            *
  *
            *                                *           *
                    *    *       5             9  **
                                       *  *
           *                *                    *
                *           *                       *

            *                    *
                            * *                  *
                                                  *
                     *             **       *
                                       **



======================================================================
======================================================================
                PLOTTED GRAPH OF STARS NEAR EARTH
                  on Cartesian Coordinates z & x
======================================================================
======================================================================
                                      *     *    *
                                                    *


            *               *                *
                                                      *
                  *            *        *     *   *
                       *
                                    *          *        *
                     *
             *                        *                   *       *
                                      3
           *                                 4*
                               *                             *      *
                                      *                    *
                    *                                 *         *   *
                             1  5  *
 *     *       **      *            S                        *
                                 *   **
               *   *         7              *         *
                                  *                    *             *
                                                                 *
   *       *         *          *                      *
                  *              9     *         *       *
                   * *         6*        *       8
          *   **         *    *            *       *
           *
                              **                                  *
                    *     *               *              *           *

                             *         *
                                *


                          *


======================================================================
======================================================================
                PLOTTED GRAPH OF STARS NEAR EARTH
                  on Cartesian Coordinates y & z
======================================================================
======================================================================
                               * *
                                       *
                    *                *
                               *
                                         *             *     *
               *                                                *
           *      *    *  * *  *
          *  * *                                                    *
       *        *
                             7                         *
                                             *
 * *          *      *
               *                     2           *    *      *       *
                             *
                  *            *                         *
            *            *              *   *    *       *           *
                             1        *          8
           *        *           *   S                               *
                          *         *        4     *
           *                          3                 *         *
                     *        *6       *
                              **                          *
                                                    *
                     *                    *   *
              *                 59         *  *                   *
                   *                                             *
                   *                  *               *
           *                          *        *

                                   *              *
                                *                     *    *
                              *
                       *        *     *
                                  *         *


Process finished with exit code 0
------------------------------------------- */


