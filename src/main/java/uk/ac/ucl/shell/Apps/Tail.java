package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Queue;

public class Tail extends Application {

    private int numOfLines;
    private String fileName;
    private Queue<String> tailLines;

    public Tail(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
        this.numOfLines = 10;
        this.tailLines = new java.util.LinkedList<>();
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new RuntimeException("tail: missing arguments");
        }
        if (args.size() > 1  && !args.get(0).equals("-n")) {
            throw new RuntimeException("tail: wrong argument " + args.get(0));
        }
        if (args.size() == 2 && input == null) {
            throw new RuntimeException("tail: wrong argument " + args.get(1));
        }
    }

    @Override
    protected void eval() throws IOException {
        loadOption();
        if (args.isEmpty() && input != null) {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(input));
            tail(reader);
        } else if (!args.isEmpty()) {
            BufferedReader reader = directory.createBufferedReader("tail", args.get(0));
            tail(reader);
        } else {
            throw new RuntimeException("tail: missing arguments");
        }
    }

    private void tail(BufferedReader reader) throws IOException {
        getTailLines(reader);
        for (String line : tailLines) {
            directory.writeLine(line, writer, lineSeparator);
        }
    }

    private void getTailLines(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            tailLines.add(line);
            if (tailLines.size() > numOfLines) {
                tailLines.remove();
            }
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
            throw new RuntimeException("tail: invalid option");
        }
    }
}
