#!/bin/bash
echo "setting classpath..."
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
echo "classpath set!"

echo "running VPL.conform on various .vpl.pl files..."
echo "CarRental.vpl.pl ======================================="
java VPL.Conform tests/test-dependencies/vpl/CarRental.vpl.pl
echo "Comp.vpl.pl ============================================"
java VPL.Conform tests/test-dependencies/vpl/Comp.vpl.pl
echo "DecayProduct.vpl.pl ===================================="
java VPL.Conform tests/test-dependencies/vpl/DecayProduct.vpl.pl
echo "diagram1.vpl.pl ========================================"
java VPL.Conform tests/test-dependencies/vpl/diagram1.vpl.pl

echo "Running tests..."
cd tests
java org.junit.runner.JUnitCore VPL.ConformTest

echo "Done."
