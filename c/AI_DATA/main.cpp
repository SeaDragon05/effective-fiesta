#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include<algorithm>
#include <sstream>
#include <cctype>
#include <unistd.h>
#include <sys/ioctl.h>
#include <locale>
#include <codecvt>
#include "GlobalVars.hpp"
#include "Sound.cpp"
#include "Database.hpp"
#include "SentenceData.hpp"
#include "GraphicalUserInterface.c"

std::vector<std::string> split(const std::string& str, char delimiter) {
    std::vector<std::string> tokens;
    std::stringstream ss(str);
    std::string item;
    while (std::getline(ss, item, delimiter)) {
        tokens.push_back(item);
    }
    return tokens;
}

std::string toLowerCase(const std::string& str) {
    std::string lowerStr = str; // Create a copy of the string to modify
    std::transform(lowerStr.begin(), lowerStr.end(), lowerStr.begin(),
                   [](unsigned char c) { return std::tolower(c); });
    return lowerStr;
}

std::string cleanWord(const std::string& word) {
    char misc[] = { ',', '.', '!', '?' };
    std::vector<char> letters(word.begin(), word.end());
    letters.push_back('\0');
    std::string cleanedWord = "";
    for (int i = 0; i < letters.size(); i++) {
        for (int j = 0; j < 4; j++) {
            if (letters[i] == misc[j]) {
	        break;
	    }
            // this only executes when nothing trips the misc list
            if (j == 3) {
                // add the character to the rebuilt word
                cleanedWord += letters[i];
            }
        } 
    }
    return cleanedWord; // Placeholder
}

void printBannedWordInSentence(const std::string& input) {
    std::string output = "";
    std::vector<std::string> words = split(input, ' ');
    for (auto& word : words) {
        std::string thatWord = cleanWord(word);
        bool is = true;
        for (auto& bannedWord : bannedWords) {
            if (toLowerCase(thatWord) == toLowerCase(bannedWord)) {
                output += "{CENSORED} ";
                is = false;
                break;
            }
        }
        if (is) {
            output += word + " ";
        }
    }
    printf("Filtered sentence: %s\n", output.c_str());
}

std::string filterStringSentence(std::string& input) {
    std::vector<std::string> words = split(input, ' ');
    for (auto& word : words) {
        std::string thatWord = cleanWord(word);
        for (auto& bannedWord : bannedWords) {
            if (toLowerCase(thatWord) == toLowerCase(bannedWord)) {
                printBannedWordInSentence(input);
                playWav("res/bwompReverb.wav");
		return "Filtered.";
            }
        }
    }
    return input;
}



void checkPackageInstalled(const char *packageName, int isReq, const char *errmsg) {
  	char command[256];
  	snprintf(command, sizeof(command), "dpkg-query -W -f='${Status}' %s", packageName);
  
  	FILE *pipe = popen(command, "r");
  	if (pipe == NULL) {
    		fprintf(stderr, "Error opening pipe for command: %s\n", command);
    		return;
  	}
  
  	char line[256];
  	int packageFound = 0;
  
  	while(fgets(line, sizeof(line), pipe) != NULL) {
   	 	if (strstr(line, "install ok installed") != NULL) {
      			packageFound = 1;
      			break;
    		}
  	}
  	
  	pclose(pipe);
  
  	if (!packageFound) {
    		fprintf(stderr, "WARN: %s is not installed.\n", packageName);
    		if (isReq) {
      			fprintf(stderr, "ERROR: THE ABOVE PACKAGE IS REQUIRED FOR THIS PROGRAM.\n%s\n", errmsg);
    		}
  	}
}

int main(int argc, char **argv) {
	std::locale::global(std::locale(""));
    	std::cout << "\033[0m";
        system("clear");
        std::vector<std::string> infoText;
        infoText.push_back("Welcome to the Copypasta Generator!");
        infoText.push_back("The program is currently being ran in terminal mode and will run automatically!");
        infoText.push_back("All generated text will appear here, line by line.");
        infoText.push_back("There are a few preset txt files, but you can add your own!");
        infoText.push_back("I hope you enjoy my useless text generator!");
        printInterface(infoText, 0.7, 0.7, 0, 0);
  	//set up some stuff with the given arguments
  	int amountOfGen = 2;
  	if (argc <= 1) {
    		PrintUsage();
  	} else {
  		int i = 0;
    		while(i < argc) {
      			if (argv[i][0] == '-') {
        			if (strcmp(&argv[i][1], "amount")==0) {
        			        amountOfGen = atoi(argv[i+1]);
          				i++; continue;
        			}
        			if (strcmp(&argv[i][1], "soundOff")==0) {
          				general_settings_enable_music = false;
          				i++; continue;
        			}
        			if (strcmp(&argv[i][1], "noTTS")==0) {
          				general_settings_enable_TTS = false;
          				i++; continue;
        			}
        			if (strcmp(&argv[i][1], "usewiki")==0) {//dev command
          				LM_test_use_wikipedia = true;
          				i++; continue;
        			}
        			if (strcmp(&argv[i][1], "filter")==0) {
          				printf("WARNING: FILTER IS OFF!\n");
          				i++; continue;
        			}
        			printf("Unknown arg: %s\n", &argv[i][1]);
      			}
      			i++;
    		}
  	}
  	//check to see if linux packages are installed
  	if (general_settings_enable_TTS) {
    		checkPackageInstalled("festival", true, "Please see 'Festival' TTS Voices:\nhttp://speech.zone/exercises/the-festival-text-to-speech-system/getting-started/installing-festival/");
  	}
    	playWav("res/copypasta.wav");
    	resetColor();
    	system("clear");
    	printf("Testing the compile of data...\n");
    	Database db("res/beemoviescript.txt");
    	db.compileData("res/copypasta.txt");
    	
    	
    	int awp = db.getWP();
    	std::unordered_map<std::string, std::shared_ptr<WordObject>>& d = db.getDictionary();
    	int wc = d.size();
    	printf("Report:\nTotal amount of word processed: %i\n", awp);
    	printf("Total amount of unique words: %i\n", wc);
    	printf("Attempting to create a random sentence with 100 as a limit:\n");
    	int i = 0;
    	pos500:
    	infoText.clear();
    	system("clear");
    	std::string line = "";
    	std::vector<std::shared_ptr<WordObject>> sentence = db.generateSentence(0);
    	for (std::shared_ptr<WordObject>& word : sentence) {
      		line += word->getWord() + " ";
    	}
    	line = filterStringSentence(line);
    	infoText.push_back(line);
    	printInterface(infoText, 0.5, 0.5, i, amountOfGen);
    	std::string command = "echo \"" + line + "\" | festival --tts";
    	int result = system(command.c_str());
    	i++;
    	if (i < amountOfGen) goto pos500;
    	resetColor();
    	std::cout << " \n\n\n\n\n\n\n\n\n\n\n\n\n";
    	system("clear");
  	return 0;
}
