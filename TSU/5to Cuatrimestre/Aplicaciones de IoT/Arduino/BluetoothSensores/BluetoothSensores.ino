#include <SoftwareSerial.h>
SoftwareSerial blue(10,11);

const int Trigger = 9;
const int Echo = 8;

const int fotoR = A2;
const int humedad = A0;

int distacia, tiempo, val = 0;

float tempC; 
int pinLM35 = 0;

void setup() {
  Serial.begin(9600);
  blue.begin(9600);
  pinMode(Trigger, OUTPUT);
  pinMode(Echo, INPUT);
  
}

void loop() {
  //sensor de humedad
  int humedad1 = map(analogRead(humedad),0,1023,100,0);

  //foto
  val = analogRead(fotoR);

  //ccensor
  digitalWrite(Trigger, HIGH);
  delayMicroseconds(10);
  digitalWrite(Trigger, LOW);
  tiempo = pulseIn(Echo, HIGH);
  distacia = tiempo/2*0.034;

  //tempe
  tempC = analogRead(pinLM35); 
  tempC = (5.0 * tempC * 100.0)/1024.0;

  //imprime
  blue.print(tempC);
  blue.print(" ");
  BT.print(distacia);
  BT.print(" ");
  BT.print(val);
  BT.print(" ");
  BT.print(humedad1);
  BT.println(" ");
  delay(1000);

}
