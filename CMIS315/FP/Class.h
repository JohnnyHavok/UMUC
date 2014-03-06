/*  
**  Class.h
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#ifndef GRADE_ENUM
#define GRADE_ENUM

enum GRADE { F, D, C, B, A, W, IP };

#endif

#ifndef CLASS_H
#define CLASS_H

#include <string>

using std::string;

class Class
{
public:
  Class(GRADE = IP); // Default all classes are IP / In Progress

  Class &setCatalogID(string);
  Class &setClassID(int);
  Class &setClassSemester(int);
  Class &setClassTitle(string);
  Class &setClassCreditHrs(int);
  Class &setClassGrade(GRADE);

  int getClassID() const;
  int getClassSemester() const;
  int getCreditHrs() const;
  GRADE getGrade() const;
  string getClassTitle() const;
  string getCatalogID() const;
  int getGradePoints() const;

  void setGrade(GRADE);

private:
  string _catalogID;
  int _classID;
  int _classSemester;
  string _classTitle;
  int _creditHrs;
  GRADE _grade;
};

#endif
