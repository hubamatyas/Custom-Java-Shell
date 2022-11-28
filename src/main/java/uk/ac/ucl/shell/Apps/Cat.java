package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Cat extends Application {
    public Cat(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new RuntimeException("cat: missing arguments");
        }
        if (!args.isEmpty() && input != null) {
            throw new RuntimeException("cat: cannot read from both file and input");
        }
    }

    @Override
    protected void eval() throws IOException {
        if (args.isEmpty()) {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(input));
            cat(reader);
        } else {
            for (String arg : args) {
                BufferedReader reader = directory.createBufferedReader("cat", arg);
                cat(reader);
            }
        }
    }

    private void cat(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            directory.writeLine(line, writer, lineSeparator);
        }
    }
}
