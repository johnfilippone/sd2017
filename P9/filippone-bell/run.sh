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
test_files=("gamma.TestPrinter"
            "gamma.TestReadRelation"
            )
java org.junit.runner.JUnitCore "${test_files[@]}"

echo "Done."


