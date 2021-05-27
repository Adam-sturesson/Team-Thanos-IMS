#include "MowerLib.h"
#include <MeAuriga.h>



static MowerIndicators mower;

/*
------------------------------------------------------------------------------------------
----------------------------------- MOTOR  -----------------------------------------------
------------------------------------------------------------------------------------------
*/

/**
* Global variables for move.
*/
static MeEncoderOnBoard Encoder_1(SLOT1);
static MeEncoderOnBoard Encoder_2(SLOT2);


/**
* move functions.
*/

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
* Updates the position of motor 2, called when getting an interrupt that indecate the motor is running.
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

int getDistance(){
    return (((-1*Encoder_1.getCurPos()) + Encoder_2.getCurPos())/2)*PI*WHEEL_RADIUS/180;
}

void resetDistance(){
    Encoder_1.setPulsePos(0);
    Encoder_2.setPulsePos(0);
}

/*
------------------------------------------------------------------------------------------
-----------------------------Line Sensor -------------------------------------------------
------------------------------------------------------------------------------------------
*/

/**
* Global variables for Line Sensor.
*/
MeLineFollower linefollower_9(9);

/**
* Line Sensor functions.
*/

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
----------------------------ultrasonic Sensor functions-----------------------------------
------------------------------------------------------------------------------------------
*/

/**
* Global variables related to ultrasonic Sensor.
*/
MeUltrasonicSensor ultrasonic_10(10);

/**
* ultrasonic Sensor functions.
*/

bool detectedObstacal(int dis){
    if(ultrasonic_10.distanceCm()<dis)
        return true;
    else
        return false;
}

/*
------------------------------------------------------------------------------------------
----------------------------GYROSCOPE Sensor functions-----------------------------------
------------------------------------------------------------------------------------------
*/

/**
* Global variables related to gyro.
*/

MeGyro gyro(0,0x69);

/**
* gyro functions.
*/

void gyroSetup(){
    gyro.begin();
}

int gyroRun(){
    gyro.update();
    return gyro.getAngle(3);
}

/*
------------------------------------------------------------------------------------------
--------------------------------------communication --------------------------------------
------------------------------------------------------------------------------------------
*/

/*
* Global variables related to communication.
*/
  MeSerial RPI_serial(PORT_5);

/*
* communication functions.
*/

bool bluetoothReceive(){
    char receivedCommand;
    bool flag=false;
  
    if(Serial.available()>0){
        receivedCommand=Serial.read();
        flag=true;
    }
    if(flag){
        if(receivedCommand=='m')//anualDriving
            mower.mode=MANUAL;
        else if(receivedCommand=='a')//utoDriving
            mower.mode=AUTO;
        else if(receivedCommand=='f')//orward
            mower.direction=1;
        else if(receivedCommand=='b')//ackward
            mower.direction=2;
        else if(receivedCommand=='l')//eft
            mower.direction=3;
        else if(receivedCommand=='r')//ight
            mower.direction=4;
        else if(receivedCommand=='s')//top
            mower.direction=0;
        else if(receivedCommand=='p'&&mower.routing==false){
            mower.routing=true;
            RPI_serial.println("p."+String(mower.angle)+"."+String(mower.distance)+".0.");
            //gyroSetup();
            //resetDistance();
        }
        else if(receivedCommand=='d'&&mower.routing==true)
        {
            mower.routing=false;
            RPI_serial.println("d."+String(mower.angle)+"."+String(mower.distance)+".0."); 
        }
            
    }
    return mower.mode;
}

void rpiSerialSetup(){
    RPI_serial.begin(115200);
}

/*
------------------------------------------------------------------------------------------
------------------------------------- Mower behaviour related -----------------------------
------------------------------------------------------------------------------------------
*/

/*
* Global variables related to Mower behaviour.
*/

int turningTimes[5]={500,750,1000,1250,1500};


/*
* Mower behaviour functions.
*/

void drivingLoop(){  
    switch (mower.state){
    case IDEAL: 
      if(bluetoothReceive()==MANUAL)
          mower.state=UPDATE_BT_COM;
      else
          mower.state=CHECK_BOUNDARY;
      break;

    case UPDATE_BT_COM: 
      moveSetup(mower.direction,SPEED);
      mower.state=DRIVE;
      break;

    case CHECK_BOUNDARY: 
      if (detectedLine()==true&&mower.boundaryDetected==false){
          mower.state = SET_BACKWARDS;
          mower.turning_stage=TURN_OFF;
          mower.boundaryDetected=true;      
      }
      else{
          mower.state = CHECK_OBSTACLE;
         
      }
        
      break;

    case CHECK_OBSTACLE: 
      if (detectedObstacal(5)&&mower.obsticalDetected==false){
          mower.state = SET_BACKWARDS;
          mower.turning_stage=TURN_OFF;
          mower.obsticalDetected=true;      
      }
      else if(mower.turning_stage!=TURN_OFF){
          mower.state=mower.turning_stage;
      }      
      else{
          mower.state = SET_FORWARDS;
          mower.obsticalDetected=false;
      }      
      break;  

    case SET_FORWARDS: 
      moveSetup(FORWARDS, SPEED);
      mower.state = DRIVE;
      break;

    case SET_BACKWARDS:
      if(mower.turning_stage==TURN_OFF){
          mower.turning_stage=SET_BACKWARDS;
          mower.wait_until_ms=millis()+TURN_BACK_TIME;
          moveSetup(BACKWARDS,SPEED);

          mower.distance=getDistance();

          if(mower.obsticalDetected==true && mower.routing==true){
            RPI_serial.println("m."+String(mower.angle)+"."+String(mower.distance)+".1.");
          }
          if(mower.boundaryDetected && mower.routing==true){
            RPI_serial.println("m."+String(mower.angle)+"."+String(mower.distance)+".0.");
          }
          
      }

      if(millis()<mower.wait_until_ms)
          mower.state=DRIVE;
      else
          mower.state=SET_STOP;
      break;

    case SET_STOP:
      if(mower.turning_stage==SET_BACKWARDS){
          mower.turning_stage=SET_STOP;
          mower.wait_until_ms=millis()+TURN_STOP_TIME;
          moveSetup(STOP,0);
           mower.boundaryDetected=false;
      }
      if(millis()<mower.wait_until_ms)
          mower.state=DRIVE;
      else
          mower.state=SET_RIGHT_LEFT;
      break;

    case SET_RIGHT_LEFT:
      if(mower.turning_stage==SET_STOP){
          mower.turning_stage=SET_RIGHT_LEFT;
          mower.wait_until_ms=millis()+randomTurningTime();
          moveSetup(random(3,5),SPEED);
      }
      if(millis()<mower.wait_until_ms)
          mower.state=DRIVE;
      else{
          mower.state=IDEAL;
          mower.turning_stage=TURN_OFF;
          mower.angle=gyroRun();
          resetDistance();
      }      
      break;

    case DRIVE:
      drive();
      mower.state=IDEAL;
      break;

    default:
      moveSetup(STOP, 0);
      mower.state=IDEAL;
      break;
    }
}


int randomTurningTime(){
    return turningTimes[random(0,5)];
}
