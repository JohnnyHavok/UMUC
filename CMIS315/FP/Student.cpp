/*  
**  Student.cpp
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#include "Student.h"
#include "Class.h"

#include <iostream>
#include <string>
using namespace std;

Student::Student(int studentID, std::string studentName)
  : _studentID(studentID),
    _studentName(studentName)
{

}

int Student::getStudentID() const { return _studentID; }
std::string Student::getStudentName() const { return _studentName; }

void Student::addClass(const Class &classToAdd)
{
  _classList[classToAdd.getCatalogID()] = classToAdd;
}

Class Student::getClass(std::string key)
{
  return _classList[key];
}
