package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Head extends Application {

    private int numOfLines;
    private String fileName;

    public Head(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        // add name of application to super
        super(args, input, output);
        this.numOfLines = 10;
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new RuntimeException("head: missing arguments");
        }
        if (args.size() > 1  && !args.get(0).equals("-n")) {
            throw new RuntimeException("head: wrong argument " + args.get(0));
        }
        if (args.size() == 2 && input == null) {
            throw new RuntimeException("head: wrong argument " + args.get(1));
        }
    }

    @Override
    protected void eval() throws IOException {
        loadOption();
        if (args.isEmpty() && input != null) {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(input));
            head(reader);
        } else if (!args.isEmpty()) {
            BufferedReader reader = directory.createBufferedReader("head", args.get(0));
            head(reader);
        } else {
            throw new RuntimeException("head: missing arguments");
        }
    }

    private void head(BufferedReader reader) throws IOException {
        int count = 0;
        while (reader.ready() && count < numOfLines) {
            String line = reader.readLine();
            directory.writeLine(line, writer, lineSeparator);
            count++;
        }
    }

    private void loadOption() {
        if (!args.isEmpty() && args.get(0).equals("-n")) {
            this.numOfLines = parseNumber(args.get(1));
            args.remove(0);
            args.remove(0);
        }
    }

    private int parseNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw new RuntimeException("head: invalid option");
        }
    }
}
