/*  
**  Student.h
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#ifndef STUDENT_H
#define STUDENT_H

#include "Class.h"
#include <string>
#include <map> 

class Student
{
  friend std::ostream &operator << (std::ostream &, const Student &);

public:
  Student(int, std::string);

  std::string getStudentName() const;


  int getStudentID() const;

  bool addClass(const Class&);
  bool updateGrade(const std::string, const GRADE grade);
  bool getClass(const std::string, Class*);
  bool deleteClass(const std::string);

  void listClasses() const;

  float getGPA() const;

private:
  int _studentID;
  std::string _studentName;
  std::map<std::string, Class> _classList;
};

#endif
