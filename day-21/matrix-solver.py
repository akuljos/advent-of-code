import numpy.linalg 
import numpy as np

A = np.array([[        65**2,       65, 1], 
              [(65+131*4)**2, 65+131*4, 1],
              [(65+131*8)**2, 65+131*8, 1]])

b = np.array([3784, 302108, 1077072])

coeff = numpy.linalg.inv(A) @ b

print(coeff[0] * (26501365 ** 2) + coeff[1] * (26501365) + coeff[2])