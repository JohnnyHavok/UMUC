/*  
**  Student.h
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.06.14
*/

#ifndef STUDENT_H
#define STUDENT_H

#include "Class.h" // Composition

#include <iostream>
#include <map> 
#include <string>

class Student
{
  friend std::ostream &operator << (std::ostream &, const Student &);

public:
  // -- All student objects must have an ID and a Name
  // -- Uniqueness not checked because Student object is not
  // -- a collection of students
  Student(int, std::string);

  std::string getStudentName() const;
  int getStudentID() const;

  // -- Following accessors into _classList map return bool
  // -- to notify user if something failed, most likely due
  // -- to a class not being in the map
  bool addClass(const Class &);
  bool updateGrade(const std::string, const GRADE grade);
  bool deleteClass(const std::string);

  // -- The bool returned will indicate if the pointer passed to the 
  // -- function contains the location of the Class object stored in
  // -- the map _classList whose key is the string.
  bool getClass(const std::string, Class **);

  void listClasses() const;

  float getGPA() const;

private:
  int _studentID;
  std::string _studentName;

  // -- This map will hold all the student's records where the key
  // -- is a string that is the classes _catalogID and the value
  // -- is the entire Class object.
  std::map<std::string, Class> _classList;

  std::string getColumnHeader() const;
};

#endif
