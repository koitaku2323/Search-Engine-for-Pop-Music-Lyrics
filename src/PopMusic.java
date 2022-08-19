/**
 * PopMusic.java
 * @author Ryan Yee
 * CIS 22C, Final Project
 */
public class PopMusic implements Comparable<PopMusic>{
	private String title;
	private String artist;
    private String year;
    private String lyrics;

    /**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new PopMusic with default values
	 * 
	 * @postcondition title, artist, genre, and lyrics are assigned an empty string,
     *                 year is assigned to -1. 
	 */
    public PopMusic(){
        title = "";
        artist = "";
        year = "";
        lyrics = "";
    }
    
    /**** OVERLOADED CONSTRUCTOR - Extra ****/

	/**
	 * Instantiates a new PopMusic by copying the title of another PopMusic
	 * 
     * @param title
	 * @postcondition a new PopMusic object with only the title
	 */
    public PopMusic(String title){
        this.title = title;
        artist = "";
        year = "";
        lyrics = "";
        //this constructor is just for experiment, it doesn't actually work...
    }
    
    /**** OVERLOADED CONSTRUCTOR 1 ****/

	/**
	 * Instantiates a new PopMusic by copying the title and artist of another PopMusic
	 * 
     * @param title
     * @param artist
	 * @postcondition a new PopMusic object with only the title and artist
	 */
    public PopMusic(String title, String artist){
        this.title = title;
        this.artist = artist;
        year = "";
        lyrics = "";
    }
    
    /**** OVERLOADED CONSTRUCTOR 2 ****/

	/**
	 * Instantiates a new PopMusic by copying the title, 
     * artist, year, genre and lyrics of another PopMusic
	 * 
     * @param artist
     * @param year
     * @param genre
     * @param lyrics
	 * @postcondition a new PopMusic object, which is an identical 
     *                but separate copy of the PopMusic original
	 */
    public PopMusic(String title, String artist, String year, String lyrics) {
    	this.title = title;
    	this.artist = artist;
    	this.year = year;
    	this.lyrics = lyrics;
    }
    
    

    /**** ACCESSORS ****/

    /**
	 * Accesses the music's title
	 * 
	 * @return the music's title
	 */
    public String getTitle(){
        return title;
    }
    
    /**
	 * Accesses the music's artist
	 * 
	 * @return the music's artist
	 */
    public String getArtist(){
        return artist;
    }
    
    /**
	 * Accesses the music's release year
	 * 
	 * @return the music's release year
	 */
    public String getYear(){
        return year;
    }
    
    /**
	 * Accesses the music's lyrics
	 * 
	 * @return the music's lyrics
	 */
    public String getLyrics(){
        return lyrics;
    }
    
    /**** MUTATORS ****/

    /**
	 * Updates the music's title
	 * 
	 * @param title a new title
	 */
    public void setTitle(String title) {
    	this.title = title;
    }
    
    /**
	 * Updates the music's artist
	 * 
	 * @param artist a new artist
	 */
    public void setArtist(String artist) {
    	this.artist = artist;
    }
    
    /**
	 * Updates the music's release year
	 * 
	 * @param year a new year
	 */
    public void setYear(String year) {
    	this.year = year;
    }
    
    /**
	 * Updates the music's lyrics
	 * 
	 * @param lyrics new lyrics
	 */
    public void setLyrics(String lyrics) {
    	this.lyrics = lyrics;
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
	 * Returns a consistent hash code for each PopMusic by summing the Unicode
	 * values of title and artist in the key 
     * Key = title + artist
	 * 
	 * @return the hash code
	 */
    @Override public int hashCode(){
        String key = title + artist;
        int sum = 0;
        for(int i = 0; i < key.length(); i++){
            sum += key.charAt(i);
        }
        return sum;
    }

    /**
	 * Compares this PopMusic to another Object for equality
	 * 
	 * @param o another Object
	 * @return true if o is a PopMusic and has a matching title and artist to this PopMusic
	 */
    @Override public boolean equals(Object o) {
		if(this == o) {
			return true;
		}else if(!(o instanceof PopMusic)) {
			return false;
		}else {
			PopMusic L = (PopMusic) o;
			if(this.title.compareTo(L.title) != 0) {
				return false;
			}
		}
		return true;
	}

    /**
     * Prints out the details of the music
     */
    @Override public String toString() {
    	return "Title: " + title + "\n" + "Artist: " + artist + "\n" + "Year: " + year + "\n" + "Lyrics:\n" + lyrics + "\n";
    }

	@Override
	public int compareTo(PopMusic music2) {
		return this.getTitle().compareTo(music2.getTitle());
	}
    
}
