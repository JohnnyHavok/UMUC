/*  
**  Student.cpp
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#include "Class.h"
#include "Student.h"

#include <iomanip>   // setprecision, setw
#include <iostream>  // endl
#include <sstream>   // stringstream
#include <string>    // string

Student::Student(int studentID, std::string studentName)
  : _studentID(studentID),
    _studentName(studentName)
{ }

std::string Student::getStudentName() const { return _studentName; }

int Student::getStudentID() const { return _studentID; }

bool Student::addClass(const Class &classToAdd)
{
  // -- Based on Stroustrup, B (2013). The C++ Programming Language (4th ed.) 
  // -- Associative Container Operations, Section 31.4.3.1 (pg 911)
  // -- Pair, Section 34.2.4.1 (pg 983)
  // -- pair(p,b) = c.insert(x) where p is an iterator, b is a bool
  // -- c is the collection and x is a tuple that can be inserted into c. 
  std::pair<std::map<std::string, Class>::iterator, bool> success;
  success = _classList.insert( std::pair<std::string, Class>(classToAdd.getCatalogID(), classToAdd) );
  return success.second;
} // -- END FUNC addClass()

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
} // -- END FUNC updateGrade()

bool Student::getClass(const std::string courseID, Class **classPointer)
{
  std::map<std::string, Class>::iterator iterator;
  iterator = _classList.find(courseID);

  if(iterator != _classList.end())
  {
    *classPointer = &iterator->second;
    return true;
  }

  return false;
} // -- END FUNC getClass()

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
} // -- END FUNC deleteClass()

void Student::listClasses() const
{
  std::cout << getColumnHeader() << std::endl;
  std::map<std::string, Class>::const_iterator iterator;
  for(iterator = _classList.begin(); iterator != _classList.end(); iterator++)
  {
    std::cout << iterator->second << std::endl;
  }
} // -- END FUNC listClasses()

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
} // -- END FUNC getGPA()

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
         << "================================================================" << std::endl
         << "Student ID: " << studentRecord._studentID << std::endl
         << "Student Name: " << studentRecord._studentName << std::endl
         << "================================================================" << std::endl;



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
    output << "Courses in Progress" << std::endl << studentRecord.getColumnHeader() << std::endl
           << inProgressList.str() << std::endl
           << "Total Credits in Progress: " << creditsInProgress << std::endl
           << "================================================================" << std::endl;
  }

  output << "Course History" << std::endl << studentRecord.getColumnHeader() << std::endl
         << courseHistory.str() << std::endl
         << "Credits Completed: " << creditsCompleted << std::endl
         << "Credits Not Completed: " << creditsNotCompleted << std::endl
         << std::setprecision(3)
         << "GPA: " << static_cast<float> (totalGradePoints) / creditsCompleted << std::endl;

  return output;

} // -- END FUNC OVERLOAD STREAM <<

std::string Student::getColumnHeader() const 
{
  std::stringstream header;

  header << std::setw(50) << "Credit" << std::setw(14) << "Grade" << std::endl
         << std::setw(8) << "Course ID" << std::setw(34) << "Course Title"
         << std::setw(7) << "Hours" << std::setw(7) << "Grade"
         << std::setw(7) << "Points";

  return header.str();
} // -- END FUNC getColumnHeader()
