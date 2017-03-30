/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package h6;

import PrologDB.*;
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
        
        // translate sol2.arc table to sol3.arc table (ark)

        db3.print(new PrintStream(args[1]));
    }
    
}
