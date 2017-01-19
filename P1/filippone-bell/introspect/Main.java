package introspect;

public class Main {
  public static void main(String[] args) {
      String packageName = args[0];
      String pathToPackage = args[1];

      System.out.println(packageName);
      System.out.println(pathToPackage);

      Class c = Class.forName("introspect.Main"); 
      System.out.println(c.getName());
      
  }
}

