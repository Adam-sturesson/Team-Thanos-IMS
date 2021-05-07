
#include "MowerLib.h"
//# include "MeAuriga.h"
//# include "Wire.h"
//# include "Arduino.h"
//# include "SoftwareSerial.h"
/* Defines */


/* Global variables */
int pos1=0;
int pos2=0;



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
        //moveSetup(FORWARDS, 0);
        //DelayAndDO(0.5,drive);
        //bluetoothSetup();

        Serial.begin(115200);
        gyroSetup();
        //bt.begin(115200);
        

}

/* Program loop */
String toPrint;
int circles1=0;
int circles2=0;

int x=0;
int y=0;
int z=0;
int counter=0;
void loop() {
        
    //drivingLoop();


}

/*

             moveSetup(FORWARDS,50);
        drive();
        
        toPrint="dis is :" + String(getDistance());
        Serial.println(toPrint+"\r\n");
        delay(100);
        counter++;

        if(counter>100){
                resetDistance();
                counter=0;
        }   
        
*/