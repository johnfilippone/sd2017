dbase(prog1,[bcClass,bcMember]).

table(bcClass,[cid,"name","superName"]).
table(bcMember,[mid,cid,static,"type","sig"]).

bcClass(c0,'p1package.ReflectionTest','Object').
bcMember(m0,c0,true,'p1package.ReflectionTest','ReflectionTest(int,int,int)').
bcMember(m1,c0,false,'double','d0').
bcMember(m2,c0,false,'double[]','d1').
bcMember(m3,c0,false,'double[][]','d2').
bcMember(m4,c0,false,'double[][][]','d3').
bcMember(m5,c0,true,'int','i1').
bcMember(m6,c0,true,'void','main(String[])').
bcMember(m7,c0,false,'String[][]','test(int[])').
bcMember(m8,c0,true,'void','main2(String[])').
bcMember(m9,c0,false,'String[]','testr(char[],byte[],short[],float[],boolean[],double[][],String[][])').
