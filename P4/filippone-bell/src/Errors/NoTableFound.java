package Errors;


public class NoTableFound extends RuntimeException {
    public NoTableFound(String error) {
        super(error);
    }
}
