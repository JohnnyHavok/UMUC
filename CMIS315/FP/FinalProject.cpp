/*  
**  FinalProject.cpp
**  Justin Smith
**  Final Project
**  CMIS 315.6380
**  03.07.14
*/

#include "Class.h"
#include "Student.h"

#include <fstream>
#include <iostream>

using namespace std;

void populateStudentFromFile(string, Student *);

int main()
{
  cout << "Begin Student Creation" << endl;
  Student goodStudent(108976, "Good Student");
  populateStudentFromFile("good_student.txt", &goodStudent);

  cout << "Good Student's transcript:" << endl << endl;  
  cout << goodStudent << endl; // Output transcript this way.

  // Do some manual updates to this student's records.
  cout << "Good Student has 4 courses in progress... Updating with new grades" << endl;
  goodStudent.updateGrade("CMIS330", A);
  goodStudent.updateGrade("CMSC335", A);
  goodStudent.updateGrade("CMSC411", A);
  cout << "Good Student had to drop one course before the cut off day, " << endl
       << "it shouldn't count against his GPA" << endl;
  goodStudent.updateGrade("CMSC412", W);


  cout << "Good Student's new transcript:" << endl << endl;
  cout << goodStudent << endl;

  cout << "Attempt to duplicate a course using catalogID: MATH141" << endl << endl;
  cout << "-----Previous MATH141-----" << endl;

  Class *mathCoursePtr;
  goodStudent.getClass("MATH141", &mathCoursePtr);
  cout << *mathCoursePtr << endl;

  Class duplicateCourse;

  duplicateCourse.setCatalogID("MATH141")
                 .setClassSemester(7000)
                 .setClassTitle("Calculus I")
                 .setClassCreditHrs(4);

  cout << "-----New MATH141-----" << endl;
  cout << duplicateCourse << endl;

  if(goodStudent.addClass(duplicateCourse))
    cout << "Duplicate Course Successfully Added!!" << endl;
  else
    cout << "Duplicate Course Was Not Accepted!!" << endl;


}

void populateStudentFromFile(string fileName, Student *student)
{
  ifstream input(fileName);

  // -- These variables will hold all the space seperated values
  // -- in the input text file.  
  Class course;
  string courseID;
  int classSemester;
  string courseTitle;
  int creditHrs;
  char grade;

  while(input >> courseID >> classSemester >> courseTitle >> creditHrs >> grade)
  {
    course.setCatalogID(courseID)
          .setClassSemester(classSemester)
          .setClassTitle(courseTitle)
          .setClassCreditHrs(creditHrs);

    switch(grade)
    {
      case 'A':
        course.setClassGrade(A);
        break;
      case 'B':
        course.setClassGrade(B);
        break;
      case 'C':
        course.setClassGrade(C);
        break;
      case 'D':
        course.setClassGrade(D);
        break;
      case 'F':
        course.setClassGrade(F);
        break;
      case 'W':
        course.setClassGrade(W);
        break;

      default:
        course.setClassGrade(IP);
        break;
    }

    student->addClass(course);       
  }

  input.close();
}
