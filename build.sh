#!/bin/bash

gradle clean build
sudo docker build -t ezrent-back .