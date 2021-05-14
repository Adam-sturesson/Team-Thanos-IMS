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
    
    
    
    decodedMessages = RD.decode("utf-8") #Convert messages.
    pastMessages = " "

    decodedMessages = "m.120.10"
    if decodedMessages[0] == 'm':
        if decodedMessages == pastMessages:
            #Do nothing since the values are the same.
            print("old value")
            
        else:
            pastMessages = decodedMessages
            values = decodedMessages.split(".",2) #splits messages and put into list.
            
            if values:
                print("hahahahaha")
                for x in range(len(values)):
                    print(values[x])
            else:
                print("no val")
                
            #print("Recieved values")
            #print(values[0])
            #print(values[1])
            #print(values[2])
            #print("end") 
        
