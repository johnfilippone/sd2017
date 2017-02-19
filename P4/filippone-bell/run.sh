#!/bin/bash

# Run this from the filippone-bell directory
echo "setting classpath..."
source setclasspath.sh
echo "classpath set!"

echo ""
echo "Compiling..."
javac src/GatesApp/*.java
javac src/logicGates/*.java
javac src/Pins/*.java
javac src/Errors/*.java
javac src/RegTest/*.java
javac test/GatesApp/*.java

echo ""
echo "Running tests..."
java org.junit.runner.JUnitCore GatesApp.MainTest

echo "Done."
