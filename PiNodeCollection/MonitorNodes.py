#!/usr/bin/python3
import logging
import os
import signal
import subprocess

"""
    Fairly straightforward script to collect information about nearby wireless access points and their clients.
    Intended to be used to send foot traffic data to the backend.

"""



#Represents an instance of a client found using monitor mode on this machine.
class Client:
    def __init__(self,station):
        self.station = station



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
    