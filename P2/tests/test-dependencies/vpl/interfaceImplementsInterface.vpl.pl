
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(interfacenode0,'B','',926,483).
violetInterface(interfacenode1,'A','',652,480).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'','',interfacenode,'','TRIANGLE',interfacenode,'VHV','DOTTED',interfacenode1,interfacenode0).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
