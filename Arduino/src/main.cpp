
#include "MowerLib.h"


/* Defines */


/* global variables declation */
 int speed =50;
 int dir=1;


 
void setup() {
        TCCR1A = _BV(WGM10);
        TCCR1B = _BV(CS11)  | _BV(WGM12);
        TCCR2A = _BV(WGM21) | _BV(WGM20);
        TCCR2B = _BV(CS21);

        void motor_position_interrupt();

        moveSetup(dir, speed / 100.0 * 255);

}

void loop() {
        if (RightSensorBlack()){        // turn right
// stop
                moveSetup(1, 0 / 100.0 * 255);
                _delay(0.5,drive);
//back
                moveSetup(2, 50 / 100.0 * 255);
                _delay(0.5,drive);
//stop
                moveSetup(1, 0 / 100.0 * 255);
                _delay(0.5,drive);
//right
                moveSetup(4, 50 / 100.0 * 255);
                _delay(0.5,drive);

        }else{
                moveSetup(1, 50 / 100.0 * 255);
                drive();
    }
}