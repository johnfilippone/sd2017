/* file: yuml.schema.pl  -- see Yuml Manual in MDELite Docs directory */

dbase(yuml,[yumlClass,yumlInterface,yumlAssociation]).

table(yumlClass,[id,"name","fields","methods"]).
table(yumlInterface,[id,"name","methods"]).
table(yumlAssociation,["name1","role1","end1","name2","role2","end2"]).


