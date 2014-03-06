/*  
**  Class.h
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#ifndef CLASS_H
#define CLASS_H

#include <string>

class Class
{
public:
  Class(int, int, string, creditHrs);

  int getClassID();
  int getClassSemester();
  int getCreditHrs();
  char getGrade();
  string getClassTitle();

  void setGrade(char);

private:
  int _classID;
  int _classSemester;
  string _classTitle;
  char _grade;
  int _creditHrs;
}
#endif
