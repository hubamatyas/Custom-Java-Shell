package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Queue;

public class Tail extends Application {
    private int numOfLines;
    private final Queue<String> tailLines;

    public Tail(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(appName, args, input, output);
        this.numOfLines = 10;
        this.tailLines = new java.util.LinkedList<>();
    }

    @Override
    protected void checkArgs() {
        if (this.args.isEmpty() && this.input == null) {
            throw new RuntimeException(this.appName + ": missing arguments");
        }
        if (this.args.size() > 1  && !this.args.get(0).equals("-n")) {
            throw new RuntimeException(this.appName + ": wrong argument " + args.get(0));
        }
        if (this.args.size() == 2 && this.input == null) {
            throw new RuntimeException(this.appName + ": wrong argument " + args.get(1));
        }
    }

    @Override
    protected void eval() throws IOException {
        loadOption();
        setIsPiped();
        redirect();
    }

    @Override
    protected void redirect() throws IOException {
        if (this.isPiped) {
            this.pipedCall();
        } else {
            this.simpleCall(this.args.get(0));
        }
    }

    @Override
    protected void app(BufferedReader reader) throws IOException {
        getTailLines(reader);
        for (String line : this.tailLines) {
            this.terminal.writeLine(line, writer, lineSeparator);
        }
    }

    private void getTailLines(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            this.tailLines.add(line);
            if (this.tailLines.size() > this.numOfLines) {
                this.tailLines.remove();
            }
        }
    }

    private void loadOption() {
        if (!this.args.isEmpty() && this.args.get(0).equals("-n")) {
            this.numOfLines = parseNumber(this.args.get(1));
            this.args.remove(0);
            this.args.remove(0);
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
