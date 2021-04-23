
#include "MowerLib.h"


/* Defines */


/* Global variables */
 int speed =50;
 int dir=1;
 static int state = 0; 

/* Program setup */
 
void setup() {

        /* Recommended set up by the manufacturer */

        TCCR1A = _BV(WGM10);
        TCCR1B = _BV(CS11)  | _BV(WGM12);
        TCCR2A = _BV(WGM21) | _BV(WGM20);
        TCCR2B = _BV(CS21);
        
        /* developers setup*/

        void motorPositionInterrupt();
        //moveSetup(dir, 50); 

        Serial.begin(9600);

}

/* Program loop */

void loop() {
     
        drivingLoop(&state);

}
