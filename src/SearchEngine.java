import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchEngine {

	public static final String FILE_FOLDER = "src/";

	public static final String FILE_NAME = "Songs.txt";

	public static final String MUSIC_FILEPATH = FILE_FOLDER + FILE_NAME;

	public static final String[] STOP_WORDS = { "a", "en", "go", "he", "if", "me", "mi", "mr", "ms", "Sí", "Ya", "ah",
			"an", "by", "em", "be", "it", "it's", "at", "on", "to", "of", "the", "and", "uh", "oh", "ohh", "yeah",
			"hmm", "mm", "mmm", "then", "just", "for", "from", "in", "is", "not", "no", "one", "under", "around",
			"every", "b", "i", "n", "p", "s", "v", "y", "c", "d", "f", "m", "o", "p", "s", "t", "y", "okay", "everyone",
			"gonna", "gotta", "that", "than", "do", "bo", "too", "as", "does", "whoa", "title:", "artist:", "year:",
			"lyric:", "ID:", "" };

	public static final int HASHTABLE_SIZE_MUTIPLE = 3;

	private List<BST<PopMusic>> invertedIndex;

	private HashTable<WordID> wordIds;

	private HashTable<PopMusic> musicTable;

	@SuppressWarnings("unchecked")
	public SearchEngine() {

		musicTable = (HashTable<PopMusic>) readFile(SearchEngine.MUSIC_FILEPATH);
		initialIndex();

	}

	/**
	 * initial the inverted index
	 */
	private void initialIndex() {
		Set<String> words = new HashSet<String>();
		// get the total music string, and transfer to array
		String[] wordsArr = musicStrToArr();

		// remove the repeat element
		words.addAll(Arrays.asList(wordsArr));
		// set default size to each list
		int wordIdSize = words.size() * HASHTABLE_SIZE_MUTIPLE;
		wordIds = new HashTable<WordID>(wordIdSize);
		invertedIndex = new ArrayList<BST<PopMusic>>(wordIdSize);

		for (int i = 0; i < wordIdSize; i++) {
			invertedIndex.add(new BST<PopMusic>());
		}

		// word id counter
		int idCount = 0;
		for (String word : words) { // insert each word to word hashtable
			if (!isContain(STOP_WORDS, word)) { // exclude stop words
				idCount++;

				WordID wId = new WordID(word, idCount);
				wordIds.add(wId);

				// set the inverted idex
				insertInvertedIdx(wId);
			}
		}
	}

	/**
	 * insert new inverted index
	 * 
	 * @param wId
	 */
	private void insertInvertedIdx(WordID wId) {

		ArrayList<PopMusic> result = new ArrayList<PopMusic>();

		for (int i = 0; i < musicTable.getTableSize(); i++) {
			LinkedList<PopMusic> list = musicTable.get(i);
			list.positionIterator();
			while (!list.offEnd()) {

				PopMusic p = list.getIterator();
				String content = p.getTitle() + " " + p.getArtist() + " " + p.getYear() + " " + p.getLyric();

				if (content.toLowerCase().contains(wId.getWord().toLowerCase())) {
					result.add(p);

					invertedIndex.get(wId.getID()).insert(p);

				}

				list.advanceIterator();

			}
		}

	}

	/**
	 * search key word
	 * 
	 * @param word
	 * @return a list of music object
	 */
	public BST<PopMusic> searchByWord(String word) {

		if (word == null || word == "") {
			throw new NullPointerException();
		}

		WordID wordId = new WordID(word);
		int bucket = wordId.hashCode() % wordIds.getTableSize();
		LinkedList<WordID> ids = wordIds.get(bucket);

		if (ids.getLength() <= 0) { // cannot find the word
			return null;
		} else if (ids.getLength() == 1) { // no collision

			return getMuscisByWordId(ids.getFirst().getID());

		} else { // has collision
			// check collision, get the word id from
			int id = getWordId(ids, word);
			if (id < 0) {
				return null;
			}

			return getMuscisByWordId(id);

		}

	}

	/**
	 * get music list by bucket from inverted index hashtable
	 * 
	 * @param bucket
	 * @return list of popMusic
	 */
	private BST<PopMusic> getMuscisByWordId(int bucket) {
		BST<PopMusic> musics = invertedIndex.get(bucket);
		if (musics.getSize() <= 0) {
			return null;
		}
		return musics;
	}

	/**
	 * get word id in a linked list
	 * 
	 * @param ids
	 * @param word
	 * @return word id
	 */
	private int getWordId(LinkedList<WordID> ids, String word) {
		ids.positionIterator();
		while (!ids.offEnd()) {
			WordID id = ids.getIterator();
			if (id.getWord().equals(word)) {
				return id.getID();
			}
			ids.advanceIterator();
		}

		return -1;
	}

	/**
	 * Searches the hash table by key
	 *
	 * @param title
	 * @param artist
	 */
	public PopMusic searchByKey(int primaryKey) {
		return musicTable.get(primaryKey).getFirst();
	}

	/**
	 * check if string array contains a string
	 * 
	 * @param arr string array
	 * @param str the string for search
	 * @return true: exist; false non-exist
	 */
	private boolean isContain(String[] arr, String str) {

		for (String string : arr) {
			if (string.equalsIgnoreCase(str)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * read file from a path
	 * 
	 * @param filePath
	 * @return object in file
	 */
	public Object readFile(String filePath) {

		Object o = null;

		FileInputStream fis = null;
		ObjectInputStream oi = null;

		try {
			fis = new FileInputStream(filePath);
			oi = new ObjectInputStream(fis);

			o = oi.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return o;

	}

	/**
	 * write a file to a path
	 * 
	 * @param o        the object want to write
	 * @param filePath
	 */
	public void writeFile(Object o, String filePath) {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(filePath);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(o);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.flush();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * reinitialize inverted index
	 */
	private void refreshData() {
		writeFile(musicTable, MUSIC_FILEPATH);
		initialIndex();
	}

	/**
	 * add a new pop music
	 * 
	 * @param popSong
	 * @return true or false
	 */
	public boolean addPopMusic(PopMusic popSong) {
		musicTable.add(popSong);
		refreshData();
		return true;
	}

	/**
	 * print the popmusic's title and artist
	 */
	public void printIntro() {
		System.out.println(musicTableToString(0));
	}

	/**
	 * search songs by the title and artist
	 * 
	 * @param title
	 * @param artist
	 * @return a music object
	 */
	public PopMusic searSongByPrimaryKey(String title, String artist) {
		return searchByPrimaryKey(title, artist);

	}

	/**
	 * split the string to a string array by specific char
	 * 
	 * @return
	 */
	private String[] musicStrToArr() {
		String str = musicTableToString(1);
		String[] wordsArr = str.split(" |\\,|\\.|\\!|\\r?\\n|\\r|\\(|\\)|\"|\'|\\?|\\-");
		return wordsArr;
	}

	/**
	 * assemble the music string
	 * 
	 * @param type 1: all the elements include; other: only title and artist
	 * @return
	 */
	public String musicTableToString(int type) {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < musicTable.getTableSize(); i++) {

			LinkedList<PopMusic> mList = musicTable.get(i);

			mList.positionIterator();
			while (!mList.offEnd()) {

				PopMusic p = mList.getIterator();

				sb.append("\nTitle: ").append(p.getTitle());
				sb.append("\nArtist: ").append(p.getArtist());

				if (type == 1) { // type 1: add all the element's String
					sb.append("\nYear: ").append(p.getYear());
					sb.append("\nLyric: ").append(p.getLyric());

				}
				sb.append("\n");
				mList.advanceIterator();
			}

		}
		return sb.toString();
	}

	private PopMusic searchByPrimaryKey(String title, String artist) {
		PopMusic p = new PopMusic(title, artist, 0, "");
		int bucket = p.hashCode() % musicTable.getTableSize();
		LinkedList<PopMusic> list = musicTable.get(bucket);
		if (list.getLength() == 1) {
			return list.getFirst();
		} else if (list.getLength() > 1) {

			list.positionIterator();

			while (!list.offEnd()) {
				PopMusic music = list.getIterator();
				if (music.equals(p)) {
					return music;
				}
				list.advanceIterator();
			}
		}

		return null;
	}

	/**
	 * delete music by title and artist
	 * 
	 * @param title
	 * @param artist
	 * @return true or false
	 */
	public boolean deletePopMusic(String title, String artist) {

		PopMusic p = searchByPrimaryKey(title, artist);
		if (p != null) {
			return deleteMuscis(p);
		}

		return false;
	}

	private boolean deleteMuscis(PopMusic music) {
		boolean flag = musicTable.delete(music);
		if (flag) {
			refreshData();
		}
		return flag;
	}

	/**
	 * save music hash table into txt file
	 * 
	 * @param userName file name prefix
	 */
	public void saveMusicTo(String userName) {
		writeFile(musicTable, FILE_FOLDER + userName + "-" + FILE_NAME);
	}

	/**
	 * modify music
	 * 
	 * @param p         old music object
	 * @param newTitle
	 * @param newArtist
	 */
	public void updateMusic(PopMusic p, String newTitle, String newArtist) {

		musicTable.delete(p);
		p.setTitle(newTitle);
		p.setArtist(newArtist);
		musicTable.add(p);
		refreshData();

	}

}
