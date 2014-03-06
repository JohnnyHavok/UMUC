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
using std::string;

class Class
{
public:
  Class();

  Class &setCatalogID(string);
  Class &setClassID(int);
  Class &setClassSemester(int);
  Class &setClassTitle(string);
  Class &setClassCreditHrs(int);

  int getClassID() const;
  int getClassSemester() const;
  int getCreditHrs() const;
  char getGrade() const;
  string getClassTitle() const;
  string getCatalogID() const;

  void setGrade(char);

private:
  // const string _catalogID;
  // const int _classID;
  // const int _classSemester;
  // const string _classTitle;
  // const int _creditHrs;
  
  string _catalogID;
  int _classID;
  int _classSemester;
  string _classTitle;
  int _creditHrs;
  char _grade;
};

#endif
