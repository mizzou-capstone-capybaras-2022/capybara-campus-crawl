#!/usr/bin/python3
import logging
import os
import signal
import subprocess
from datetime import datetime
import csv

"""
    Fairly straightforward script to collect information about nearby wireless access points and their clients.
    Intended to be used to send foot traffic data to the backend.

"""

#Monitor mode instructions
#sudo ip link set wlan1 down
#sudo iw wlan1 set monitor none



#Represents an instance of a client found using monitor mode on this machine.
class Client:
    def __init__(self,mac,seen,station=None,power=None,packets=None,probes=None,organization=None):
        self.mac = mac
        self.firstSeen = seen
        self.lastSeen = seen
        self.power = power
        self.packets = packets
        self.probes = probes
        self.organization = organization

#Represents an access point found using monitor mode.
class AccessPoint:
    def __init__(self,mac,bssid,seen,station=None,power=None,packets=None,probes=None,organization=None):
        self.mac = mac
        self.bssid = bssid
        self.firstSeen = seen
        self.lastSeen = seen
        self.power = power
        self.packets = packets
        self.probes = probes
        self.organization = organization

def extractAccessPointData(csvFile):
    timestringExampleFormat = "2006-01-02 15:04:05"
    now = datetime.now()
    
    #with open('outputTest')


#Start of script
if __name__ == "__main__":
    logger = logging.getLogger(__name__)
    logger.setLevel(os.environ.get("LOGLEVEL", "INFO"))

    with logging as l:
        handler = l.handlers.WatchedFileHandler(os.environ.get("LOGFILE", "/var/log/MonitorNode.log"))
        formatter = l.Formatter(logging.BASIC_FORMAT)
        handler.setFormatter(formatter)
        logger.addHandler(handler)

    logger.info("Starting airodump-ng...")
    redirectOutput = open("/dev/null",'w')
    airdumpProcess = subprocess.Popen("exec airodump-ng -w output --output-format csv wlan1",stdout=redirectOutput,shell=True)



    airdumpProcess.kill()
    redirectOutput.close()
    
    #Split weird default csv created by airdump-ng into two for access points and clients.
    