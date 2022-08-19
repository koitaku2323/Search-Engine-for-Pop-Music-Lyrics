import java.io.Serializable;
import java.util.NoSuchElementException;

@SuppressWarnings("serial")
public class LinkedList<T> implements Serializable {


	private class Node implements Serializable  {
		
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}

	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTORS ****/

	/**
	 * Instantiates a new LinkedList with default values
	 * 
	 * @postcondition created a new LinkedList with default values
	 */
	public LinkedList() {
		first = null;
		last = null;
		iterator = null;
		length = 0;
	}

	/**
	 * Converts the given array into a LinkedList
	 * 
	 * @param array the array of values to insert into this LinkedList
	 * @postcondition <fill in here>
	 */
	public LinkedList(T[] array) {
		if(array == null) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			this.addLast(array[i]);
		}
	}

	/**
	 * Instantiates a new LinkedList by copying another List
	 * 
	 * @param original the LinkedList to copy
	 * @postcondition a new List object, which is an identical, but separate, copy
	 *                of the LinkedList original
	 */
	public LinkedList(LinkedList<T> original) {
		if (original == null) { // if original is null, return
			return;
		}
		if (original.length == 0) { // edge case: if original list is not null but length == 0
			length = 0; // set the member variables to default value
			first = null;
			last = null;
			iterator = null;
		} else { // general case
			Node temp = original.first; // get the first node of the list
			while (temp != null) { // Loop through until the last node
				addLast(temp.data); // call addLast(data) method to add new node
				temp = temp.next; // set the next to temp node
			}
			iterator = null;
		}
	}

	/**** ACCESSORS ****/

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition length != 0
	 * @return the value stored at node first
	 * @throws NoSuchElementException when LinkedList is empty (violates
	 *                                precondition)
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst: LinkedList is empty");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition length != 0
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when LinkedList is empty (violates
	 *                                precondition)
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast: LinkedList is empty");
		} else {
			return last.data;
		}
	}

	/**
	 * Returns the data stored in the iterator node
	 * 
	 * @precondition iterator is not null
	 * @throw NullPointerException when iterator is null
	 */
	public T getIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("getIterator: iterator is null");
		}
		return iterator.data;
	}

	/**
	 * Returns the current length of the LinkedList
	 * 
	 * @return the length of the LinkedList from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the LinkedList is currently empty
	 * 
	 * @return whether the LinkedList is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * Returns whether the iterator is offEnd, i.e. null
	 * 
	 * @return whether the iterator is null
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**** MUTATORS ****/

	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the LinkedList
	 * @postcondition <Element is inserted to the first position of the List>
	 */
	public void addFirst(T data) {
		if (length == 0) {
			first = last = new Node(data);
		} else {
			Node node = new Node(data);
			first.prev = node;
			node.next = first;
			first = node;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the LinkedList
	 * @postcondition <Element is inserted to the last position of the List>
	 */
	public void addLast(T data) {
		Node newNode = new Node(data);
		if (length == 0) { // if current last node is null, set the first
			first = last = newNode;
		} else { // set the reference to the prev last node
			last.next = newNode;
			newNode.prev = last;
			last = newNode; // set the new node to the last
		}
		length++;
	}

	/**
	 * Inserts a new element after the iterator
	 * 
	 * @param data the data to insert
	 * @precondition <!offEnd()>
	 * @throws NullPointerException <when iterator is null>
	 */
	public void addIterator(T data) throws NullPointerException {

		if (offEnd()) { // precondition
			throw new NullPointerException("addIterator(data): Iterator cannot be null");
		} else if (iterator == last) { // edge case: last node doesn't have next
			addLast(data);
		} else { // general case: insert new node after the iterator
			Node newNode = new Node(data); // Declare a new Node variable
			newNode.next = iterator.next; // Update the new node's next link to equal the iterator's next
			newNode.prev = iterator; // Set new node's prev to iterator
			iterator.next.prev = newNode; // set iterator.next.prev to new node
			iterator.next = newNode; // Set the iterator's next to link to the new
			length++;
		}
	}

	/**
	 * removes the element at the front of the LinkedList
	 * 
	 * @precondition <length != 0>
	 * @postcondition <The first node is removed>
	 * @throws NoSuchElementException <when the list is empty>
	 */
	public void removeFirst() throws NoSuchElementException {
		if (length == 0) { // length cannot be 0
			throw new NoSuchElementException("removeFirst(): Cannot remove from an empty List");
		} else if (length == 1) { // edge case 1: if there's only one node
			first = last = iterator = null;
		} else { // general case

			if (iterator == first) { // if the first referenced by iterator
				iterator = null; // remove iterator
			}
			first = first.next; // set the next be the new first
			first.prev = null; // clear the new first.prev

		}
		length--;
	}

	/**
	 * removes the element at the end of the LinkedList
	 * 
	 * @precondition <length != 0>
	 * @postcondition <The last node is removed>
	 * @throws NoSuchElementException <when the list is empty>
	 */
	public void removeLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("removeLast(): Cannot remove from an empty List");
		} else if (length == 1) {
			first = last = iterator = null;
		} else {
			last = last.prev;
			last.next = null;
			if (iterator == last) {
				iterator = null;
			}
		}
		length--;
	}

	/**
	 * removes the element referenced by the iterator
	 * 
	 * @precondition !offEnd()
	 * @postcondition the Node referenced by iterator is removed
	 * @throws NullPointerException when iterator is null
	 */
	public void removeIterator() throws NullPointerException {
		if (offEnd()) { // precondition
			throw new NullPointerException("removeIterator: Iterator cannot be null");
		} else if (iterator == first) { // edge case 1: iterator equals first
			removeFirst();
		} else if (iterator == last) { // edge case 2: iterator equals last
			removeLast();
		} else { // general case: connect the two nodes before and after the iterator
			iterator.next.prev = iterator.prev; // set iterator.next's prev to the previous one
			iterator.prev.next = iterator.next; // set iterator.prev's next to iterator.next
			iterator = null;
			length--;
		}
	}

	/**
	 * places the iterator at the first node
	 * 
	 * @postcondition <The iterator settled to the first position>
	 */
	public void positionIterator() {
		iterator = first;
	}

	/**
	 * Moves the iterator one node towards the last
	 * 
	 * @precondition <!offEnd()>
	 * @postcondition <Iterator referenced to the next>
	 * @throws NullPointerException when iterator is null
	 */
	public void advanceIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("advanceIterator(): Iterator is offEnd, cannot be advanced");
		}
		iterator = iterator.next;
	}

	/**
	 * Moves the iterator one node towards the first
	 * 
	 * @precondition <!offEnd()>
	 * @postcondition <Iterator referenced to the prev>
	 * @throws NullPointerException <when iterator is null>
	 */
	public void reverseIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("reverseIterator(): Iterator is offEnd, cannot be reversed");
		}
		iterator = iterator.prev;
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Converts the LinkedList to a String, with each value separated by a blank
	 * line At the end of the String, place a new line character
	 * 
	 * @return the LinkedList as a String
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		Node temp = first;
		while (temp != null) {
			result.append(temp.data + " ");
			temp = temp.next;
		}
		return result.toString() + "\n";
	}

	/**
	 * Determines whether the given Object is another LinkedList, containing the
	 * same data in the same order
	 * 
	 * @param o another Object
	 * @return whether there is equality
	 */
	@SuppressWarnings("unchecked") // good practice to remove warning here
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof LinkedList)) {
			return false;
		} else {
			LinkedList<T> L = (LinkedList<T>) o;
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) {
					if (!temp1.data.equals(temp2.data)) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/** CHALLENGE METHODS */

	/**
	 * Moves all nodes in the list towards the end of the list the number of times
	 * specified Any node that falls off the end of the list as it moves forward
	 * will be placed the front of the list For example: [1, 2, 3, 4, 5], numMoves =
	 * 2 -> [4, 5, 1, 2 ,3] For example: [1, 2, 3, 4, 5], numMoves = 4 -> [2, 3, 4,
	 * 5, 1] For example: [1, 2, 3, 4, 5], numMoves = 7 -> [4, 5, 1, 2 ,3]
	 * 
	 * @param numMoves the number of times to move each node.
	 * @precondition numMoves >= 0
	 * @postcondition iterator position unchanged (i.e. still referencing the same
	 *                node in the list, regardless of new location of Node)
	 * @throws IllegalArgumentException when numMoves < 0
	 */
	public void revolvingList(int numMoves) throws IllegalArgumentException {
		if (numMoves <= 0) {
			throw new IllegalArgumentException("revolvingList: numMoves cannot be equal or less than 0.");
		} else {
			for (int i = 0; i < numMoves; i++) {
				T tempFirst = first.data;
				this.removeFirst();
				addFirst(last.data);
				this.removeLast();
				addLast(tempFirst);

			}
		}
	}

	/**
	 * Splices together two LinkedLists to create a third List which contains
	 * alternating values from this list and the given parameter For example:
	 * [1,2,3] and [4,5,6] -> [1,4,2,5,3,6] For example: [1, 2, 3, 4] and [5, 6] ->
	 * [1, 5, 2, 6, 3, 4] For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
	 * 
	 * @param list the second LinkedList
	 * @return a new LinkedList, which is the result of interlocking this and list
	 * @postcondition this and list are unchanged
	 */
	public LinkedList<T> interlockLists(LinkedList<T> list) {
		if (this.length == 0 && list.length == 0) {
			return new LinkedList<T>();
		} else if (this.length == 0) {
			return list;
		} else if (list.length == 0) {
			return this;
		} else {

			LinkedList<T> newList = new LinkedList<T>();

			int newLength = this.length + list.length;
			this.positionIterator();
			list.positionIterator();

			for (int i = 0; i < newLength; i++) {

				if (!this.offEnd()) {
					newList.addLast(this.getIterator());
					this.advanceIterator();
				}

				if (!list.offEnd()) {
					newList.addLast(list.getIterator());
					list.advanceIterator();
				}

			}

			return newList;
		}
	}

	/**
	 * Determines at which index the iterator is located Indexed from 0 to length -
	 * 1
	 * 
	 * @return the index number of the iterator
	 * @precondition !isOffEnd()
	 * @throws NullPointerException when precondition is violated
	 */
	public int getIteratorIndex() throws NullPointerException {

		if (offEnd()) {
			throw new NullPointerException("getIteratorIndex: iterator can not be null");
		}

		int index = -1;
		Node temp = first;
		while (temp != null) {

			index++;

			if (temp == iterator) {
				return index;
			}

			temp = temp.next;

		}

		return -1;
	}

	/**
	 * Searches the LinkedList for a given element's index
	 * 
	 * @param data the data whose index to locate
	 * @return the index of the data or -1 if the data is not contained in the
	 *         LinkedList
	 */
	public int findIndex(T data) {

		int index = -1;
		Node temp = first;
		while (temp != null) {

			index++;

			if (temp.data.equals(data)) {
				return index;
			}
			
			temp = temp.next;
		}

		return -1;
	}

	/**
	 * Advances the iterator to location within the LinkedList specified by the
	 * given index
	 * 
	 * @param index the index at which to place the iterator.
	 * @precondition !offEnd()
	 * @throws NullPointerException when the precondition is violated
	 */
	public void advanceIteratorToIndex(int index) throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("advanceIteratorToIndex: iterator can not be null");
		}
		
		for (int i = 0; i < index; i++) {
			this.advanceIterator();
		}
		
	}

}
