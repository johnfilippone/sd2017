#!/bin/bash
CLASSPATH=$PWD:
CLASSPATH+=$PWD/tests/lib/hamcrest-core-1.3.jar:
CLASSPATH+=$PWD/tests/lib/junit-4.12.jar:
CLASSPATH+=$PWD/tests:
CLASSPATH+=$PWD/tests/lib/RegTest.jar:
CLASSPATH+=$PWD/tests/ClassesOdd:
export CLASSPATH
echo "done"
