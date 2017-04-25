#!/bin/bash

# Run this from the filippone-bell directory
echo "setting classpath..."
source setclasspath.sh
echo "classpath set!"

echo ""
echo "Compiling..."
javac src/gamma/*.java
javac tests/gamma/*.java

echo ""
echo "Running tests..."
cd tests
java org.junit.runner.JUnitCore gamma.TestPrinter

echo "Done."


