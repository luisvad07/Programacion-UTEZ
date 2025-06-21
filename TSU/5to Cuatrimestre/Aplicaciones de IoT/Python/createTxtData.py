count = 0
while count != -1:
    data = input("Enter a data-> \t")
    data += "\n"; 
    with open('./../pyton/DocumentsTxt/test.txt','a') as f:
        f.write(data)
    count = int( input("Add another data? -1.NO\t0.YES ->\t") )

print("Document was closed")
f.close(); 
