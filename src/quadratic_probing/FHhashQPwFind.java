package quadratic_probing;

import cs_1c.*;
import java.util.NoSuchElementException;

/**
 * Quadratic Probing with find() for hash table.
 * Created by diazagasatya on 11/6/17.
 */
public class FHhashQPwFind<KeyType, E extends Comparable<KeyType> >
      extends FHhashQP<E> {

   // Public Methods Starts Here--------------------------------
   /**
    * Returns the found object, else throws a NoSuchElementException.
    * @param key      Key Value to find position
    * @return foundObject      Corresponding object from key value
    */
   public E find(KeyType key) {

      E foundObject;
      boolean findValidation;

      findValidation = mArray[findPosKey(key)].state == ACTIVE;

      if(findValidation) {
         foundObject = mArray[findPosKey(key)].data;
      }
      else {
         throw new NoSuchElementException();
      }

      return foundObject;
   }

   // Private (Protected) Methods Starts Here------------------
   /**
    * Creates a hash code based upon given key value.
    * @param key      Key Value to hash
    * @return hashVal      Index of the object from given key value
    */
   protected int myHashKey(KeyType key) {

      int hashVal;

      hashVal = key.hashCode() % mTableSize;
      if(hashVal < 0)
         hashVal += mTableSize;

      return hashVal;
   }

   /**
    * Finding the position of the object based on the key by hashing
    * @param key      Key Value to hash
    * @return index      Position of the requested object
    */
   protected int findPosKey(KeyType key) {

      int kthOddNum = 1;
      int index = myHashKey(key);

      while ( mArray[index].state != EMPTY
            && mArray[index].data.compareTo(key) != 0 )
      {
         index += kthOddNum; // k squared = (k-1) squared + kth odd #
         kthOddNum += 2;     // compute next odd #
         if ( index >= mTableSize )
            index -= mTableSize;
      }
      return index;
   }


}