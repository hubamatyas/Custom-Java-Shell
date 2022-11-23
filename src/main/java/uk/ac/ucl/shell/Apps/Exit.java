package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.shell.Evaluator;

public class Exit implements Application{


    public void exec(ArrayList<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        evaluator.exitProgram();
    }
    
    
}
