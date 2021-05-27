import pyrebase
import time
import math
import serial
from time import sleep




############################################CLASSES#######################################################


# This is the raw, not yet converted, movement data sent from the arduino
class MovementData:
    def __init__(self, angle, distanceFromPrevPos, avoidedObstacle, previousPosition):
        self.previousPosition = previousPosition
        self.distanceFromPrevPos = distanceFromPrevPos
        self.angle = angle
        self.avoidedObstacle = avoidedObstacle
        
# This is a position which has been converted into x and y
class Position:
    def __init__ (self, x, y):
        self.x = x
        self.y = y
    def getJSONFormat(self):
        return {
            "x" : self.x,
            "y" : self.y
        }
        

############################################FUNCTIONS#######################################################
    
def convertMovementDataToMowerPosition(movementData):
    radians = math.radians(movementData.angle)
    x = math.floor(math.cos(radians) * movementData.distanceFromPrevPos + movementData.previousPosition.x)
    y = math.floor(math.sin(radians) * movementData.distanceFromPrevPos + movementData.previousPosition.y)
    return Position(x,y)
    
def convertMovementDataToObstaclePosition(movementData):
    radians = math.radians(movementData.angle)
    x = math.floor(math.cos(radians) * movementData.distanceFromPrevPos + movementData.previousPosition.x)
    y = math.floor(math.sin(radians) * movementData.distanceFromPrevPos + movementData.previousPosition.y)
    return Position(x,y)

def addRouteInDb():
    db.child("Routes").child(routeStartTime).child("mowerPositions").set(mowerPositions)
    db.child("Routes").child(routeStartTime).child("obstaclePositions").set(obstaclePositions)
    print("db check\n")
    print(routeStartTime)

def resetData():
    previousPosition.x = 0
    previousPosition.y = 0
    mowerPositions.clear()
    obstaclePositions.clear()



############################################MAIN CODE#######################################################



# Variables
mowerPositions = []
obstaclePositions = []
currentRouteStartTime = ""
previousPosition = Position(0,0)
pastMessagesX = [0,0,0,0,0]
decodedMessagesX =[0,0,0,0,0]
ser = serial.Serial("/dev/ttyS0",115200,timeout=1)
ser.flush()

print("Connecting to firebase...\n")

# Connect to firebase
config = {
  "apiKey": "AAAActQSqd0:APA91bHYXP7Y1PJGv0moMGKLQJQI87fHufCP5mv8tx3pNZxRQgJxRPkFnwwqvRTCBpMsRKIy42Ci3aq3C5d9yM2JxzaO-uEsTlK-_tZHLhXCCrhYlWQ2VqzWQj58oKQA24sk86pUNEUj",
  "authDomain": "ims-thanos.firebaseapp.com",
  "databaseURL": "https://ims-thanos-default-rtdb.europe-west1.firebasedatabase.app/",
  "storageBucket": "ims-thanos.appspot.com"
}
firebase = pyrebase.initialize_app(config)
db = firebase.database()
#loopVar = 0

print("Connected to firebase \n")

print("Starting loop...\n")

# Receive commands from Arduino continiously 
while True:

    #routeStartTime = time.asctime(time.localtime(time.time()))
    RD = ser.read()
    sleep(0.03)
    DL = ser.inWaiting()
    RD += ser.read(DL)
  
    decodedMessages = RD.decode("utf-8") #Convert messages.
    #m.angle.distance.obstacle
    # decodedMessages = "m.120.10.1"
    #print(decodedMessages[0])
    decodedMessagesX = decodedMessages.split(".",4)

    if decodedMessagesX[0] == 'm':   #MOVEMENT DATA
        if decodedMessagesX[1] != pastMessagesX[1]:
            pastMessagesX = decodedMessagesX
            values = decodedMessagesX #splits messages and put into list.
            
            if values:
                movementData = MovementData(int(values[1]), int(values[2]), int(values[3]), previousPosition)
                currentPosition = convertMovementDataToMowerPosition(movementData)
                mowerPositions.append(currentPosition.getJSONFormat())
                if movementData.avoidedObstacle:
                    obstaclePosition = convertMovementDataToObstaclePosition(movementData)
                    obstaclePositions.append(obstaclePosition.getJSONFormat())
                previousPosition = currentPosition
                
                if values:
                    for x in range(len(values)):
                
                        print(values[x])
                
                print("Inserted position to list")
               # loopVar += 1
                #if loopVar == 5:
                 #   addRouteInDb()
                  #  break
            else:
                print("No values")
                
    elif decodedMessagesX[0] == 'p': #Start route
        routeStartTime = time.asctime(time.localtime(time.time()))
        print("Start route")
        print(routeStartTime)
        
    elif decodedMessagesX[0] == 'd':  #Stop route
        print("stop route")
        addRouteInDb()
        resetData()
                
    
   
      

   
    
