package uk.ac.ucl.shell.Commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Command {
    
    private String inputString;

    public Command(String inputString){
        this.inputString = inputString;
    }
    
    public abstract void eval(InputStream input, OutputStream output) throws IOException;

    protected String getInputString(){
        return inputString;
    }
}
