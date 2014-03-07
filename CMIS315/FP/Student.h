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
public:
  Student(int, std::string);

  int getStudentID() const;
  std::string getStudentName() const;

  void addClass(const Class&);

  Class getClass(std::string);

private:
  int _studentID;
  std::string _studentName;
  std::map<std::string, Class> _classList;
};

#endif
