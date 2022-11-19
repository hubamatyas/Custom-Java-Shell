package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.shell.Evaluator;

public class Pwd extends Application {
    
    public void exec(ArrayList<String> appArgs, OutputStreamWriter writer) throws IOException {
        writer.write(Evaluator.getInstance().getDirectory());
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}
