#include <iostream>
#include <fstream>

char** read(const char* fileName, int& count)
{
  std::ifstream countingStream(fileName);
  // first count them
  count = 0;
  while (true) {
    char line[100];
    countingStream.getline(line, 100);
    if (strlen(line) == 0) {
      break;
    }
    count += 1;
  }
  countingStream.close();
  std::ifstream readingStream(fileName);
  char** words = new char* [count];
  for (int index = 0; index < count; ++index) {
    char line[100];
    readingStream.getline(line, 100);
    words[index] = _strdup(line);
  }
  readingStream.close();
  return words;
}
