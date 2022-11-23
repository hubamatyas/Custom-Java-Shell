package uk.ac.ucl.shell;

import java.io.OutputStream;

public interface Command {
    
    public void eval(String input, OutputStream output);
}
