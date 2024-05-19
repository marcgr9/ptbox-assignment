#!/bin/bash

cd ./theHarvester || exit
docker build -t theharvester .

cd ..

docker image pull caffix/amass
