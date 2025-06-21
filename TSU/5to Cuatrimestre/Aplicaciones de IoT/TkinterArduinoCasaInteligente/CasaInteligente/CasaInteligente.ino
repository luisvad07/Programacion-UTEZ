#include <WiFiEsp.h>
#include "WiFiEspClient.h"
#include "SoftwareSerial.h"
#include <Servo.h>     // Incluímos la librería para poder controlar el servo
#include "DHT.h"       // Incluímos la librería para poder controlar el sensor DHT11
#define DHTPIN 8       // Pin donde está conectado el sensor
#define DHTTYPE DHT11  // Tipo de sensor DHT11

DHT dht(DHTPIN, DHTTYPE);  // Creamos el objeto para el sensor

// Declaramos las variables para controlar los servos
Servo servoMotorPuerta;
Servo servoMotorVentana;

const int Trigger = 12;  //Pin digital 2 para el Trigger del sensor
const int Echo = 11;     //Pin digital 3 para el Echo del sensor

const int bombaAgua = 7;  //Pin digital 7 para la bomba de agua

int LedEntrada = 4;  //Pin digital 4 para el led de entrada

int sensorHumedad = A0;  //Pin analogico 0 para el sensor de humedad

int pinLDR = A1;  // Pin analogico de entrada para el LDR

//Nombre de la red
#define WIFI_AP "Totalplay-EBA6"
//Contraseña de la res
#define WIFI_PASSWORD "EBA6388CRgs3A33g"

//Ip del Modulo Wifi
String host = "192.168.100.95";

SoftwareSerial soft(2, 3);  // RX, TX

String text;
int randomNumber = 0;

void setup() {
  Serial.begin(9600);
  soft.begin(9600);  //Inicialización de wifi
  Serial.println("Starting process");

  dht.begin();  // Iniciamos el sensor DHT11

  connectWifi();

  // Iniciamos el servo para que empiece a trabajar con el pin 9
  servoMotorPuerta.attach(9);
  servoMotorVentana.attach(10);

  pinMode(Trigger, OUTPUT);     //pin como salida
  pinMode(Echo, INPUT);         //pin como entrada
  pinMode(LedEntrada, OUTPUT);  //pin como salida
  pinMode(bombaAgua, OUTPUT);   //pin como salida
  digitalWrite(Trigger, LOW);   //Inicializamos el pin con 0
  delay(10001);
  servoMotorPuerta.write(80);    //Cerrar puerta
  servoMotorVentana.write(175);  //Cerrar ventana
}

void loop() {

  while (soft.available()) {   //Si esta prendida
    if (soft.find("+IPD,")) {  //buscamos
      String text = soft.readString();
      delay(600);
      String value = getString(text);  //extraemos el valor
      delay(600);
      Serial.println(value);

      if (value == "1") {
        Serial.println("Foco");
        delay(5000);
        digitalWrite(LedEntrada, HIGH);
        delay(5000);
        digitalWrite(LedEntrada, LOW);

      } else if (value == "2") {
        Serial.println("Puerta");
        abrirPuerta();
        delay(3000);
        cerrarPuerta();

      } else if (value == "3") {
        Serial.println("Ventana");
        delay(500);
        abrirVentana();

      } else if (value == "4") {
        Serial.println("Bomba");
        encenderBomba();
      } else {
        Serial.println("Dato desconcido");
      }
    }
  }

  // Leemos la temperatura
  long temperatura = obtenerTemperatura();
  // Leemos la distancia
  long distancia = obtenerDistancia();
  // Leemos la humedad
  int humedad = obtenerHumedad();
  //Leemos la luz
  int luz = obtenerLuz();

  String data = String(distancia) + "," + String(temperatura) + "," + String(humedad) + "," + String(luz) + "";
  String LONG_MESSAGE = String("AT+CIPSEND=0," + String(data.length()) + "\r\n");


  delay(3000);
  sendATCommand(LONG_MESSAGE);
  delay(2000);
  sendATCommand(data);


  if (distancia < 11) {
    abrirPuerta();
    delay(3000);
    cerrarPuerta();
  } else {
    Serial.println("No hay nadie");
    cerrarPuerta();
  }

  if (temperatura > 32) {
    abrirVentana();
    Serial.println("Temperatura alta");
  } else {
    Serial.println("Temperatura normal");
    cerrarVentana();
  }

  if (humedad < 110) {
    if (luz > 500) {
      encenderBomba();
      Serial.println("Humedad baja");
    } else {
      apagarBomba();
      Serial.println("Humedad baja, esperar a que haya menos luz");
    }
  } else {
    apagarBomba();
    Serial.println("Humedad suficiente");
  }
}

