********************
Directory structure
********************
filippone-bell
  filippone-bell-P1.pdf         required pdf
  setclasspath.sh               run this to set classpath 
  readme.txt                    this
  introspect/                   reflection program
  results/                      output from reflection program (prog1.pl files)
  tests/                         
    TestMain.java               Unit tests source
    lib/                        jars needed for unit tests
    validation-files/           correct output files used to validate unit tests
    p1package/
    ClassesOdd/
  


********************
    Unit tests
********************
Required:
TestReflectP1package    -> test introspect on p1package
TestReflectIntrospect   -> test introspect on self

More tests:
TestReflectGraff        -> test introspect on graff
TestReflectNotepad      -> test introspect on Notepad
TestReflectQuark        -> test introspect on Quark
TestReflectYparser      -> test introspect on yparser
TestPathEndsWithSlash   -> test user inputs a path that ends with a slash for the pathToPackage patameter
