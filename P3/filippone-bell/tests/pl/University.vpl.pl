
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'university','name','',385,208).
violetClass(classnode1,'campus','location','',723,209).
violetClass(classnode2,'city','name','',525,434).
violetClass(classnode3,'building','name','',944,409).

table(violetInterface,[id,"name","methods",x,y]).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'1 belongsTo','',classnode,'* has','',classnode,'HVH','',classnode0,classnode1).
violetAssociation(id1,'1 isOn','',classnode,'* has','',classnode,'HVH','',classnode1,classnode3).
violetAssociation(id2,'* hasCampOf','',classnode,'* hasCampIn','',classnode,'VHV','',classnode0,classnode2).
violetAssociation(id3,'* siteOf','',classnode,'1 isIn','',classnode,'','',classnode2,classnode1).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
