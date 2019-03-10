# parenthesis-matchig

This project presents two implementations of the Parenthesis Matching algorithm. I started this project as a study of Java 8, method reference, streams and Enums. I did two implementations to test the difference between using a Class Hierarchy or Enums to make the type checking (see item 2.a and 2.b of the algorithm) of Brackets.

It consists of a Maven project with tests written using JUnit.

## Problem Definition

Given an expression string exp , write a program to examine whether the pairs and the orders of “{“,”}”,”(“,”)”,”[“,”]” are correct in exp. 

## Solution (Algorithm)

1) Declare a character stack S.
2) Now traverse the expression string exp.
    * a) If the current character is a starting bracket (‘(‘ or ‘{‘ or ‘[‘) then push it to stack.
    * b) If the current character is a closing bracket (‘)’ or ‘}’ or ‘]’) then pop from stack and if the popped character is the matching starting bracket then fine else parenthesis are not balanced.
3) After complete traversal, if there is some starting bracket left in stack then “not balanced”

## Test Cases

* (a[0]+b[2c[6]]){24 + 53} -> true
* f(e(d)) -> true
* [()]{}([]) -> true
* ((b)-> false
* (c]-> false
* {(a[]))}-> false
* ([)]-> false
* )(-> false
* empty-> true
