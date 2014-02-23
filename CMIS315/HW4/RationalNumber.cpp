/*  
**  RationalNumber.cpp
**  Justin Smith
**  Homework 4 
**  CMIS 315.6380
**  02.22.14
*/
#include <iostream>
#include "RationalNumber.h"

using std::ostream;


RationalNumber::RationalNumber(int n, int d)
  : numerator(n),
    denominator(d)
{
  if(denominator == 0)
    throw "Invalid Denominator, cannot divide by 0";

  if(denominator < 0)
    numerator = negate(numerator);

  gcdReduce();
}

RationalNumber::RationalNumber(const RationalNumber &rationalToCopy)
  : numerator(rationalToCopy.numerator),
    denominator(rationalToCopy.denominator)
{  
}

RationalNumber RationalNumber::operator + (const RationalNumber &RNOperand) const
{
  // Convert fractions so they have equal demoniators
  int op1Numerator = numerator * RNOperand.denominator;
  int op1Denominator = denominator * RNOperand.denominator;

  int op2Numerator = RNOperand.numerator * denominator;
  int op2Denominator = RNOperand.denominator * denominator; 

  return RationalNumber(op1Numerator + op2Numerator, op1Denominator);
}

RationalNumber RationalNumber::operator - (const RationalNumber &RNOperand) const
{
  // Convert fractions so they have equal demoniators
  int op1Numerator = numerator * RNOperand.denominator;
  int op1Denominator = denominator * RNOperand.denominator;

  int op2Numerator = RNOperand.numerator * denominator;
  int op2Denominator = RNOperand.denominator * denominator; 

  return RationalNumber(op1Numerator - op2Numerator, op1Denominator);
}

RationalNumber RationalNumber::operator * (const RationalNumber &RNOperand) const
{
  return RationalNumber(RNOperand.numerator * numerator,
                        RNOperand.denominator * denominator);
}

RationalNumber RationalNumber::operator / (const RationalNumber &RNOperand) const
{
  return RationalNumber(RNOperand.denominator * numerator,
                        RNOperand.numerator * denominator);
}

bool RationalNumber::operator == (const RationalNumber &RNOperand) const
{
  return (numerator * RNOperand.denominator == denominator * RNOperand.numerator);
}

bool RationalNumber::operator != (const RationalNumber &RNOperand) const
{
  return (numerator * RNOperand.denominator != denominator * RNOperand.numerator);
}

bool RationalNumber::operator > (const RationalNumber &RNOperand) const
{
  return (numerator * RNOperand.denominator > denominator * RNOperand.numerator);
}

bool RationalNumber::operator < (const RationalNumber &RNOperand) const
{
  return (numerator * RNOperand.denominator < denominator * RNOperand.numerator);
}


void RationalNumber::gcdReduce()
{
  /*
  ** Algorithm created from pseudocode implementation from Wikipedia
  ** http://en.wikipedia.org/wiki/Euclidean_algorithm#Implementations
  */
  int a = numerator;
  int b = denominator;
  int t;

  if(a < 0)
    a = negate(a);

  while(b != 0)
  {
    t = b;
    b = a % b;
    a = t;
  }

  numerator = numerator / a;
  denominator = denominator / a;
}

ostream &operator << (ostream &output, const RationalNumber &RNOutput)
{
  output << RNOutput.numerator << "/" << RNOutput.denominator;
  return output;
}
