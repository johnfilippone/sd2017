
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'class','','',434,156).
violetClass(classnode1,'student','','',432,393).
violetClass(classnode2,'grade','score','',649,271).

table(violetInterface,[id,"name","methods",x,y]).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'*','',classnode,'1 ','V',classnode,'','',classnode2,classnode0).
violetAssociation(id1,'*','',classnode,'1','V',classnode,'','',classnode2,classnode1).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
