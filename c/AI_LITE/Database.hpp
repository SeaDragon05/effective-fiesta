#ifndef DATABASE_H
#define DATABASE_H
#include <unordered_map>
#include <unordered_set>
#include <string>
#include <vector>
#include <memory>
#include "WordObject.hpp"
#include "SentenceData.hpp"
#include "Castle.hpp"

class SentenceData;
class Castle;



class Database {
public:
		Database() {}
		Database(const std::string& url);
		void compileFromFile(const std::string& url);
		void add(std::shared_ptr<WordObject> word);
		void compileData(const std::string& URL);
		void computeChunks();
		int getWP() const;
		std::shared_ptr<WordObject>& getStarterWord() const;
		std::vector<WordData*> getWordNextList(const std::string& word) const;
		std::unordered_map<std::string, std::shared_ptr<WordObject>>& getDictionary() const;
		SentenceData& generateRandomSentence() const;
		std::vector<std::shared_ptr<WordObject>> generateSentence(const float& correctness) const;
		std::vector<std::shared_ptr<WordObject>>& redoSentence(const std::vector<std::shared_ptr<WordObject>>& sentence) const;
		std::shared_ptr<WordObject> getWord(const std::string& word) const;
		std::shared_ptr<WordObject> SearchForWord(const std::string& term, const char& flags) const;
private:
		mutable std::unordered_map<std::string, std::shared_ptr<WordObject>> dictionary;
		mutable std::vector<std::shared_ptr<WordObject>> manifest;
		mutable std::vector<std::shared_ptr<std::string>> references;
};

#endif
