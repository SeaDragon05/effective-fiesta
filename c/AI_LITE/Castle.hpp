#ifndef CASTLE_H
#define CASTLE_H
#include <string>
#include <vector>
#include <unordered_set>
#include "SentenceData.hpp"
#include "WordObject.hpp"
#include "Database.hpp"

enum class Type { CORRECT, INCORRECT };

class Database;
class SentenceData;

class Castle {
public:
    	Castle(Database& data) : data(data) {}
    	void logCorrect(const std::shared_ptr<SentenceData> sentence);
    	void logIncorrect(const std::shared_ptr<SentenceData> sentence);
		void getAll();
		void compileData(const std::string& url);
    	bool checkData() const;
    	bool hasIncorrectData() const;
    	std::vector<std::shared_ptr<SentenceData>> getExactSentences(const std::vector<std::shared_ptr<WordObject>> woringSentence) const;
    	std::vector<std::shared_ptr<WordObject>> getPartialSentenceNextWord(const std::vector<std::shared_ptr<WordObject>> workingSentence, const int& workingSentenceIndexLength) const;
    	std::vector<std::shared_ptr<SentenceData>> findSentencesWith(const std::vector<std::shared_ptr<WordObject>> list) const;
    	std::vector<std::shared_ptr<WordObject>> generateSentence(const int& exactMin, const int&partialMin) const;
		std::vector<std::shared_ptr<WordObject>> generateSentenceWithWordObject(const std::shared_ptr<WordObject> word, const int& partialMin) const;
		std::vector<std::shared_ptr<WordObject>> generateSentenceWithSetWordsInAnyOrder(const std::vector<std::shared_ptr<WordObject>> words, const int& partialMin) const;
		std::vector<std::shared_ptr<SentenceData>> getSentencesWith(const std::shared_ptr<WordObject> word) const;
		std::vector<std::shared_ptr<SentenceData>> getSentencesWith_NoOrder(const std::vector<std::shared_ptr<WordObject>> words) const;
		std::vector<std::shared_ptr<SentenceData>> getSentencesWith_InOrder(const std::vector<std::shared_ptr<WordObject>> words) const;
		std::vector<std::shared_ptr<SentenceData>> reviseSentenceWithData(const std::vector<std::shared_ptr<WordObject>> sentence) const;
		std::vector<std::shared_ptr<WordObject>> getWordsFromString(const std::vector<std::string>& words) const;
		std::shared_ptr<WordObject> getWordFromString(const std::string& word) const;
		std::vector<std::shared_ptr<WordObject>> generateSentenceWithWeights(const int& exactMin, const int& partialMin, const float& weightMin) const;
		void createNonExistingConnection(const std::vector<std::shared_ptr<WordObject>> sentence, const int& start, const int& end);
		void createNonExistingWord(const std::string& word, const std::string& nextWord);
		void trainWith(const std::string& URL);
		void setPOS();
		int getCorrectCount() const {
				return static_cast<int>(correct.size());
		}
private:
    	mutable std::vector<std::shared_ptr<SentenceData>> correct;
    	mutable std::vector<std::shared_ptr<SentenceData>> incorrect;
    	mutable std::vector<std::shared_ptr<std::string>> URLs;
		std::shared_ptr<std::string> topic;
    	Database& data;
};


#endif
