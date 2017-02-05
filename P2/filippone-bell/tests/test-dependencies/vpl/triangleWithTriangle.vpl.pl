
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'A','','',610,476).
violetClass(classnode1,'B','','',901,478).

table(violetInterface,[id,"name","methods",x,y]).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'','TRIANGLE',classnode,'','TRIANGLE',classnode,'HVH','',classnode0,classnode1).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
