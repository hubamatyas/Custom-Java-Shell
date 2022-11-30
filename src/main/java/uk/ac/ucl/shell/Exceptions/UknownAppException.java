package uk.ac.ucl.shell.Exceptions;

public class UknownAppException extends AppException {
    public UknownAppException(String appName){
        super(appName, "uknown application");
    }
}
