#!/bin/bash
echo "setting classpath..."
source setclasspath.sh
echo "classpath set!"

echo "Sample outputs:"
echo ""
echo "./tests/pl/ERMetaModel.vpl.pl ==========================="
java MDL.Vm2t ./tests/pl/ERMetaModel.vpl.pl src/toYuml.vm
cat vm2toutput.txt
echo ""
echo "./tests/pl/gates.vpl.pl ================================="
java MDL.Vm2t ./tests/pl/gates.vpl.pl src/toYuml.vm
cat vm2toutput.txt
echo ""
echo "./tests/pl/hard.vpl.pl =================================="
java MDL.Vm2t ./tests/pl/hard.vpl.pl src/toYuml.vm
cat vm2toutput.txt
echo ""
echo "./tests/pl/school.vpl.pl ================================"
java MDL.Vm2t ./tests/pl/shchool.vpl.pl src/toYuml.vm
cat vm2toutput.txt
echo ""
echo "./tests/pl/shoppingCart.vpl.pl =========================="
java MDL.Vm2t ./tests/pl/shoppingCart.vpl.pl src/toYuml.vm
cat vm2toutput.txt
echo ""
echo "./tests/pl/University.vpl.pl ============================"
java MDL.Vm2t ./tests/pl/University.vpl.pl src/toYuml.vm
cat vm2toutput.txt

rm vm2toutput.txt

echo ""
echo "Running tests..."
cd tests
javac TestToYuml.java
java org.junit.runner.JUnitCore TestToYuml

echo "Done."
