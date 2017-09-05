#!/bin/bash

# Log Location on Server.
PROJECT_LOCATION=/home/pi/vision_acuity
exec > >(tee -i $PROJECT_LOCATION/visionacuity_install.log)
exec 2>&1

echo "Project Location will be: [ $PROJECT_LOCATION ]"

#echo "Switching user to root"
#sudo su

echo "Removing existing open jdk"
sudo apt-get purge open-jdk*

echo "Installing jdk 1.8 ..."
sudo tar zxvf ./jdk-8u6-linux-arm-vfp-hflt.gz -C /opt
echo "Done!!!"

echo "Checking java version..."
/opt/jdk1.8.0_06/bin/java -version
echo "Done!!!"

#exit

echo "Switching to vision acuity installtion directory"
cd /home/pi/vision_acuity
echo "Done!!!"

echo "Changing permission on vision acuity installation directory"
sudo chmod 777 /home/pi/vision_acuity
sudo chmod 777 /home/pi/vision_acuity/*
echo "Done!!!"

echo "Creating vision acuity own command"
sudo rm /usr/bin/runvisionacuityapp.sh
sudo cp runvisionacuityapp.sh /usr/bin
sudo chmod 777 /usr/bin/runvisionacuityapp.sh
echo "Done!!!"

echo "Configuring auto startup for vision acuity software"
echo "@runvisionacuityapp.sh" >> /home/pi/.config/lxsession/LXDE-pi/autostart
echo "Done!!!"

echo "Done!!!"
echo "Vision acuity installation has been completed successfully!!!"
echo "Please restart raspberry pi and ready to use"
echo "Thank you for choosing us"





