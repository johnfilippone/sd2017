#!/bin/bash

# Run this from the filippone-bell directory
echo "setting classpath..."
source setclasspath.sh
echo "classpath set!"

echo ""
echo "Compiling..."
javac src/h6/*.java
javac tests/h6/*.java

echo ""
echo "Running tests..."
java org.junit.runner.JUnitCore h6.oneTest

echo "Done."
