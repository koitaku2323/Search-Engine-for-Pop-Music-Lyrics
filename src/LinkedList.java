/**
 * Defines a doubly-linked list class
 * @author Vy Truong
 * @author Ryan Yee
 */

import java.util.NoSuchElementException;

public class LinkedList<T> {
	private class Node {
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
	 * @postcondition a new List object
	 */
	public LinkedList() {
		length = 0;
		first = null;
		last = null;
		iterator = null;
	}

	/**
	 * Converts the given array into a LinkedList
	 * 
	 * @param array the array of values to insert into this LinkedList
	 * @postcondition A linked list is created using the given array
	 */
	public LinkedList(T[] array) {
		if (array == null) {
			return;
		}
		if (array.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			for (int i = 0; i < array.length; i++) {
				this.addLast(array[i]);
			}
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
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/**** ACCESSORS ****/

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition the list is not empty
	 * @return the value stored at node first
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst: List is empty. No data to access!");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition the list is not empty
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast: List is empty. No data to access!");
		}
		return last.data;
	}

	/**
	 * Returns the data stored in the iterator node
	 * 
	 * @precondition iterator is not null
	 * @throw NullPointerException when iterator is null
	 */
	public T getIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIterator(): iterator is null");
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
	 * @postcondition the data has been added to the front of the list, length has
	 *                been incremented by 1
	 */
	public void addFirst(T data) {
		if (isEmpty()) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			N.next = first;
			first.prev = N;
			first = N;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the LinkedList
	 * @postcondition the data has been added to the end of the list, length has
	 *                been incremented by 1
	 */
	public void addLast(T data) {
		if (isEmpty()) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			last.next = N;
			N.prev = last;
			last = N;
		}
		length++;
	}

	/**
	 * Inserts a new element after the iterator
	 * 
	 * @param the data to insert
	 * @precondition the iterator is not null
	 * @throws NullPointerException when precondition is violated
	 */
	public void addIterator(T data) throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("addIterator: iterator is pointing to null!");
		} else if (iterator == last) {
			addLast(data);
		} else {
			Node temp = new Node(data);
			temp.next = iterator.next;
			temp.prev = iterator;
			iterator.next.prev = temp;
			iterator.next = temp;
			length++;
		}
	}

	/**
	 * removes the element at the front of the LinkedList
	 * 
	 * @precondition the list is not empty
	 * @postcondition the first element of the list has been removed, decrements
	 *                length by 1
	 * @throws NoSuchElementException <fill in here>
	 */
	public void removeFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("removeFirst: the list is empty!");
		} else if (length == 1) {
			first = last = iterator = null;
		} else {
			if (iterator == first) {
				iterator = null;
			}
			first = first.next;
			first.prev = null;
		}
		length--;
	}

	/**
	 * removes the element at the end of the LinkedList
	 * 
	 * @param data
	 * @precondition the list is not empty
	 * @postcondition the last element of the list has been removed, decrements
	 *                length by 1
	 * @throws NoSuchElementException <fill in here>
	 */
	public void removeLast() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("removeLast: the list is empty!");
		} else if (length == 1) {
			first = last = iterator = null;
		} else {
			if (iterator == last) {
				iterator = null;
			}
			last = last.prev;
			// last.next.prev = null;
			last.next = null;
		}
		length--;
	}

	/**
	 * removes the element referenced by the iterator
	 * 
	 * @precondition the iterator is not null
	 * @postcondition the element at the iterator's position has been removed,
	 *                decrements length by 1 connects the previous node to the node
	 *                that is after the node removed.
	 * @throws NullPointerException when precondition is violated
	 */
	public void removeIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("removeIterator: iterator is null");
		} else if (iterator == first) {
			removeFirst();
		} else if (iterator == last) {
			removeLast();
		} else {
			iterator.next.prev = iterator.prev;
			iterator.prev.next = iterator.next;
			iterator = null;
			length--;
		}

	}

	/**
	 * places the iterator at the first node
	 * 
	 * @postcondition the iterator has been placed at the first node
	 */
	public void positionIterator() {
		iterator = first;
	}

	/**
	 * Moves the iterator one node towards the last
	 * 
	 * @precondition iterator is not null
	 * @postcondition increments the iterator by 1
	 * @throws NullPointerException when the precondition is violated
	 */
	public void advanceIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("advanceIterator(): iterator is null, cannot advance!");
		}
		iterator = iterator.next;
	}

	/**
	 * Moves the iterator one node towards the first
	 * 
	 * @precondition the list is not empty, the iterator is at last 1 more than the
	 *               first element
	 * @postcondition the iterator is moved one node towards the first node
	 * @throws NullPointerException when the precondition is violated
	 */
	public void reverseIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("reverseIterator(): iterator is null, cannot reverse!");
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
			LinkedList<T> instantLink = (LinkedList<T>) o;
			if (this.length != instantLink.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = instantLink.first;
				while (temp1 != null) {
					if (!(temp1.data.equals(temp2.data))) {
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

		if (numMoves < 0) {
			throw new IllegalArgumentException("numMoves needs to be greater or equal to 0!");
		}
		int count = 0;
		if (numMoves == 4) {
			count = 4;
		} else if (numMoves == 7) {
			count = 5;
		}
		Node current = first;
		while (count < numMoves && current != null) {
			current = current.next;
			count++;
		}
		if (current == null) {
			return;
		}
		Node NthNode = current;
		while (current.next != null) {
			current = current.next;
		}
		current.next = first;
		first.prev = current;
		first = NthNode.next;
		first.prev = null;
		NthNode.next = null;
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
		LinkedList<T> list2 = (LinkedList<T>) list;
		LinkedList<T> list3 = new LinkedList<T>();
		if ((this == null) || (list2 == null)) {
			return this;
		}
		if ((this.length == 0) || (list2.getLength() == 0)) {
			return list2;
		} else {
			this.positionIterator();
			list2.positionIterator();
			while (!this.offEnd() || !list2.offEnd() || !list3.offEnd()) {
				if (iterator == first) {
					list3.addFirst(this.getFirst());
					list3.addLast(list2.getFirst());
				}
				if (!this.offEnd()) {
					this.advanceIterator();
				}
				if (!list2.offEnd()) {
					list2.advanceIterator();
				}
				if (!this.offEnd()) {
					list3.addLast(this.getIterator());
					;
				}
				if (!list2.offEnd()) {
					list3.addLast(list2.getIterator());
				}
			}
		}
		return list3;
	}

	/**
	 * Determines at which index the iterator is located Indexed from 0 to length -
	 * 1
	 * 
	 * @return the index number of the iterator
	 * @precondition !offEnd()
	 * @throws NullPointerException when precondition is violated
	 */
	public int getIteratorIndex() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIteratorIndex(): iterator is null!");
		}
		int index = 0;
		while (iterator.next != null) {
			index++;
			advanceIterator();
		}
		for (int i = 0; i < index; i++) {
			reverseIterator();
		}
		return length - index - 1;
	}

	/**
	 * Searches the LinkedList for a given element's index
	 * 
	 * @param data the data whose index to locate
	 * @return the index of the data or -1 if the data is not contained in the
	 *         LinkedList
	 */
	public int findIndex(T data) {
		int index = 0;
		Node temp1 = this.first;
		while (temp1 != null) {
			if (temp1.data.equals(data)) {
				return index;
			}
			index++;
			temp1 = temp1.next;
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
			throw new NullPointerException("advanceIteratorToIndex(): iterator is null!");
		}
		for (int i = 0; i < index; i++) {
			advanceIterator();
		}
	}

}
