package uk.ac.ucl.shell.Exceptions;

public class UnknownAppException extends AppException {
    public UnknownAppException(String appName){
        super(appName, "unknown application");
    }
}
