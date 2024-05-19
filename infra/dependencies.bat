@echo off

cd \theHarvester
docker build -t theharvester .

cd ..

docker image pull caffix/amass
