import java.util.*;

public class MenuInterface {


	public static void main(String[] args) throws InterruptedException {
		MenuInterface menuInterface = new MenuInterface();

		Scanner sc = new Scanner(System.in);
		int choice;

		do {
			System.out.println("\n\n*********************Welcome to Pop Music Interface!*********************\n");

			System.out.println("Here are some ways you can interact with it:\n");
			menuInterface.displayMenu(MENU);
			System.out.println("\nEnter 1-7 for the operation you want to take place: ");
			choice = Integer.parseInt(sc.nextLine());
			switch (choice) { // insert
			case 1:

				menuInterface.insertMenu(sc);
				Thread.sleep(3000);

				break;

			case 2: // delete

				menuInterface.deleteMenu(sc);
				Thread.sleep(3000);

				break;

			case 3: // Search by primary key

				menuInterface.searchByKeyMenu(sc);
				Thread.sleep(3000);

				break;

			case 4: // Search by keywords

				menuInterface.searchByWordMenu(sc);
				Thread.sleep(3000);

				break;
			case 5: // Modify a song

				menuInterface.modifyMenu(sc);
				Thread.sleep(3000);

				break;
			case 6: // Display statistics

				menuInterface.statisticsMenu();
				Thread.sleep(3000);

				break;
			case 7: // save and exist

				menuInterface.saveMenu(sc);
				Thread.sleep(3000);

				break;
			default:
				System.out.println("You must enter a number from 1 to 7. Enter a number: ");
				choice = Integer.parseInt(sc.nextLine());
			}

		} while (choice >= 1 && choice <= 6);
		sc.close();
	}

	public static final String[] MENU = { "1. Insert a song", "2. Delete a song", "3. Search a song by primary key",
			"4. Seach a song by keywords", "5. Modify a song", "6. Display statistics of a song", "7. Save and Quit" };

	private SearchEngine searchEngine;

	public MenuInterface() {
		this.searchEngine = new SearchEngine();
	}

	public void displayMenu(String[] menu) {
		for (int i = 0; i < menu.length; i++) {
			System.out.println("\t" + menu[i]);
		}
	}

	public void insertMenu(Scanner sc) {
		System.out.println("\nYou have chosen to insert a new song! Proceed to next steps.");

		System.out.print("Enter the song's title: ");
		String songTitle = sc.nextLine();

		System.out.printf("Enter the song's artist: ");
		String songArtist = sc.nextLine();

		System.out.println("Enter the song's release year: ");
		int songYear = sc.nextInt();

		System.out.println("Enter the song's lyrics: ");
		sc.nextLine();
		StringBuilder lyrics = new StringBuilder();
		do {

			String string = sc.nextLine();

			if (string.equals("")) {

				break;

			}

			lyrics.append(string).append("\n");

		} while (true);

		String songLyrics = lyrics.toString();

		PopMusic popSong = new PopMusic(songTitle, songArtist, songYear, songLyrics);

		if (searchEngine.addPopMusic(popSong)) {
			System.out.println("\n--------------------------------------------------------------");
			System.out.println("\n\tSuccessfully added.\n");
			System.out.println("--------------------------------------------------------------\n");
		}

	}

	public void deleteMenu(Scanner sc) {
		System.out.println("\nYou have chosen to delete a song! Proceed to next steps.");

		System.out.println("Here are the list of the songs you can delete: ");
		searchEngine.printIntro();

		System.out.println("Enter the song's title ");
		String title = sc.nextLine();

		System.out.println("Enter the song's artist: ");
		String artist = sc.nextLine();

		if (searchEngine.deletePopMusic(title, artist)) {
			System.out.println("\n--------------------------------------------------------------");
			System.out.println("\n\tSuccessfully deleted.\n");
			System.out.println("--------------------------------------------------------------\n");

		} else {
			System.err.println("\n--------------------------------------------------------------");
			System.err.println("\n\tCan't delete the song because it's not in the list!\n");
			System.err.println("--------------------------------------------------------------\n");
		}

	}

