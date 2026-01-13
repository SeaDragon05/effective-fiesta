#include "WordObject.hpp"
#include <unordered_map> 
#include <algorithm>
#include <stdexcept>
#include <random>

WordObject::WordObject(const std::string& word, const std::string& nextWord, const char& flags)
  : word(word), useFlags(flags) {
  WordObject::addWord(nextWord);
}

void WordObject::addWord(const std::string& word) {
  auto it = nextWord.find(word);
  if (it == nextWord.end()) {
    nextWord[word] = std::make_unique<WordData>(word);
  } else {
    it->second->add();
  }
}

std::string WordObject::getWord() const {
  return word;
}

bool WordObject::isStart() {
  return is_start;
}

bool WordObject::checkWord(const std::string& word) const {
    return (word.find('.') != std::string::npos ||
      word.find('!') != std::string::npos ||
      word.find('?') != std::string::npos);
}

void WordObject::combine(const WordObject& word) {
    for (const auto& wd : word.getNextWordList()) {
      addWord(wd->getWord());
    }
}

std::vector<WordData*> WordObject::getValuesFromMap(const std::unordered_map<std::string, std::unique_ptr<WordData>>& map) const {
  std::vector<WordData*> values;
  for (const auto& pair : map) {
    values.push_back(pair.second.get());
  }
  return values;
}

std::vector<WordData*> WordObject::getNextWordList() const {
  return getValuesFromMap(nextWord);
}

std::string WordObject::getRandomNext() const {
  if (nextWord.empty()) throw std::runtime_error("No next words available!");
  std::random_device rd;
  std::mt19937 rng(rd());
  std::uniform_int_distribution<int> uni(0, static_cast<int>(nextWord.size() - 1)); // Define the range [1, 100]
  return getNextWordList()[uni(rng)]->getWord();
}
