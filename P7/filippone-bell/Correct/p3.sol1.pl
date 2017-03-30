dbase(sol1,[token,place,tbox,arc]).

table(token,[kid,inside]).
token(k1,p1).
token(k2,p1).
token(k3,p1).

table(place,[pid,"name"]).
place(p1,'p1').
place(p2,'p2').
place(p3,'p3').

table(tbox,[tid,"name"]).
tbox(t1,'t1').
tbox(t2,'t2').
tbox(t3,'t3').

table(arc,[aid,cap,toTbox,pid,tid]).
arc(a1,1,true,p1,t1).
arc(a2,2,false,p2,t1).
arc(a3,3,true,p2,t2).
arc(a4,4,true,p2,t2).
arc(a5,4,false,p3,t2).
arc(a6,4,true,p3,t3).
arc(a7,4,false,p1,t3).



