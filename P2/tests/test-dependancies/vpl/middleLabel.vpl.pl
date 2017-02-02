
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'','','',528,366).
violetClass(classnode1,'','','',896,370).

table(violetInterface,[id,"name","methods",x,y]).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'','',classnode,'','V',classnode,'HVH','',classnode1,classnode0).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
violetMiddleLabels(id1,classnode1,classnode0,'Middle labels are bad').
