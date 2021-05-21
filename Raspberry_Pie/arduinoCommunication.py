import serial
from time import sleep

#ser = serial.Serial("/dev/ttyS0",9600, timeout=1)
ser = serial.Serial("/dev/ttyS0",115200, timeout=1)
#ser = serial.Serial("/dev/ttyUSB0",115200, timeout=1) 
ser.flush()
#ser = serial.Serial("/dev/ttyS0",9600) 

#s = ""

pastMessagesX = [0,0,0,0,0]
decodedMessagesX =[0,0,0,0,0]



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
    
#m.angle.distance.obstacle
#    decodedMessages = "m.120.10.1"
    #print(decodedMessages[0])
    
    decodedMessagesX = decodedMessages.split(".",4)
    #print(decodedMessagesX[0])
    if decodedMessagesX[0] == 'm':
        
            if decodedMessagesX[1] != pastMessagesX[1]:
                pastMessagesX = decodedMessagesX
                #pastMessagesX[1] = decodedMessagesX[1]
                values = decodedMessages.split(".",4) #splits messages and put into list.
                
                ##if values:
                  #  for x in range(len(values)):
                
                   #     print(values[x])
                #else:
                 #   print("no val")
 
                print("Recieved values")
                print(values[0])
                print(values[1])
                print(values[2])
            

                print(values[3])
                print("end") 
                
                
