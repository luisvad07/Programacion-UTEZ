#include <SoftwareSerial.h>
SoftwareSerial blue(10,11);

const int LED = 2;
const String datos;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  blue.begin(9600);
  pinMode(LED, OUTPUT);
}

void loop() {
  if (blue.available()>0) {
      //blue.println("1");
      //Serial.println(blue.readString());
      //blue.println("hola");
      datos = BT.readString();
      Serial.println(datos);

      if (datos == "1") {
        Serial.println("Encendido :)");
        digitalWrite(LED, HIGH);
      } else {
        Serial.println("Apagado :(");
        digitalWrite(LED, LOW);
      }
  }
}
