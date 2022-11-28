package uk.ac.ucl.shell.Apps;

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
        directory.writeLine(directory.getCurrentDirectory(), writer, lineSeparator);
    }
}
