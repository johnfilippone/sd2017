
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'A','','',610,476).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(interfacenode0,'B','',926,483).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'','',classnode,'','TRIANGLE',interfacenode,'VHV','',classnode0,interfacenode0).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
