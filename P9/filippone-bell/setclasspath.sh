#!/bin/bash
CLASSPATH=$PWD:
CLASSPATH+=$PWD/lib/basicConnector:
CLASSPATH+=$PWD/lib/gammaSupport:
CLASSPATH+=$PWD/src/parallelHJoin:
CLASSPATH+=$PWD/tests/parallelHJoin:
export CLASSPATH
echo "done"
