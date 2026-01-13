# AI LITE

A lightweight language model that doesn't need to be relentlessly trained to work!
- Other names: CopyPasta generator, Pasta bot, UselessTextGenerator
- This lightweight language model uses the Markov chain algorithm, and uses the unsupervised machine learning principle.

## Table of Contents

1. [Features](#features)
2. [Dependencies](#install_dependencies)
3. [Installing](#installing)
4. [Known issues](#known_issues)

## Features

- Small and light weight
- Able to learn any language as long as words are seperated apart (japanese tests didn't quite work out since spaces really don't exist)
- No GPU required

## Install Dependencies
	* SDL: `sudo apt-get install libsdl2.0-dev`
	* Festival: `sudo apt-get install festival`

## Prerequisites

This project relies on SDL/SDL2 components (sounds) and festival (text to speech)
This project is built using linux. So this will likely not work on windows based systems.

### Installing

1. **Clone the Repository**
	```bash
	git clone https://github.com/SeaDragon05/AI_LITE.git
	cd AI_LITE
	
2. **Compile the program**
	```bash
	g++ main.cpp WordObject.cpp Database.cpp Castle.cpp SentenceData.cpp -o AI_LITE -lSDL2

3. **Run the program**
	```bash
	./AI_LITE

### Known issues

There have been a few issues discovered while developing this project:

1. **Using multiple languages**
	- When using more than one language, the model will sometimes switch between each language without knowing. This mainly occurs with languages that share common words such as English, Spanish, Latin, and many more.

2. **Some languages do not work**
	- There is a limitation to which languages can be used. I’ve tested Japanese and Chinese, to which both did not work. This is because of how they are written compared to most other languages: The words/characters are not spaced apart. Not tested in the C++ version.

3. **Segmentation faults**
	- Rare, but the LM may get segmentation faults.

4. **Inconsistent sentence generation**
	- Sometimes the sentence generation quality wont be the best. This is mainly caused by low amounts of data. To ensure better sentence generation, use at least 10 MB of text data.


