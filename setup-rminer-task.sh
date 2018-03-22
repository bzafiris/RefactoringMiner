#!/bin/bash

# $1: git repository for analysis

# prepare ramdisk
sudo umount /media/ramdisk
sudo mount -t tmpfs -o size=1024M tmpfs /media/ramdisk

cp -R $1 /media/ramdisk

cp build/distributions/RefactoringMiner.zip  /media/ramdisk
unzip /media/ramdisk/RefactoringMiner.zip -d /media/ramdisk

cp run.sh /media/ramdisk

