package uk.ac.ucl.shell.Commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Command {
    
    private String inputString;

    /**
     * @param inputString : <code> String </code> to be evaluated
     */
    public Command(String inputString){
        this.inputString = inputString;
    }
    
    /**
     * Evaluates the <code>inputString</code> that was passed into the constructor
     * @param input : nullable <code> InputStream </code> to substitute missing file argument, primarly used for piping
     * @param output : <code> OutputStream </code> to write the evaluation of the command to
     */
    public abstract void eval(InputStream input, OutputStream output) throws IOException;

    protected String getInputString(){
        return inputString;
    }
}
