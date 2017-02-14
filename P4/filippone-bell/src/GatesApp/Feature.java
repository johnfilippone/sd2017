package GatesApp;


public @interface Feature {
    
   public static boolean   base        = true;
   public static boolean   tables      = false;
   public static boolean   constraints = false;
   public static boolean   eval        = false;
   
   boolean value();
}
