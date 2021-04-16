#include "LineSensor.h"
#include "MeAuriga.h"


MeLineFollower linefollower_9(9);

bool RightSensorBlack(){

    //right (sensor 1) - black    
    int ifBlackLine = (linefollower_9.readSensors() & 1);
    bool blackLine;

    if(ifBlackLine==0)
        blackLine = true;
    
    else
        blackLine = false;

    return blackLine;
}
/*
LeftSensorBlack(){
    //left (sensor 2) - black
    (linefollower_9.readSensors() & 2)==0;
}

RightSensorWhite(){
    //right (sensor 1) - white
    (linefollower_9.readSensors() & 1)==1;
}

LeftSensorWhite(){
    //left (sensor 2) - white
    (linefollower_9.readSensors() & 2)==2;
}

*/
                




