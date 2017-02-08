**********************************
     PROJECT DESCRIPTION
**********************************
The core program, toYuml.vm, takes a database in the form of a prolog file
and outputs yUML specifications that can be put into the online yUML
tool to get a diagram.  

The following is the 10,000 foot view of the way this project fits into
the bigger picture.

Violet --> P2 --> vpl.pl database file --> P3 --> yuml spec --> online yUML tool --> diagram




**********************************
     DIRECTORY STRUCTURE
**********************************
lib                       jars and other files necessary for running MDL and unit tests
src                       toYuml.vm (core program)
tests                     
  TestToYuml.*            unit tests for toYuml.vm
  pl                      prolog files 
  violet                  .class.violet files
  yuml                    toYuml.vm output files
  correct                 validation files for unit tests
diagrams                  image files of UML diagrams from online yuml tool and from violet
