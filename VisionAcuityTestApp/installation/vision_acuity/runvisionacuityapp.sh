#!/bin/bash

export JAVA_HOME=/opt/jdk1.8.0_06
export PATH=$PATH:$JAVA_HOME/bin

cd /home/pi/vision_acuity

java -Xms245m -Xmx512m -cp ".:/home/pi/vision_acuity/jssc.jar:/home/pi/vision_acuity/log4j-1.2.17.jar:/home/pi/vision_acuity/visionacuityapp.jar"  com.marvel.visionacuity.Application linux_config.properties bookmark.properties
