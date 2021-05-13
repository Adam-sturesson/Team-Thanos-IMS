#ifndef MowerLib_H
#define MowerLib_H


#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>

#define BOUNDARY_CHECK      0
#define DRIVE_FORWARD       1
#define TURN_FROM_BOUNDARY  2
#define OBSTICALS_CHECK     3
#define TURN_FROM_OBSTACAL  4
#define MANUEL              5
#define AUTO                6
#define IDEAL               7
#define RECEIVE_BT          8

#define STOP               0
#define FORWARDS           1
#define BACKWARDS          2
#define LEFT               3
#define RIGHT              4
#define SPEED              40


struct MowerIndicators{
    bool Manuel     =false; // auto or man
    int speed       =SPEED; 
    int direction   =STOP;
    int state       =IDEAL;
    int angle       =0;
    int distance    =0;
};

/*
                                                MOTOR RELATED
*/



/** 

* Sets up the direction and the speed of the motors.  

* @param   direction    the overall direction of the mower.

* @param   speed        a percentage that indicates the motor speed.

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

* return the distanc run by the motor

* @returns      distance.

*/ 
int getDistance( );

/** 

* resets the distance

*/ 
void resetDistance();




/*
                                                LINE-SENSOR
*/

/** 

* Detects black colored lines.

* @returns      blackLine which is true if the line is black and false otherwise.

*/ 
bool detectedLine();

/*
                                                ULTRASONIC
*/

/** 

*  Reads data from ultrasonic sensor.

* @returns      true if there was an obstical within "distance" cm form the sensor

*/
bool detectedObstacal(int distance);

/*
                                                GYROSCOPE
*/


/** 

* Sets up gyro according to manufacturers recommendations.

*/ 
void gyroSetup();

/** 

* Reads turning angle 

* @returns   an angel between 0 and +/- 180, there + is right.

*/ 
int gyroRun();

/*
                                                BLUETOOTH
*/

/** 

* Reads received data from bluetooth via Serial.

* @param   distance    distance value to obstical.

* @returns   received data as a String.

*/ 
bool bluetoothReceive();


/** 

* sends data to bluetooth via Serial.

* @param   data data as a String to be sent.

*/ 
void bluetoothTransmitt(String data);

/*
                                                MOWER BEHAVIOUR
*/


/** 

* Steers the beahvior of the mower.

* @param   *state the start state fo the machine

*/
void drivingLoop();

/** 

* Excuting a function for certin period of time what allows to delay without blocking the program. 

* 

* @param   seconds the delay time.

* @param   func    the function to be excuted.

*/ 
void delayAndDO(float seconds,void (*func)(void));



#endif // header_H