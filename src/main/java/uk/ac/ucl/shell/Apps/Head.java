package uk.ac.ucl.shell.Apps;

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
        if ((args.size() == 0 || args.size() == 2) && input != null) {
            args.add(input.toString());
        }
        if (args.isEmpty()) {
            throw new RuntimeException("head: missing arguments");
        }
        if (args.size() != 1 && args.size() != 3) {
            throw new RuntimeException("head: wrong arguments");
        }
        if (args.size() == 3 && !args.get(0).equals("-n")) {
            throw new RuntimeException("head: wrong argument " + args.get(0));
        }
    }

    @Override
    protected void eval() throws IOException {
        loadArgs();
        head();
    }

    private void head() throws IOException {
        // add numOfLines parameter to readFile?
        List<String> fileLines = directory.readFile("head", this.fileName);
        this.numOfLines = Math.min(this.numOfLines, fileLines.size());
        List<String> headLines = fileLines.subList(0, this.numOfLines);
        directory.writeFile(headLines, writer, lineSeparator);
    }

    private void loadArgs() {
        if (args.size() == 3) {
            try {
                this.numOfLines = Integer.parseInt(args.get(1));
            } catch (Exception e) {
                throw new RuntimeException("head: wrong argument " + args.get(1));
            }
            this.fileName = args.get(2);
        } else {
            this.fileName = args.get(0);
        }
    }
}
