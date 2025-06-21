# send data to bluetooth 
import serial, time 

# conect to port bt
SerialArduino = serial.Serial('COM15', 9600)
time.sleep(3)

while 'j' == 'j'
    data = input('Enter data ->')
    number = SerialArduino.write(data.encode())
    print(number)
    time.sleep(1)