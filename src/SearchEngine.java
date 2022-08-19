import java.util.ArrayList;

public class SearchEngine {
	private final int SIZE = 100;
    
	private HashTable<PopMusic> music_list = new HashTable<>(SIZE);
    private ArrayList<BST<PopMusic>> music_id = new ArrayList<>();
	private HashTable<WordID> word_id = new HashTable<>(SIZE);
	private ArrayList<String> yearArr = new ArrayList<>();

	public SearchEngine() {
		
	}
	
	/**
	 * Searches the BST by word
	 *
	 * @param word
	 */
	public void searchByWord(String word){
		System.out.println(music_id.get(word_id.get(new WordID(word)).getID()).inOrderString());
		//find and get the PopMusic Object by first finding the location 
		//of the indicated PopMusic that contains the word in one of its 
		//attributes using the Hashtable of WordIDs' id then print it out
		//when called in the menu driven interface
	}

	/**
	 * Searches the hash table by key
	 *
	 * @param title
	 * @param artist
	 */
	public PopMusic searchByKey(String title, String artist){
		return music_list.get(new PopMusic(title, artist)); // returns the PopMusic Object if it is found in the hashTable
	}
	
	/**
	 * Searches the hash table by title
	 *
	 * @param title
	 */
	public PopMusic searchByTitle(String title){ //It actually doesn't work for some reason, someone pls fix this if necessary
		return music_list.get(new PopMusic(title)); // returns the PopMusic Object if it is found in the hashTable
	}
	
	/**
	 * Check whether the given word is in any music
	 *
	 * @param word the specific word of the lyric
	 * @return true if the given word has a music lyric with that word
	 */
	
	public boolean checkByWord(String word){
		return word_id.get(new WordID(word)) != null && music_id.get(word_id.get(new WordID(word)).getID()).getSize() != 0;
		//If the word exists in the HashTable of WordID and the HashTable of music_id is not empty return true;
	}
	
	/**
	 * Check whether the given title is in the list of BST
	 *
	 * @param title the title of the music
	 * @return true if the given title is in the list of BST
	 */
	public boolean checkByTitle(String title, String word) { //Doesn't work, could use checkByKey instead.
		return music_id.get(word_id.get(new WordID(word)).getID()).search(new PopMusic(title)) != null;
	}
	
	/**
	 * Check whether the given title and artist combo (key) is in the list of BST
	 *
	 * @param title, artist, and the user inputs of title and artist
	 * @return true if the given title is in the list of BST
	 */
	public boolean checkByKey(String title, String artist, String word1, String word2) { //This one will work, I think
		return music_id.get(word_id.get(new WordID(word1)).getID()).search(new PopMusic(title, artist)) != null && music_id.get(word_id.get(new WordID(word2)).getID()).search(new PopMusic(title, artist))!= null;
	}
	
	/**
	 * Compares whether the music is released in the year input
	 *
	 * @param input the year in which the music was released
	 * @return the number of musics released in the year input
	 */
	public int statisticsByYear(String input){
		int count  = 0;
		for(String year : yearArr){
			if(year == input)
				count++;
		}
		return count;
	}
	
	/**
	 * Prints the musics to a string
	 *
	 * @return all of the music's title? Idk, maybe it returns the whole object in strings
	 */
	public String printDetails(){
		//ArrayList<PopMusic> temp = new ArrayList<>();
		return music_list.rowToString();
		//String sum = "";
		//for(int i = 0; i < temp.size(); i++){
 		//	sum += (i+1) + ". " + temp.get(i).getTitle() + "\n";
		//}
		//return sum;
	}

	/**
	 * Sets the information of a music
	 *
	 * @param musics the list of musics
	 * @param music1 the list of musics
	 * @param wordId the mapping of words to numerical IDs
	 * @param years the release years
	 */
	public void setInfo(ArrayList<BST<PopMusic>> musics, HashTable<PopMusic> music1, HashTable<WordID> wordId, String [] years){
		for(BST<PopMusic> music : musics){
            music_id.add(new BST<>(music));
        }
        music_list = music1;
		word_id = wordId;
		for(String year : years){
			yearArr.add(year);
		}
	}

