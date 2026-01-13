#include "SentenceData.hpp"
#include "Database.hpp"
#include<string>
#include<memory>
#include<vector>
#include<algorithm>
#include <iostream>
#include <sstream>
#include <cctype>

extern std::string bannedWords[];

SentenceData::SentenceData(const std::vector<std::shared_ptr<WordObject>>& words)
    : words(words) {}

SentenceData::SentenceData(const std::shared_ptr<WordObject>& word) {
    words.push_back(word);
}

int SentenceData::getWordUseCount(const std::shared_ptr<WordObject>& word) const {
    int result = 0;
    for (auto& w: words) {
      if (word->getWord() == w->getWord()) {
        result += 1;
      }
    }
    return result;
}

int SentenceData::getLength() const {
    return static_cast<int>(words.size());
}

std::string SentenceData::toString() const {
    std::string result = "";
    for (auto& w : words) {
      result = result + " " + w->getWord();
    }
    return result;
}

void SentenceData::addWord(const std::shared_ptr<WordObject>& word) {
    words.push_back(word);
}
std::vector<std::shared_ptr<WordObject>> SentenceData::getWordList() const {
    return words;
}

std::shared_ptr<WordObject> SentenceData::getWordAtIndex(int index) const {
    if (index < 0 || index >= static_cast<int>(words.size())) {
        throw std::runtime_error("Access requested is outside of range"); // Or handle the out-of-range index appropriately
    }
    return words[index];
}

void SentenceData::clear() {
    words.clear();
}

int SentenceData::size() const {
    return static_cast<int>(words.size());
}

bool SentenceData::contains(const std::vector<std::shared_ptr<WordObject>>& wordsToCheck) const {
    for (const auto& word : wordsToCheck) {
        if (std::find(words.begin(), words.end(), word) == words.end()) {
            return false;
        }
    }
    return true;
}

bool SentenceData::contains(const std::shared_ptr<WordObject> wordToCheck) const {
    if (std::find(words.begin(), words.end(), wordToCheck) == words.end()) {
        return false;
    }
    return true;
}

void SentenceData::fillExtendedBannedWordList() {
    printf("Filling banned word list possibilities\n");
    
}
//compares the first sentence to the second one
//that order is important btw
bool SentenceData::compare(const SentenceData& s1, const SentenceData& s2) const {
    if (s1.size() == 0 && s2.size() == 0) return true;
    if (s1.size() == 0 || s2.size() == 0) return false;
    if (s1.getLength() != s2.getLength()) return false;
    for (int i = 0; i < s1.getLength(); i++) {
        try {
            if (s1.getWordAtIndex(i)->getWord() != s2.getWordAtIndex(i)->getWord()) return false;
        } catch (const std::runtime_error& e) {
            return false;
        }
    }
    return true;
}

