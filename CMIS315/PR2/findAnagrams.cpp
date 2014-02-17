/*	
**  findAnagrams.cpp
**  Justin Smith
**  Project 2
**  CMIS 315.6380
**  02.16.14
*/


#include <iostream> // cout, endl;

using namespace std;

char** read(const char* fileName, int& count);

class KeyedWord
{
public:
	KeyedWord(char* word);
	char* getWord();
	char* getKey(); 
private:
	void swap(char *const, char *const);
	void selectionSort(char *const);
	int wordSize(const char *const);
	void wordCopy(const char *const, char *const);

	char* _word;
	char* _key;
};

//------- 
// Begin KeyedWord class implementation

KeyedWord::KeyedWord(char *word)
{
	_word = word;
	_key = new char[wordSize(word)];
	wordCopy(_word, _key);

	selectionSort(_key);

	cout << getWord() << ":" << getKey() << endl;

}

char* KeyedWord::getWord() {return _word;}
char* KeyedWord::getKey() {return _key;}


int KeyedWord::wordSize(const char *const word) 
{
	int letterCount = 0;
	for(int i = 0; word[i] != '\0'; i++)
		letterCount++;
	return letterCount + 1; // Returns size of string literal
}

void KeyedWord::wordCopy(const char *const word, char *const key)
{
	int i = 0;

	while(word[i] != '\0')
	{
		key[i] = *(word + i);
		++i;
	}

	key[i] = '\0';
}

void KeyedWord::swap(char *const element1Ptr, char *const element2Ptr)
{
	char temp = *element1Ptr;
	*element1Ptr = *element2Ptr;
	*element2Ptr = temp;
}

void KeyedWord::selectionSort(char *const key) 
{
	int smallest; // index of smallest element
	int _wordSize = wordSize(key); // size of word

	for(int i = 0; i < _wordSize - 1; ++i)
	{
		smallest = i;
		for(int index = i + 1; index < _wordSize - 1; ++index)
		{
			if(key[index] < key[smallest])
				smallest = index;
		}

		swap(&key[i], &key[smallest]);

	}
}


// End KeyedWord class implementation
//-------

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