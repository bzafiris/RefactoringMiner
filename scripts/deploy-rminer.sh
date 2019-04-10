#!/bin/bash
./gradlew distZip

rm -f /media/ramdisk/RefactoringMiner.zip
rm -R -f /media/ramdisk/RefactoringMiner
cp build/distributions/RefactoringMiner.zip  /media/ramdisk
unzip /media/ramdisk/RefactoringMiner.zip -d /media/ramdisk

