#include <WiFiEsp.h>
#include "ThingsBoard.h"
#include "WiFiEspClient.h"
#include "SoftwareSerial.h"
//Red Name WIFI
#define WIFI_AP "GalaxyNote"
// //Password red
#define WIFI_PASSWORD "alexxposada"
// //Password red
//Device token (you get it on thingsboard)
#define TOKEN "alexx"
//Domain, it's the default
#define thingsboardServer "thingsboard.cloud"

//variable for walle
int trig = 8, echo = 9;  //ecgo is pwd ~
int led1 = 4, led2 = 5;

//it's not necesary declared the analogic variables
int pinHumedad = A2, pinLuz = A0, pinTemp = A1;


SoftwareSerial soft(2, 3);  // RX, TX
WiFiEspClient espClient;
ThingsBoard tb(espClient);


void setup() {

  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  digitalWrite(led1, HIGH);


  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);

  digitalWrite(trig, LOW);
  digitalWrite(led1, LOW);
  digitalWrite(led2, LOW);
  //---------------

  uint8_t wifiStatus;
  Serial.begin(9600);
  soft.begin(9600);
  WiFi.init(&soft);

  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("Wifi no responde");
    while (true)
      ;
  }

  Serial.println("Conectando...");
  while (wifiStatus != WL_CONNECTED) {
    Serial.println("Intentando conectar a ..." + String(WIFI_AP));
    wifiStatus = WiFi.begin(WIFI_AP, WIFI_PASSWORD);
    delay(500);
  }
}

void loop() {
  delay(1000);
  if (!tb.connected()) {
    Serial.println("Conectando a ..." + String(thingsboardServer));
    Serial.println("Token: " + String(TOKEN));

    if (!tb.connect(thingsboardServer, TOKEN)) {
      Serial.println("Error al conectar, reintentando");
      return;
    }
  }
  Serial.println("Enviando datos...\n -----------------------------------------------");

  long tiempo, distancia, humedad, luz;
  float temperatura;

  digitalWrite(trig, HIGH);
  delayMicroseconds(10);
  digitalWrite(trig, LOW);

  tiempo = pulseIn(echo, HIGH);
  distancia = (tiempo / 59);

  humedad = getHumidity(pinHumedad);
  float porcentajehumedad = humedad;

  luz = getLight(pinLuz);
  luz = luz * 100 / 3000;

  float porcerntajeLuz = (100 * luz) / 800;

  temperatura = (int)getTemperture(pinTemp);

  if (humedad < 2) {
    digitalWrite(led1, HIGH);
    tb.sendAttributeBool("value2", true);

  } else {
    digitalWrite(led1, LOW);
    tb.sendAttributeBool("value2", false);
  }

  if (luz < 25 && distancia <= 20) {
    digitalWrite(led2, HIGH);
    tb.sendAttributeBool("value1", true);  //  Suscribe for atribue
  } else {
    digitalWrite(led2, LOW);
    tb.sendAttributeBool("value1", false);
  }



  const int data_items = 4;
  Telemetry data[data_items] = {
    { "distancia", distancia },
    { "humedad", porcentajehumedad },
    { "luz", luz },
    { "temperatura", temperatura }
  };


  Serial.println("distancia -> " + String(distancia));
  Serial.println("humedad -> " + String(porcentajehumedad));
  Serial.println("luz -> " + String(luz));
  Serial.println("temperatura -> " + String(temperatura));
  tb.sendTelemetry(data, data_items);

  tb.loop();
}

//metodo para obetener humedad
int getHumidity(int pinHumedad) {
  int value = analogRead(pinHumedad);
  int humedad = map(value, 0, 1023, 100, 0);
  return humedad;
}
//método para obtener luz
int getLight(int pinLuz) {
  int light, voltage;

  voltage = analogRead(pinLuz);
  return light = ((long)voltage * 1000 * 10) / ((long)15 * 10 * (1024 - voltage));
}

//Método para obtener temperatura
float getTemperture(int pinTemp) {
  float volts, celsius, valueAdc;
  valueAdc = analogRead(pinTemp);
  volts = ((valueAdc * 500L) / 1023.0)/10;
  return volts;
}