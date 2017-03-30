dbase(sol3,[node,arc]).

table(node,[nid,"name",ntokens,isPlace]).
node(p1,'p1',1,true).
node(p2,'p2',0,true).
node(p3,'p3',2,true).
node(p4,'p4',1,true).
node(t1,'t1',0,false).
node(t2,'t2',0,false).

table(arc,[aid,cap,startsAt,endsAt]).
arc(a1,1,p1,t1).
arc(a2,2,t1,p2).
arc(a3,3,t1,p3).
arc(a4,4,p2,t2).
arc(a5,5,p3,t2).
arc(a6,6,t2,p2).
arc(a7,7,t2,p4).

