import numpy.linalg 
import numpy as np

A = []
b = []

items = []

count = 0

with open("matrix_coeffs.txt") as f:
    currLine = f.readline()[:-1]

    while count < 5:
        posValItem = list(map(float, currLine.split(",")))
        items.append(posValItem)

        currLine = f.readline()[:-1]
        count += 1
    
for i in range(4):
    pX, pY, pZ, vX, vY, vZ = items[i]
    pX_, pY_, pZ_, vX_, vY_, vZ_ = items[i+1]

    A.append([vY - vY_, vX_ - vX, pY_ - pY, pX - pX_])
    b.append(pY_ * vX_ - pY * vX - pX_ * vY_ + pX * vY)

A = np.array(A)
b = np.array(b)

x = np.linalg.inv(A) @ b

pXr, pYr, vXr, vYr = list(map(round, x))

A = []
b = []

for i in range(2):
    pX, pY, pZ, vX, vY, vZ = items[i]

    A.append([vX - vXr, pXr - pX])
    b.append(vZ * (pXr - pX) + pZ * (vX - vXr))

A = np.array(A)
b = np.array(b)

x = np.linalg.inv(A) @ b

pZr, vZr = list(map(round, x))

print("Sum of positions is " + str(pXr + pYr + pZr))