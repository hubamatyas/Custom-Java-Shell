package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.util.ArrayList;

import uk.ac.ucl.shell.Evaluator;

public class Exit extends Application{

	@Override
    public void exec(ArrayList<String> appArgs, Evaluator evaluator) throws IOException {
        evaluator.exitProgram();
    }
    
    
}
