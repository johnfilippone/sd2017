package Errors;

public class NoPinFound extends RuntimeException {
    public NoPinFound(String error) {
        super(error);
    }
}
