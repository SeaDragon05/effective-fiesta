#include "Castle.hpp"
#include <string>
#include <vector>
#include <fstream>
#include <iostream>
#include <random>
#include "SentenceData.hpp"
#include "Database.hpp"
#include "WordObject.hpp"

extern int LM_generate_sentence_word_limit;
extern bool LM_debug;
extern bool checkWord(const std::string&);

void Castle::logCorrect(const std::shared_ptr<SentenceData> sentence) {
        correct.push_back(sentence);
}

void Castle::logIncorrect(const std::shared_ptr<SentenceData> sentence) {
        incorrect.push_back(sentence);
}

void Castle::getAll() {
        int itterations = (correct.size() + incorrect.size());
        if (itterations % 100 == 0) {
                printf("%i iterations completed\n", itterations);
        }
}

std::shared_ptr<WordObject> Castle::getWordFromString(const std::string& word) const {
        return data.getWord(word);
}

//THIS ONLY WORKS ONLY AND ONLY IF THE DATABASE HAS COMPILED ITS DATA ON THE SAME FILE
//OR EVERY SINGLE WORD IN THE FILE YOU ARE PASSING ARE ALREADY DEFINED

void Castle::compileData(const std::string& URL) {
        data.compileData(URL);
        if (URL.empty()) throw std::runtime_error("URL is empty!");

        std::ifstream file(URL);
        if (!file.is_open()) {
                if (LM_debug) printf("File not found: Check the file path. -> %s\n", URL.c_str());
                return;
        }
        if (LM_debug) printf("Learning from: %s\n", URL.c_str());

        std::string word;
        if (!(file >> word)) {
                file.close();
                return;
        }

        auto dict = data.getDictionary();
        auto it = dict.find(word);
        if (it == dict.end()) {
                file.close();
                return;
        }

        auto workingSentence = std::make_shared<SentenceData>(it->second);

        while (file >> word) {
                it = dict.find(word);
                if (it != dict.end()) {
                workingSentence->addWord(it->second);
                }
                if (word.find('.') != std::string::npos ||
                        word.find('?') != std::string::npos ||
                        word.find('!') != std::string::npos) {
                        correct.push_back(workingSentence);
                        workingSentence = std::make_shared<SentenceData>();
                }
        }

        file.close();
}

std::vector<std::shared_ptr<SentenceData>> Castle::getExactSentences(const std::vector<std::shared_ptr<WordObject>> workingSentence) const {
        std::vector<std::shared_ptr<SentenceData>> results;
        size_t index = workingSentence.size();
        for (size_t i = 0; i < correct.size(); i++) {
                search:
                if (i >= correct.size()) break;
                for (size_t c = 0; c < index; c++) {
                        if (static_cast<int>(c) >= correct[static_cast<int>(i)]->getLength()) {
                                i++;
                                goto search;//continue search;
                        }
                        if (workingSentence[static_cast<int>(c)]->getWord() != correct[i]->getWordAtIndex(c)->getWord()) {
                                i++;
                                goto search;
                        }
                }
                results.push_back(correct[i]);
        }
        return results;
}


std::vector<std::shared_ptr<WordObject>> Castle::getPartialSentenceNextWord(const std::vector<std::shared_ptr<WordObject>> workingSentence, const int& workingSentenceIndexLength) const {
        std::vector<std::shared_ptr<WordObject>> results, wordSet, workingSet;
        for (int i = 0; i < workingSentenceIndexLength; i++) {
                auto& word = workingSentence[workingSentence.size() - 1 - i];
                workingSet.push_back(word);
                if (static_cast<int>(workingSet.size()) > workingSentenceIndexLength) break;
        }
        for (int i = 0; i < workingSentenceIndexLength; i++) {
                wordSet.push_back(workingSet[i]);
        }
        for (size_t i = 0; i < correct.size(); i++) {
                if (i > correct.size()) break;
                std::vector<std::shared_ptr<WordObject>> sentenceInQuestion = correct[static_cast<int>(i)]->getWordList();
                int nextWordIndex = 0;
                bool br = false;
                for (size_t j = 0; j < sentenceInQuestion.size(); j++) {
                        if (br) break;
                        if (wordSet.size() > 0 && wordSet[0] && sentenceInQuestion[static_cast<int>(j)] && sentenceInQuestion[static_cast<int>(j)]->getWord() == wordSet[0]->getWord()) {
                                nextWordIndex = static_cast<int>(j) + 1;
                                for (size_t k = 1; k < workingSet.size(); k++) {
                                        if (j - k < 0 || j - k > sentenceInQuestion.size()) {
                                                br = true;
                                                break;
                                        }
                                        if (sentenceInQuestion[static_cast<int>(j - k)]->getWord() != wordSet[static_cast<int>(k)]->getWord()) {
                                                br = true;
                                                break;
                                        }
                                }
                                if (!br && nextWordIndex < static_cast<int>(sentenceInQuestion.size())) results.push_back(sentenceInQuestion[nextWordIndex]);
                        }
                        if (results.size() > 64) break;

                }
                if (results.size() > 64) break;
        }
        return results;
}

