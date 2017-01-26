dbase(prog1,[bcClass,bcMember]).

table(bcClass,[cid,"name","superName"]).
table(bcMember,[mid,cid,static,"type","sig"]).

bcClass(c0,'graff.Neighbor','Object').
bcMember(m0,c0,true,'graff.Neighbor','Neighbor)').
bcMember(m1,c0,true,'graff.Neighbor','Neighbor(Vertex,Edge)').
bcMember(m2,c0,false,'Vertex','end').
bcMember(m3,c0,false,'Edge','edge').
bcClass(c1,'graff.WorkSpace','Object').
bcMember(m0,c1,true,'graff.WorkSpace','WorkSpace)').
bcMember(m1,c1,false,'void','init_vertex(Vertex)').
bcMember(m2,c1,false,'void','preVisitAction(Vertex)').
bcMember(m3,c1,false,'void','checkNeighborAction(Vertex,Vertex)').
bcMember(m4,c1,false,'void','postVisitAction(Vertex)').
bcMember(m5,c1,false,'void','nextRegionAction(Vertex)').
bcClass(c2,'graff.Main','Object').
bcMember(m0,c2,true,'graff.Main','Main)').
bcMember(m1,c2,true,'void','main(String[])').
bcClass(c3,'graff.NumberWorkSpace','WorkSpace').
bcMember(m0,c3,true,'graff.NumberWorkSpace','NumberWorkSpace)').
bcMember(m1,c3,false,'void','preVisitAction(Vertex)').
bcClass(c4,'graff.Graph','Object').
bcMember(m0,c4,true,'graff.Graph','Graph)').
bcMember(m1,c4,true,'boolean','isDirected').
bcMember(m2,c4,true,'int','ch').
bcMember(m3,c4,false,'LinkedList','vertices').
bcMember(m4,c4,false,'LinkedList','edges').
bcMember(m5,c4,false,'Reader','inFile').
bcMember(m6,c4,false,'void','display)').
bcMember(m7,c4,false,'void','runBenchmark(String)').
bcMember(m8,c4,false,'int','readNumber)').
bcMember(m9,c4,false,'void','addVertex(Vertex)').
bcMember(m10,c4,false,'void','stopBenchmark)').
bcMember(m11,c4,false,'void','addAnEdge(Vertex,Vertex,int)').
bcMember(m12,c4,true,'void','startProfile)').
bcMember(m13,c4,false,'Vertex','findsVertex(String)').
bcMember(m14,c4,true,'void','stopProfile)').
bcMember(m15,c4,true,'void','resumeProfile)').
bcMember(m16,c4,true,'void','endProfile)').
bcMember(m17,c4,false,'boolean','CycleCheck)').
bcMember(m18,c4,false,'void','GraphSearch(WorkSpace)').
bcMember(m19,c4,false,'void','NumberVertices)').
bcMember(m20,c4,false,'void','addEdge(Edge)').
bcMember(m21,c4,false,'void','addOnlyEdge(Edge)').
bcMember(m22,c4,false,'void','run(Vertex)').
bcClass(c5,'graff.Vertex','Object').
bcMember(m0,c5,true,'graff.Vertex','Vertex)').
bcMember(m1,c5,false,'LinkedList','neighbors').
bcMember(m2,c5,false,'String','name').
bcMember(m3,c5,false,'boolean','visited').
bcMember(m4,c5,false,'int','VertexCycle').
bcMember(m5,c5,false,'int','VertexColor').
bcMember(m6,c5,false,'int','VertexNumber').
bcMember(m7,c5,false,'Vertex','assignName(String)').
bcMember(m8,c5,false,'void','dftNodeSearch(WorkSpace)').
bcMember(m9,c5,false,'void','display)').
bcMember(m10,c5,false,'void','init_vertex(WorkSpace)').
bcMember(m11,c5,false,'void','VertexConstructor)').
bcMember(m12,c5,false,'void','addNeighbor(Neighbor)').
bcClass(c6,'graff.CycleWorkSpace','WorkSpace').
bcMember(m0,c6,true,'graff.CycleWorkSpace','CycleWorkSpace(boolean)').
bcMember(m1,c6,false,'boolean','AnyCycles').
bcMember(m2,c6,false,'int','counter').
bcMember(m3,c6,false,'boolean','isDirected').
bcMember(m4,c6,true,'int','WHITE').
bcMember(m5,c6,true,'int','GRAY').
bcMember(m6,c6,true,'int','BLACK').
bcMember(m7,c6,false,'void','init_vertex(Vertex)').
bcMember(m8,c6,false,'void','preVisitAction(Vertex)').
bcMember(m9,c6,false,'void','checkNeighborAction(Vertex,Vertex)').
bcMember(m10,c6,false,'void','postVisitAction(Vertex)').
bcClass(c7,'graff.Edge','Neighbor').
bcMember(m0,c7,true,'graff.Edge','Edge)').
bcMember(m1,c7,false,'Vertex','start').
bcMember(m2,c7,false,'int','weight').
bcMember(m3,c7,false,'void','display)').
bcMember(m4,c7,false,'void','EdgeConstructor(Vertex,Vertex,int)').
bcMember(m5,c7,false,'void','EdgeConstructor(Vertex,Vertex)').
bcMember(m6,c7,false,'void','adjustAdorns(Edge)').
