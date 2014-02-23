/*  
**  hw4.cpp
**  Justin Smith
**  Homework 4 
**  CMIS 315.6380
**  02.22.14
*/

#ifndef RATIONALNUMBER_H
#define RATIONALNUMBER_H

class RationalNumber
{
  friend ostream &operator << (ostream &, const RationalNumber &);
  friend ostream &operator >> (ostream &, RationalNumber &);

public:
  RationalNumber(int numerator = 0, int denominator = 0);
  RationalNumber(const RationalNumber &);

  RationalNumber operator + (const RationalNumber &) const;
  RationalNumber operator - (const RationalNumber &) const;
  RationalNumber operator * (const RationalNumber &) const;
  RationalNumber operator / (const RationalNumber &) const;

  bool operator == (const RationalNumber &) const;
  bool operator != (const RationalNumber &) const;
  bool operator >  (const RationalNumber &) const;
  bool operator <  (const RationalNumber &) const;

  inline double getDouble() { return numerator / denominator; }

private:
  int numerator;
  int denominator;

  void gcdReduce();
};

#endif