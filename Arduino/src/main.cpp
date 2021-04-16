#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>
//#include <MeAuriga.h>
#include <LineSensor.h>

//MeLineFollower linefollower_9(9);

void _loop() {

}

void setup() {

  while(1) {

      bool something = RightSensorBlack(); 

      if(something){
        int num = 1;
      }
      _loop();
  }

}



void loop() {
  _loop();
}

/*
void setup() {

    //right (sensor 1) - black
  ((0?(1==0?linefollower_9.readSensors()==0:(linefollower_9.readSensors() & 1)==1):(1==0?linefollower_9.readSensors()==3:(linefollower_9.readSensors() & 1)==0)));

    //left (sensor 2) - black
  ((0?(2==0?linefollower_9.readSensors()==0:(linefollower_9.readSensors() & 2)==2):(2==0?linefollower_9.readSensors()==3:(linefollower_9.readSensors() & 2)==0)));
           
    //right (sensor 1) - white
  ((1?(1==0?linefollower_9.readSensors()==0:(linefollower_9.readSensors() & 1)==1):(1==0?linefollower_9.readSensors()==3:(linefollower_9.readSensors() & 1)==0)));

    //left (sensor 2) - white
  ((1?(2==0?linefollower_9.readSensors()==0:(linefollower_9.readSensors() & 2)==2):(2==0?linefollower_9.readSensors()==3:(linefollower_9.readSensors() & 2)==0)));



            
                //right (sensor 1) - black    
                (linefollower_9.readSensors() & 1)==0;

                //left (sensor 2) - black
                (linefollower_9.readSensors() & 2)==0;

                //right (sensor 1) - white
                (linefollower_9.readSensors() & 1)==1;

                //left (sensor 2) - white
                (linefollower_9.readSensors() & 2)==2;



}

void _loop() {
}

void loop() {
  _loop();
}*/