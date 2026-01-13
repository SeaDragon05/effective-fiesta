#include "Kingdom.hpp"
#include <string>

void Soldier::addConnection(const std::shared_ptr<WordObject> w) {
        for (auto& word : connections) {
                if (word->getWordObject() == w) {
                        word->add();
                        return;
                }
        }
        connections.push_back(std::make_shared<Soldier>(w));
}

std::vector<std::shared_ptr<Soldier>> Soldier::getConnections() const {
        return connections;
}

std::string Soldier::getStringFromWord() const {
        return word->getWord();
}
std::shared_ptr<WordObject> Soldier::getWordObject() const {
        return word;
}

void Soldier::add() {
        uses++;
}

int Soldier::getUses() const {
        return uses;
}

std::vector<std::shared_ptr<Soldier>> Legion::getDictionary() const {
        return dictionary;
}
void Legion::addSentence(const std::shared_ptr<SentenceData> sentence) {
        if (sentence->size() == 0) return; //dont add empty sentences please
        temp_sentence_data.push_back(sentence);
}

void Legion::ComputeData() {
        for (auto& sentence : temp_sentence_data) {
                for (auto& word : sentence->getWordList()) {
                        Soldier sol = Soldier(word);
                        //addSoldier(sol);
                }
        }
}

void Kingdom::setRequest(const std::string& command) {

}