	/**
	 * Adds a new music after removing the 20 unwanted words
	 *
	 * @param music the music to be added
	 */
	public void addPopMusic(PopMusic music){
		int id = music_id.size();
		yearArr.add(music.getYear());
		removeUnwantedWords(music);
		music_list.add(music);
		for(String word : music.getLyrics().split(" ")) {
			word = word.trim();
			WordID words = new WordID(word);
			if(!(word_id.contains(words))) {
				words.setID(id);
				word_id.add(words);
				music_id.add(new BST<>());
				id++;
			}
			music_id.get(word_id.get(words).getID()).insert(music);
		}
	}

	/**
	 * Deletes a music
	 *
	 * @param music the music to be deleted
	 */
	public void deletePopMusic(PopMusic music){
		music = music_list.get(music);
		music_list.delete(music);
		for(int i = 0; i < yearArr.size(); i++){
			if(yearArr.get(i) == music.getYear()){
				yearArr.remove(i);
				break;
			}
		}
		for(String word : music.getLyrics().split(" ")) {
			word = word.trim();
			WordID words = new WordID(word);
			if(!(music_id.get(word_id.get(words).getID()).search(music) == null))
				music_id.get(word_id.get(words).getID()).remove(music);
		}
	}

	/**
	 * Updates information of a music
	 *
	 * @param music the music to be updated
	 * @param year the year to be updated
	 * @param lyrics the lyrics to be updated
	 */
	public void updatePopMusic(PopMusic music, String year, String lyrics){
		PopMusic temp = music_list.get(music);
		
		if(year != ""){
			for(int i = 0; i < yearArr.size(); i++){
				if(yearArr.get(i) == temp.getYear()){
					yearArr.remove(i);
					break;
				}
			}
			temp.setYear(year);
			yearArr.add(year);
			for(String word : temp.getLyrics().split(" ")) {
				word = word.trim();
				WordID words = new WordID(word);
				music_id.get(word_id.get(words).getID()).search(temp).setYear(year);
			}
		}
		if(lyrics != ""){
			PopMusic temp2 = new PopMusic("", "", "", lyrics);
			removeUnwantedWords(temp2);
			for(String word : temp.getLyrics().split(" ")) {
				word = word.trim();
				WordID words = new WordID(word);
				if(!(music_id.get(word_id.get(words).getID()).search(temp) == null))
					music_id.get(word_id.get(words).getID()).remove(temp);
			}
			temp.setLyrics(temp2.getLyrics());
			int id = music_id.size();
			for(String word : temp2.getLyrics().split(" ")) {
				word = word.trim();
				WordID words = new WordID(word);
				if(!(word_id.contains(words))) {
					words.setID(id);
					word_id.add(words);
					music_id.add(new BST<>());
					id++;
				}
				music_id.get(word_id.get(words).getID()).insert(music);
			}
		}
	}
	
	//removes Remove any words that would not be considered useful, 
	//such as as any articles (a, the), prepositions (of, to), conjunctions (and, but, nor) 
	//and common adjectives such as some, any, very.
	public void removeUnwantedWords(PopMusic music) {
		music.setLyrics(music.getLyrics().replaceAll("A ", ""));
		music.setLyrics(music.getLyrics().replaceAll("It ", ""));
		music.setLyrics(music.getLyrics().replaceAll("It's '", ""));
		music.setLyrics(music.getLyrics().replaceAll("At ", ""));
		music.setLyrics(music.getLyrics().replaceAll("On ", ""));
		music.setLyrics(music.getLyrics().replaceAll("To ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Of ", ""));
		music.setLyrics(music.getLyrics().replaceAll("The ", ""));
		music.setLyrics(music.getLyrics().replaceAll("And ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Uh ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Oh ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Ooh ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Yeah ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Hmm ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Mm ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Mmm ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Then ", ""));
		music.setLyrics(music.getLyrics().replaceAll("Just ", ""));
		music.setLyrics(music.getLyrics().replaceAll("For ", ""));
		music.setLyrics(music.getLyrics().replaceAll(" a ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" it ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" it's ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" at ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" on ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" to ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" of ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" the ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" and ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" uh ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" oh ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" ooh ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" yeah ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" hmm ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" mm ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" mmm ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" then ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" just ", " "));
		music.setLyrics(music.getLyrics().replaceAll(" for ", " "));
	}
	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Creates a String of the list of musics
	 */
	@Override public String toString() {
		return  music_list.toString();
	}
}
