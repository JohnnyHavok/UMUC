/*  
**  RationalNumber.h
**  Justin Smith
**  Homework 4 
**  CMIS 315.6380
**  02.22.14
*/

#ifndef RATIONALNUMBER_H
#define RATIONALNUMBER_H

#include <iostream>
using std::ostream;

class RationalNumber
{
  friend ostream &operator << (ostream &, const RationalNumber &);

public:
  RationalNumber(int numerator = 0, int denominator = 1);
  RationalNumber(const RationalNumber &);

  RationalNumber operator + (const RationalNumber &) const;
  RationalNumber operator - (const RationalNumber &) const;
  RationalNumber operator * (const RationalNumber &) const;
  RationalNumber operator / (const RationalNumber &) const;

  bool operator == (const RationalNumber &) const;
  bool operator != (const RationalNumber &) const;
  bool operator >  (const RationalNumber &) const;
  bool operator <  (const RationalNumber &) const;

  inline int negate(int i) { return i * -1; }
  inline double getDouble() { return numerator / denominator; }

private:
  void gcdReduce();

  int numerator;
  int denominator;
};

#endif
