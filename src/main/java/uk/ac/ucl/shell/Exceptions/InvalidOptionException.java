package uk.ac.ucl.shell.Exceptions;

public class InvalidOptionException extends AppException {
    public InvalidOptionException(String appName, String option){
        super(appName, "invalid option " + option);
    }
}
