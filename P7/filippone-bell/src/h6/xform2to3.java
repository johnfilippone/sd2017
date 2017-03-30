/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package h6;

import PrologDB.DB;
import PrologDB.DBSchema;
import PrologDB.Table;
import PrologDB.Tuple;
import java.io.PrintStream;

/**
 *
 * @author don
 */
public class xform2to3 {
    public static void main(String... args) throws Exception {
        if (args.length !=2 || !args[0].endsWith(".sol2.pl") || !args[1].endsWith(".sol3.pl")) {
            System.err.format("Usage: %s <name>.sol2.pl <name>.sol3.pl\n", xform2to3.class.getName());
            System.err.format("       converts .sol2 database into sol3 database\n");
            return;
        }
        
        DB db2 = DB.readDataBase(args[0]);
        DBSchema sol3 = DBSchema.readSchema("Correct/sol3.schema.pl");
        
        Table place = db2.getTableEH("place");
        Table tbox  = db2.getTableEH("tbox");
        Table arc   = db2.getTableEH("arc");
        
        DB db3 = new DB("p1",sol3);
        Table nod = db3.getTableEH("node");
        Table ark = db3.getTableEH("arc");
        
        // merge place and tbox into node
        place.stream().forEach(t->{
          Tuple newNode = new Tuple(nod);
          newNode.set("nid", t.get("pid"));
          newNode.set("name", t.get("name"));
          newNode.set("ntokens", t.get("ntokens"));
          newNode.set("isPlace", true);
          nod.add(newNode);
        });
        tbox.stream().forEach(t->{
          Tuple newNode = new Tuple(nod);
          newNode.set("nid", t.get("tid"));
          newNode.set("name", t.get("name"));
          newNode.set("ntokens", 0);
          newNode.set("isPlace", false);
          nod.add(newNode);
        });

        // translate sol2.arc table to sol3.arc table (ark)
        arc.stream().forEach(t->{
          Tuple newArc = new Tuple(ark);
          newArc.set("aid", t.get("aid"));
          newArc.set("cap", t.get("cap"));
          if (Boolean.parseBoolean(t.get("toTbox"))){
            newArc.set("startsAt", t.get("pid"));
            newArc.set("endsAt", t.get("tid"));
          } else {
            newArc.set("startsAt", t.get("tid"));
            newArc.set("endsAt", t.get("pid"));
          }
          ark.add(newArc);
        });

        db3.print(new PrintStream(args[1]));
    }
    
}
