#!/bin/bash
CLASSPATH=$PWD:
CLASSPATH+=$PWD/src/:
CLASSPATH+=$PWD/test/:
CLASSPATH+=$PWD/lib/junit-4.12.jar:
CLASSPATH+=$PWD/lib/hamcrest-core-1.3.jar:
export CLASSPATH
echo "done"
