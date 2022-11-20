package uk.ac.ucl.shell.Apps;
import java.io.IOException;
import java.util.ArrayList;

import uk.ac.ucl.shell.Evaluator;

public abstract class Application {
    
    public Application(){};

    protected Evaluator evaluator;

    public abstract void exec(ArrayList<String> appArgs, Evaluator evaluator) throws IOException;

}
