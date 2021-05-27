from bluetooth import *
import socket
import subprocess
import time
import os
import glob



import socket

hostMACAddress = "" # The MAC address of a Bluetooth adapter on the server. The server might have multiple Bluetooth adapters.
port = PORT_ANY 
backlog = 1 # nr of connections queued
maxSizeReceived = 1024

serverSocket = BluetoothSocket( RFCOMM )
serverSocket.bind((hostMACAddress,port))
serverSocket.listen(backlog)

uuid = "e0cbf06c-cd8b-4647-bb8a-263b43f0f974"

advertise_service(  serverSocket, "TestServer",
                    service_id = uuid,
                    service_classes = [ uuid, SERIAL_PORT_CLASS ],
                    profiles = [ SERIAL_PORT_PROFILE ], 
#                   protocols = [ OBEX_UUID ] 
                )

print("Listening for connections...")
client_sock, client_info = serverSocket.accept()
print ("Accepted connection from ", client_info)

   
while 1:
    receivedByteArray = client_sock.recv(4096)
    if receivedByteArray:
        receivedString = receivedByteArray.decode("utf-8")
        print(receivedString)
        client_sock.send("Hello! This is a response from the Raspberry Pi!".encode("utf-8"))




