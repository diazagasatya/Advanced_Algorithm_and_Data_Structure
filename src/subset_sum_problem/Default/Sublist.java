package subset_sum_problem.Default;


import java.util.*;
/**
 * One object of this class represents a sub-lists containing a reference to
 * the master data set and a combination list contain index of data set list.
 * Created by diazagasatya on 10/2/17.
 */
class Sublist implements Cloneable {

    private int sum = 0;
    private ArrayList<Integer> originalObjects;
    private ArrayList<Integer> indices;

    /**
     * A constructor that creates an empty Sublist (no indices)
     * @param orig
     */
    public Sublist(ArrayList<Integer> orig) {
        sum = 0;
        originalObjects = orig;
        indices = new ArrayList<Integer>();
    }

    /**
     * Getters for sum
     * @return sum         The sum of the indices list.
     */
    int getSum() { return sum; }

    /**
     * A deep copy of calling object.
     * @return newObject         Object clone.
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        // shallow copy
        Sublist newObject = (Sublist)super.clone();
        // deep copy
        newObject.indices = (ArrayList<Integer>)indices.clone();

        return newObject;
    }

    /**
     * Creates an augmented sublist cloning the calling object, and adds
     * the index from the parameter.
     * @param indexOfItemToAdd         The index from the master set.
     * @return augmentedSublist        The newly cloned calling object
     *                                 with added item.
     */
    Sublist addItem( int indexOfItemToAdd ) {
        // Creates a clone of the calling object to the augmented sublist
        Sublist augmentedSublist = new Sublist(originalObjects);
        augmentedSublist.indices = (ArrayList<Integer>)this.indices.clone();

        // Adding the parameter item to the augmented sublist
        augmentedSublist.indices.add(indexOfItemToAdd);

        // Adjust the sum of the newly created sublist
        int newSublistLength = augmentedSublist.indices.size();
        for( int a = 0; a < newSublistLength; a++ ) {
            augmentedSublist.sum += originalObjects.get
                   (augmentedSublist.indices.get(a));
        }

        return augmentedSublist;
    }

    /**
     * Print the whole sublist to client.
     */
    void showSublist() {
        System.out.println("Sublist ----------------------------- ");
        System.out.println("  Sum: " + sum);

        int sublistLength = indices.size();

        for( int a = 0; a < sublistLength; a++ ) {
            System.out.println("  Array[" + indices.get(a) + "] = " +
                   originalObjects.get(indices.get(a)) + ",");
        }
        System.out.println();
    }

    /**
     * Master set's total value validation with respect to target
     * @param dataSet         Master set list of integer values
     * @return validity       Boolean validation
     */
    public boolean validTarget(ArrayList<Integer> dataSet, int target) {
        int masterSum = 0;
        int dataLength = dataSet.size();
        boolean validity = false;

        for(int a = 0; a < dataLength; a++) {
            masterSum += dataSet.get(a);
        }

        if(target <= masterSum) {
            validity = true;
        }

        return validity;
    }

    /**
     * Getters for master set's total sum
     * @return masterSum         Master set's sum
     */
    int getMasterSum() {
        int dataSetLength = originalObjects.size();
        int masterSum = 0;

        for(int a = 0; a < dataSetLength; a++) {
            masterSum += originalObjects.get(a);
        }
        return masterSum;
    }
}