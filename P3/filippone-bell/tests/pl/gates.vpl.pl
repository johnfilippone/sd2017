
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'Gate','','',674,81).
violetClass(classnode1,'And','','',373,222).
violetClass(classnode2,'Or','','',510,219).
violetClass(classnode3,'Not','','',679,216).
violetClass(classnode4,'InputPort','','',829,220).
violetClass(classnode5,'OutputPort','','',963,223).
violetClass(classnode6,'InputPin','','',381,84).
violetClass(classnode7,'OutputPin','','',949,89).

table(violetInterface,[id,"name","methods",x,y]).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'','',classnode,'* exports','V',classnode,'HVH','',classnode0,classnode7).
violetAssociation(id1,'','',classnode,'* imports','V',classnode,'HVH','',classnode0,classnode6).
violetAssociation(id2,'','',classnode,'','TRIANGLE',classnode,'VHV','',classnode1,classnode0).
violetAssociation(id3,'','',classnode,'','TRIANGLE',classnode,'VHV','',classnode2,classnode0).
violetAssociation(id4,'','',classnode,'','TRIANGLE',classnode,'VHV','',classnode3,classnode0).
violetAssociation(id5,'','',classnode,'','TRIANGLE',classnode,'VHV','',classnode4,classnode0).
violetAssociation(id6,'','',classnode,'','TRIANGLE',classnode,'VHV','',classnode5,classnode0).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
