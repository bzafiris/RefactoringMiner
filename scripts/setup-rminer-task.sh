#!/bin/bash
# ./gradlew distZip
# $1: git repository for analysis
# $2: name of the ramdisk directory for git clone

# prepare ramdisk
sudo umount /media/ramdisk
sudo mount -t tmpfs -o size=1024M tmpfs /media/ramdisk

if [[ $1 = *"https://github.com"* ]]; then
  git clone $1 /media/ramdisk/$2
else
  cp -R $1 /media/ramdisk
fi

cp build/distributions/RefactoringMiner.zip  /media/ramdisk
unzip /media/ramdisk/RefactoringMiner.zip -d /media/ramdisk

cp run.sh /media/ramdisk
cp run-bc.sh /media/ramdisk

chmod +x /media/ramdisk/run.sh
chmod +x /media/ramdisk/run-bc.sh

