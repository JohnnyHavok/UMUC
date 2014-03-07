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
#include <iostream>

class Class
{
  friend std::ostream &operator << (std::ostream &, const Class &);

public:
  Class(GRADE = IP); // Default all classes are IP / In Progress

  Class &setCatalogID(std::string);
  Class &setClassSemester(int);
  Class &setClassTitle(std::string);
  Class &setClassCreditHrs(int);
  Class &setClassGrade(GRADE);

  int getClassSemester() const;
  int getCreditHrs() const;
  GRADE getGrade() const;
  std::string getClassTitle() const;
  std::string getCatalogID() const;
  int getGradePoints() const;

  void setGrade(GRADE);

private:
  std::string _catalogID;
  int _classSemester;
  std::string _classTitle;
  int _creditHrs;
  GRADE _grade;

  std::string getLetterGrade() const;
};

#endif
