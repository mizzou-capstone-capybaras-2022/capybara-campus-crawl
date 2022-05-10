#!/bin/bash


if [ "$EUID" -ne 0 ];
  then echo "Please run as root"
  exit 1
fi

pip install -r ./requirements.txt
#apt install aircrack-ng

#Remember to change this to the desired interface name.
ip link set wlp0s20f0u1 down
iw wlp0s20f0u1 set monitor none
ip link set wlp0s20f0u1 up

cp ./MonitorNodes.py /root

chmod +x /root/MonitorNodes.py

#cp ./env.json /etc/pi_env.json

#Will run hourly
(crontab -l 2>/dev/null; echo "0 * * * * python3 /root/MonitorNodes.py") | crontab -