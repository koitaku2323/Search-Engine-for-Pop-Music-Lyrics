# Search Engine for Pop Music Lyrics

## Overview

This project is a Search Engine designed to index and retrieve information about pop music lyrics. It includes functionality to add, search, delete, and update pop music entries. The search engine uses an inverted index and hash tables to efficiently organize and retrieve data.

## Table of Contents

- [Search Engine for Pop Music Lyrics](#search-engine-for-pop-music-lyrics)
  - [Overview](#overview)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [Usage](#usage)
    - [Adding a Pop Music Entry](#adding-a-pop-music-entry)
    - [Searching by Word](#searching-by-word)
    - [Searching by Title and Artist](#searching-by-title-and-artist)
    - [Deleting a Pop Music Entry](#deleting-a-pop-music-entry)
    - [Updating Pop Music Information](#updating-pop-music-information)
  - [Customization](#customization)
    - [Stop Words](#stop-words)
    - [File Paths](#file-paths)
  - [Contributing](#contributing)

## Features

- **Inverted Index:** Efficiently indexes pop music entries for quick search and retrieval.
- **Hash Tables:** Utilizes hash tables for storing word identifiers, pop music entries, and managing collisions.
- **CRUD Operations:** Supports creating, reading, updating, and deleting pop music entries.
- **Search Functionality:** Enables searching for pop music entries by word, title, and artist.
- **File Persistence:** Saves and loads pop music data to and from files for data persistence.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- Integrated Development Environment (IDE) like IntelliJ or Eclipse

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/koitaku2323/Search-Engine-for-Pop-Music-Lyrics.git
   ```

2. Open the project in your preferred IDE.

3. Build the project.

## Usage

### Adding a Pop Music Entry

To add a new pop music entry, use the `addPopMusic` method in the `SearchEngine` class.

### Searching by Word

Use the `searchByWord` method to search for pop music entries containing a specific word.

### Searching by Title and Artist

For searching by title and artist, utilize the `searSongByPrimaryKey` method in the `SearchEngine` class.

### Deleting a Pop Music Entry

To delete a pop music entry, use the `deletePopMusic` method in the `SearchEngine` class.

### Updating Pop Music Information

Modify pop music information using the `updateMusic` method in the `SearchEngine` class.

## Customization

### Stop Words

You can customize the list of stop words in the `SearchEngine` class by modifying the `STOP_WORDS` array.

### File Paths

Adjust file paths and names by modifying constants like `FILE_FOLDER` and `FILE_NAME` in the `SearchEngine` class.

## Contributing

Feel free to contribute to the project by opening issues or submitting pull requests. Your feedback and contributions are highly appreciated.


---