void sendATCommand(String command) {
  soft.print(command);      //Envio de datos
  Serial.println(command);  //Mostramos lo que enviamos

  delay(500);
}

void connectWifi() {
  Serial.println("Connecting process");
  delay(500);
  //Comprobación medinate libreria si esta conectado
  uint8_t wifiStatus;
  WiFi.init(&soft);

  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("Wifi no responde");
    while (true)
      ;
  }


  while (wifiStatus != WL_CONNECTED) {
    Serial.println("Intentando conectar a ..." + String(WIFI_AP));
    wifiStatus = WiFi.begin(WIFI_AP, WIFI_PASSWORD);  //Conexión a la red
    delay(500);
  }

  Serial.println("Conectada");

  sendATCommand("AT+CWMODE=3");  //Modo del modulo wifi
  esp01_config();
}
void esp01_config() {
  int counter = 0;

  String HOST = String("AT+CIPSTART=1,\"TCP\",\"" + host + "\",80\r\n");  // incio de servidor
  sendATCommand(HOST);
  delay(1000);


  Serial.println("AT+CIPMUX=1");  // activación de multiconexiones

  while (1) {
    soft.println("AT+CIPMUX=1");
    delay(250);
    if (soft.find("OK")) {
      counter += 1;
    }
    if (counter == 10) {
      counter = 0;
      break;
    }
  }
  Serial.println("AT+CIPSERVER=1,80");  // Activamos El puerto

  while (1) {
    soft.println("AT+CIPSERVER=1,80");
    delay(250);
    if (soft.find("OK")) {
      counter += 1;
    }
    if (counter == 10) {
      counter = 0;
      break;
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

//Funcion para abrir la puerta
void abrirPuerta() {
  servoMotorPuerta.write(80);  //Abrir puerta
  digitalWrite(LedEntrada, LOW);
  Serial.println("Abriendo puerta");
}

//Funcion para cerrar la puerta
void cerrarPuerta() {
  servoMotorPuerta.write(0);  //Cerrar puerta
  digitalWrite(LedEntrada, HIGH);
  Serial.println("Cerrando puerta");
}

//Funcion para abrir la ventana
void abrirVentana() {
  servoMotorVentana.write(120);  //Abrir ventana
  Serial.println("Abriendo ventana");
}

//Funcion para cerrar la ventana
void cerrarVentana() {
  servoMotorVentana.write(175);  //Cerrar ventana
  Serial.println("Cerrando ventana");
}

void encenderBomba() {
  digitalWrite(bombaAgua, HIGH);
  Serial.println("Encendiendo bomba");
}

void apagarBomba() {
  digitalWrite(bombaAgua, LOW);
  Serial.println("Apagando bomba");
}

//Funcion para leer la temperatura
float obtenerTemperatura() {
  float temperatura = dht.readTemperature();  //Leemos la temperatura en grados Celsius
  Serial.print("Temperatura: ");
  Serial.println(temperatura);
  return temperatura;
}

//Funcion para obtener la distancia
long obtenerDistancia() {
  long t;  //timepo que demora en llegar el eco
  long d;  //distancia en centimetros
  digitalWrite(Trigger, HIGH);
  delayMicroseconds(10);  //Enviamos un pulso de 10us
  digitalWrite(Trigger, LOW);

  t = pulseIn(Echo, HIGH);  //obtenemos el ancho del pulso
  d = t / 59;               //escalamos el tiempo a una distancia en cm
  Serial.print("Distancia: ");
  Serial.println(d);  //Enviamos serialmente el valor de la distancia
  return d;
}

int obtenerHumedad() {
  int humedad = analogRead(sensorHumedad);
  Serial.print("Humedad: ");
  Serial.println(humedad);
  return humedad;
}

int obtenerLuz() {
  // Variable donde se almacena el valor del LDR
  int valorLDR = analogRead(pinLDR);
  return valorLDR;
}
