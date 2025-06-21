#include <SoftwareSerial.h> //Librería del Serial de Sensor
SoftwareSerial Wifi(2,3); //Se declara la variable global de Módulo Wifi

int sensorHumedad = A0; //Posición del Sensor de Humedad
int sensorLuz = A1; //Posición de la Fotoresistencia
int bombaAgua = 8; //Posición de la Bomba de agua

const String datos;

void setup() { //Es la parte encargada de recoger la configuración 
  Serial.begin(9600);
  Wifi.begin(9600);
  pinMode(sensorHumedad, INPUT); //Declaración de Entrada del Sensor Humedad
  pinMode(sensorLuz, INPUT); //Declaración de Entrada de la Fotoresistencia
  pinMode(bombaAgua, OUTPUT); //Declaración de Salida de la Bomba de agua
  
}

void loop() { //Es la que contienen el programa que se ejecutará cíclicamente
  int hum = analogRead(sensorHumedad); //Lee el valor del sensor de Humedad
  float humedad = map(hum, 0, 1023, 100, 0); //Mapea el valor del sensor humedad a procentaje
  float luz = analogRead(sensorLuz); //Lee el valor del sensor de la Fotoresistencia
  String mensaje = String(humedad) + "," + String(luz) + "\n"; //Declara un mensaje donde guarda los anteriores valores

  if (Wifi.available()) { //Si se conecta a la red wifi hace el proceso
    Wifi.print(mensaje); //Imprime Wifi el resultado de "mensaje"
    String datos = Wifi.readString(); //Lee datos del Wifi de Python
    Serial.println(datos); //Imprime al Serial ese dato
    String message = getString(datos); //Se realiza un método para imprimir solo el mensaje y no el icp
    //Serial.write(Wifi.read());
    if (message == "encender") { //Si la palabra es encender
      //Se prende la bomba
      digitalWrite(bombaAgua, HIGH);
      Serial.print("Se prendio la bomba");
    } else if (message == "off") { //Si la palabra es apagar
      //Se apaga la bomba
      digitalWrite(bombaAgua, LOW);
      Serial.print("Se apago la bomba");
    } else { //Si la palabra dice otra cosa
      Serial.println("Error de Opción!");
    }
  }
  
  delay(1000); //Imprime cada segundo esos valores
}

String getString(String datos) {
  int u = 0, limit = 0;
  String fin;

  u = datos.length();
  for (int e = 0; e < u; e++) {
    if (datos[e] == ':'){
       limit = e;
    }
    if(i> limit && limit !=0){
      fin += datos[e]; 
    }
  }
  return fin;
}
