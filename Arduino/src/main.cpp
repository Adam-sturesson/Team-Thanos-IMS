#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>
#include <MeAuriga.h>
#include <LineSensor.h>

MeLineFollower linefollower_9(9);
MeEncoderOnBoard Encoder_1(SLOT1);
MeEncoderOnBoard Encoder_2(SLOT2);
MeLightSensor lightsensor_12(12);

void _loop() {
  Encoder_1.loop();
  Encoder_2.loop();
}

void isr_process_encoder1(void)
{
  if(digitalRead(Encoder_1.getPortB()) == 0){
    Encoder_1.pulsePosMinus();
  }else{
    Encoder_1.pulsePosPlus();
  }
}
void isr_process_encoder2(void)
{
  if(digitalRead(Encoder_2.getPortB()) == 0){
    Encoder_2.pulsePosMinus();
  }else{
    Encoder_2.pulsePosPlus();
  }
}
void move(int direction, int speed)
{
  int leftSpeed = 0;
  int rightSpeed = 0;
  if(direction == 1){
    leftSpeed = -speed;
    rightSpeed = speed;
  }else if(direction == 2){
    leftSpeed = speed;
    rightSpeed = -speed;
  }else if(direction == 3){
    leftSpeed = -speed;
    rightSpeed = -speed;
  }else if(direction == 4){
    leftSpeed = speed;
    rightSpeed = speed;
  }
  Encoder_1.setTarPWM(leftSpeed);
  Encoder_2.setTarPWM(rightSpeed);
}

void _delay(float seconds) {
  if(seconds < 0.0){
    seconds = 0.0;
  }
  long endTime = millis() + seconds * 1000;
  while(millis() < endTime) _loop();
}

void setup() {
  TCCR1A = _BV(WGM10);
  TCCR1B = _BV(CS11) | _BV(WGM12);
  TCCR2A = _BV(WGM21) | _BV(WGM20);
  TCCR2B = _BV(CS21);
  attachInterrupt(Encoder_1.getIntNum(), isr_process_encoder1, RISING);
  attachInterrupt(Encoder_2.getIntNum(), isr_process_encoder2, RISING);
  randomSeed((unsigned long)(lightsensor_12.read() * 123456));
  while(1) {
      
       
      
      LineSensor::RightSensorBlack();
      bool something = true; //RightSensorBlack();
      if(something){
          Encoder_1.setTarPWM(0);
          Encoder_2.setTarPWM(0);
          _delay(0.5);

      }else{

          move(1, 50 / 100.0 * 255);

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