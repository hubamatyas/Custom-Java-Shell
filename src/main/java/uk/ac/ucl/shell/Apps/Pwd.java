package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Pwd extends Application {

    public Pwd(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
    }

    @Override
    protected void checkArgs() {
        if (args.size() != 0 || input != null) {
            throw new RuntimeException("pwd: too many arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        writer.write(directory.getCurrentDirectory());
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}
