#!/bin/bash
CLASSPATH=$PWD:
CLASSPATH+=$PWD/lib/dist/lib/commons-collections.jar:
CLASSPATH+=$PWD/lib/dist/lib/commons-io-2.4.jar:
CLASSPATH+=$PWD/lib/dist/lib/commons-lang.jar:
CLASSPATH+=$PWD/lib/dist/lib/junit-4.12.jar:
CLASSPATH+=$PWD/lib/dist/lib/velocity.jar:
CLASSPATH+=$PWD/lib/dist/lib/violet.jar:
CLASSPATH+=$PWD/lib/dist/MDELite6.jar:
CLASSPATH+=$PWD/lib/hamcrest-core-1.3.jar:
CLASSPATH+=$PWD/lib/RegTest.jar:
CLASSPATH+=$PWD/src/:
CLASSPATH+=$PWD/tests/:
export CLASSPATH
echo "done"
