package uk.ac.ucl.shell.Apps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import uk.ac.ucl.shell.Shell;

public class Cd implements Application {

    public void exec(ArrayList<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        checkArgs(args);
        String dirString = args.get(0);
        File dir = new File(Shell.getDirectory(), dirString);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("cd: " + dirString + " is not an existing directory");
        }
        Shell.setDirectory(dir.getCanonicalPath());
    }

    private void checkArgs(ArrayList<String> args) {
        if (args.isEmpty()) {
            throw new RuntimeException("cd: missing argument");
        } else if (args.size() > 1) {
            throw new RuntimeException("cd: too many arguments");
        }
    }
}
