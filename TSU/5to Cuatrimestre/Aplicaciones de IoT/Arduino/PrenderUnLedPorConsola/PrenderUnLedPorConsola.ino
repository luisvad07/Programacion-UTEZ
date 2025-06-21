void setup() {
  pinMode(2,OUTPUT);
  Serial.begin(9600);
}

void loop() {
  if (Serial.available()>0) {
    char dato = Serial.read(); 
    if (dato=='1') {
      digitalWrite(2, HIGH);
    }
    if (dato=='A') {
      digitalWrite(2, LOW);
    }
  }

}
