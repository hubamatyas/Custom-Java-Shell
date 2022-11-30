package uk.ac.ucl.shell.Exceptions;

public class InvalidByteRangeException extends AppException{
    public InvalidByteRangeException(){
        super("cut", "invalid byte range");
    }
}
