package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Pwd implements Application {

    public void exec(ArrayList<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        output.write(Shell.getDirectory());
        output.write(System.getProperty("line.separator"));
        output.flush();
    }
}
