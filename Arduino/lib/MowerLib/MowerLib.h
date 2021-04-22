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

void moveSetup(int direction, int speed);
void drive();
void motor_position_interrupt();

void _delay(float seconds,void (*func)(void));

/*
    line_sensor related
*/

bool lineSensorBlack(); 

/*
    Mower behavior related.
*/

#define BOUNDARY_CHECK      0
#define DRIVE_FORWARD       1
#define TURN_FROM_BOUNDARY  2

void drivingLoop(int *state);



#endif // header_H