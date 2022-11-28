package uk.ac.ucl.shell.Apps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Find extends Application {

    private final Queue<String> toVisit;
    private Pattern findPattern;
    public Find(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
        toVisit = new LinkedList<>();
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input != null) {
            args.add(input.toString());
            // TODO: piping
        }
        if (args.size() < 2) {
            throw new RuntimeException("find: missing arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        setParams();
        find();
    }

    private void setParams() {
        if (!args.get(0).equals("-name")) {
            toVisit.add(args.get(0));
            findPattern = parsePattern(args.get(2));
        } else {
            toVisit.add("");
            findPattern = parsePattern(args.get(1));
        }
    }

    private Pattern parsePattern(String rawPattern) {
        rawPattern = rawPattern.replace(".", "\\.");
        rawPattern = rawPattern.replace("*", ".*");
        return Pattern.compile(rawPattern);
    }

    private void find() throws IOException {
        if (toVisit.isEmpty()) {
            return;
        }

        String nextDir = toVisit.remove();
        for (String child : directory.getSubDirectories("find", nextDir)) {
            toVisit.add(nextDir + File.separator + child);
            find();
        }
        findPattern(nextDir);
    }

    private void findPattern(String dir) throws IOException {
        ArrayList<String> files = directory.getFiles("find", dir);
        for (String file : files) {
            Matcher matcher = findPattern.matcher(file);
            if (matcher.find()) {
                directory.writeLine(dir + File.separator + file, writer, lineSeparator);
            }
        }
    }
}
