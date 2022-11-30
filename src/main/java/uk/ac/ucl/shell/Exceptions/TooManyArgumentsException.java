package uk.ac.ucl.shell.Exceptions;

public class TooManyArgumentsException extends AppException{
    public TooManyArgumentsException(String appname){
        super(appname, "too many arguments");
    }
}
