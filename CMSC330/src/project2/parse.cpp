/*
 * Justin D Smith
 * CMSC330 6980
 * Project 2
 * Submitted March 28, 2010
 * Development Environment:  Microsoft Windows 7
 *                           Microsoft Visual Studio Professional 2008
*/
#include <cctype> 
#include <iostream> 
#include <string> 
#include <strstream>
using namespace std;

#include "parse.h"

string parseName(strstream& in)
{
    char alnum;
    string name = "";

    in >> ws;
    while (isalnum(in.peek()))
    {
        in >> alnum;
        name += alnum;
    }
    return name;
}