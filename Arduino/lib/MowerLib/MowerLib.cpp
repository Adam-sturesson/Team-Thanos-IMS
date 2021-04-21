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


/** 

* Sets up the direction and the speed of the motors.  

* @param   direction    the overall direction of the mower.

* @param   speed        the speed of the mower.

*/ 

void moveSetup(int direction, int speed)
{
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


/** 

* Starts both motors by callig Encoder_1.loop().
  Encoder_1.loop() :
   deliverd by the manufacturer,
   runs respective motor according setup no more than 1 time every 40 ms.

*/ 

void drive() {       

  Encoder_1.loop();
  Encoder_2.loop();
}

/** 

* Updates the position of motor 1, called when getting an interrupt that indecate the motor is running.

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

*/ 

void isr_process_encoder2(void)
{
  if(digitalRead(Encoder_2.getPortB()) == uint8_t(0)){
    Encoder_2.pulsePosMinus();
  }else{
    Encoder_2.pulsePosPlus();
  }
}

/** 

* Excutes isr_process_encoder1 and isr_process_encoder2 when the an interrupt from respective motor occure.
  This make the continues update of motors position possible.

*/ 

void motor_position_interrupt(){  
attachInterrupt(Encoder_1.getIntNum(), isr_process_encoder1, RISING);
attachInterrupt(Encoder_2.getIntNum(), isr_process_encoder2, RISING);
}

/** 

* Excuting a function for certin period of time what allows to delay without blocking the program. 

* 

* @param   seconds the delay time.

* @param   func    the function to be excuted.

*/ 
void _delay(float seconds,void (*func)(void)) {
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


/** 

* Detects black colored lines.

* @returns      blackLine which is true if the line is black and false otherwise.

*/ 

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
------------------------------------------------------------------------------------------
-------------------------------------------------------------
------------------------------------------------------------------------------------------
*/

/*
  Global variables related to 
*/