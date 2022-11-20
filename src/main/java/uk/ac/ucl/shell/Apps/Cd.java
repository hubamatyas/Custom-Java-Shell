package uk.ac.ucl.shell.Apps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import uk.ac.ucl.shell.Evaluator;

public class Cd extends Application {
    

    public void exec(ArrayList<String> appArgs, Evaluator evaluator) throws IOException {
        if (appArgs.isEmpty()) {
            throw new RuntimeException("cd: missing argument");
        } else if (appArgs.size() > 1) {
            throw new RuntimeException("cd: too many arguments");
        }
        String dirString = appArgs.get(0);
        File dir = new File(evaluator.getDirectory(), dirString);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("cd: " + dirString + " is not an existing directory");
        }
        evaluator.setDirectory(dir.getCanonicalPath());
    }
}
