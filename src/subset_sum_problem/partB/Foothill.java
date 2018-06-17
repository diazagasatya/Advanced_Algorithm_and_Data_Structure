package subset_sum_problem.partB;

import cs_1c.*;
import java.text.*;
import java.util.*;

//------------------------------------------------------
public class Foothill
{
    // ------- Part B  main --------------
    public static void main(String[] args) throws Exception
    {
        int target = 3600;
        ArrayList<iTunesEntry> dataSet = new ArrayList<iTunesEntry>();
        ArrayList<Sublist> choices = new ArrayList<Sublist>();
        int k, arraySize, masterSum, dataSize, currentSize,
                choiceSum, newChoiceSum, currentSum;
        int kBest = 0;
        int temporarySum = 0;
        boolean foundPerfect = false;
        boolean targetValid;

        // for formatting and timing
        NumberFormat tidy = NumberFormat.getInstance(Locale.US);
        tidy.setMaximumFractionDigits(4);
        long startTime, stopTime;

        // read the iTunes Data
        iTunesEntryReader tunesInput = new iTunesEntryReader("itunes_file.txt");

        // test the success of the read:
        if (tunesInput.readError())
        {
            System.out.println("couldn't open " + tunesInput.getFileName()
                    + " for input.");
            return;
        }

        // load the dataSet ArrayList with the iTunes:
        arraySize = tunesInput.getNumTunes();
        for (k = 0; k < arraySize; k++)
            dataSet.add(tunesInput.getTune(k));

        choices.clear();
        System.out.println("Target time: " + target);

        // code supplied by student
        // Subset Sum Algorithm
        Sublist collection = new Sublist(dataSet);
        choices.add(collection);
        dataSize = dataSet.size();

        // Checking target validity in respect to master set reachable total
        targetValid = collection.validTarget(dataSet, target);
        masterSum = collection.getMasterSum();

        if(!targetValid) {
            System.out.println("Target time " + target + " is too " +
                    "large for Data set, current reachable total time is: "
                    + "\n" + masterSum);
        }

        startTime = System.nanoTime();

        while(targetValid == true) {

            // Loop over all element a in master set
            for(int a = 0; a < dataSize; a++) {
                currentSize = choices.size();
                // Loop over all sub-lists in choices
                for(int b = 0; b < currentSize; b++) {
                    choiceSum = choices.get(b).getSum();
                    newChoiceSum = choiceSum + dataSet.get(a).getTime();
                    if(newChoiceSum <= target) {
                        choices.add(choices.get(b).addItem(a));
                        if(newChoiceSum == target) {
                            foundPerfect = true;
                            break;
                        }
                    }
                }
            }

            stopTime = System.nanoTime();

            // Find the sub-lists that has the highest sum
            for(int b = 0; b < choices.size(); b++ ) {
                currentSum = choices.get(b).getSum();
                if(temporarySum < currentSum ) {
                    temporarySum = currentSum;
                    kBest = b;
                }
            }

            System.out.println("Highest Sum : " + temporarySum + "\n");
            choices.get(kBest).showSublist();
            System.out.println("Algorithm Elapsed Time: "
                    + tidy.format((stopTime - startTime) / 1e9)
                    + " seconds.\n");


            // Testing purposes
            if(foundPerfect == true) {
                int choiceSize = choices.size();
                int testOne = choiceSize / 2;
                int testTwo = choiceSize / 4;
                int testThree = choiceSize / 7;

                System.out.println("Showing tests that does not meet target:");
                choices.get(testOne).showSublist();
                choices.get(testTwo).showSublist();
                choices.get(testThree).showSublist();
            }
            targetValid = false;
        }

    }
}

