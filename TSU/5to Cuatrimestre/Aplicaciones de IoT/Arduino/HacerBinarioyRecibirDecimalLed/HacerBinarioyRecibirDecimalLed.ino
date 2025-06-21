//Variables and constant
const int BTN1= 2;  //port from arduino hw
const int BTN2= 3;
const int BTN3= 4;
const int BTN4= 5;
const int LED= 6;
String cadenaBin;
String resdata;

void setup() {

  //pinMode()  definide devices 
  pinMode(BTN1, INPUT);
  pinMode(BTN2, INPUT);
  pinMode(BTN3, INPUT);
  pinMode(BTN4, INPUT);
  pinMode(LED, OUTPUT);
  Serial.begin(9600);  //start to use console

}

void loop() {

//String(argumento)   function to came a variable to string

  cadenaBin= String(digitalRead(BTN1)) + String(digitalRead(BTN2)) + String(digitalRead(BTN3)) + String(digitalRead(BTN4));
  Serial.println(cadenaBin);
  delay(1000);

  //intermeiario python 

 resdata= Serial.readString();  //read data from console but as String

 for(int i=0; i<resdata.toInt(); i++){ // parseo to int 
  digitalWrite(LED, HIGH);
  delay(500);
  digitalWrite(LED, LOW);
  delay(100);
 }
}
