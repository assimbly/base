#!/bin/bash

if [ -n "$1" ]; then
  git add -A
  git commit -m "$1"
else
    printf "\nUsage:\n"
    printf "\ncommit.sh <Commit Message>\n"
fi
