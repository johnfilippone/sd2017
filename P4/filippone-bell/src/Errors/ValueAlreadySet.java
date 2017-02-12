package Errors;


public class ValueAlreadySet extends RuntimeException {
    public ValueAlreadySet(String error) {
        super(error);
    }
}
