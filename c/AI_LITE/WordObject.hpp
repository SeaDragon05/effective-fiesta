#ifndef WORDOBJECT_H
#define WORDOBJECT_H
#include <string>
#include <memory>
#include <vector>
#include <unordered_map>

enum class POS { NOUN, PRONOUN, VERB, ADJECTIVE, ADVERB, PREPOSITION, CONJUNCTION, INTERJECTION };

class WordData {
public:
        std::string word;
        int uses;
        WordData(const std::string& w) : word(w), uses(1) {}

        void add() {
                uses++;
        }

        int getUses() const {
                return uses;
        }

        std::string getWord() const {
                return word;
        }

        float getWeight(const int amount) const {
                float r = uses / amount;
                return r > 1 ? 1 : r < 0 ? 0 : r;
        }
};

class WordObject {
public:
        WordObject(const std::string& word, const std::string& nextWord, const char& flags);
        WordObject(const WordObject& other);
        WordObject(WordObject&& other) noexcept;
        std::string getWord() const;
        bool isStart();
        bool checkWord(const std::string& word) const;
        void addWord(const std::string& word);
        void combine(const WordObject& wordObject);
        std::vector<WordData*> getValuesFromMap(const std::unordered_map<std::string, std::unique_ptr<WordData>>& map) const;
        std::vector<WordData*> getNextWordList() const;
        std::string getRandomNext() const;
        bool is_start = false;
        void setPOS(const POS& type);
        POS getPOS() const;
        void updateUse(int type);
private:
        const std::string word;
        mutable std::unordered_map<std::string, std::unique_ptr<WordData>> nextWord;
        POS type;
        char useFlags = 0x00000000;
        //128xPeriod 64xComma 32xQuestion 16xExclamation 8xNumbers 4xCaps 2xQuote 1xhifen
};

#endif
