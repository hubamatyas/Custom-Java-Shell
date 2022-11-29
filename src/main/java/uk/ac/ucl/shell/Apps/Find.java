package uk.ac.ucl.shell.Apps;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Find extends Application {
    private final Queue<String> toVisit;
    private Pattern pattern;

    public Find(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(appName, args, input, writer);
        this.toVisit = new LinkedList<>();
    }

    @Override
    protected void checkArgs() {
        if (this.args.size() < 2) {
            throw new RuntimeException(appName + ": missing arguments");
        }
        int maxArgs = this.args.get(0).equals("-name") ? 2 : 3;
        if(this.args.size() > maxArgs){
            throw new RuntimeException(appName + ": too many arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        setParams();
        find();
    }

    private void setParams() {
        if (!this.args.get(0).equals("-name")) {
            this.toVisit.add(this.args.get(0));
            this.pattern = parsePattern(this.args.get(2));
        } else {
            this.toVisit.add("");
            this.pattern = parsePattern(this.args.get(1));
        } 
    }

    private Pattern parsePattern(String rawPattern) {
        rawPattern = rawPattern.replace(".", "\\.");
        rawPattern = rawPattern.replace("*", ".*");
        return Pattern.compile(rawPattern);
    }

    private void find() throws IOException {
        if (this.toVisit.isEmpty()) {
            return;
        }

        String nextDir = this.toVisit.remove();
        for (String child : this.directory.getSubDirectories("find", nextDir)) {
            this.toVisit.add(nextDir + File.separator + child);
            find();
        }
        findPattern(nextDir);
    }

    private void findPattern(String dir) throws IOException {
        ArrayList<String> files = this.directory.getFiles("find", dir);
        for (String file : files) {
            Matcher matcher = this.pattern.matcher(file);
            if (matcher.find()) {
                this.terminal.writeLine(dir + File.separator + file, writer, lineSeparator);
            }
        }
    }

    @Override
    protected void redirect() {}

    @Override
    protected void app(BufferedReader reader) throws IOException {}
}
