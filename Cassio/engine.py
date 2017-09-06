""" 
file: engine.py
language: python3
description: Controls the Cassio game
"""
do="played illegally and forfeit."
woo="has won the game."
shx="The game was a tie."
du="doesn't realize it is the winner."
dv="incorrectly claimed victory."
K="__class__"
y="Player"
tx=next
I=print
z=None
X=range
k=property
F=int
Y=eval
h=str
D=map
m=chr
R=type
A=sum
f=len
__author__='James Heliotis'
from typing import Sequence \
as strr
import functools
v=functools.reduce
I("Cassio Engine, version of 16 February 2017\n")
from cassio_API import Board as room,Move as st,Player as \
ll,N_ROWS as L,N_COLS as U
UL="Board dimensions are too small."
o=L//2
b=U//2
G=st(0,o-1,b-1),st(1,o-1,b), st(0,o,b),st(1,o,b-1)
l=2
class T(room):
 def __init__(p,H:strr[ll])->z:
  crt=D(lambda w:lambda j:I((y+'%d: '+w.__name__)%j),(getattr(zp,K)for zp in H));tx(crt)(l-2)
  p.om=[[l]*U for _ in X(L)]
  p.xi=H
  p.ps=z
  tx(crt)(l-1)
 @k
 def s(p):
  return p.ps
 @s.setter
 def s(p,s)->z:
  p.ps=s
 def c(p,n:F,E:F)->z:
  p.s.update(p,n,E)
 def i(p,new_piece,n:F,E:F)->z:
  p.om[n][E]=new_piece
  p.c(n,E)
 def j(p,n:F,E:F)->F:
  return p.om[n][E]
 def C(zoid,p:F,n:F,col:F)->z:
  for w in-1,0,1:
   for u in-1,0,1:
    if w==0 and u==0:continue
    r=n+w
    c=col+u
    while(0<=r<L and 0<=c<U)and zoid.j(r,c)==1-p:
     r+=w
     c+=u
    if(0<=r<L and 0<=c<U)and zoid.j(r,c)==p:
     r=n+w
     c=col+u
     while(0<=r<L and 0<=c<U)and zoid.j(r,c)==1-p:
      zoid.i(p,r,c)
      for K in zoid.xi:
       if not K.is_student_player():
        K.move_was(st(p,r,c))
      r+=w
      c+=u
 get_piece=j
 def N(a):
  ok=l
  B=Y(v(h.__add__,D(m,[91]))+','.join(D(lambda l:h(l).strip('[]'),a.om))+']')
  g=v(lambda z,y:z+y if R(y)==F else z,B,0)
  if(g+g)>A(D(lambda _:U,a.om)):
   return 1
  elif(g+g)<A(D(lambda _:L,a.om)):
   return 0
  return ok
 def x(kat):
  for y in G:
   for K in kat.xi:K.move_was(y)
   kat.i(y.player,y.row,y.column)
  M=l
  p=0
  for _ in X(L*U-f(G)):
   y=kat.xi[p].your_move()
   if p!=y.player:
    I(uz+" "+h(p)+wid)
    M=p
   elif y.row<0 or y.row>=L or y.column<0 or y.column>=U:
    I(uz+" "+h(p)+oob)
    M=p
   elif kat.j(y.row,y.column)!=l:
    I(uz+" "+h(p)+occ)
    M=p
   else:
    for K in kat.xi:K.move_was(y)
    kat.i(p,y.row,y.column)
    kat.C(p,y.row,y.column)
    p=1-p
   if M!=l:break
  if M!=l:
   a=1-M
   I(uz,M,do)
  else:
   a=kat.N()
   if a!=2:
    I(uz,a,woo)
   else:
    I(shx)
  for p in 0,1:
   if a==p:
    if M==l and not kat.xi[p].has_won():
     I(uz,p,du)
    if kat.xi[1-p].has_won():
     I(uz,1-p,dv)
 has_won=N
 run_game=x
 view=s
assert L>=2 and U>=2,UL
wid="'s move has the wrong id."
oob="'s move is out of bounds."
occ="'s move already contains a disc."
if __name__=='__main__':
 import sys
 I("No main program to run in the engine module.",file=sys.stderr)
 I("Instead, create an instance of engine.Engine.",file=sys.stderr)
uz="Player"
