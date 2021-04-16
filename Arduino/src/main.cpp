
#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>
#include <Motor.h>






void setup() {
  TCCR1A = _BV(WGM10);
  TCCR1B = _BV(CS11)  | _BV(WGM12);
  TCCR2A = _BV(WGM21) | _BV(WGM20);
  TCCR2B = _BV(CS21);
  void motor_position_interrupt();

  move(1, 50 / 100.0 * 255);

}

void loop() {
  _loop();
}