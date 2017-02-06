dbase(yparser,[bcClass,bcMember]).
table(bcClass,[id,"name","super"]).
table(bcMember,[id,cid,static,field,"typeName","arrQual","signature"]).



bcClass(c0,'yparser.connection','Object').

/* public Constructors */
bcMember(m0,c0,true,false,'yparser.connection','','connection(String,String,String,String,String,String)').

/* public Fields */
bcMember(m1,c0,true,true,'String','','quote').
bcMember(m2,c0,true,true,'String','','comma').
bcMember(m3,c0,false,true,'String','','name1').
bcMember(m4,c0,false,true,'String','','role1').
bcMember(m5,c0,false,true,'String','','end1').
bcMember(m6,c0,false,true,'String','','name2').
bcMember(m7,c0,false,true,'String','','role2').
bcMember(m8,c0,false,true,'String','','end2').

/* public Methods */
bcMember(m9,c0,true,false,'void','','dump()').

/*-------------*/
bcClass(c1,'yparser.klass','Object').

/* public Constructors */
bcMember(m10,c1,true,false,'yparser.klass','','klass(String[])').
bcMember(m11,c1,true,false,'yparser.klass','','klass(String,String,String)').

/* public Fields */
bcMember(m12,c1,true,true,'String','','comma').
bcMember(m13,c1,true,true,'String','','quote').
bcMember(m14,c1,false,true,'String','','id').
bcMember(m15,c1,false,true,'String','','name').
bcMember(m16,c1,false,true,'String','','fields').
bcMember(m17,c1,false,true,'String','','methods').

/* public Methods */
bcMember(m18,c1,true,false,'void','','dump()').
bcMember(m19,c1,true,false,'String','','toId(String)').

/*-------------*/
bcClass(c2,'yparser.Main','Object').

/* public Constructors */
bcMember(m20,c2,true,false,'yparser.Main','','Main()').

/* public Fields */
bcMember(m21,c2,true,true,'String','','gen').
bcMember(m22,c2,true,true,'String','','comp').
bcMember(m23,c2,true,true,'String','','agg').
bcMember(m24,c2,true,true,'String','','none').
bcMember(m25,c2,true,true,'String','','arrow1').
bcMember(m26,c2,true,true,'String','','arrow2').
bcMember(m27,c2,true,true,'String','','comma').
bcMember(m28,c2,true,true,'String','','commaApost').
bcMember(m29,c2,true,true,'String','','apostComma').
bcMember(m30,c2,true,true,'String','','equals').
bcMember(m31,c2,true,true,'String','','apost').
bcMember(m32,c2,true,true,'int','','lineCount').
bcMember(m33,c2,true,true,'String','','line').
bcMember(m34,c2,true,true,'String','','original').
bcMember(m35,c2,true,true,'String','','name1').
bcMember(m36,c2,true,true,'String','','end1').
bcMember(m37,c2,true,true,'String','','role1').
bcMember(m38,c2,true,true,'String','','name2').
bcMember(m39,c2,true,true,'String','','end2').
bcMember(m40,c2,true,true,'String','','role2').
bcMember(m41,c2,true,true,'String','[]','array1').
bcMember(m42,c2,true,true,'String','[]','array2').
bcMember(m43,c2,true,true,'java.util.HashMap','','klassS').
bcMember(m44,c2,true,true,'java.util.LinkedList','','connectionS').
bcMember(m45,c2,true,true,'int','','kounter').
bcMember(m46,c2,true,true,'java.io.PrintStream','','out').

/* public Methods */
bcMember(m47,c2,true,false,'void','','main(String[])').
bcMember(m48,c2,true,false,'void','','err(String)').
bcMember(m49,c2,true,false,'void','','printArray(String,String[])').
bcMember(m50,c2,true,false,'String','[]','checkNameStructure(String)').

/*-------------*/

