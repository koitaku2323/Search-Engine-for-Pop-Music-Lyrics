import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuInterface {
	public static void main(String[] args) {
		final int USER = 100;
		final int MUSIC = 15;
		HashTable<PopMusic> music1 = new HashTable<>(MUSIC);
		HashTable<PopMusic> musicTitle = new HashTable<>(MUSIC);
		ArrayList<BST<PopMusic>> musics_list = new ArrayList<>();
	    HashTable<WordID> word_id = new HashTable<>(USER);
		PopMusic music = null;
		PopMusic music3 = null;
		WordID music_id = null;
		File inFile = new File("song.txt");
		Scanner input = null;
		PrintWriter output = null;
		
		// Variable Declarations
		String title, enWord, enWord2, outFile, addFile;
		String lyrics = "", artist = "", temp = "", year = "";
		char uncapChoice, choice;
		int id = 0, count = 0, number = 0, decision = 0;
		String [] yearArr = new String[USER]; 
		@SuppressWarnings("unused")
		boolean invalid = true;

		try{
			input = new Scanner(inFile);
			while(input.hasNext()){
				title = input.nextLine().trim();
				artist = input.nextLine().trim();
				year = input.nextLine().trim();
				yearArr[count] = year;
				count++;
				while(input.hasNext()) {
					temp = input.nextLine().trim() + " ";
					if(temp.equals(" "))
						break;
					temp = temp.replaceAll("[^a-zA-Z0-9 '_-]", ""); // replaces all starting and trailing non alphanumeric characters from the string
					lyrics += temp + "\n";
				}
				music = new PopMusic(title, artist, year, lyrics);
				music3 = new PopMusic(title);
				music1.add(music);
				musicTitle.add(music3);
				for(String word : lyrics.split(" ")) {
					word = word.trim();
					music_id = new WordID(word);
					if(!(word_id.contains(music_id))) {
						music_id.setID(id);
						word_id.add(music_id);
						musics_list.add(new BST<>());
						id++;
					}
					musics_list.get(word_id.get(music_id).getID()).insert(music);
				}
				lyrics = "";
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		input = new Scanner(System.in);
		SearchEngine temp1 = new SearchEngine();
		SearchEngine temp2 = new SearchEngine();
		do {
			System.out.println("\nPlease select from the following options:\n");
			System.out.println("A. Upload a new music");
			System.out.println("B. Delete a music");
			System.out.println("C. Search for a music");
			System.out.println("D. Modify or update a music");
			System.out.println("E. Display music statistics");
			System.out.println("X. Quit");
			
			System.out.print("\nEnter your choice: ");
			uncapChoice = input.next().charAt(0); 
			choice = Character.toUpperCase(uncapChoice);

			if(choice == 'A') {
				input.nextLine();
				System.out.print("Please enter the file name: ");
				addFile = input.nextLine().trim();
				
				while(true){
					try{
						File newFile = new File(addFile);
						Scanner input1 = new Scanner(newFile);
						while(input1.hasNext()){
							title = input1.nextLine().trim();
							artist = input1.nextLine().trim();
							year = input1.nextLine().trim();
							yearArr[count] = year;
							count++;
							while(input1.hasNext()) {
								temp = input1.nextLine().trim() + " ";
								if(temp.equals(" "))
									break;
								temp = temp.replaceAll("[^a-zA-Z0-9 '_-]", ""); //  replaces all starting and trailing non alphanumeric characters from the string
								lyrics += temp + "\n";
							}
							music = new PopMusic(title, artist, year, lyrics);
							music3 = new PopMusic(title);
							if(!music1.contains(music))
								temp1.addPopMusic(music);
							if(!musicTitle.contains(music3))
								temp2.addPopMusic(music3);
							lyrics = "";
						}
						input1.close();
						break;
					} catch(FileNotFoundException e){
						System.out.print("File not found! Please enter a valid file name: ");
						addFile = input.nextLine().trim();
					}
				}
			}else if(choice == 'B') {
				input.nextLine();
				System.out.println(temp1.printDetails());
				System.out.print("Please enter the music title to delete: ");
				title = input.nextLine().trim();
				while(temp2.searchByTitle(title) == null) {
					System.out.print("Please enter the valid music title to delete: ");
					title = input.nextLine().trim();
				}
				music = new PopMusic(title, artist);
				temp1.deletePopMusic(music);
				music3 = new PopMusic(title);
				temp2.deletePopMusic(music3);
			}else if(choice == 'C') {
				System.out.println("\nPlease select from the following options:");
				System.out.println("1.Search for specific music (using primary key)");
				System.out.println("2.Search for specific music (using search engine)");		
				System.out.print("\n"+"Enter your choice (1-2): ");
				temp = input.next();
				input.nextLine();
				while(true){
					try{
						number  = Integer.parseInt(temp);
						if(number != 1 || number != 2)
							break;
					}catch(NumberFormatException e){
						System.out.print("Please enter a valid choice: ");
						temp = input.next();
						input.nextLine();
					}
				}
				if(number == 1) {
					System.out.print("Enter the title of the music (using primary key): ");
					enWord = input.nextLine().trim();
					System.out.print("Enter the artist of the music (using primary key): ");
					enWord2 = input.nextLine().trim();
						if(!(temp1.searchByKey(enWord, enWord2) == null))
							System.out.println(temp1.searchByKey(enWord, enWord2));	
						else
							System.out.println("You don't have a record by that title and artist.");
				}else if(number == 2) {
					System.out.print("Enter the word (search engine): ");
					enWord = input.nextLine().trim();
					music_id = new WordID(enWord);
					if(temp1.checkByWord(enWord)) {
						System.out.println("The following musics contains the word \"" + enWord + "\":");
						temp1.searchByWord(enWord);
						System.out.print("To view more information about any of these musics, enter the title name: ");
						title = input.nextLine().trim();
						while(true){
							if(temp2.checkByTitle(title, enWord)){
								System.out.println(temp2.searchByTitle(title));
								break;
							}else{
								System.out.print("Please input a valid title name from the list above: ");
								title = input.nextLine().trim();
							}
						}
					}else {
						System.out.println("There are no lyrics with that word.");
					}
				}else {
					System.out.println("\nInvalid menu option. Please enter 1 or 2.");
				}
			}else if(choice == 'D') {
				System.out.println(temp1.printDetails());
				input.nextLine();
				
				System.out.print("Please enter the music title to modify or update: ");
				title = input.nextLine().trim();
				
				while(true) {
					if(temp2.searchByTitle(title) != null) {
						break;
					}else {
						System.out.print("Please enter the valid music title to modify or update: ");
						title = input.nextLine().trim();
					}
				}

				System.out.println("Choose one of the following: ");
				System.out.println("1. Year");
				System.out.println("2. Lyrics");
				System.out.println("3. Both");
				System.out.print("Choice to modify or update: ");
				temp = input.next();
				while(true){
					try{
						decision  = Integer.parseInt(temp);
						if(number != 1 || number != 2)
							break;
					}catch(NumberFormatException e){
						System.out.print("Please enter a valid choice: ");
						temp = input.next();
						input.nextLine();
					}
				}
				
				if (decision == 1) {
					System.out.print("Please enter a new year to modify or update: ");
					temp = input.next();
					input.nextLine();
					while(true){
					try{
						year  = temp;
						break;
					}catch(NumberFormatException e){
						System.out.print("Please enter a valid year: ");
						temp = input.next();
						input.nextLine();
					}
				}
					temp1.updatePopMusic(temp1.searchByTitle(title), year, "");
				}else if (decision == 2) {
					input.nextLine();
					System.out.println("Please enter new lyrics to modify or update following with the ENTER key pressed twice: ");
					while(input.hasNextLine()) {
						temp = input.nextLine().trim() + " ";
						if(temp.equals(" "))
							break;
						temp = temp.replaceAll("[^a-zA-Z0-9 '_-]", ""); //  replaces all starting and trailing non alphanumeric characters from the string
						lyrics += temp + "\n";
					}
					temp1.updatePopMusic(temp2.searchByTitle(title), "", lyrics);
					temp2.updatePopMusic(temp2.searchByTitle(title), "", lyrics);
				}else if (decision == 3) {
					System.out.print("Please enter a new year to modify or update: ");
					temp = input.next();
					input.nextLine();
					while(true){
					try{
						year  = temp;
						break;
					}catch(NumberFormatException e){
						System.out.print("Please enter a valid year: ");
						temp = input.next();
						input.nextLine();
					}

				}
					System.out.print("Please enter new lyrics to modify or update following with the ENTER key pressed twice: ");
					while(input.hasNextLine()) {
						temp = input.nextLine().trim() + " ";
						if(temp.equals(" "))
							break;
						temp = temp.replaceAll("[^a-zA-Z0-9 '_-]", ""); // replaces all starting and trailing non alphanumeric characters from the string
						lyrics += temp + "\n";
					}
					temp1.updatePopMusic(temp2.searchByTitle(title), year, lyrics);
					temp2.updatePopMusic(temp2.searchByTitle(title), year, lyrics);
				}else {
					System.out.println("Invalid Choice!");
				}

			}else if(choice == 'E') {
				// 1. Total number of musics overall
				System.out.println("Number of musics in record: " + music1.getNumElements());
				// 2. Total number of musics released in year 2017
				System.out.println("Number of musics released in year 2017: " + temp1.statisticsByYear("2017"));
				// 3. Total number of musics released in year 2018
				System.out.println("Number of musics released in year 2018: " + temp1.statisticsByYear("2018"));
			}else if(choice != 'X') {
				System.out.println("\nInvalid menu option. Please enter A-D or X to exit.");
			}
		}while(choice != 'X');
		System.out.print("Please enter the file name to output records: ");
		outFile = input.next();
		File outputFile = new File(outFile);
		try{
			output = new PrintWriter(outputFile);
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		output.println(temp1);
		output.close();
		System.out.println("\nGoodbye!");
		input.close();
	}
}
