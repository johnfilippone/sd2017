dbase(sol3,[node,arc]).

table(node,[nid,"name",ntokens,isPlace]).
node(p1,'Person waiting',3,true).
node(p2,'Person on bus',0,true).
node(p3,'Bus arriving',1,true).
node(p4,'Bus waiting',0,true).
node(p5,'Bus leaving',0,true).
node(t1,'One person gets on bus',0,false).
node(t2,'Bus stops',0,false).
node(t3,'Bus waiting',0,false).

table(arc,[aid,cap,startsAt,endsAt]).
arc(a1,9,p1,t1).
arc(a2,9,t1,p2).
arc(a3,9,p3,t2).
arc(a4,9,t2,p4).
arc(a5,5,p4,t1).
arc(a6,5,t1,p4).
arc(a7,9,p4,t3).
arc(a8,9,t3,p5).

