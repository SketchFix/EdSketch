# EdSketch: Execution-Driven Sketching for Java

Master branch: 
- v0.1 March 9, 2017: SPIN submission. Basic sketching functions that involves expression, assignments, and condition expressions.   


## Overview

EdSketch executes test cases to synthesize sketches with lazy candidate generation. EdSketch will not generate concrete candidates for a hole until the test execution reaches the hole. 


When the test execution first reaches a hole,  EdSketch initializes the hole's candidates based on the given visible variables and default values. Each candidate is assigned a unique identifier, which is its index in the vector. Each hole's candidate
identifier is initialized as -1, indicating that EdSketch
has not selected a candidate for this hole.  

During the test execution, the sketch engine selects an identifier starting from 0,  and the candidate with the corresponding identifier is used to fill in the hole. The execution continues with this selection until it encounters a runtime exception or a test failure, which leads to a backtrack with an increment of the candidate identifier, indicating the next candidate at runtime (incrementCounter()). 


Whenever the program backtracks, the candidate identifier is reset as -1 to fill a new candidate (reset()). The process terminates when a complete program that passes all tests is found or the space of candidate programs is exhaustively explored.  

