package Application;


public class Main {
    
    public static void main(String args[]) {
        Framework.Factory instance;
        //instance = new DPlugIn.Factory(); 
        instance = new BIPlugIn.Factory(); 

        instance.NewGui().display();
    }
}
