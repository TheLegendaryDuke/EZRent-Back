#!/bin/bash

gradle clean build
sudo docker build -t ezrent-back .
sudo docker tag ezrent-back gcr.io/ezrent-227517/back
sudo docker push gcr.io/ezrent-227517/back