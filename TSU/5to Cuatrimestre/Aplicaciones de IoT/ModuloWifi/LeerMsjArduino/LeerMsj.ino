#include <SoftwareSerial.h>
String flsmg;
SoftwareSerial Wifi(2, 3); //TX 10 RX 11

int led = 8;


void setup() {
  Serial.begin(9600);
  Wifi.begin(9600);
  pinMode(led, OUTPUT);
}

void loop() {


  if (Wifi.available()) {  //read data bluetooth
    String flsmg = Wifi.readString();
    Serial.println(flsmg);
    String msg = getString(flsmg);
    //Serial.print(msg);
    if (msg == "on") {
      digitalWrite(led, HIGH);
      Serial.print("entro al if");
      
    } else if (msg == "off") {
      digitalWrite(led, LOW);
      
    } else {
      Serial.println("Opción no disponible, no haré nada :)");
    }

  }


  if (Serial.available()) {
    Wifi.write(Serial.read());  // alarm msj
  }



}


String getString(String flsmg) {
  int lng = 0, delimiter = 0;
  String lmsg;

  lng = flsmg.length();
  for (int i = 0; i < lng; i++) {
    if (flsmg[i] == ':'){
       delimiter  = i;
    }
    if(i> delimiter && delimiter!=0){
      lmsg += flsmg[i]; 
    }
  }
  return lmsg;
}