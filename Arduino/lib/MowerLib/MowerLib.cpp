#include "MowerLib.h"
#include <MeAuriga.h>



/*
------------------------------------------------------------------------------------------
----------------------------------- Move related functions -------------------------------
------------------------------------------------------------------------------------------
*/

/*
  Global variables related to move
*/

static MeEncoderOnBoard Encoder_1(SLOT1);
static MeEncoderOnBoard Encoder_2(SLOT2);


void moveSetup(int direction, int speed){
  
  speed=speed / 100.0 * 255;
  int leftSpeed = 0;
  int rightSpeed = 0;

  if(direction == FORWARDS){
    leftSpeed = -speed;
    rightSpeed = speed;
  }else if(direction == BACKWARDS){
    leftSpeed = speed;
    rightSpeed = -speed;
  }else if(direction == RIGHT){
    leftSpeed = -speed;
    rightSpeed = -speed;
  }else if(direction == LEFT){
    leftSpeed = speed;
    rightSpeed = speed;
  }else if(direction == STOP){
    leftSpeed = 0;
    rightSpeed = 0;
  }
  Encoder_1.setTarPWM(leftSpeed);
  Encoder_2.setTarPWM(rightSpeed);
}


void drive() {       
  Encoder_1.loop();
  Encoder_2.loop();
}

/** 

* Updates the position of motor 1, called when getting an interrupt that indecate the motor is running.
  This function was provided by the manufacturer.
*/ 

void isr_process_encoder1(void)
{
  if(digitalRead(Encoder_1.getPortB()) == uint8_t(0)){
    Encoder_1.pulsePosMinus();
  }else{
    Encoder_1.pulsePosPlus();
  }
}


/** 

* Updates the position of motor 1, called when getting an interrupt that indecate the motor is running.
  This function was provided by the manufacturer.
*/ 

void isr_process_encoder2(void)
{
  if(digitalRead(Encoder_2.getPortB()) == uint8_t(0)){
    Encoder_2.pulsePosMinus();
  }else{
    Encoder_2.pulsePosPlus();
  }
}


void motorPositionInterrupt(){  
attachInterrupt(Encoder_1.getIntNum(), isr_process_encoder1, RISING);
attachInterrupt(Encoder_2.getIntNum(), isr_process_encoder2, RISING);
}


void DelayAndDO(float seconds,void (*func)(void)) {
  if(seconds < 0.0){
    seconds = 0.0;
  }
  unsigned long endTime = millis() + seconds * 1000;
  while(millis() < endTime) func();
}


/*
------------------------------------------------------------------------------------------
------------------------------------------Line Sensor related functions-------------------
------------------------------------------------------------------------------------------
*/

/*
  Global variables related to Line Sensor.
*/


MeLineFollower linefollower_9(9);


bool lineSensorBlack(){
  
    int ifBlackLine = (linefollower_9.readSensors() & 3);
    bool blackLine;

    if(ifBlackLine==0)
        blackLine = true;
    
    else
        blackLine = false;

    return blackLine;
}

/*
------------------------------------------------------------------------------------------
--------------------------------------Mower behavoir related -----------------------------
------------------------------------------------------------------------------------------
*/


void drivingLoop(int *state){
        switch (*state)
        {
        case BOUNDARY_CHECK :         //check for the line.
                if (lineSensorBlack())
                        *state = TURN_FROM_BOUNDARY;
                else
                        *state = DRIVE_FORWARD;
                break;
        case DRIVE_FORWARD :         // drive forward.
                moveSetup(FORWARDS, 50 );
                drive();
                // delay?  
                *state = BOUNDARY_CHECK; // check again, or maybe set an interrupt for line sensor?
                break;
        case TURN_FROM_BOUNDARY :         // turn from the line
                // stop
                moveSetup(STOP, 0 );
                DelayAndDO(0.5,drive);
                //back
                moveSetup(BACKWARDS, 50);
                DelayAndDO(0.5,drive);
                //stop
                moveSetup(STOP, 0 );
                DelayAndDO(0.5,drive);
                //right
                moveSetup(RIGHT, 50 );
                DelayAndDO(2.5,drive);

                *state = BOUNDARY_CHECK;
                break;
        
        
        default:
                moveSetup(STOP, 0 );
                //_delay(0.5,drive);
                // what to do in case program ended up here
                break;
        }
}

/*
  Global variables related to 
*/