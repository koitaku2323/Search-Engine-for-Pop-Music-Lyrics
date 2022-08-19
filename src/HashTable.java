import java.io.Serializable;
import java.util.ArrayList;

public class HashTable<T> implements Serializable {

	private int numElements;
	private ArrayList<LinkedList<T>> Table;

	/**
	 * Constructor for the HashTable class. Initializes the Table to be sized
	 * according to value passed in as a parameter Inserts size empty Lists into the
	 * table. Sets numElements to 0
	 * 
	 * @param size the table size
	 * @precondition size > 0
	 * @throws IllegalArgumentException when size < 0
	 */
	public HashTable(int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("HashTable: table size can not be less than 0");
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
	 * @throws IllegalArgumentException when size < 0
	 */
	public HashTable(T[] array, int size) throws IllegalArgumentException {
		if (size <= 0) {
			throw new IllegalArgumentException("HashTable: table size can not be less than 0");
		}

		Table = new ArrayList<LinkedList<T>>();
		for (int i = 0; i < size; i++) {
			Table.add(new LinkedList<T>());
		}
		numElements = 0;

		if (null != array) {
			for (int i = 0; i < array.length; i++) {
				add(array[i]);
			}
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
	 * @precondition index >= 0 && index < Table.size
	 * @return the count of elements at this index
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= Table.size()) {
			throw new IndexOutOfBoundsException("countBucket: index is out of range");
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
	 * @return the bucket number where the element is located or -1 if it is not
	 *         found
	 * @precondition t != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public int find(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("find: t cannot be null");
		}

		int bucket = hash(t);

		if (Table.get(bucket).findIndex(t) >= 0) {
			return bucket;
		}

		return -1;
	}

	/**
	 * Determines whether a specified element is in the Table
	 * 
	 * @param t the element to locate
	 * @return whether the element is in the Table
	 * @precondition t != null
	 * @throws NullPointerException when the precondition is violated
	 */
	public boolean contains(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("contains: t cannot be null");
		}
		return find(t) >= 0;
	}

	/** Mutators */

	/**
	 * Inserts a new element in the Table at the end of the chain of the correct
	 * bucket
	 * 
	 * @param t the element to insert
	 * @precondition The key t cannot be null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void add(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("add: Object cannot be null");
		}
		int bucket = hash(t);
		Table.get(bucket).addLast(t);
		numElements++;
	}

	/**
	 * Inserts a new element in the Table at the end of the chain of the correct
	 * bucket
	 * 
	 * @param t      the element to insert
	 * @param bucket the bucket to insert
	 * @precondition The key t cannot be null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void add(T t, int bucket) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("add: Object cannot be null");
		}
		Table.get(bucket).addLast(t);
		numElements++;
	}

	/**
	 * Removes the given element from the Table
	 * 
	 * @param t the element to remove
	 * @precondition t != null
	 * @return whether t exists and was removed from the Table
	 * @throws NullPointerException when the precondition is violated
	 */
	public boolean delete(T t) throws NullPointerException {
		if (t == null) {
			throw new NullPointerException("delete: element cannot be null");
		}

		int bucket = find(t);
		if (bucket < 0) { // doesn't exist
			return false;
		}

		LinkedList<T> list = Table.get(bucket);
		list.positionIterator();
		list.advanceIteratorToIndex(list.findIndex(t));
		list.removeIterator();

		numElements--;

		return true;
	}

	/**
	 * Resets the hash table back to the empty state, as if the one argument
	 * constructor has just been called.
	 */
	public void clear() {
		for (int i = 0; i < Table.size(); i++) {
			Table.set(i, new LinkedList<T>());
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
		return (double) numElements / Table.size();
	}

	/**
	 * Creates a String of all elements at a given bucket
	 * 
	 * @param bucket the index in the Table
	 * @return a String of elements, separated by spaces with a new line character
	 *         at the end
	 * @precondition bucket >= 0 && bucket < Table.size()
	 * @throws IndexOutOfBoundsException when bucket is out of bounds
	 */
	public String bucketToString(int bucket) throws IndexOutOfBoundsException {
		if (bucket < 0 || bucket >= Table.size()) {
			throw new IndexOutOfBoundsException("bucketToString: index is out of range");
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
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			sb.append("Bucket ").append(i).append(": ");

			LinkedList<T> list = Table.get(i);

			if (list.isEmpty()) {
				sb.append("empty\n");
			} else {
				sb.append(list.getFirst()).append("\n");
			}
		}

		return sb.toString();
	}

	/**
	 * Starting at the 0th bucket, and continuing in order until the last
	 * bucket,concatenates all elements at all buckets into one String, with a new
	 * line between buckets and one more new line at the end of the entire String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Table.size(); i++) {
			LinkedList<T> list = Table.get(i);
			if (!list.isEmpty()) {
				sb.append(list.toString());
			}
		}
		return sb.append("\n").toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Get the bucket
	 * @param bucketNum
	 * @return LinkedList
	 * @precondition bucket >= 0 && bucket < Table.size()
	 */
	public LinkedList<T> get(int bucketNum) {
		if (bucketNum < 0 || bucketNum >= Table.size()) {
			throw new IndexOutOfBoundsException("bucketToString: index is out of range");
		}
		return Table.get(bucketNum);
	}

	/**
	 * get table size
	 * @return table size
	 */
	public int getTableSize() {
		return Table.size();
	}

}
