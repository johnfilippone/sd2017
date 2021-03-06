package VPL;

import PrologDB.DB;
import PrologDB.ErrorReport;
import PrologDB.Table;
import PrologDB.Tuple;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Conform {

    static private DB vpl;
    static private Table violetClass;
    static private Table violetInterface;
    static private Table violetAssociation;
    static private Table violetMiddleLabels;

    /**
     * @param args -- single path to vpl database which is to be tested for conformance
     */
    public static void main(String... args) {
        if (args.length != 1 || !args[0].endsWith(".vpl.pl")) {
            System.out.format("Usage: %s <file.vpl.pl>\n", Conform.class.getName());
            System.out.format("       <file.vpl.pl> will be checked for constraint violations\n");
            return;
        }
        try {
            ErrorReport er = new ErrorReport();

            // initialize the vpl database and tables
            vpl = DB.readDataBase(args[0]);
            violetClass = vpl.getTable("violetClass");
            violetInterface = vpl.getTable("violetInterface");
            violetAssociation = vpl.getTable("violetAssociation");
            violetMiddleLabels = vpl.getTable("violetMiddleLabels");
            
            // following are rules/constraints to check on vpl database
            // each rule (constraint) has its own static error method below
            

            // MiddleLabel Rule: each MiddleLable tuple generates an error
            violetMiddleLabels.stream().forEach(t->er.add(middleLabel(t)));

            // Unique Names Constraint: Classes and Interfaces have unique names constraint
            violetClass.stream()
                    .filter(t1->violetClass.stream()
                    .filter(t2->t2.is("name",t1.get("name"))).count() > 1)
                    .forEach(t->er.add(ciShareName("multiple classes", t)));
            violetInterface.stream()
                    .filter(t1->violetClass.stream()
                    .filter(t2->t2.is("name",t1.get("name"))).count() > 1)
                    .forEach(t->er.add(ciShareName("classes and interfaces", t)));
            violetInterface.stream()
                    .filter(t1->violetInterface.stream()  
                    .filter(t2->t2.is("name",t1.get("name"))).count() > 1)
                    .forEach(t->er.add(ciShareName("multiple interfaces", t)));

            //  Null Names Constraint: classes and interfaces cannot have null names
            violetClass.stream()
                    .filter(t->t.get("name").equals(""))
                    .forEach(t->er.add(nullName("class", t)));
            violetInterface.stream()
                    .filter(t->t.get("name").equals(""))
                    .forEach(t->er.add(nullName("interface", t)));

            // Black Diamond Constraint: if a black diamond has a cardinality, it must be 1
            violetAssociation.stream()
                    .filter(t->t.get("arrow1").equals("BLACK_DIAMOND"))
                    .filter(t->!getCardinality(t.get("role1")).equals("1"))
                    .filter(t->!getCardinality(t.get("role1")).equals(""))
                    .forEach(t->er.add(blackDiamond(t)));
            violetAssociation.stream()
                    .filter(t->t.get("arrow2").equals("BLACK_DIAMOND"))
                    .filter(t->!getCardinality(t.get("role2")).equals("1"))
                    .filter(t->!getCardinality(t.get("role2")).equals(""))
                    .forEach(t->er.add(blackDiamond(t)));
            
            // Diamond Constraint: if a diamond has a cardinality, it must be 0..1
            violetAssociation.stream()
                    .filter(t->t.get("arrow1").equals("DIAMOND"))
                    .filter(t->!getCardinality(t.get("role1")).equals("0..1"))
                    .filter(t->!getCardinality(t.get("role1")).equals(""))
                    .forEach(t->er.add(diamond(t)));
            violetAssociation.stream()
                    .filter(t->t.get("arrow2").equals("DIAMOND"))
                    .filter(t->!getCardinality(t.get("role2")).equals("0..1"))
                    .filter(t->!getCardinality(t.get("role2")).equals(""))
                    .forEach(t->er.add(diamond(t)));

            // Triangle Constraint: no Triangle association can have anything other than '' for its other arrow 
            violetAssociation.stream()
                    .filter(t->t.get("arrow1").equals("TRIANGLE"))
                    .filter(t->!t.get("arrow2").equals(""))
                    .forEach(t->er.add(arrow(t)));
            violetAssociation.stream()  
                    .filter(t->t.get("arrow2").equals("TRIANGLE"))
                    .filter(t->!t.get("arrow1").equals(""))
                    .forEach(t->er.add(arrow(t)));

            // No Labels In Inheritance Constraint: inheritance associations cannot have non-empty roles
            violetAssociation.stream()
                    .filter(t->t.get("arrow1").equals("TRIANGLE") || t.get("arrow2").equals("TRIANGLE"))
                    .filter(t->!t.get("role1").equals("") || !t.get("role2").equals(""))
                    .forEach(t->er.add(noRoles(t)));

            // Solid Association Constraint: non-implements, non-extends association must be solid
            violetAssociation.stream()
                    .filter(t->!t.get("arrow1").equals("TRIANGLE"))
                    .filter(t->!t.get("arrow2").equals("TRIANGLE"))
                    .filter(t->!t.get("lineStyle").equals(""))
                    .forEach(t->er.add(noDottedAssoc(t)));

            // Extends Constraint: extends relationships must be solid
            violetAssociation.stream()
                    .filter(t->t.get("arrow1").equals("TRIANGLE") || t.get("arrow2").equals("TRIANGLE"))
                    .filter(t->t.get("type1").equals("classnode"))
                    .filter(t->t.get("type2").equals("classnode"))
                    .filter(t->!t.get("lineStyle").equals(""))
                    .forEach(t->er.add(dotted(t)));

            // Implements Constraint1: implementation relationships must be dotted
            violetAssociation.stream()
                    .filter(t->t.get("lineStyle").equals(""))
                    .filter(t->t.get("type1").equals("classnode"))
                    .filter(t->t.get("type2").equals("interfacenode"))
                    .filter(t->t.get("arrow2").equals("TRIANGLE"))
                    .forEach(t->er.add(dotted(t)));
            violetAssociation.stream()
                    .filter(t->t.get("lineStyle").equals(""))
                    .filter(t->t.get("type2").equals("classnode"))
                    .filter(t->t.get("type1").equals("interfacenode"))
                    .filter(t->t.get("arrow1").equals("TRIANGLE"))
                    .forEach(t->er.add(dotted(t)));

            // Implements Constraint2: only classes can implement interfaces
            violetAssociation.stream()
                    .filter(t->t.get("arrow1").equals("TRIANGLE"))
                    .filter(t->t.get("lineStyle").equals("DOTTED"))
                    .filter(t->t.get("type1").equals("classnode"))
                    .filter(t->t.get("type2").equals("interfacenode"))
                    .forEach(t->er.add(impls(t)));
            violetAssociation.stream()
                    .filter(t->t.get("arrow2").equals("TRIANGLE"))
                    .filter(t->t.get("lineStyle").equals("DOTTED"))
                    .filter(t->t.get("type2").equals("classnode"))
                    .filter(t->t.get("type1").equals("interfacenode"))
                    .forEach(t->er.add(impls(t)));

            // Self Inheritance Rule: no class or interface can inherit from itself
            violetAssociation.stream()
                    .filter(t->t.get("arrow1").equals("TRIANGLE") || t.get("arrow2").equals("TRIANGLE"))
                    .filter(t->t.get("cid1").equals(t.get("cid2")))
                    .forEach(t->er.add(selfInherit(t)));
          


            // report errors
            er.printReport(System.out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*  error messages */

    private static String middleLabel(Tuple t) {
        String e = String.format("association (%s - %s) has middle label %s",
                convert(t.get("cid1")), convert(t.get("cid2")), t.get("label"));
        return e;
    }

    /** parameter variant has 1 of 3 values "multiple classes" or "multiple interfaces"  or "classes and interfaces **/
    private static String ciShareName(String variant, Tuple t) {
        String e  = variant + " share the same name: " + t.get("name");
        return e;
    }
    
    /** parameter variant has 1 of 2 values "class" or "interface" **/
    private static String nullName(String variant, Tuple t) {
        String e = variant + " with id=" + t.get("id") + " has null name";
        return e;
    }

    private static String blackDiamond(Tuple t) {
        String e = String.format("black diamond on association (%s - %s) does not have cardinality 1",
                convert(t.get("cid1")), convert(t.get("cid2")));
        return e;
    }
    
    private static String diamond(Tuple t) {
        String e = String.format("diamond on association (%s - %s) does not have cardinality 0..1",
                convert(t.get("cid1")), convert(t.get("cid2")));
        return e;
    }
    
    private static String arrow(Tuple t) {
        String e = String.format("inheritance paired with non-null end (%s - %s)", convert(t.get("cid1")), convert(t.get("cid2")));
        return e;
    }

    private static String noRoles(Tuple t) {
        String e = String.format("inheritance (%s - %s) should have no roles", convert(t.get("cid1")), convert(t.get("cid2")));
        return e;
    }

    private static String noDottedAssoc(Tuple t) {
        String e = String.format("association (%s - %s) cannot be dotted", convert(t.get("cid1")), convert(t.get("cid2")));
        return e;
    }

    private static String dotted(Tuple t) {
        String e;
        e = String.format("dotted inheritance cannot connect %s to %s", convert(t.get("cid1")), convert(t.get("cid2")));
        return e;
    }

    private static String impls(Tuple t) {
        String e;
        if (t.is("type1", "classnode")) {
            e = String.format("interface %s cannot implement class %s", t.get("cid2"), t.get("cid1"));
        } else {
            e = String.format("interface %s cannot implement class %s", t.get("cid1"), t.get("cid2"));
        }
        return e;
    }

    private static String selfInherit(Tuple t) {
        String e = String.format("%s cannot inherit from itself", convert(t.get("cid1")));
        return e;
    }


    /* utilities needed for precise error reporting */

    /* I give one below: converts a class or interface
     * id into the name of a class or interface
     * @param id -- string id of a class or interface
     * @return name of the corresponding class or interface
     */

    private static String convert(String id) {
        Predicate<Tuple> idtest = r -> r.is("id", id);
        // We had to fix this, if wasn't found in classes,
        // name would be called on null and would throw
        // an error which got suppressed. Took a while
        Tuple match = violetClass.getFirst(idtest);
        if (match == null) {
            match = violetInterface.getFirst(idtest);
        }
        String n = match.get("name");
        // should not be null... which also would be an error
        // I'm not checking this but you might...
        return n;
    }

    private static String getCardinality(String role) {
        /* inputs: role
         * output: cardinality
         * 
         * a role can take one of three forms
         *      cardinality
         *      label
         *      cardinality label
         * 
         * this function parses any role of these forms and
         * returns the cardinality as a string
         *
         * if the role has no cardinality, an empty string 
         * is returned
        */
        String[] split = role.split("\\s+");
        
        // Make the assumption that if the role has a cardinality and a label
        // then the cardinality is given first
        if (split.length > 1) return split[0];

        Pattern p;
        Matcher m;
        
        //is any integer
        p = Pattern.compile("\\d+");
        m = p.matcher(split[0]);
        if (m.matches()) return split[0];

        //is int..int
        p = Pattern.compile("\\d+..\\d+");
        m = p.matcher(split[0]);
        if (m.matches()) return split[0];

        //is int..*
        p = Pattern.compile("\\d+..*");
        m = p.matcher(split[0]);
        if (m.matches()) return split[0];

        //is *
        if (split[0].equals("*")) return split[0];


        //null cardinality
        return "";
    }

}
