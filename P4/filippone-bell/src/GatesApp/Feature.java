package GatesApp;


public @interface Feature {
    
   public static boolean   base        = true;
   public static boolean   tables      = true;
   public static boolean   constraints = true;
   public static boolean   eval        = true;
   
   boolean value();
}
