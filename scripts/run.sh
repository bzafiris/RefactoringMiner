#!/bin/bash
./RefactoringMiner/bin/RefactoringMiner -a $1 $2
cp $1/all_refactorings_master.csv ~/workspace-eclipse/workspace-rminer/rminer-results/$1.csv
