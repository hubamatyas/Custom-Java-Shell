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
    protected void checkArgs() {}

    @Override
    protected void eval() throws IOException {
        for (String arg : args) {
            writer.write(arg);
            writer.write(" ");
            writer.flush();
        }

        if (!args.isEmpty()) {
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}
