package introspect;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        String packageName = args[0];
        String pathToPackage = args[1];

        // TODO: Open up a new file for writing, write the header info
        // This could be done in a separate method possibly

        File dir = new File(pathToPackage);
        File[] fList = dir.listFiles();
        for (File file : fList) {

            String filename = file.getName();
            String extension = filename.substring(filename.length() - 6);

            System.out.println(filename);
            System.out.println(extension);

            if (extension.equals(".class") && file.isFile()) {
                System.out.println("Class file found!");
            }

            // TODO: IF substring of last 6 characters is .class,
            // call forName and add info to file -- maybe do this
            // in separate method, dumpClassData(String className, outfile)
        }
        
    }

    // TODO: Implement a method similar to sudo code below and run for
    // each class file
    /*
    print class info to Prolog file
    int memberCount = 0;
    for (Constructor : filename.getDeclaredConstructors()) {
        print Prolog info
        increment member count
    }
    Do same for fields
    Do same for methods
    */
        

}

