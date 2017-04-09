#!/bin/bash
echo "Compiling classes..."
javac OOFrameworkShell/src/Application/Main.java
javac OOFrameworkShell/src/Framework/Calc.java
javac OOFrameworkShell/src/Framework/Gui.java
javac OOFrameworkShell/src/Framework/Factory.java
javac OOFrameworkShell/src/BIPlugIn/Calc.java
javac OOFrameworkShell/src/BIPlugIn/Gui.java
javac OOFrameworkShell/src/BIPlugIn/Factory.java
javac OOFrameworkShell/src/DPlugIn/Calc.java
javac OOFrameworkShell/src/DPlugIn/Gui.java
javac OOFrameworkShell/src/DPlugIn/Factory.java
echo "Classes compiled!"
