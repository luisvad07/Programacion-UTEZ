#include <SoftwareSeril.h>
String data;
const int LED = 2;
SoftwareSerial BT(10, 11)  //TX 10 RX 11

  void setup() {
  Serial.begin(9600);
  BT.begin(9600);
  pinMode(LED, OUTPUT);
}

void loop() {


  if (BT.available()) {  //read data bluetooth
    data = BT.readString();
    Serial.println(data);

    if (data == "1") {
      digitalWrite(LED, HIGH);
    } else {
      digitalWrite(LED, LOW);
    }
  }


}
