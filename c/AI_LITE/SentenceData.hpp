#ifndef SENTENCEDATA_H
#define SENTENCEDATA_H
#include<string>
#include<vector>
#include<memory>
#include "WordObject.hpp"
#include "Database.hpp"


// Rule because of what happened when I trained this bloody AI on copypasta. Don't do this please
extern std::string bannedWords[];



class Database;

class SentenceData {
public:
    static std::vector<std::string> extendedBannedWordList;
    SentenceData(const std::vector<std::shared_ptr<WordObject>>& words);
    SentenceData(const std::shared_ptr<WordObject>& word);
    SentenceData() {}
    int getWordUseCount(const std::shared_ptr<WordObject>& word) const;
    int getLength() const;
    std::string toString() const;
    std::vector<std::shared_ptr<WordObject>> getWordList() const;
    std::shared_ptr<WordObject> getWordAtIndex(int index) const;
    void clear();
    int size() const;
    bool contains(const std::vector<std::shared_ptr<WordObject>>& words) const;
    bool contains(const std::shared_ptr<WordObject> word) const;
    static std::string filterStringSentence(std::string& input);
    void fillExtendedBannedWordList();
    std::string cleanStringSentence(const std::string& sentence) const;
    std::string cleanWord(const std::string& word) const;
    void printBannedWordInSentence(const std::string& input);
    void addWord(const std::shared_ptr<WordObject>& word);
    bool compare(const SentenceData& s1, const SentenceData& s2) const;

private:
    mutable std::vector<std::shared_ptr<WordObject>> words;
    mutable std::vector<char> wordFlags;
};

#endif
