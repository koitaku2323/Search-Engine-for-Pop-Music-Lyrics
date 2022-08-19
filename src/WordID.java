public class WordID {

	private String word;

	private int id;

	public WordID() {

	}

	public WordID(String word) {
		super();
		this.word = word;
	}

	public WordID(String word, int id) {
		super();
		this.word = word;
		this.id = id;
	}

	/**** MUTATORS ****/

	/**
	 * Updates the word
	 * 
	 * @param word a new word
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * Updates the id
	 * 
	 * @param id a new id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**** ACCESSORS ****/

	/**
	 * Accesses the id
	 * 
	 * @return the id
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Accesses the word
	 * 
	 * @return the word
	 */
	public String getWord() {
		return this.word;
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Returns a consistent hash code for each PopMusic by summing the Unicode
	 * values of title and artist in the key Key = word
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		/*String key = word;
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;*/
		int code = word.hashCode() & Integer.MAX_VALUE;
		return code;
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Compares this WordID to another Object for equality
	 * 
	 * @param o another Object
	 * @return true if o is a PopMusic and has a matching word to this WordID
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof WordID)) {
			return false;
		} else {
			WordID L = (WordID) o;
			if (this.word.compareTo(L.word) != 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "WordID [word=" + word + ", id=" + id + "]";
	}

}
