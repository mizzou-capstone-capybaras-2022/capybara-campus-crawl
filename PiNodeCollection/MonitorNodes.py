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

logger = logging.getLogger(__name__)

#Monitor mode instructions
#sudo ip link set wlan1 down
#sudo iw wlan1 set monitor none



#Represents an instance of a client found using monitor mode on this machine.
class Client:
    def __init__(self,mac,bssid,seen,station=None,power=None,packets=None,probes=None,organization=None):
        self.mac = mac
        self.bssid = bssid
        self.firstSeen = seen
        self.lastSeen = seen
        self.power = power
        self.packets = packets
        self.probes = probes
        self.organization = organization

#Represents an access point found using monitor mode.
class AccessPoint:
    def __init__(self,mac,firstSeen,lastSeen,channel=None,power=None,packets=None,probes=None,organization=None,speed=None,privacy=None,authentication=None,name=None):
        self.mac = mac
        self.firstSeen = firstSeen
        self.lastSeen = lastSeen
        self.power = power
        self.packets = packets
        self.probes = probes
        self.organization = organization
        self.channel = channel
        self.speed = speed
        self.privacy = privacy
        self.authentication = authentication
        self.name = name

def extractAccessPointData(csvFileAPString):
    accessPoints = []
    now = datetime.now()
    
    apReader = csv.reader(csvFileAPString,delimeter=',',quotechar='|')
    
    for row in apReader:
        if len(row) < 14:
            logger.error(f'Not enough columns for access points!: {row}')
            continue
        
        firstSeen = row[1]
        lastSeen = row[2]
        channel = row[3]
        power = row[8]
        
        
        #mac = row[0]
        newAccessPoint = AccessPoint(row[0].replace(":", "-"),
                                    firstSeen, lastSeen,
                                    channel=channel,
                                    speed=row[4],
                                    privacy=row[5],
                                    authentication=row[7],
                                    power=int(record[8]),
                                    name=row[13]
                                    )

        accessPoints.append(newAccessPoint)
    
    return accessPoints

def isLocalMAC(macAddress):
    #Get the first octet for bit math
    firstOctet = macAddress.split(':')[0]

    octetBits = int(firstOctet, 16)

    print(bin(octetBits))

    secondLeastSignificantBit = bin(octetBits >> 1)[-1]
    print(bin(octetBits >> 1))
    print(secondLeastSignificantBit)
    #second least significant bit of the first octet is what determins locality

    if secondLeastSignificantBit == '1':
        return True
    

    return False
        


#Start of script
if __name__ == "__main__":
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
    