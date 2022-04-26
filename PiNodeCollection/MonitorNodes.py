#!/usr/bin/python3
import logging
import logging.handlers
import os
import signal
import subprocess
from datetime import datetime
import csv
from mac_vendor_lookup import MacLookup

"""
    Fairly straightforward script to collect information about nearby wireless access points and their clients.
    Intended to be used to send foot traffic data to the backend.

"""

logger = logging.getLogger(__name__)
mac = MacLookup()
mac.update_vendors()

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
    
    #apReader = csv.reader(csvFileAPString,delimiter=',')
    lines = csvFileAPString.split("\\r\\n")


    #print(csvFileAPString)
    for row in lines:
        record = row.split(',')

        #print(list(record))
        #print(row)
        if len(list(record)) < 14:
            logger.error(f'Not enough columns for access points!: {row}')
            continue

        record = list(record)
        #Skip non numeral header
        try:
            int(record[8])
        except:
            continue

        firstSeen = record[1]
        lastSeen = record[2]
        channel = record[3]
        power = record[8]
        
        
        
        #mac = record[0]
        newAccessPoint = AccessPoint(record[0].replace(":", "-"),
                                    firstSeen, lastSeen,
                                    channel=channel,
                                    speed=record[4],
                                    privacy=record[5],
                                    authentication=record[7],
                                    power=int(record[8]),
                                    name=record[13]
                                    )

        #print(newAccessPoint.mac)
        accessPoints.append(newAccessPoint)
    
    return accessPoints

def isLocalMAC(macAddress):
    #Get the first octet for bit math
    firstOctet = macAddress.split('-')[0]

    #print(macAddress)
    #print(firstOctet)
    octetBits = int(firstOctet, 16)

    secondLeastSignificantBit = bin(octetBits >> 1)[-1]
    #second least significant bit of the first octet is what determins locality

    if secondLeastSignificantBit == '1':
        return True
    

    return False
        
def extractClientData(csvFileClientsString):
    clients = []
    now = datetime.now()
    
    lines = csvFileClientsString.split("\\r\\n")

    for row in lines:
        record = row.split(',')

        if len(list(record)) < 7:
            logger.error(f'Not enough columns for clients!: {row}')
            continue

        
        firstSeen = record[1]
        lastSeen = record[2]
        packets = record[4]
        power = record[3]
        
        
        macAddr = record[0]
        newClient = Client(record[0].replace(":", "-"),#mac
                                    record[5], #bssid
                                    lastSeen,
                                    packets=packets,
                                    probes=record[6],
                                    power=int(record[3])
                                    )
        
        if isLocalMAC(newClient.mac):
            newClient.organization = "LOCAL"
        else:
            newClient.organization = mac.lookup(macAddr)


        clients.append(newClient)
    
    return clients


def parseAirdumpCsv(filename):
    with open(filename, 'rb') as file:
        wholeCSV = file.read()
    

    #Split into string for clients and string for AP

    parts = str(wholeCSV).split('Station MAC, First time seen, Last time seen, Power, # packets, BSSID, Probed ESSIDs')

    apString = parts[0]

    clientString = parts[1]

    accessPoints = extractAccessPointData(apString)

    clients = extractClientData(clientString)

    #print(len(accessPoints))
    #print(len(clients))

    return


#Start of script
if __name__ == "__main__":
    logger.setLevel(os.environ.get("LOGLEVEL", "INFO"))

    
    handler = logging.handlers.WatchedFileHandler(os.environ.get("LOGFILE", "/var/log/MonitorNode.log"))
    formatter = logging.Formatter(logging.BASIC_FORMAT)
    handler.setFormatter(formatter)
    logger.addHandler(handler)

    

    logger.info("Starting airodump-ng...")
    redirectOutput = open("/dev/null",'w')
    airdumpProcess = subprocess.Popen("exec airodump-ng -w output --output-format csv wlan1",stdout=redirectOutput,shell=True)



    airdumpProcess.kill()
    redirectOutput.close()
    
    #Split weird default csv created by airdump-ng into two for access points and clients.
    