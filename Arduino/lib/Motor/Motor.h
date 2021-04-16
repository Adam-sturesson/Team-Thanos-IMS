#ifndef Motor_H
#define Motor_H



#include <Arduino.h>



void move(int direction, int speed);
void _loop();
void motor_position_interrupt();
void _delay(float seconds);

#endif // Motor_H