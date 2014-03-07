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
#include <iomanip>
#include <string>
#include <sstream>

Student::Student(int studentID, std::string studentName)
  : _studentID(studentID),
    _studentName(studentName)
{ }

std::string Student::getStudentName() const { return _studentName; }

int Student::getStudentID() const { return _studentID; }

bool Student::addClass(const Class &classToAdd)
{
  // -- http://www.cplusplus.com/reference/map/map/insert/
  std::pair<std::map<std::string, Class>::iterator, bool> success;

  success = _classList.insert( std::pair<std::string, Class>(classToAdd.getCatalogID(), classToAdd) );

  return success.second;
}

bool Student::updateGrade(const std::string courseID, GRADE grade)
{
  std::map<std::string, Class>::iterator iterator;
  iterator = _classList.find(courseID);

  if(iterator != _classList.end())
  {
    iterator->second.setGrade(grade);
    return true;
  }

  return false;
}

bool Student::getClass(const std::string courseID, Class *classCopy)
{
  std::map<std::string, Class>::iterator iterator;
  iterator = _classList.find(courseID);

  if(iterator != _classList.end())
  {
    *classCopy = iterator->second;
    return true;    
  }

  return false;
}

bool Student::deleteClass(const std::string courseID)
{
  std::map<std::string, Class>::iterator iterator;
  iterator = _classList.find(courseID);

  if(iterator != _classList.end())
  {
    _classList.erase(iterator);
    return true;
  }

  return false;
}

void Student::listClasses() const
{
  std::map<std::string, Class>::const_iterator iterator;
  for(iterator = _classList.begin(); iterator != _classList.end(); iterator++)
  {
    std::cout << iterator->second << std::endl;
  }
}

float Student::getGPA() const
{
  int totalGradePoints = 0;
  int creditsCompleted = 0;

  std::map<std::string, Class>::const_iterator iterator;
  for(iterator = _classList.begin(); iterator != _classList.end(); iterator++)
  {
    switch(iterator->second.getGrade())
    {
      case IP:
      case W:
        break;

      default: // Catches A, B, C, D, F
        totalGradePoints += iterator->second.getGradePoints();
        creditsCompleted += iterator->second.getCreditHrs();
    }
  }

  return static_cast<float> (totalGradePoints) / creditsCompleted;
}

std::ostream &operator << (std::ostream &output, const Student &studentRecord)
{
  std::stringstream inProgressList;
  std::stringstream courseHistory;

  int creditsInProgress = 0;
  int totalGradePoints = 0;
  int creditsCompleted = 0;
  int creditsNotCompleted = 0;

  bool coursesInProgress = false;

  output << "Student Record" << std::endl
         << "=============================================================" << std::endl
         << "Student ID: " << studentRecord._studentID << std::endl
         << "Student Name: " << studentRecord._studentName << std::endl
         << "=============================================================" << std::endl;



  std::map<std::string, Class>::const_iterator iterator;
  for(iterator = studentRecord._classList.begin(); 
        iterator != studentRecord._classList.end(); iterator++)
  {
    switch(iterator->second.getGrade())
    {
      case IP:
        inProgressList << iterator->second << std::endl;
        coursesInProgress = true;
        creditsInProgress += iterator->second.getCreditHrs();
        break;

      case W:
        courseHistory << iterator->second << std::endl;
        creditsNotCompleted += iterator->second.getCreditHrs();
        break;

      default: // Catches A, B, C, D, F
        courseHistory << iterator->second << std::endl;
        totalGradePoints += iterator->second.getGradePoints();
        creditsCompleted += iterator->second.getCreditHrs();
    }

  }

  if(coursesInProgress)
  {
    output << "Courses in Progress" << std::endl << std::endl
           << inProgressList.str() << std::endl
           << "Total Credits in Progress: " << creditsInProgress << std::endl
           << "=============================================================" << std::endl;
  }

  output << "Course History" << std::endl << std::endl
         << courseHistory.str() << std::endl
         << "Credits Completed: " << creditsCompleted << std::endl
         << "Credits Not Completed: " << creditsNotCompleted << std::endl
         << std::setprecision(3)
         << "GPA: " << static_cast<float> (totalGradePoints) / creditsCompleted << std::endl;

  return output;

}
