
#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>
#include <Motor.h>
#include <LineSensor.h>



 int speed =50;
 int dir=1;
void setup() {
  TCCR1A = _BV(WGM10);
  TCCR1B = _BV(CS11)  | _BV(WGM12);
  TCCR2A = _BV(WGM21) | _BV(WGM20);
  TCCR2B = _BV(CS21);
  //void motor_position_interrupt();
   
  move(dir, speed / 100.0 * 255);

}

void loop() {

    if (RightSensorBlack()){
// stop
        move(1, 0 / 100.0 * 255);
        _delay(0.5);
//back
        move(2, 50 / 100.0 * 255);
        _delay(0.5);
//stop
        move(1, 0 / 100.0 * 255);
        _delay(0.5);
//right
        move(4, 50 / 100.0 * 255);
        _delay(0.5);

    }else{
        move(1, 50 / 100.0 * 255);
        _loop();
    }
        
}