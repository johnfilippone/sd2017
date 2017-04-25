#!/bin/bash
CLASSPATH=$PWD:
CLASSPATH+=$PWD/lib/:
CLASSPATH+=$PWD/lib/RegTest.jar:
CLASSPATH+=$PWD/lib/junit-4.12.jar:
CLASSPATH+=$PWD/lib/hamcrest-core-1.3.jar:
CLASSPATH+=$PWD/src/:
CLASSPATH+=$PWD/tests/:
export CLASSPATH
