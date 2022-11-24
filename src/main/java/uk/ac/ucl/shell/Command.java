package uk.ac.ucl.shell;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Command {
    
    private String inputString;

    public Command(String inputString){
        this.inputString = inputString;
    }
    
    public abstract void eval(InputStream input, OutputStream output);
}
