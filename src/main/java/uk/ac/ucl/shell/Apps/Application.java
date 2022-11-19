package uk.ac.ucl.shell.Apps;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public abstract class Application {
    
    public Application(){};

    public abstract void exec(ArrayList<String> appArgs, OutputStreamWriter writer) throws IOException;

}
