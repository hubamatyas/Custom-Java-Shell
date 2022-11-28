package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Echo extends Application {

    public Echo(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
    }

    @Override
    protected void checkArgs() {
        if (input != null) {
            throw new RuntimeException("echo: cannot pipe output");
        }
    }

    @Override
    protected void eval() throws IOException {
        directory.writeFile(args, writer, " ");
        if (!args.isEmpty()) {
            directory.writeNewLine(writer);
        }
    }
}
