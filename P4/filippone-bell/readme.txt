**********************************
     PROJECT DESCRIPTION
**********************************
This project implements a logic gate framework.  The core program sets up prolog 
tables that describe the circuit that the programmer implements and verifies
that the design is a valid curcuit. Finally, it returns the value of the OutputPort.



**********************************
     DIRECTORY STRUCTURE
**********************************
lib                       jars and other files necessary for running unit tests
src                       
  GatesApp                core program
  logicGates              GatesApp dependancies
  Pins                    GatesApp dependancies
  RegTest             
  Errors
tests                     unit tests for GatesApp
Correct                   validation files for unit tests
