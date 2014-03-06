/*  
**  Class.cpp
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#include "Class.h"

// Class::Class(string catalogID, int classID, int classSemester, string classTitle, int creditHrs)
//   : _catalogID(catalogID),
//     _classID(classID),
//     _classSemester(classSemester),
//     _classTitle(classTitle),
//     _creditHrs(creditHrs)
// { }

Class::Class() { }

Class &Class::setCatalogID(string catalogID)
{
  _catalogID = catalogID;
  return *this;
}

Class &Class::setClassID(int classID)
{
  _classID = classID;
  return *this;
}

Class &Class::setClassSemester(int classSemester)
{
  _classSemester = classSemester;
  return *this;
}

Class &Class::setClassTitle(string classTitle)
{
  _classTitle = classTitle;
  return *this;
}

Class &Class::setClassCreditHrs(int creditHrs)
{
  _creditHrs = creditHrs;
  return *this;
}

int Class::getClassID() const { return _classID; }
int Class::getClassSemester() const { return _classSemester; }
int Class::getCreditHrs() const { return _creditHrs; }
char Class::getGrade() const { return _grade; }
string Class::getClassTitle() const { return _classTitle; }
string Class::getCatalogID() const { return _catalogID; }

void Class::setGrade(char grade)
{
  return;
}
