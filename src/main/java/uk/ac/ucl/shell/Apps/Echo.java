package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Echo implements Application{

    public void exec(ArrayList<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        for (String arg : args) {
            output.write(arg);
            output.write(" ");
            output.flush();
        }

        if (!args.isEmpty()) {
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }
}
