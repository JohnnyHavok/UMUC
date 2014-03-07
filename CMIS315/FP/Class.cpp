/*  
**  Class.cpp
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#include "Class.h"

Class::Class(GRADE grade) { _grade = grade; }

Class &Class::setCatalogID(std::string catalogID)
{
  _catalogID = catalogID;
  return *this;
}

// Class &Class::setClassID(int classID)
// {
//   _classID = classID;
//   return *this;
// }

Class &Class::setClassSemester(int classSemester)
{
  _classSemester = classSemester;
  return *this;
}

Class &Class::setClassTitle(std::string classTitle)
{
  _classTitle = classTitle;
  return *this;
}

Class &Class::setClassCreditHrs(int creditHrs)
{
  _creditHrs = creditHrs;
  return *this;
}

Class &Class::setClassGrade(GRADE grade)
{
  _grade = grade;
  return *this;
}

// int Class::getClassID() const { return _classID; }
int Class::getClassSemester() const { return _classSemester; }
int Class::getCreditHrs() const { return _creditHrs; }
GRADE Class::getGrade() const { return _grade; }
std::string Class::getClassTitle() const { return _classTitle; }
std::string Class::getCatalogID() const { return _catalogID; }

int Class::getGradePoints() const
{
  switch(_grade)
  {
    case W:
    case IP:
      return -1; // Sentinel value, must check for this.
    
    default:
      return static_cast<int>(_grade) * _creditHrs;
  }
}

void Class::setGrade(GRADE grade) { _grade = grade; }
