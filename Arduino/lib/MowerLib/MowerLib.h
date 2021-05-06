#ifndef MowerLib_H
#define MowerLib_H


#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>



struct MowerIndicators{
    int mode; // auto or man
    int speed; 
    int direction;

};

/*
    move related
*/

#define STOP        0
#define FORWARDS    1
#define BACKWARDS   2
#define LEFT        3
#define RIGHT       4

/** 

* Sets up the direction and the speed of the motors.  

* @param   direction    the overall direction of the mower.

* @param   speed        a procentage that indicate the motor speed.

*/ 

void moveSetup(int direction, int speed);

/** 

* Starts both motors by callig Encoder_1.loop().
  Encoder_1.loop() :
   deliverd by the manufacturer,
   runs respective motor according setup no more than 1 time every 40 ms.

*/ 

void drive();

/** 

* Excutes isr_process_encoder1 and isr_process_encoder2 when the an interrupt from respective motor occure.
  This make the continues update of motors position possible.

*/ 

void motorPositionInterrupt();

/** 

* Excuting a function for certin period of time what allows to delay without blocking the program. 

* 

* @param   seconds the delay time.

* @param   func    the function to be excuted.

*/ 

void DelayAndDO(float seconds,void (*func)(void));

/*
    line_sensor related
*/

/** 

* Detects black colored lines.

* @returns      blackLine which is true if the line is black and false otherwise.

*/ 

bool lineSensorBlack();

/*
    Bluetooth related.
*/

/** 

* Reads received data from bluetooth via Serial.

* @returns   received data as a String.

*/ 

String bluetoothReceive();

/** 

* sends data to bluetooth via Serial.

* @param   data data as a String to be sent.

*/ 

void bluetoothTransmitt(String data);

/*
    Mower behavior related.
*/

#define BOUNDARY_CHECK      0
#define DRIVE_FORWARD       1
#define TURN_FROM_BOUNDARY  2

/** 

* Steers the beahvior of the mower.
* 

* @param   *state the start state fo the machine

*/

void drivingLoop(int *state);

void getPos(int * pos1, int *pos2 );

#endif // header_H