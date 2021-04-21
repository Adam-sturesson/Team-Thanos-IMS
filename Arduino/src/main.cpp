
#include "MowerLib.h"


/* Defines */


/* Global variables */
 int speed =50;
 int dir=1;

/* Program setup */
 
void setup() {

        /* Recommended set up by the manufacturer */
        TCCR1A = _BV(WGM10);
        TCCR1B = _BV(CS11)  | _BV(WGM12);
        TCCR2A = _BV(WGM21) | _BV(WGM20);
        TCCR2B = _BV(CS21);
        
        /* developers setup*/

        void motor_position_interrupt();
        moveSetup(dir, speed / 100.0 * 255);

}

/* Program loop */

void loop() {
        if (lineSensorBlack()){        // turn right
// stop
                moveSetup(STOP, 0 / 100.0 * 255);
                _delay(0.5,drive);
//back
                moveSetup(BACKWARDS, 50 / 100.0 * 255);
                _delay(0.5,drive);
//stop
                moveSetup(STOP, 0 / 100.0 * 255);
                _delay(0.5,drive);
//right
                moveSetup(RIGHT, 50 / 100.0 * 255);
                _delay(0.5,drive);

        }else{
                moveSetup(1, 50 / 100.0 * 255);
                drive();
    }
}