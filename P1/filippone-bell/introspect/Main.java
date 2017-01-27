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
        
        // stub code for prolog files to set up tables
        StringBuilder output = new StringBuilder();
        // append the basic info
        output.append("dbase(prog1,[bcClass,bcMember]).\n\n");
        output.append("table(bcClass,[cid,\"name\",\"superName\"]).\n");
        output.append("table(bcMember,[mid,cid,static,\"type\",\"sig\"]).\n\n");

        // find all class files in package
        File dir = new File(pathToPackage);
        File[] fList = dir.listFiles();

        // fill out the rest of the prolog file
        int classID = 0;
        int memberID = 0;
        for (File file : fList) {
            if (file.getName().substring(file.getName().lastIndexOf('.')+1).equals("class") && !file.getName().contains("$")) {
                
                // get class
                Class<?> c = Class.forName(packageName + "." + file.getName().substring(0,file.getName().lastIndexOf('.')));
                output.append(String.format("bcClass(c%d,'%s','%s').\n", classID, c.getName(), c.getSuperclass().getSimpleName()));
                
                memberID = 0;
                // get constructors
                Constructor<?>[] constructors = c.getDeclaredConstructors();
                for (Constructor<?> constructor : constructors) {
                    if (Modifier.isPublic(constructor.getModifiers())) {
                        String signature = getSignature(constructor.getParameterTypes());
                        output.append(String.format("bcMember(m%d,c%d,true,'%s','%s').\n", memberID, classID, c.getName(), c.getSimpleName() + signature));
                        memberID += 1;
                    }
                }
                
                // get fields
                Field[] fields = c.getDeclaredFields();
                for (Field field : fields) {
                    if (Modifier.isPublic(field.getModifiers())) {
                        boolean isStatic = Modifier.isStatic(field.getModifiers());
                        output.append(String.format("bcMember(m%d,c%d,%s,'%s','%s').\n", memberID, classID, isStatic, field.getType().getSimpleName(), field.getName()));
                        memberID += 1;
                    }
                }

                // get methods
                Method[] methods = c.getDeclaredMethods();
                for (Method method : methods) {
                    if (Modifier.isPublic(method.getModifiers())) {
                        String signature = getSignature(method.getParameterTypes());
                        boolean isStatic = Modifier.isStatic(method.getModifiers());
                        output.append(String.format("bcMember(m%d,c%d,%s,'%s','%s').\n", memberID, classID, isStatic, method.getReturnType().getSimpleName(), method.getName() + signature));
                        memberID += 1;
                    }
                }

                classID += 1;
            }
        }

        return output.toString();
    }
    
    private static String getSignature(Class<?>[] parameters) {
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append("(");
        for (Class<?> param : parameters) {
             signatureBuilder.append(param.getSimpleName());
             signatureBuilder.append(",");
        }
        String signature = signatureBuilder.toString();
        signature = signature.substring(0, signature.length() - 1);
        return signature + ")";
    }


}
