from Crypto.Util.number import getStrongPrime, bytes_to_long
f = open("flag.txt").read()
m = bytes_to_long(f.encode())
p = getStrongPrime(512)
q = getStrongPrime(512)
n = p*q
e = 0x10001
c = pow(m,e,n)

print("n =",n)
print("e =",e)
print("c =",c)
print("p + q = ",p + q)
print("p - q = ",p - q)
