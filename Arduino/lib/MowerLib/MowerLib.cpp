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

//static MeBluetooth bluetooth(PORT_2);

void moveSetup(int direction, int speed)
{

  speed = speed / 100.0 * 255;
  int leftSpeed = 0;
  int rightSpeed = 0;

  if (direction == FORWARDS)
  {
    leftSpeed = -speed;
    rightSpeed = speed;
  }
  else if (direction == BACKWARDS)
  {
    leftSpeed = speed;
    rightSpeed = -speed;
  }
  else if (direction == LEFT)
  {
    leftSpeed = -speed;
    rightSpeed = -speed;
  }
  else if (direction == RIGHT)
  {
    leftSpeed = speed;
    rightSpeed = speed;
  }
  else if (direction == STOP)
  {
    leftSpeed = 0;
    rightSpeed = 0;
  }
  Encoder_1.setTarPWM(leftSpeed);
  Encoder_2.setTarPWM(rightSpeed);
}

void drive()
{
  Encoder_1.loop();
  Encoder_2.loop();
}

/** 

* Updates the position of motor 1, called when getting an interrupt that indecate the motor is running.
  This function was provided by the manufacturer.
*/

void isr_process_encoder1(void)
{
  if (digitalRead(Encoder_1.getPortB()) == uint8_t(0))
  {
    Encoder_1.pulsePosMinus();
  }
  else
  {
    Encoder_1.pulsePosPlus();
  }
}

/** 

* Updates the position of motor 1, called when getting an interrupt that indecate the motor is running.
  This function was provided by the manufacturer.
*/

void isr_process_encoder2(void)
{
  if (digitalRead(Encoder_2.getPortB()) == uint8_t(0))
  {
    Encoder_2.pulsePosMinus();
  }
  else
  {
    Encoder_2.pulsePosPlus();
  }
}

void motorPositionInterrupt()
{
  attachInterrupt(Encoder_1.getIntNum(), isr_process_encoder1, RISING);
  attachInterrupt(Encoder_2.getIntNum(), isr_process_encoder2, RISING);
}

void getPos(int * pos1, int *pos2 ){
  *pos1=Encoder_1.getCurPos();
  *pos2=Encoder_2.getCurPos();
}



void DelayAndDO(float seconds, void (*func)(void))
{
  if (seconds < 0.0)
  {
    seconds = 0.0;
  }
  unsigned long endTime = millis() + seconds * 1000;
  while (millis() < endTime)
    func();
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

bool detectedLine()
{

  int ifBlackLine = (linefollower_9.readSensors() & 1) || (linefollower_9.readSensors() & 2);
  bool blackLine;

  if (ifBlackLine == 0)
    blackLine = true;

  else
    blackLine = false;

  return blackLine;
}


/*
------------------------------------------------------------------------------------------
------------------------------------------ultrasonic Sensor related functions-------------
------------------------------------------------------------------------------------------
*/

/*
  Global variables related to ultrasonic Sensor.
*/

MeUltrasonicSensor ultrasonic_10(10);

bool detectedObstical(int dis){

  if(ultrasonic_10.distanceCm()<dis)
    return true;
  else
    return false;

}

/*
------------------------------------------------------------------------------------------
--------------------------------------Bluetooth related -----------------------------
------------------------------------------------------------------------------------------
*/
static bool manual=false;

bool bluetoothReceive(int *direction){
  String receivedString;
  bool flag=false;
  
  if(Serial.available()>0){
    receivedString=Serial.readString();
    flag=true;
  }

  if(flag){
    if(receivedString=="m\r\n")//anualDriving
      manual=true;
    else if(receivedString=="a\r\n")//utoDriving
      manual=false;
    else if(receivedString=="f\r\n")//orward
      *direction=1;
    else if(receivedString=="b\r\n")//ackward
      *direction=2;
    else if(receivedString=="l\r\n")//eft
      *direction=3;
    else if(receivedString=="r\r\n")//ight
      *direction=4;
    else if(receivedString=="s\r\n")//top
      *direction=0;
  }

  return manual;

}

void bluetoothTransmitt(String data){
  
}


/*
------------------------------------------------------------------------------------------
--------------------------------------Mower behavoir related -----------------------------
------------------------------------------------------------------------------------------
*/

int dir=0;
bool autoPi =true;

void drivingLoop(int *state)
{
  
  switch (*state)
  {
  case IDEAL: //check for the line.
    if(bluetoothReceive(&dir))
      *state=MANUAL;
    else
      *state=BOUNDARY_CHECK;
    break;

  case MANUAL: //check for the line.
    if(bluetoothReceive(&dir)){
      moveSetup(dir,SPEED);
      drive();
      *state=MANUAL;
    }
    else
      *state= BOUNDARY_CHECK;
    break;

  case BOUNDARY_CHECK: //check for the line.
    if (detectedLine())
      *state = TURN_FROM_BOUNDARY;
    else
      *state = OBSTICALS_CHECK;
    break;

  case OBSTICALS_CHECK: //check for the line.
    if (detectedObstical(5))
      *state = TURN_FROM_OBSTICAL;
    else
      *state = DRIVE_FORWARD;
    break;  

  case DRIVE_FORWARD: // drive forward.
    moveSetup(FORWARDS, SPEED);
    drive();
    // delay?
    *state = IDEAL; // check again, or maybe set an interrupt for line sensor?
    break;

  case TURN_FROM_BOUNDARY: // turn from the line
    // stop
    moveSetup(STOP, 0);
    DelayAndDO(0.2, drive);
    //back
    moveSetup(BACKWARDS, SPEED);
    DelayAndDO(0.5, drive);
    //stop
    moveSetup(STOP, 0);
    DelayAndDO(0.2, drive);
    //random returns 3 or 4 meaning left or right
    moveSetup(random(3,5), SPEED);
    DelayAndDO(random(5,15)/10.0, drive);
    *state = IDEAL;
    break;
  case TURN_FROM_OBSTICAL: // turn from the line
    // stop
    moveSetup(STOP, 0);
    DelayAndDO(0.2, drive);
    //back
    moveSetup(BACKWARDS, SPEED);
    DelayAndDO(0.5, drive);
    //stop
    moveSetup(STOP, 0);
    DelayAndDO(0.2, drive);
    //random returns 3 or 4 meaning left or right
    moveSetup(random(3,5), SPEED);
    DelayAndDO(random(5,15)/10.0, drive);
    *state = IDEAL;
    break;


  default:
    moveSetup(STOP, 0);
    *state=IDEAL;
    //_delay(0.5,drive);
    // what to do in case program ended up here
    break;
  }
}

/*
  Global variables related to 
*/