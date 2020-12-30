package pl.wsb.arkadiusz.stanislaw.lega.springnotebookapp.exceptions;

public class UnauthorizedException extends Exception{

    public UnauthorizedException() {
        super();
    }
    public UnauthorizedException(String message) {
        super(message);
    }
}
