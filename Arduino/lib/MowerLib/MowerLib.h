#ifndef MowerLib_H
#define MowerLib_H


#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>

#define IDEAL               0
#define UPDATE_BT_COM       1
#define CHECK_BOUNDARY      2
#define CHECK_OBSTACLE      3
#define SET_FORWARDS        4
#define SET_BACKWARDS       5
#define SET_RIGHT_LEFT      6
#define SET_STOP            7
#define DRIVE               8

#define MANUAL              0
#define AUTO                1

#define TURN_OFF            0

#define TURN_BACK_TIME      500
#define TURN_STOP_TIME      500
#define TURN_R_L_TIME       500

#define STOP                0
#define FORWARDS            1
#define BACKWARDS           2
#define LEFT                3
#define RIGHT               4
#define SPEED               40

#define WHEEL_RADIUS        2


struct MowerIndicators{
    bool mode                    = AUTO; // auto or man
    int speed                    = SPEED;
    int direction                = STOP;
    int state                    = IDEAL;
    int angle                    = 0;
    int distance                 = 0;
    int turning_stage            = TURN_OFF;
    unsigned long wait_until_ms  = 0;
    int turn_l_r_wait            = 0;
};

/*
                                                MOTOR RELATED
*/



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

/** 

* Executes a SoftwareSerial.begin() function with 9600 baudrate.

*/ 
void rpiSerialSetup();

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

/** 

* choose one random value of the value array turningTimes[]. 

* 

* @returns   one of 5 predefined values of waitin time.

*/ 
int randomTurningTime();



#endif // header_H