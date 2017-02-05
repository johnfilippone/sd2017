**********************************
     PROJECT DESCRIPTION
**********************************
Conform.java is the core program of this project.  Its function is to take a set of 
tables and validate that they conform to a set of CONSTRAINTS.  If a constraint
is violated, Conform outputs error messages that describe the error.




**********************************
     DIRECTORY STRUCTURE
**********************************
lib                       jars and other files necessary for running MDL and unit tests
src                      
  VPL                     main core program of VPL package
tests                     
  VPL                     tests for the VPL package
  test-dependencies
    violet                .class.violet files for all tests and given examples
    vpl                   .vpl.pl files for all tests and given examples
    validation-files      files used by unit tests to validate correct program functionality




**********************************
        ADDED FUNCTIONS
**********************************

In src/VPL/Conform.java:

    getCardinality(String role)
        This function takes a role and outputs the cardinality if one is given
        A role can take one of three forms:
          - cardinality
          - label
          - cardinality label
        The assumption is made that there will never be a role of the form, "label cardinality".
        
        If the role is of the form "label", the empty string is returned.
        Otherwise, cardinality is returned.
