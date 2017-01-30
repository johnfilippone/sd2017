
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'Hello','tester','test()',510,227).
violetClass(classnode1,'World','-hasExclamation','-getHello()',698,229).

table(violetInterface,[id,"name","methods",x,y]).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'','',classnode,'','V',classnode,'HVH','',classnode0,classnode1).
violetAssociation(id1,'','',classnode,'','V',classnode,'HVH','',classnode1,classnode0).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