/*----------------------Paste of Run B with Testing 1--------------------------
Target time: 3600
Highest Sum : 3600

Sublist -----------------------------
  Sum: 3600
  Array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
  Array[1] = Carrie Underwood | Quitter |  3:40,
  Array[2] = Rihanna | Russian Roulette |  3:48,
  Array[4] = Foo Fighters | Monkey Wrench |  3:50,
  Array[5] = Eric Clapton | Pretending |  4:43,
  Array[6] = Eric Clapton | Bad Love |  5:08,
  Array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
  Array[8] = Howlin' Wolf | Well That's All Right |  2:55,
  Array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
  Array[11] = Roy Buchanan | Hot Cha |  3:28,
  Array[12] = Roy Buchanan | Green Onions |  7:23,
  Array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
  Array[14] = Janiva Magness | You Were Never Mine |  4:36,
  Array[15] = John Lee Hooker | Hobo Blues |  3:07,
  Array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,

Algorithm Elapsed Time: 5.9933 seconds.

Showing tests that does not meet target:
Sublist -----------------------------
  Sum: 2281
  Array[1] = Carrie Underwood | Quitter |  3:40,
  Array[3] = Foo Fighters | All My Life |  4:23,
  Array[6] = Eric Clapton | Bad Love |  5:08,
  Array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
  Array[8] = Howlin' Wolf | Well That's All Right |  2:55,
  Array[11] = Roy Buchanan | Hot Cha |  3:28,
  Array[12] = Roy Buchanan | Green Onions |  7:23,
  Array[15] = John Lee Hooker | Hobo Blues |  3:07,
  Array[46] = Jeff Golub | Fish Fare |  4:59,

Sublist -----------------------------
  Sum: 2061
  Array[1] = Carrie Underwood | Quitter |  3:40,
  Array[2] = Rihanna | Russian Roulette |  3:48,
  Array[4] = Foo Fighters | Monkey Wrench |  3:50,
  Array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
  Array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
  Array[12] = Roy Buchanan | Green Onions |  7:23,
  Array[14] = Janiva Magness | You Were Never Mine |  4:36,
  Array[31] = Terra Incogni | Lizard Skin |  4:30,

Sublist -----------------------------
  Sum: 1751
  Array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
  Array[1] = Carrie Underwood | Quitter |  3:40,
  Array[4] = Foo Fighters | Monkey Wrench |  3:50,
  Array[6] = Eric Clapton | Bad Love |  5:08,
  Array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
  Array[14] = Janiva Magness | You Were Never Mine |  4:36,
  Array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,
  Array[22] = Veggie Tales | Our Big Break |  1:09,



---------------------Paste of Run B with Testing 2-----------------------------
Target time: 40000
Target time 40000 is too large for Data set, current reachable total time is:
22110

---------------------Paste of Run B with Testing 3-----------------------------
Target time: 4000
Highest Sum : 4000

Sublist -----------------------------
  Sum: 4000
  Array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
  Array[1] = Carrie Underwood | Quitter |  3:40,
  Array[2] = Rihanna | Russian Roulette |  3:48,
  Array[3] = Foo Fighters | All My Life |  4:23,
  Array[4] = Foo Fighters | Monkey Wrench |  3:50,
  Array[5] = Eric Clapton | Pretending |  4:43,
  Array[6] = Eric Clapton | Bad Love |  5:08,
  Array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
  Array[8] = Howlin' Wolf | Well That's All Right |  2:55,
  Array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
  Array[11] = Roy Buchanan | Hot Cha |  3:28,
  Array[12] = Roy Buchanan | Green Onions |  7:23,
  Array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
  Array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,
  Array[17] = Snoop Dogg | That's The Homie |  5:43,
  Array[18] = Snoop Dogg | Gangsta Luv |  4:17,

Algorithm Elapsed Time: 27.0471 seconds.

Showing tests that does not meet target:
Sublist -----------------------------
  Sum: 2511
  Array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
  Array[1] = Carrie Underwood | Quitter |  3:40,
  Array[3] = Foo Fighters | All My Life |  4:23,
  Array[4] = Foo Fighters | Monkey Wrench |  3:50,
  Array[8] = Howlin' Wolf | Well That's All Right |  2:55,
  Array[12] = Roy Buchanan | Green Onions |  7:23,
  Array[14] = Janiva Magness | You Were Never Mine |  4:36,
  Array[15] = John Lee Hooker | Hobo Blues |  3:07,
  Array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,
  Array[46] = Jeff Golub | Fish Fare |  4:59,

Sublist -----------------------------
  Sum: 2049
  Array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
  Array[2] = Rihanna | Russian Roulette |  3:48,
  Array[3] = Foo Fighters | All My Life |  4:23,
  Array[4] = Foo Fighters | Monkey Wrench |  3:50,
  Array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
  Array[10] = Reverend Gary Davis | Twelve Sticks |  3:14,
  Array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
  Array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,
  Array[31] = Terra Incogni | Lizard Skin |  4:30,

Sublist -----------------------------
  Sum: 2305
  Array[1] = Carrie Underwood | Quitter |  3:40,
  Array[2] = Rihanna | Russian Roulette |  3:48,
  Array[5] = Eric Clapton | Pretending |  4:43,
  Array[6] = Eric Clapton | Bad Love |  5:08,
  Array[8] = Howlin' Wolf | Well That's All Right |  2:55,
  Array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
  Array[14] = Janiva Magness | You Were Never Mine |  4:36,
  Array[15] = John Lee Hooker | Hobo Blues |  3:07,
  Array[18] = Snoop Dogg | Gangsta Luv |  4:17,
  Array[25] = Yo-yo Ma | Bach: Suite for Cello No. 1 in G Major Prelude |  2:21,



---------------------Paste of Run B with Testing 4-----------------------------
Target time: 2700
Highest Sum : 2700

Sublist -----------------------------
  Sum: 2700
  Array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
  Array[2] = Rihanna | Russian Roulette |  3:48,
  Array[3] = Foo Fighters | All My Life |  4:23,
  Array[5] = Eric Clapton | Pretending |  4:43,
  Array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
  Array[8] = Howlin' Wolf | Well That's All Right |  2:55,
  Array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
  Array[10] = Reverend Gary Davis | Twelve Sticks |  3:14,
  Array[11] = Roy Buchanan | Hot Cha |  3:28,
  Array[12] = Roy Buchanan | Green Onions |  7:23,
  Array[14] = Janiva Magness | You Were Never Mine |  4:36,

Algorithm Elapsed Time: 0.1298 seconds.

Showing tests that does not meet target:
Sublist -----------------------------
  Sum: 1466
  Array[3] = Foo Fighters | All My Life |  4:23,
  Array[4] = Foo Fighters | Monkey Wrench |  3:50,
  Array[5] = Eric Clapton | Pretending |  4:43,
  Array[8] = Howlin' Wolf | Well That's All Right |  2:55,
  Array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
  Array[46] = Jeff Golub | Fish Fare |  4:59,

Sublist -----------------------------
  Sum: 1195
  Array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
  Array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
  Array[8] = Howlin' Wolf | Well That's All Right |  2:55,
  Array[10] = Reverend Gary Davis | Twelve Sticks |  3:14,
  Array[11] = Roy Buchanan | Hot Cha |  3:28,
  Array[29] = Aaron Watson | The Road |  3:24,

Sublist -----------------------------
  Sum: 1362
  Array[3] = Foo Fighters | All My Life |  4:23,
  Array[4] = Foo Fighters | Monkey Wrench |  3:50,
  Array[5] = Eric Clapton | Pretending |  4:43,
  Array[10] = Reverend Gary Davis | Twelve Sticks |  3:14,
  Array[11] = Roy Buchanan | Hot Cha |  3:28,
  Array[21] = Veggie Tales | Donuts for Benny |  3:04,



--------------------------------End of run B----------------------------------*/