package introspect;

import java.io.File;

public class Main {
  public static void main(String[] args) {
      String packageName = args[0];
      String pathToPackage = args[1];

      File dir = new File(pathToPackage);
      File[] fList = dir.listFiles();
      for (File file : fList){
          System.out.println(file.getName());
      }
      
  }
}

