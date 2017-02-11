#!/bin/bash
echo "setting classpath..."
source setclasspath.sh
echo "classpath set!"

javac tests/TestToYuml.java

echo "Running tests..."
cd tests
java org.junit.runner.JUnitCore TestToYuml

echo "Done."
