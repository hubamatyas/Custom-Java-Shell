package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

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
            catPiped();
        } else {
            cat();
        }
    }

    private void catPiped() throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(input));
        String fileName;
        while ((fileName = reader.readLine()) != null) {
            readFromFile(fileName);
        }
    }

    private void cat() throws IOException {
        for (String arg : args) {
            readFromFile(arg);
        }
    }

    private void readFromFile(String fileName) throws IOException {
        BufferedReader reader = directory.createBufferedReader("cat", fileName);
        String line;
        while ((line = reader.readLine()) != null) {
            directory.writeLine(line, writer, lineSeparator);
        }
    }
}
