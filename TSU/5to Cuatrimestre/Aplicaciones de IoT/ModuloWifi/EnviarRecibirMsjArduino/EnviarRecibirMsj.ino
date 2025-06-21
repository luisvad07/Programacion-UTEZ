HardwareSerial &arduino = Serial;
#include <SoftwareSerial.h>
SoftwareSerial wifi(2, 3);  // RX | TX


String red=  "AlexxJoel";
String contra =  "123456789";
String IP =  "192.168.77.51";
String PORT =  "80";

String lecturaWifi, console;
const int pinFotoresistencia = A1;
const int pinHumedad = A0;
const int bomba = 7;



void setup() {
  arduino.begin(9600);
  wifi.begin(9600);
  delay(1000);
  pinMode(bomba, OUTPUT);
  digitalWrite(bomba, LOW);

  wifi.println("ATE0");
  delay(1000);

  wifi.println("AT");
  delay(1000);

  if (wifi.find("OK")) {

    arduino.println("Modulo conectado");

    wifi.println("AT+CWMODE=3");
    delay(1000);
    wifi.println("AT+CIPMUX=1");
    delay(1000);
    wifi.println("AT+CIPSERVER=1,80");
    delay(1000);
    console = "AT+CWJAP=\"" + String(red) + "\",\"" + String(contra) + "\"";
    wifi.println(console);
    delay(2000);

    arduino.println("WiFi configurado");
    arduino.println("Iniciando el envio de datos:");
  }
}



void loop() {

  //sensores
  int light = map(analogRead(pinFotoresistencia), 0, 1023, 0, 100);
  int wet = map(analogRead(pinHumedad), 0, 1023, 100, 0);
  String data = String(light) + " " + String(wet);
  //envio de datos
  String conn = "AT+CIPSTART=0,\"TCP\",\"" + String(IP) + "\"," + String(PORT);

  arduino.println(conn);
  wifi.println(conn);
  delay(2000);

  if (wifi.find("ALREADY CONNECTED")) {
    wifi.println("AT+CIPSEND=0," + String(data.length()));
    if (wifi.find(">")) {
      wifi.println(data);
    } else {
      arduino.println("cipsend fail");
    }
  } else {
    arduino.println("cipstart fail");
  }
  delay(2000);

  if (wifi.available()) {  //read data bluetooth
    String lecturaWifi = wifi.readString();
    arduino.println(lecturaWifi);
    String message = getString(lecturaWifi);
    arduino.print(message);

    if (message == "on") {
      digitalWrite(bomba, HIGH);
      arduino.print("on");

    } else if (message == "off") {
      digitalWrite(bomba, LOW);
      arduino.print("off");
    }
  }
}



String getString(String lecturaWifi) {
  int lng = 0, delimiter = 0;
  String lmessage;

  lng = lecturaWifi.length();
  for (int i = 0; i < lng; i++) {
    if (lecturaWifi[i] == ':') {
      delimiter = i;
    }
    if (i > delimiter && delimiter != 0) {
      lmessage += lecturaWifi[i];
    }
  }
  return lmessage;
}
