
#include <iostream>

char** read(const char* fileName, int& count);

class KeyedWord
{
public:
	KeyedWord(char* word); // TO BE IMPLEMENTED
	char* getWord(); // TO BE IMPLEMENTED
	char* getKey();  // TO BE IMPLEMENTED
private:
	char * _word;
	char* _key;
};

void sort(KeyedWord* keyedWords[], int numberOfObjects); // TO BE IMPLEMENTED
void printAnagrams(KeyedWord* keyedWords[], int numberOfObjects); // TO BE IMPLEMENTED

void main(int argumentCount, char** arguments)
{
	if (argumentCount <= 1) {
		std::cout << "No file name given as argument" << std::endl;
	} else {
		int numberOfWords = 0;
		char** words = read(arguments[1], numberOfWords);

		// allocate the array of KeyedWord objects
		KeyedWord** keyedWords = new KeyedWord* [numberOfWords];

		// populate the array of keyedWord objects
		for (int wordIndex = 0; wordIndex < numberOfWords; ++wordIndex)
		{
			keyedWords[wordIndex] = new KeyedWord(words[wordIndex]);
		}

		sort(keyedWords, numberOfWords);

		printAnagrams(keyedWords, numberOfWords);
		
		// EXTRA CREDIT (+5 points): clean up all allocated memory

	}
}