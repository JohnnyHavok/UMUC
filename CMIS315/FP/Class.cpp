/*  
**  Class.cpp
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#include "Class.h"

#include <iomanip>  // setw
#include <iostream> // endl

Class::Class(GRADE grade) { _grade = grade; }

Class &Class::setCatalogID(std::string catalogID)
{
  _catalogID = catalogID;
  return *this;
}

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

std::ostream &operator << (std::ostream &output, const Class &classOutput)
{
  output << std::setw(8) << classOutput._catalogID << "." << classOutput._classSemester
         << std::setw(30) << classOutput._classTitle << std::setw(6) << classOutput._creditHrs
         << std::setw(6) << classOutput.getLetterGrade();

  int gradePoints = classOutput.getGradePoints();

  if(gradePoints != -1)
    output << std::setw(7) << gradePoints;
  else
    output << std::setw(7) << "--";

  return output;
}

std::string Class::getLetterGrade() const
{
  switch(_grade)
  {
    case A:
      return "A";
    case B:
      return "B";
    case C:
      return "C";
    case D:
      return "D";
    case F:
      return "F";
    case W:
      return "W";
    
    default:
      return "IP";
  }
}