std::vector<std::shared_ptr<SentenceData>> Castle::getSentencesWith(const std::shared_ptr<WordObject> word) const {
        std::vector<std::shared_ptr<SentenceData>> listy;
        for (const auto& sentence : correct) {
                if (sentence->contains(word)) {
                        listy.push_back(sentence);
                }
                if (listy.size() > 1024) break;//limit reached
        }
        return listy;
}

std::vector<std::shared_ptr<SentenceData>> Castle::getSentencesWith_InOrder(const std::vector<std::shared_ptr<WordObject>> words) const {
        if (words.size() == 0) {
                throw std::runtime_error("Can't search for sentences with no words!");
        }
        std::vector<std::shared_ptr<SentenceData>> listy = getSentencesWith(words[0]);
        if (words.size() > 1) {
                std::vector<std::shared_ptr<SentenceData>> results;
                for (auto& sentence : listy) {
                        int w_index = 1;
                        for (auto& word : sentence->getWordList()) {
                                if (word == words[w_index]) {
                                        w_index++;
                                }
                        }
                        if (w_index == static_cast<int>(words.size())) results.push_back(sentence);
                }
                return results;
        } else {
                return listy;
        }
}

std::vector<std::shared_ptr<SentenceData>> Castle::getSentencesWith_NoOrder(const std::vector<std::shared_ptr<WordObject>> words) const {
        if (words.size() == 0) throw std::runtime_error("Can't search for sentences with no words!");
        std::vector<std::shared_ptr<SentenceData>> listy = getSentencesWith(words[0]);
        if (words.size() > 1) {
                std::vector<std::shared_ptr<SentenceData>> results;
                for (auto& sentence : listy) {
                        bool contain = true;
                        for (size_t i = 0; i < words.size(); i++) {
                                if (!sentence->contains(words[static_cast<int>(i)])) contain = false;
                        }
                        if (contain) results.push_back(sentence);
                }
                return results;
        } else {
                return listy;
        }
}



void Castle::setPOS() {

}


/** !!This comment was copied from the java version!!
  * <h1>Sentence generation:</h1>
  * <p>
  * The method first selects a {@code WordObject} that can be used as a sentence
  * starter from the list {@code Manifest}. This allows the method to begin a new
  * sentence.
  * <p>
  * The method then checks the {@code Castle} for any exact sentences at the
  * index. This then returns an {@code ArrayList} of all matching sentences and then
  * collects all possible next words into a new {@code ArrayList} for the language model
  * to choose from. At this time, the language model does not use any special
  * tools to logically choose its next word. Once a next word it chosen, it then
  * moves onto the next step.
  * <p>
  * The next step is repeated until a stop is detected. At each start, the
  * language model will check with the databases castle to get all exact XOR
  * partial sentences. If no exact sentences are found, the language model will
  * switch to partial for the remaining process. If no exact XOR partial
  * sentences are found, the language model will switch to random. This is a
  * temporary solution, but it eventually catches into partial sentences soon
  * after.
  * <p>
  * Once a stop is detected, the method will check to see if whether or not the
  * generated sentence is an exact copy of a sentence from its training data. It
  * then will clean the sentence up, and return the {@code SentenceData}
  *
  * @return new SentenceData The sentence
  */
std::vector<std::shared_ptr<WordObject>> Castle::generateSentence(const int& EM, const int& PM) const {
        if (LM_debug) printf("START:");
        std::vector<std::shared_ptr<WordObject>> sentence;
        //start the sentence with a random starting word
        sentence.push_back(data.getStarterWord());
        bool exactSwitch = false; //assume true to start with
        int rLimit = 10, cR = 0, correction = 0;
        for (int i = 0; i < LM_generate_sentence_word_limit; i++) {
                if (cR >= rLimit) {
                        correction++;
                        if (LM_debug) printf("C%i", correction);
                }
                if (exactSwitch) {
                        std::vector<std::shared_ptr<SentenceData>> Exact = getExactSentences(sentence);
                        if (Exact.size() == 0 || static_cast<int>(Exact.size()) < EM) {
                                exactSwitch = false;
                                --i;
                                continue;
                        }
                        std::vector<std::shared_ptr<WordObject>> ExactNext;
                        for (size_t E = 0; E < Exact.size(); E++) {
                                try {
                                        ExactNext.push_back(Exact[E]->getWordAtIndex(i + 1));
                                } catch (...) {}
                        }
                        if (static_cast<int>(ExactNext.size()) > EM) {
                                if (LM_debug) printf("E%i:", static_cast<int>(ExactNext.size()));
                                std::random_device rd;
                                std::mt19937 rng(rd());
                                std::uniform_int_distribution<int> uni(0, static_cast<int>(ExactNext.size() - 1));
                                auto& nxtwrd = ExactNext[uni(rng)];
                                sentence.push_back(nxtwrd);
                                if (!checkWord(nxtwrd->getWord())) break;
                        } else {
                                exactSwitch = false;
                                --i;
                                continue;
                        }
                } else {
                        std::vector<std::shared_ptr<WordObject>> Partial = getPartialSentenceNextWord(sentence, std::min(static_cast<int>(sentence.size()), 4 - correction));
                        if (static_cast<int>(Partial.size()) > PM && static_cast<int>(Partial.size()) != 0) {
                                cR = (cR <= 0) ? 0 : cR-1;
                                correction = 0;
                                if (LM_debug) printf("P%i:", static_cast<int>(Partial.size()));
                                std::random_device rd;
                                std::mt19937 rng(rd());
                                std::uniform_int_distribution<int> uni(0, static_cast<int>(Partial.size()));
                                int index = std::max(0, uni(rng) - 1);
                                auto& nxtwrd = Partial[index%Partial.size()];

                                sentence.push_back(nxtwrd);
                                if (checkWord(nxtwrd->getWord())) break;
                        } else {
                                if (LM_debug) printf("R%i:", static_cast<int>(i));
                                auto& nxtwrd = data.getDictionary()[sentence[sentence.size()-1]->getRandomNext()];
                                sentence.push_back(nxtwrd);
                                if (checkWord(nxtwrd->getWord())) break;
                                cR++;
                                continue;
                        }
                }
        }
        if (LM_debug) printf("::END");
        return sentence;
}



