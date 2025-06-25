import time
import os
import readchar

i = 0
while True:
    os.system("clear")
    print(f"Epoch {i}\n")
    with open(f"./samples/step_{i}.txt") as f:
        data = f.read()
        print(data)
    
    key = readchar.readchar()
    if (key == 's'):
        i += 1
        i %= 20000
    if (key == 'a'):
        i -= 1
        i %= 20000