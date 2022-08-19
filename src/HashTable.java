/**
 * HashTable.java
 * @author Ryan Yee
 * CIS 22C, Final Project
 */
import java.util.ArrayList;

public class HashTable<T> {

	private int numElements;
	private ArrayList<LinkedList<T>> Table;

	/**
	 * Constructor for the HashTable class. Initializes the Table to be sized
	 * according to value passed in as a parameter Inserts size empty Lists into the
	 * table. Sets numElements to 0
	 * 
	 * @param size the table size
	 * @precondition size > 0
	 * @throws IllegalArgumentException when size <= 0
	 */
	public HashTable(int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("HashTable(): size must be greater than zero!");
		}
		Table = new ArrayList<LinkedList<T>>();
		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		numElements = 0;
	}

	/**
	 * Constructor for HashTable class Inserts the contents of the given array into
	 * the Table at the appropriate indices
	 * 
	 * @param array an array of elements to insert
	 * @param size  the size of the Table
	 * @precondition size > 0
	 * @throws IllegalArgumentException when size <= 0
	 */
	public HashTable(T[] array, int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("HashTable(): size must be greater than zero!");
		}
		if (array == null) {
			return;
		}
		Table = new ArrayList<LinkedList<T>>();
		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		for (int i = 0; i < array.length; i++) {
			this.add(array[i]);
		}

	}

	/** Accessors */

	/**
	 * returns the hash value in the Table for a given Object
	 * 
	 * @param t the Object
	 * @return the index in the Table
	 */
	private int hash(T t) {
		int code = t.hashCode();
		return code % Table.size();
	}

	/**
	 * Counts the number of elements at this index
	 * 
	 * @param index the index in the Table
	 * @precondition index is not out of bound
	 * @return the count of elements at this index
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if ((index < 0) || (index >= Table.size())) {
			throw new IndexOutOfBoundsException("countBucket(): index is out of bounds cannot find the value!");
		}
		return Table.get(index).getLength();
	}

	/**
	 * Determines total number of elements in the Table
	 * 
	 * @return total number of elements
	 */
	public int getNumElements() {
		return numElements;
	}

	/**
	 * Accesses a specified element in the Table
	 * 
	 * @param t the element to locate
	 * @return the object in the Table if found.
	 * @precondition t != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public T get(T t) throws NullPointerException { //previously the find() method
		if (t == null) {
			throw new NullPointerException("get(): cannot access a null!");
		}
		int bucket = hash(t);
		int index = Table.get(bucket).findIndex(t);
		if (index == -1) {
			return null;
		}
		Table.get(bucket).positionIterator();
		Table.get(bucket).advanceIteratorToIndex(index);
		return Table.get(bucket).getIterator();
	}

	/**
	 * Determines whether a specified element is in the Table
	 * 
	 * @param t the element to locate
	 * @return whether the element is in the Table
	 * @precondition t the element != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public boolean contains(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("contains(): cannot access a null!");
		}
		int bucket = hash(t);
		return Table.get(bucket).findIndex(t) != -1;
	}

	/** Mutators */

	/**
	 * Inserts a new element in the Table at the end of the chain of the correct
	 * bucket
	 * 
	 * @param t the element to insert
	 * @precondition t the element != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void add(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("put(): cannot access null");
		}
		int bucket = hash(t);
		Table.get(bucket).addLast(t);
		numElements++;
	}

	/**
	 * Removes the given element from the Table
	 * 
	 * @param t the element to remove
	 * @precondition t the element != null
	 * @return whether t exists and was removed from the Table
	 * @throws NullPointerException when the precondition is violated
	 */
	public boolean delete(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("remove(): cannot access null");
		}
		int bucket = hash(t);
		int index = Table.get(bucket).findIndex(t);
		if (index != -1) {
			Table.get(bucket).positionIterator();
			Table.get(bucket).advanceIteratorToIndex(index);
			Table.get(bucket).removeIterator();
			numElements--;
			return true;
		}
		return false;
	}

	/**
	 * Resets the hash table back to the empty state, as if the one argument
	 * constructor has just been called.
	 */
	public void clear() {
		int size = Table.size();
		Table = new ArrayList<LinkedList<T>>();
		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		numElements = 0;
	}

	/** Additional Methods */

	/**
	 * Computes the load factor
	 * 
	 * @return the load factor
	 */
	public double getLoadFactor() {
		return numElements / (double) Table.size();
	}

	/**
	 * Creates a String of all elements at a given bucket
	 * 
	 * @param bucket the index in the Table
	 * @return a String of elements, separated by spaces with a new line character
	 *         at the end
	 * @precondition index not out of bound
	 * @throws IndexOutOfBoundsException when bucket is out of bounds
	 */
	public String bucketToString(int bucket) throws IndexOutOfBoundsException {
		if ((bucket < 0) || (bucket >= Table.size())) {
			throw new IndexOutOfBoundsException("bucketToString(): index is out of bounds cannot find the value!");
		}
		return Table.get(bucket).toString();
	}

	/**
	 * Creates a String of the bucket number followed by a colon followed by the
	 * first element at each bucket followed by a new line For empty buckets, add
	 * the bucket number followed by a colon followed by empty
	 * 
	 * @return a String of all first elements at each bucket
	 */
	public String rowToString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).isEmpty()) {
				result.append("Bucket " + i + ": empty\n");
			} else {
				result.append("Bucket " + i + ": ").append(Table.get(i).getFirst()).append("\n");
			}
		}
		return result.toString();
	}

	/**
	 * Starting at the 0th bucket, and continuing in order until the last
	 * bucket,concatenates all elements at all buckets into one String, with a new
	 * line between buckets and one more new line at the end of the entire String
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			if (!Table.get(i).isEmpty()) {
				result.append(Table.get(i));
			}
		}
		return result.append("\n").toString();
	}
}

