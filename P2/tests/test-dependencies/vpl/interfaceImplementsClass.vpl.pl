
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'A','','',661,345).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(interfacenode0,'B','',659,457).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'','',interfacenode,'','TRIANGLE',classnode,'VHV','DOTTED',interfacenode0,classnode0).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
