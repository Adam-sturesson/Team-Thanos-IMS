
#include "MowerLib.h"


/* Defines */


/* Global variables */



/* Program setup */
 
void setup() {

        /* Recommended set up by the manufacturer */

        TCCR1A = _BV(WGM10);
        TCCR1B = _BV(CS11)  | _BV(WGM12);
        TCCR2A = _BV(WGM21) | _BV(WGM20);
        TCCR2B = _BV(CS21);
        
        /* developers setup*/

        randomSeed(analogRead(0));
        motorPositionInterrupt();
        Serial.begin(115200);
        gyroSetup();
        rpiSerialSetup();
        

}

/* Program loop */

void loop() {
        
    drivingLoop();


}
