/*  
**  Class.cpp
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#include "Class.h"

Class::Class(int classID, int classSemester, string classTitle, int creditHrs)
  : _classID(classID),
    _classSemester(classSemester),
    _classTitle(classTitle),
    _creditHrs(creditHrs)
{ }

int Class::getClassID() { return _classID; }
int Class::getClassSemester() { return _classSemester; }
int Class::getCreditHrs() { return _creditHrs; }
char Class::getGrade() { return _grade; }
string Class::getClassTitle() { return _classTitle; }

void Class::setGrade(char grade)
{
  return;
}
