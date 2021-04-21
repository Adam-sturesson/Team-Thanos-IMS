#ifndef MowerLib_H
#define MowerLib_H


#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>


/*
move related
*/

#define STOP        0
#define FORWARDS    1
#define BACKWARDS   2
#define RIGHT       3
#define LEFT        4

void move(int direction, int speed);
void _loop();
void motor_position_interrupt();
void _delay(float seconds);

/*
line_sensor related
*/

bool RightSensorBlack(); 

/*

*/



#endif // header_H