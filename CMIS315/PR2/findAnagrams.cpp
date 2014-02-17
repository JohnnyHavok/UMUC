/*	
**  findAnagrams.cpp
**  Justin Smith
**  Project 2
**  CMIS 315.6380
**  02.16.14
*/


#include <iostream> // cout, endl
#include <cstring>  // strlen, strcpy, strcmp

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

	char* _word;
	char* _key;
};

//------- 
// Begin KeyedWord class implementation

KeyedWord::KeyedWord(char *word)
{
	_word = word;
	_key = new char[strlen(_word)];
	strcpy(_key, _word);
	selectionSort(_key);
}

char* KeyedWord::getWord() {return _word;}
char* KeyedWord::getKey() {return _key;}

/*
** The following selectionSort() and swap() function code
** was copied and modified from (Dietel, Dietel, C++ LTP, 8th)
** Figure 8.13 pg. 345.  It varies in that it figures out the
** the size of the string literal that was passed in and it
** has to deal with the terminating '\0' character.
*/
void KeyedWord::selectionSort(char *const key) 
{
	int smallest; // index of smallest element
	int _wordSize = strlen(key); // size of word

	for(int i = 0; i < _wordSize - 1; ++i)
	{
		smallest = i;
		for(int index = i + 1; index < _wordSize; ++index)
			if(key[index] < key[smallest])
				smallest = index;

		swap(&key[i], &key[smallest]);
	}
}

void KeyedWord::swap(char *const element1Ptr, char *const element2Ptr)
{
	char temp = *element1Ptr;
	*element1Ptr = *element2Ptr;
	*element2Ptr = temp;
}

// End KeyedWord class implementation
//-------

void sort(KeyedWord* keyedWords[], int numberOfObjects);
void printAnagrams(KeyedWord* keyedWords[], int numberOfObjects);

int main(int argumentCount, char** arguments)
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

		cout << "Begin print function" << endl << endl << endl;

		printAnagrams(keyedWords, numberOfWords);
	}
}

/*
** Again, modified from SelectionSort found in (Dietel, Dietel, C++ LTP, 8th)
** Figure 8.13, page 345.
*/
void sort(KeyedWord *keyedWords[], int numberOfObjects)
{
	int smallest; // index of smallest element

	for(int i = 0; i < numberOfObjects - 1; ++i)
	{
		smallest = i;
		for(int index = i + 1; index < numberOfObjects; ++index)
			if(strlen(keyedWords[index]->getKey()) < 
				strlen(keyedWords[smallest]->getKey()))
				smallest = index;

		KeyedWord keyedWordPtr = *keyedWords[i];
		*keyedWords[i] = *keyedWords[smallest];
		*keyedWords[smallest] = keyedWordPtr;
	}
}

/*
** This function will iterate through the array and attempt to match 
** the current word key to any key past its current index.  It will
** short-curcuit the inner-loop if it starts seeing keys longer than 
** it's own indicating that there will be no more key matches possible.
*/
void printAnagrams(KeyedWord *keyedWords[], int numberOfObjects)
{
	int totalAnagrams = 0;

	for(int i = 0; i < numberOfObjects - 1; ++i)
	{
		char *currentKey = keyedWords[i]->getKey();
		int maxLengthSearch = strlen(currentKey);

		for(int j = i + 1; j < numberOfObjects; ++j)
		{
			// If the next anagram key is longer and array is sorted
			// we know there will never be another match, no need
			// to keep searching, go to next outer for loop index.
			if(strlen(keyedWords[j]->getKey()) > maxLengthSearch)
				break;

			if(strcmp(currentKey, keyedWords[j]->getKey()) == 0) 
			{
				++totalAnagrams;
				cout << keyedWords[i]->getWord() << endl;
				cout << keyedWords[j]->getWord() << endl << endl; 
			}
		}
	}

	cout << "Found " << totalAnagrams << " anagrams in this file" << endl;
}