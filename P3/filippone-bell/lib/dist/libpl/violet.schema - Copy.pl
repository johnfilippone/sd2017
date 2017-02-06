/*  file violet.schema.pl -- see Violet manual in MDELite Docs directory */

dbase(violet,[violetClass,violetInterface,violetAssociation,violetInterfaceExtends,violetClassImplements]).

table(violetClass,[id,"name","fields","methods",superid,x,y]).
table(violetInterface,[id,"name","methods",x,y]).
table(violetAssociation,[cid1,"role1",arrow1,type1,cid2,"role2",arrow2,type2,lineStyle]).
table(violetInterfaceExtends,[id,idx]).
table(violetClassImplements,[cid,iid]).


