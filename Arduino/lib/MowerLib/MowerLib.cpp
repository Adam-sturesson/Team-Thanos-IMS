#include "MowerLib.h"
#include <MeAuriga.h>



/*
------------------------------------------------------------------------------------------
----------------------------------- Move related functions -------------------------------
------------------------------------------------------------------------------------------
*/

static MeEncoderOnBoard Encoder_1(SLOT1);
static MeEncoderOnBoard Encoder_2(SLOT2);

void move(int direction, int speed)
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
  }
  Encoder_1.setTarPWM(leftSpeed);
  Encoder_2.setTarPWM(rightSpeed);
}

void _loop() {
  Encoder_1.loop();
  Encoder_2.loop();
}

/**/
void isr_process_encoder1(void)
{
  if(digitalRead(Encoder_1.getPortB()) == 0){
    Encoder_1.pulsePosMinus();
  }else{
    Encoder_1.pulsePosPlus();
  }
}
void isr_process_encoder2(void)
{
  if(digitalRead(Encoder_2.getPortB()) == 0){
    Encoder_2.pulsePosMinus();
  }else{
    Encoder_2.pulsePosPlus();
  }
}



void motor_position_interrupt(){  
attachInterrupt(Encoder_1.getIntNum(), isr_process_encoder1, RISING);
attachInterrupt(Encoder_2.getIntNum(), isr_process_encoder2, RISING);
}


void _delay(float seconds) {
  if(seconds < 0.0){
    seconds = 0.0;
  }
  long endTime = millis() + seconds * 1000;
  while(millis() < endTime) _loop();
}


/*
------------------------------------------------------------------------------------------
------------------------------------------Line Sensor related functions-------------------
------------------------------------------------------------------------------------------
*/



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
------------------------------------------------------------------------------------------
-------------------------------------------------------------
------------------------------------------------------------------------------------------
*/