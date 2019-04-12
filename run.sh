#!/bin/bash
./RefactoringMiner/bin/RefactoringMiner -aggregate -a $1 $2
cp $1/all_refactorings_master.csv ~/data/data/workspace-eclipse/workspace-design-tradeoffs/rminer-v2/$1.csv
