package subset_sum_problem.Default;

import java.util.*;

//------------------------------------------------------
public class Foothill
{
    // -------  PART A main --------------
    public static void main(String[] args) throws Exception
    {
        int target = 14;
        ArrayList<Integer> dataSet = new ArrayList<Integer>();
        ArrayList<Sublist> choices = new ArrayList<Sublist>();
        int choiceSum, masterSum, newChoiceSum, currentSum,
               dataSize, currentSize;
        int kBest = 0;
        int temporarySum = 0;
        boolean targetValid;
        boolean foundPerfect = false;

        dataSet.add(15); dataSet.add(5); dataSet.add(3);
        dataSet.add(1); dataSet.add(3);

        // Subset Sum Algorithm
        Sublist collection = new Sublist(dataSet);
        choices.add(collection);
        dataSize = dataSet.size();

        // Checking target validity in respect to master set reachable total
        targetValid = collection.validTarget(dataSet, target);
        masterSum = collection.getMasterSum();

        if(!targetValid) {
            System.out.println("Target value " + target + " is too " +
                    "large for Data set, current reachable total is " +
                    masterSum);
        }

        while(targetValid == true) {

            // Loop over all element a in master set
            for(int a = 0; a < dataSize; a++) {
                currentSize = choices.size();
                // Loop over all sub-lists in choices
                for(int b = 0; b < currentSize; b++) {
                    choiceSum = choices.get(b).getSum();
                    newChoiceSum = choiceSum + dataSet.get(a);
                    if(newChoiceSum <= target) {
                        choices.add(choices.get(b).addItem(a));
                        if(newChoiceSum == target) {
                            foundPerfect = true;
                            break;
                        }
                    }
                }
            }
            // Find the sub-lists that has the highest sum
            for(int b = 0; b < choices.size(); b++ ) {
                currentSum = choices.get(b).getSum();
                if(temporarySum < currentSum ) {
                    temporarySum = currentSum;
                    kBest = b;
                }
            }

            System.out.println("Target : " + target);
            System.out.println("Highest Sum : " + temporarySum + "\n");
            choices.get(kBest).showSublist();

            // Testing purposes
            if(foundPerfect == true) {
                int choiceSize = choices.size();
                int testOne = choiceSize / 2;
                int testTwo = choiceSize / 4;
                int testThree = choiceSize / 6;

                System.out.println("Showing tests that does not meet target:");
                choices.get(testOne).showSublist();
                choices.get(testTwo).showSublist();
                choices.get(testThree).showSublist();
            }

            targetValid = false;
        }
    }
}

/*----------------------Paste of Run A with Testing 1--------------------------
Target : 72
Highest Sum : 72

Sublist -----------------------------
  Sum: 72
  Array[0] = 2,
  Array[2] = 22,
  Array[3] = 5,
  Array[4] = 15,
  Array[6] = 9,
  Array[7] = 19,

Showing tests that does not meet target:
Sublist -----------------------------
  Sum: 21
  Array[0] = 2,
  Array[7] = 19,

Sublist -----------------------------
  Sum: 67
  Array[2] = 22,
  Array[3] = 5,
  Array[4] = 15,
  Array[5] = 25,

Sublist -----------------------------
  Sum: 59
  Array[1] = 12,
  Array[2] = 22,
  Array[5] = 25,


---------------------Paste of Run A with Testing 2-----------------------------
Target value 4829 is too large for Data set, current reachable total is 138

---------------------Paste of Run A with Testing 3-----------------------------
Target : 13
Highest Sum : 12

Sublist -----------------------------
  Sum: 12
  Array[1] = 12,

---------------------Paste of Run A with Testing 4-----------------------------
Target : 89
Highest Sum : 89

Sublist -----------------------------
  Sum: 89
  Array[0] = 2,
  Array[1] = 12,
  Array[2] = 22,
  Array[5] = 25,
  Array[6] = 9,
  Array[7] = 19,

Showing tests that does not meet target:
Sublist -----------------------------
  Sum: 41
  Array[0] = 2,
  Array[3] = 5,
  Array[4] = 15,
  Array[7] = 19,

Sublist -----------------------------
  Sum: 36
  Array[2] = 22,
  Array[3] = 5,
  Array[6] = 9,

Sublist -----------------------------
  Sum: 52
  Array[1] = 12,
  Array[4] = 15,
  Array[5] = 25,

---------------------Paste of Run A with Testing 5-----------------------------
Target : 138
Highest Sum : 138

Sublist -----------------------------
  Sum: 138
  Array[0] = 2,
  Array[1] = 12,
  Array[2] = 22,
  Array[3] = 5,
  Array[4] = 15,
  Array[5] = 25,
  Array[6] = 9,
  Array[7] = 19,
  Array[8] = 29,

Showing tests that does not meet target:
Sublist -----------------------------
  Sum: 29
  Array[8] = 29,

Sublist -----------------------------
  Sum: 19
  Array[7] = 19,

Sublist -----------------------------
  Sum: 48
  Array[0] = 2,
  Array[2] = 22,
  Array[4] = 15,
  Array[6] = 9,


--------------------------------End of run A----------------------------------*/
