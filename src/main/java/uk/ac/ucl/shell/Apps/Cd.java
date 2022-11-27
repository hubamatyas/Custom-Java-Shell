package uk.ac.ucl.shell.Apps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import uk.ac.ucl.shell.Shell;

public class Cd extends Application {
    public Cd(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
    }

    @Override
    protected void eval() throws IOException {
        String subDir = args.get(0);
        directory.checkDirectoryToHandle("cd", subDir);
        directory.setCurrentDirectory(String.valueOf(directory.getPathTo(subDir)));
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty()) {
            throw new RuntimeException("cd: missing argument");
        } else if (args.size() > 1) {
            throw new RuntimeException("cd: too many arguments");
        }
    }
}