	public void searchByKeyMenu(Scanner sc) {
		System.out.println("You have chosen to search for a song! Proceed to next steps.");


		System.out.println("\nEnter the song's title: ");
		String title1 = sc.nextLine();

		System.out.println("Enter the song's artist: ");
		String artist1 = sc.nextLine();

		PopMusic p = searchEngine.searSongByPrimaryKey(title1, artist1);

		if (p != null) {
			System.out.println("\n--------------------------------------------------------------");
			System.out.print("title: " + p.getTitle() + "\nartist: " + p.getArtist() + "\nyear: " + p.getYear() + "\nlyric: " + p.getLyric() + "\n");
			System.out.println("--------------------------------------------------------------\n");
		} else {
			System.err.println("\n--------------------------------------------------------------");
			System.err.println("\n\tCan't search for the song because it's not in the list!\n");
			System.err.println("--------------------------------------------------------------\n");

		}

	}

	public void searchByWordMenu(Scanner sc) {
		System.out.println("\nYou have chosen to search for a song by its lyrics! Proceed to next steps.");

		System.out.println("Enter the lyrics of the song you're searching for: ");
		String lyrics1 = sc.nextLine();

		BST<PopMusic> p = searchEngine.searchByWord(lyrics1);
		if (p != null) {
			System.out.println("\n--------------------------------------------------------------");
			System.out.println(p.inOrderString());
			System.out.println("--------------------------------------------------------------\n");
		} else {
			System.err.println("\n--------------------------------------------------------------");
			System.err.println("\n\tCan't search for the song because it's not in the list!\n");
			System.err.println("--------------------------------------------------------------\n");
		}

	}

	public void modifyMenu(Scanner sc) {
		System.out.println("You have chosen to modify a song! Proceed to next steps.");

		System.out.println("Here are the list of the songs: ");
		searchEngine.printIntro();

		System.out.println("Enter the song's title you want to modify: ");
		String title = sc.nextLine();
		System.out.println("Enter the song's artist you want to modify: ");
		String artist = sc.nextLine();

		PopMusic p = searchEngine.searSongByPrimaryKey(title, artist);

		if (p == null) {
			System.err.println("\n--------------------------------------------------------------");
			System.err.println("\n\tCan't modify the song because it's not in the list!\n");
			System.err.println("--------------------------------------------------------------\n");
		} else {
			System.out.println("--------------------------------------------------------------\n");
			System.out.println("\nEnter the new song's title: ");
			String newTitle = sc.nextLine();
			System.out.println("Enter the new song's artist: ");
			String newArtist = sc.nextLine();
			
			searchEngine.updateMusic(p, newTitle, newArtist);

			System.out.println("\n--------------------------------------------------------------");
			System.out.println("\n\tSuccessfully modified.\n");
			System.out.println("--------------------------------------------------------------\n");
		}

	}

	public void statisticsMenu() {
		System.out.println("\nPop music Statistics"); // title

		// Statistics 1
		System.out.println("Most of our data base songs are from 2004 to 2019");
		System.out.println();

		// Statistics 2
		System.out.println("Pop music is the most popular music genre in the world, \n"
				+ "with around 64% of the 19,000 respondents from 18 countries. (STATISTA)");
		System.out.println(); // skip a line

		// Statistics 3
		System.out.println(
				"Sales of digital assets through blockchain grew to a size of tens of million of U.S. dollars in early 2021.\n"
						+ "The music world sales and overall respect and valus for NFTs, \n"
						+ "this is something that will likely be the norm in the future. (STATISTA)\n\n");
	}

	public void saveMenu(Scanner sc) {
		System.out.println("Please enter your username: ");
		String userName = sc.nextLine();
		searchEngine.saveMusicTo(userName);
		System.out.println("\n\tSaved successfully\n");
	}

}