#!/bin/bash

# Print list of users, who have read right for given file

LOG_FILE=~/lab3_log
exec 2>> $LOG_FILE
echo Run at `date` 1>&2

DIRECTORY=$1

if [ -z ${DIRECTORY} ]; then
  DIRECTORY=`pwd`
fi

TYPE=`file -b $DIRECTORY`

if [ ! $TYPE = "directory" ]; then
  echo "Given file is not a directory" 1>&2
  exit 1;
fi

for FILE in $DIRECTORY/*; do
  TYPE=`file -b ~`

  if [ TYPE = "directory"  ]; then
    continue;
  fi

  APPEND_ATTR=`lsattr -d $FILE | cut -f1 -d' ' | grep -o a`
  if [ ! -z ${APPEND_ATTR} ]; then
    echo $FILE
  fi
done;
