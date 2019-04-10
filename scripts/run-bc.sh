#!/bin/bash
./RefactoringMiner/bin/RefactoringMiner -bc $1 $2 $3
cp $1/all_refactorings_master.csv ~/workspace-eclipse/workspace-rminer/rminer-results/$1.csv
