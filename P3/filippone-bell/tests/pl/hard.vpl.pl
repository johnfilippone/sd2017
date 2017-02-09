
dbase(vpl,[violetMiddleLabels,violetAssociation,violetInterface,violetClass]).

table(violetClass,[id,"name","fields","methods",x,y]).
violetClass(classnode0,'LinkedList','node;headOfList','getFirst();getNext();atEnd();insert();delete()',240,420).
violetClass(classnode1,'BinaryTree','node;headOfTree','getFirst();getNext();atEnd();insert();delete()',569,422).

table(violetInterface,[id,"name","methods",x,y]).
violetInterface(interfacenode0,'EditableContainer','insert();delete()',439,187).
violetInterface(interfacenode1,'ReadOnlyContainer','getFirst();getNext();atEnd()',434,35).

table(violetAssociation,[id,"role1","arrow1",type1,"role2","arrow2",type2,"bentStyle","lineStyle",cid1,cid2]).
violetAssociation(id0,'','',classnode,'','TRIANGLE',interfacenode,'VHV','DOTTED',classnode0,interfacenode0).
violetAssociation(id1,'','',classnode,'','TRIANGLE',interfacenode,'VHV','DOTTED',classnode1,interfacenode0).
violetAssociation(id2,'','',classnode,'','TRIANGLE',interfacenode,'','DOTTED',classnode0,interfacenode1).
violetAssociation(id3,'','',classnode,'','TRIANGLE',interfacenode,'','DOTTED',classnode1,interfacenode1).
violetAssociation(id4,'','',interfacenode,'','TRIANGLE',interfacenode,'VHV','',interfacenode0,interfacenode1).

table(violetMiddleLabels,[id,cid1,cid2,"label"]).
