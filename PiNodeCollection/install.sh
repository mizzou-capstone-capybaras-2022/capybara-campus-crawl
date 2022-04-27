#!/bin/bash

if [ "$EUID" -ne 0 ];
  then echo "Please run as root"
  exit 1
fi

pip install -r ./requirements.txt
apt install aircrack-ng
ip link set wlan1 down
iw wlan1 set monitor none
ip link set wlan1 up

cp ./MonitorNodes.py /root

chmod +x /root/MonitorNodes.py


(crontab -l 2>/dev/null; echo "*/5 * * * * python3 /root/MonitorNodes.py") | crontab -