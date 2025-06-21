import serial, time
board = serial.Serial('COM3',9600)

while 1==1: 

    # Read data in console the limit is one line
    a = board.readline()
    # Show the data 
    print ( a)

board.close