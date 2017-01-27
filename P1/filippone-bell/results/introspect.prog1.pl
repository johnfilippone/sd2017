dbase(prog1,[bcClass,bcMember]).

table(bcClass,[cid,"name","superName"]).
table(bcMember,[mid,cid,static,"type","sig"]).

bcClass(c0,'introspect.Main','Object').
bcMember(m0,c0,true,'introspect.Main','Main)').
bcMember(m1,c0,true,'String','getTables(String,String)').
bcMember(m2,c0,true,'void','main(String[])').