std::vector<std::shared_ptr<WordObject>> Castle::generateSentenceWithWordObject(const std::shared_ptr<WordObject> word, const int& min) const {
        bool starting = word->isStart();
        int EM = min, PM = min;//annoying vars
        std::vector<std::shared_ptr<SentenceData>> sentences;
        std::vector<std::shared_ptr<WordObject>> sentence;
        if (starting) {
                sentence.push_back(word);
        } else {
                //not a starter word, so we need to make a connection to one:
                //get all sentences that have this word (max 1024 for reeesons)
                sentences = getSentencesWith(word);
                std::random_device rd;
                std::mt19937 rng(rd());
                std::uniform_int_distribution<int> uni(0, static_cast<int>(sentences.size() - 1));
                std::shared_ptr<SentenceData> AAA = sentences[uni(rng)];
                for (std::shared_ptr<WordObject> W : AAA->getWordList()) {
                        if (W == word) break;
                        sentence.push_back(W);
                }
        }
        bool exactSwitch = false; //assume true to start with
        int rLimit = 10, cR = 0, correction = 0;
        for (int i = static_cast<int>(sentence.size()); i < LM_generate_sentence_word_limit; i++) {
                if (cR >= rLimit) {
                        correction++;
                        if (LM_debug) printf("C%i", correction);
                }
                if (exactSwitch) {
                        std::vector<std::shared_ptr<SentenceData>> Exact = getExactSentences(sentence);
                        if (Exact.size() == 0 || static_cast<int>(Exact.size()) < EM) {
                                exactSwitch = false;
                                --i;
                                continue;
                        }
                        std::vector<std::shared_ptr<WordObject>> ExactNext;
                        for (size_t E = 0; E < Exact.size(); E++) {
                                try {
                                        ExactNext.push_back(Exact[E]->getWordAtIndex(i + 1));
                                } catch (...) {}
                        }
                        if (static_cast<int>(ExactNext.size()) > EM) {
                                if (LM_debug) printf("E%i:", static_cast<int>(ExactNext.size()));
                                std::random_device rd;
                                std::mt19937 rng(rd());
                                std::uniform_int_distribution<int> uni(0, static_cast<int>(ExactNext.size() - 1));
                                auto& nxtwrd = ExactNext[uni(rng)];
                                sentence.push_back(nxtwrd);
                                if (!checkWord(nxtwrd->getWord())) break;
                        } else {
                                exactSwitch = false;
                                --i;
                                continue;
                        }
                } else {
                        std::vector<std::shared_ptr<WordObject>> Partial = getPartialSentenceNextWord(sentence, std::min(static_cast<int>(sentence.size()), 4 - correction));
                        if (static_cast<int>(Partial.size()) > PM && static_cast<int>(Partial.size()) != 0) {
                                cR = (cR <= 0) ? 0 : cR-1;
                                correction = 0;
                                if (LM_debug) printf("P%i:", static_cast<int>(Partial.size()));
                                std::random_device rd;
                                std::mt19937 rng(rd());
                                std::uniform_int_distribution<int> uni(0, static_cast<int>(Partial.size()));
                                int index = std::max(0, uni(rng) - 1);
                                auto& nxtwrd = Partial[index%Partial.size()];

                                sentence.push_back(nxtwrd);
                                if (checkWord(nxtwrd->getWord())) break;
                        } else {
                                if (LM_debug) printf("R%i:", static_cast<int>(i));
                                auto& nxtwrd = data.getDictionary()[sentence[sentence.size()-1]->getRandomNext()];
                                sentence.push_back(nxtwrd);
                                if (checkWord(nxtwrd->getWord())) break;
                                cR++;
                                continue;
                        }
                }
        }
        if (LM_debug) printf("::END");
        return sentence;

}
