#ifndef KINGDOM_H
#define KINGDOM_H
#include <string>
#include "Castle.hpp"
#include <vector>

class Soldier {
public:
		Soldier(const std::shared_ptr<WordObject> w) : word(w) {}
		void addConnection(const std::shared_ptr<WordObject> w);
		std::vector<std::shared_ptr<Soldier>> getConnections() const;
		std::string getStringFromWord() const;
		std::shared_ptr<WordObject> getWordObject() const;
		void add();
		int getUses() const;
private:
		int uses = 1;
		const std::shared_ptr<WordObject> word;
		mutable std::vector<std::shared_ptr<Soldier>> connections;
};

//class legion is used in a temporary form that stores sentence data and acts as a Database for that data
class Legion {
public:
	Legion() {}
	std::vector<std::shared_ptr<Soldier>> getDictionary() const;
	void addSentence(const std::shared_ptr<SentenceData> sentence);
	void ComputeData();
	void addSoldier(const Soldier& sol);
private:
	mutable std::vector<std::shared_ptr<SentenceData>> temp_sentence_data;
	mutable std::vector<std::shared_ptr<Soldier>> dictionary;
	mutable std::vector<std::shared_ptr<WordObject>> manifest;
};

class Kingdom {
public:
    void setRequest(const std::string& command);
    void compute();
    std::vector<std::shared_ptr<WordObject>> getSentence() const;
private:
    mutable std::string input;
    static Castle& castle;
    Legion legion;
};

#endif










