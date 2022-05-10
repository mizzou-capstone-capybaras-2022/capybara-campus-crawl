#!/usr/bin/python3
import logging
import logging.handlers
import os
import glob
import sqlalchemy as s
from sqlalchemy.sql.expression import bindparam
from sqlalchemy.ext.automap import automap_base
import signal
import subprocess
from datetime import datetime
import csv
from mac_vendor_lookup import MacLookup
import time
import socket
import json
import psycopg2
import urllib.parse

"""
    Fairly straightforward script to collect information about nearby wireless access points and their clients.
    Intended to be used to send foot traffic data to the backend.

    REMEMBER TO MAKE SURE THAT YOUR DEVICE NAME MATCHES THE ONE IN THIS SCRIPT. Linux can be annoyingly arbitrary so you kind of have to do it manually.
"""

logger = logging.getLogger(__name__)
mac = MacLookup()
mac.update_vendors()

#Monitor mode instructions
#sudo ip link set wlan1 down
#sudo iw wlan1 set monitor none


#return a database connection object
def initialize_database(database_name,password,host,port):
  DB_STR = 'postgresql://{}:{}@{}:{}/{}'.format(
            "capybaradevuser", urllib.parse.quote_plus(password), host, port, database_name
        )

  logger.info("Making database connections...")

  db = s.create_engine(DB_STR, poolclass=s.pool.NullPool)

  return db



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
    
    def isLocalMAC(self):

        assert self.mac is not None

        #Get the first octet for bit math
        firstOctet = self.mac.split('-')[0]

        #print(macAddress)
        #print(firstOctet)
        octetBits = int(firstOctet, 16)

        secondLeastSignificantBit = bin(octetBits >> 1)[-1]
        #second least significant bit of the first octet is what determins locality

        if secondLeastSignificantBit == '1':
            return True
    

        return False

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
    
    def isLocalMAC(self):

        assert self.mac is not None

        #Get the first octet for bit math
        firstOctet = self.mac.split('-')[0]

        #print(macAddress)
        #print(firstOctet)
        octetBits = int(firstOctet, 16)

        secondLeastSignificantBit = bin(octetBits >> 1)[-1]
        #second least significant bit of the first octet is what determins locality

        if secondLeastSignificantBit == '1':
            return True
    

        return False

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


        
def extractClientData(csvFileClientsString):
    clients = []
    #now = datetime.now()
    
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
        
        if newClient.isLocalMAC():
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

    print(len(accessPoints))
    print(len(clients))

    return clients


#Start of script
if __name__ == "__main__":
    logger.setLevel(os.environ.get("LOGLEVEL", "INFO"))

    #For database keeping track
    hostname = socket.gethostname()
    #Assign a unique number to this device manually
    nodeValue = 1#int(hostname[-1])

    dbPass = None
    dbHost = None

    with open('/etc/pi_env.json') as json_file:
        data = json.load(json_file)
        dbPass = data['PiNodeDBPass']
        dbHost = data['PiNodeDBHost']

    #This should be consistant
    dbName = "capybara_db"
    dbPort = 5432#os.environ.get('PiNodeDBPort')

    db = initialize_database(dbName,dbPass,dbHost,dbPort)

    #Get a python object that corresponds to the pi metrics table.
    metadata = s.MetaData()
    print(metadata.tables.keys())
    metadata.reflect(db, only=["PIMetrics"])

    Base = automap_base(metadata=metadata)
    Base.prepare()

    PIMetricsTable = Base.classes["PIMetrics"].__table__

    os.chdir('/tmp')
    cwd = os.getcwd()

    
    handler = logging.handlers.WatchedFileHandler(os.environ.get("LOGFILE", "/var/log/MonitorNode.log"))
    formatter = logging.Formatter(logging.BASIC_FORMAT)
    handler.setFormatter(formatter)
    logger.addHandler(handler)

    

    logger.info("Starting airodump-ng...")
    redirectOutput = open("/dev/null",'w')
    airdumpProcess = subprocess.Popen("exec airodump-ng -w output --output-format csv wlp0s20f0u1",stdout=redirectOutput,shell=True)

    #Let run for a minute
    time.sleep(60)


    airdumpProcess.kill()
    redirectOutput.close()
    
    #Split weird default csv created by airdump-ng into two for access points and clients.
    # parseAirdumpCsv('output-01.csv')

    path = '/tmp/*.csv'

    intensity = 0
    for filename in glob.glob(path):
        
        parsed = parseAirdumpCsv(filename)
        #print(parsed)
        intensity += len(parsed)
        

        os.remove(filename) # Don't keep temp info
    
    #insert to db
    toInsert = {
        "NodeID" : nodeValue,
        "Time" : datetime.now(),
        "Intensity" : intensity
    }  
    db.execute(
        PIMetricsTable.insert().values(toInsert))


