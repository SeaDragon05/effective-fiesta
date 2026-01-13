#include "Database.hpp"
#include <stdexcept>
#include <string>
#include <fstream>
#include <iostream>
#include <random>
#include<algorithm>
#include<cctype>
#include "WordObject.hpp"

extern bool general_settings_console_mode;
extern int LM_generate_sentence_word_limit;

int amountOfWords = 0;

Database::Database(const std::string& url) {
        Database::compileFromFile(url);
}

int Database::getWP() const {
        return amountOfWords;
}

char getFlags(const std::string& word) {
        char flags = 0;
        //128xPeriod 64xComma 32xQuestion 16xExclamation 8xNumbers 4xCaps 2xQuote 1xhifen
        for (char c : word) {
                if (c ==  '.' && (flags & 128) == 0) flags |= 128;
                if (c ==  ',' && (flags &  64) == 0) flags |=  64;
                if (c ==  '?' && (flags &  32) == 0) flags |=  32;
                if (c ==  '!' && (flags &  16) == 0) flags |=  16;
                if (isdigit(c) && (flags & 8) == 0)  flags |=   8;
                if (isupper(c) && (flags & 4) ==  0) flags |=   4;
                if (c == '"' && (flags & 2) == 0) flags |= 2;
                if (c == '\'' && (flags & 1) == 0) flags |= 1;
        }
        return flags;
}

std::shared_ptr<WordObject> Database::getWord(const std::string& word) const {
        auto result = dictionary.find(word);
        if (result != dictionary.end()) {
                return result->second;
        }
        throw std::runtime_error("Word was not found!");
}

std::unordered_map<std::string, std::shared_ptr<WordObject>>& Database::getDictionary() const {
        return dictionary;
}  

std::vector<WordData*> Database::getWordNextList(const std::string& word) const {
        auto it = dictionary.find(word);
        if (it != dictionary.end()) {
                return it->second->getNextWordList();
        }
        throw std::runtime_error("lmao that failed");
}

void Database::compileFromFile(const std::string& url) {
        if (url.empty()) throw std::invalid_argument("ERROR: Invalid url!");
        printf("Compiling from: %s\n", url.c_str());
        if (!general_settings_console_mode) {}//future window stuff
        Database::compileData(url);
}

std::string cleanString(const std::string& input) {
        std::string result;
        for (char c : input) {
                if (std::isalpha(c)) {
                        result += std::tolower(c);
                }
        }
        return result;
}

void Database::add(const std::shared_ptr<WordObject> word) {
        if (!word) {
                throw std::invalid_argument("WARN: Attempted to add an invalid word to the dictionary!");
        }
        std::string key = word->getWord();
        auto it = dictionary.find(key);
        if (it != dictionary.end()) {
                // If the key exists, add the new word to the existing object
                it->second->combine(*word); // Use the existing WordObject's add method
        } else {
                dictionary[key] = word;
        }

        if (word->isStart()) {
                manifest.push_back(word); // Add to the manifest if the word is a starting word
        }
}


void Database::compileData(const std::string& URL) {
        if (URL.empty()) throw std::runtime_error("URL is empty!");
        std::ifstream file(URL);
        if (!file.is_open()) {
                printf("File not found: Check the file path. -> %s\n", URL.c_str());
                return;
        }
        printf("Compiling from: %s\n", URL.c_str());
        std::string word, nextWord;
        if (file >> word >> nextWord) {
                auto ptr = std::make_shared<WordObject>(cleanString(word), nextWord, getFlags(word));
                add(ptr);
                word = nextWord;
                amountOfWords++;
                bool is = false;
                while (file >> nextWord) {
                        ptr = std::make_shared<WordObject>(word, nextWord, getFlags(word));
                        ptr->is_start = is;
                        add(ptr);
                        is = ptr->checkWord(word);
                        word = nextWord;
                        amountOfWords++;
                }
        }
        file.close();
}

std::shared_ptr<WordObject>& Database::getStarterWord() const {
        if (manifest.empty()) throw std::runtime_error("Manifest was not used!");
        std::random_device rd;
        std::mt19937 rng(rd());
        std::uniform_int_distribution<int> uni(0, static_cast<int>(manifest.size() - 1)); // Define the range [1, 100]
        return this->manifest[uni(rng)];
}

bool checkWord(const std::string& word) {
        return (word.find('.') != std::string::npos ||
                word.find('!') != std::string::npos ||
                word.find('?') != std::string::npos);
}

std::vector<std::shared_ptr<WordObject>> Database::generateSentence(const float& correctness) const {
        std::vector<std::shared_ptr<WordObject>> sentence;
        sentence.push_back(getStarterWord());
        for (int i =0; i < LM_generate_sentence_word_limit; i++) {
                std::shared_ptr<WordObject> lastWord = sentence.back();
                std::string lastWordKey = lastWord->getRandomNext();
                auto it = dictionary.find(lastWordKey);
                if (it != dictionary.end()) {
                        std::shared_ptr<WordObject> nextWord = it->second;
                        sentence.push_back(nextWord);
                        if (checkWord(nextWord->getWord())) break;
                } else break;
        }
        return sentence;
}

