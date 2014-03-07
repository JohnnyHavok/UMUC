/*  
**  Class.h
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#ifndef GRADE_ENUM
#define GRADE_ENUM

// -- enum for comparing grades more concise
enum GRADE { F, D, C, B, A, W, IP };

#endif

#ifndef CLASS_H
#define CLASS_H

#include <iostream>
#include <string>

class Class
{
  friend std::ostream &operator << (std::ostream &, const Class &);

public:
  Class(GRADE = IP); // Default all classes are IP / In Progress

  // -- set functions for chaining object construction
  // -- makes instantiating a class a little easier to read, a la Java
  Class &setCatalogID(std::string);
  Class &setClassSemester(int);
  Class &setClassTitle(std::string);
  Class &setClassCreditHrs(int);
  Class &setClassGrade(GRADE);

  // -- get functions for getting at any private variable defined for
  // -- this class
  int getClassSemester() const;
  int getCreditHrs() const;
  GRADE getGrade() const;
  std::string getClassTitle() const;
  std::string getCatalogID() const;
  int getGradePoints() const;

  // -- setGrade functions allows a class grade to be updated
  void setGrade(GRADE);

private:
  std::string _catalogID;
  int _classSemester;
  std::string _classTitle;
  int _creditHrs;
  GRADE _grade;

  // -- Returns string representation of grade enum
  std::string getLetterGrade() const; 
};

#endif
