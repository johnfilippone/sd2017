dbase(sol1,[token,place,tbox,arc]).

table(token,[kid,inside]).
token(k1,p1).
token(k2,p1).
token(k3,p1).
token(k4,p3).

table(place,[pid,"name"]).
place(p1,'Person waiting').
place(p2,'Person on bus').
place(p3,'Bus arriving').
place(p4,'Bus waiting').
place(p5,'Bus leaving').

table(tbox,[tid,"name"]).
tbox(t1,'One person gets on bus').
tbox(t2,'Bus stops').
tbox(t3,'Bus waiting').

table(arc,[aid,cap,toTbox,pid,tid]).
arc(a1,9,true,p1,t1).
arc(a2,9,false,p2,t1).
arc(a3,9,true,p3,t2).
arc(a4,9,false,p4,t2).
arc(a5,5,true,p4,t1).
arc(a6,5,false,p4,t1).
arc(a7,9,true,p4,t3).
arc(a8,9,false,p5,t3).
