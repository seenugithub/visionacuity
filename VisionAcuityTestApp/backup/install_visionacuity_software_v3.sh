#!/bin/bash

# Log Location on Server.
LOG_LOCATION=/home/ubuntu
exec > >(tee -i $LOG_LOCATION/visionacuity_install.log)
exec 2>&1

echo "Log Location should be: [ $LOG_LOCATION ]"

#echo "Switching user to root"
#sudo su

echo "Removing existing open jdk"
apt-get purge openjdk*

echo "Installing jdk 1.8 ..."
tar zxvf ./jdk-8u6-linux-arm-vfp-hflt.gz -C /opt
echo "Done!!!"

echo "Checking java version..."
/opt/jdk1.8.0_06/bin/java -version
echo "Done!!!"

#exit

echo "Creating vision acuity installation directory"
mkdir /home/ubuntu/vision_acuity
mkdir /home/ubuntu/vision_acuity/final
chmod 777 /home/ubuntu/vision_acuity
echo "Done!!!"

echo "Extracting visionacuity package"
tar -xvf visionacuitypkg.tar -C /home/ubuntu/vision_acuity/final
echo "Done"

echo "Switching to vision acuity installtion directory"
cd /home/ubuntu/vision_acuity/final
echo "Done!!!"

echo "Creating vision acuity own command"
rm /usr/bin/runvisionacuityapp.sh
chmod 777 runvisionacuityapp.sh
#sudo su
cp runvisionacuityapp.sh /usr/bin
chmod 777 /usr/bin/runvisionacuityapp.sh
#exit
echo "Done!!!"

echo "Configuring auto startup for vision acuity software"
rm /home/ubuntu/.config/lxsession/Lubuntu/autostart
mkdir -p /home/ubuntu/.config/lxsession/Lubuntu
touch /home/ubuntu/.config/lxsession/Lubuntu/autostart
echo "@runvisionacuityapp.sh" > /home/ubuntu/.config/lxsession/Lubuntu/autostart
chmod 777 /home/ubuntu/.config/lxsession/Lubuntu/autostart
echo "Done!!!"
echo "Vision acuity installation has been installed successfully!!!"
echo "Please restart pcduino and ready to use"
echo "Thank you for choosing us"





