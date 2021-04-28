
#include "MowerLib.h"
//# include "MeAuriga.h"
//# include "Wire.h"
//# include "Arduino.h"
//# include "SoftwareSerial.h"
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

        //motorPositionInterrupt();
        //moveSetup(FORWARDS, 0);
        //DelayAndDO(0.5,drive);
        //bluetoothSetup();

        Serial.begin(115200);
        //bt.begin(115200);
        

}

/* Program loop */

String st="";
void loop() {

        Serial.println(st);
        delay(100);

        if(Serial.available()>0){
                st = Serial.readString();
                delay(100);
        }

        if(st[0]=='f')
                moveSetup(FORWARDS,50);
        if(st[0]=='s')
                moveSetup(STOP,0); 

drive();       

}

//st=="f\r\n"
//st=="s\r\n"
