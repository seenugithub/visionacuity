#!/bin/bash

export JAVA_HOME=/opt/jdk1.8.0_06
export PATH=$PATH:$JAVA_HOME/bin

cd /home/ubuntu/vision_acuity/final

java -Xms512m -Xmx512m -cp ".:/home/ubuntu/vision_acuity/final/jssc.jar:/home/ubuntu/vision_acuity/final/log4j-1.2.17.jar:/home/ubuntu/vision_acuity/final/visionacuityapp.jar"  com.marvel.visionacuity.Application linux_config.properties bookmark.properties
