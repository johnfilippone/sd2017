package introspect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main { 

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // parse args
        String packageName = args[0];
        String pathToPackage = args[1];
        
        // get tables
        String tables = getTables(packageName, pathToPackage);
        
        // create prolog file
        FileWriter fw = new FileWriter("results/" + packageName + ".prog1.pl");
        fw.write(tables);
        fw.close();
    }

    public static String getTables(String packageName, String pathToPackage) throws ClassNotFoundException {
        
        String output = "dbase(prog1,[bcClass,bcMember]).\n\ntable(bcClass,[cid,\"name\",\"superName\"]).\ntable(bcMember,[mid,cid,static,\"type\",\"sig\"]).\n\n";

        // find all class files in package
        File dir = new File(pathToPackage);
        File[] fList = dir.listFiles();


        int classID = 0;
        int memberID = 0;
        for (File file : fList) {
            if (file.getName().substring(file.getName().lastIndexOf('.')+1).equals("class")) {
                
                // get class
                Class<?> c = Class.forName(packageName + "." + file.getName().substring(0,file.getName().lastIndexOf('.')));
                output += String.format("bcClass(c%d,'%s','%s').\n", classID, c.getName(), c.getSuperclass().getSimpleName());
                
                
                memberID = 0;
                // get constructors
                Constructor<?>[] constructors = c.getDeclaredConstructors();
                for (Constructor<?> constructor : constructors) {
                    if (Modifier.isPublic(constructor.getModifiers())) {
                        String signiture = getSigniture(constructor.getParameterTypes());
                        output += String.format("bcMember(m%d,c%d,true,'%s','%s').\n", memberID, classID, c.getName(), c.getSimpleName() + signiture);
                        memberID += 1;
                    }
                }
                
                // get fields
                Field[] fields = c.getDeclaredFields();
                for (Field field : fields) {
                    if (Modifier.isPublic(field.getModifiers())) {
                        output += String.format("bcMember(m%d,c%d,%s,'%s','%s').\n", memberID, classID, Modifier.isStatic(field.getModifiers()), field.getType().getSimpleName(), field.getName());
                        memberID += 1;
                    }
                }

                // get methods
                Method[] methods = c.getDeclaredMethods();
                for (Method method : methods) {
                    if (Modifier.isPublic(method.getModifiers())) {
                        String signiture = getSigniture(method.getParameterTypes());
                        output += String.format("bcMember(m%d,c%d,%s,'%s','%s').\n", memberID, classID, Modifier.isStatic(method.getModifiers()), method.getReturnType().getSimpleName(), method.getName() + signiture);
                        memberID += 1;
                    }
                }

                classID += 1;
            }
        }

        return output;
    }
    
    private static String getSigniture(Class<?>[] parameters) {
        String sig = "(";
        for (Class<?> param : parameters) {
            sig += param.getSimpleName() + ",";
        }
        return sig.substring(0,sig.length()-1) + ")";
    }


}
