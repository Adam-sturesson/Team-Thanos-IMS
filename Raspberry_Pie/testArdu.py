import serial
from time import sleep

ser = serial.Serial("/dev/ttyS0",9600, timeout=1)
#ser = serial.Serial("/dev/ttyUSB0",115200, timeout=1) 
ser.flush()
#ser = serial.Serial("/dev/ttyS0",9600) 

#s = ""
while True:

    #if ser.in_waiting > 0:
        #s = ser.readline().decode('utf-8').rstrip()
        #s = ser.readline()
        #s = ser.read()
        #print (s)

    RD = ser.read()
    sleep(0.03)
    DL = ser.inWaiting()
    RD += ser.read(DL)
    
    
    #print(RD)
    decodedMessages = RD.decode("utf-8") #Convert messages.
    pastMessages = " "
#m.angle.distance.obstacle
#    decodedMessages = "m.120.10.1"
    print(decodedMessages[0])
    #print(decodedMessages[0])
