#include <SoftwareSerial.h>
SoftwareSerial Wifi(2,3);

const int LED = 2;
const String datos;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Wifi.begin(9600);
  pinMode(LED, OUTPUT);
}


void loop() {
  // put your main code here, to run repeatedly:
  if (Wifi.available()) {
    String datos = Wifi.readString();
    Serial.println(datos);
    Serial.write(Wifi.read());
  }
}
