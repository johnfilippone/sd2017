
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'Attribute','name;key?','',324,222).
violetClass(classnode1,'Entity','name','',620,229).
violetClass(classnode2,'Relationship','name','',917,229).

table(violetInterface,[id,"name","methods",x,y]).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'* hasAttr','',classnode,'1 belongsTo','',classnode,'HVH','',classnode0,classnode1).
violetAssociation(id1,'2..* participant','',classnode,'* participates','',classnode,'HVH','',classnode1,classnode2).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
