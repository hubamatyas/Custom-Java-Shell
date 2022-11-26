package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Tail extends Application {

    private int numOfLines;
    private String fileName;

    public Tail(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
        numOfLines = 10;
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
        tail();
    }

    private void tail() throws IOException {
        List<String> fileLines = directory.readFile("head", fileName);
        numOfLines = Math.max(fileLines.size() - numOfLines, 0);
        List<String> tailLines = fileLines.subList(numOfLines, fileLines.size());
        directory.writeFile(tailLines, writer, lineSeparator);
    }

    private void loadArgs() {
        if (args.size() == 3) {
            try {
                numOfLines = Integer.parseInt(args.get(1));
            } catch (Exception e) {
                throw new RuntimeException("head: wrong argument " + args.get(1));
            }
            fileName = args.get(2);
        } else {
            fileName = args.get(0);
        }
    }

    /*
    private void getIndex(ArrayList<String> storage) {
        if (numOfLines > storage.size()) {
            index = 0;
        } else {
            index = storage.size() - numOfLines;
        }
    } */
}
