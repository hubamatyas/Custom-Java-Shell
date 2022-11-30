package uk.ac.ucl.shell.Exceptions;

public class MissingArgumentsException extends AppException{
    public MissingArgumentsException(String appName){
        super(appName, "missing arguments");
    }
}
