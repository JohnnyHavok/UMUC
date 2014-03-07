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

using namespace std; // Remove before ship

Student::Student(int studentID, std::string studentName)
  : _studentID(studentID),
    _studentName(studentName)
{

}

int Student::getStudentID() const { return _studentID; }
std::string Student::getStudentName() const { return _studentName; }

bool Student::addClass(const Class &classToAdd)
{
  // -- http://www.cplusplus.com/reference/map/map/insert/
  std::pair<std::map<string, Class>::iterator, bool> success;

  success = _classList.insert( std::pair<string, Class>(classToAdd.getCatalogID(), classToAdd) );

  return success.second;
}

bool Student::updateGrade(const string courseID, GRADE grade)
{
  Class &classRef = _classList[courseID];
  classRef.setGrade(grade);
  return true; // TODO: Check to make sure courseID was valid and grade was updated.
}

Class Student::getClass(std::string key)
{
  return _classList[key];
}

// -- Test Harness, remove before ship

void Student::listClasses()
{
  map<string, Class>::const_iterator iter;
  for(iter = _classList.begin(); iter != _classList.end(); iter++)
  {
    cout << "Key: " << iter->first << " Value: " << iter->second.getClassSemester() << endl;
  }
}
