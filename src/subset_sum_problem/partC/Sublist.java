package subset_sum_problem.partC;

import java.util.*;

/**
 * One object of this class represents a sub-lists containing a reference to
 * the master data set and a combination list contain index of data set list.
 * Created by diazagasatya on 10/2/17.
 */
class Sublist<E> implements Cloneable {

    private int sum = 0;
    private ArrayList<E> originalObjects;
    private ArrayList<Integer> indices;

    /**
     * A constructor that creates an empty Sublist (no indices)
     * @param orig
     */
    public Sublist(ArrayList<E> orig) {
        sum = 0;
        originalObjects = orig;
        indices = new ArrayList<>();
    }

    /**
     * A Copy constructor that creates a deep copy of Sublist Object
     * @param copyObject         Sublist Object
     */
    public Sublist(Sublist copyObject) {
        sum = copyObject.sum;
        originalObjects = copyObject.originalObjects;
        indices = (ArrayList<Integer>)copyObject.indices.clone();
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
    Sublist addItem( int indexOfItemToAdd , int tuneDuration ) {
        // Creates a clone of the calling object to the augmented sublist
        Sublist augmentedSublist = new Sublist(this);

        // Adding the parameter item to the augmented sublist
        augmentedSublist.indices.add(indexOfItemToAdd);

        // Adjust the sum of the newly created sublist
        augmentedSublist.sum += tuneDuration;

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
     * @param masterSum       Sum of master data set
     * @param target          Target time duration
     * @return validity       Boolean validation
     */
    public boolean validTarget(int masterSum, int target) {
        boolean validity = false;

        if(target <= masterSum) {
            validity = true;
        }

        return validity;
    }
}