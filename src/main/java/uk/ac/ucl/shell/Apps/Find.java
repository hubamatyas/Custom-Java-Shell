package uk.ac.ucl.shell.Apps;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Find extends Application {
    private final Queue<String> toVisit;
    private String pattern;

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
        if(this.args.size() != maxArgs){
            throw new RuntimeException(appName + ": incorrect number of arguments");
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
            this.pattern = this.args.get(2);
        } else {
            this.toVisit.add("");
            this.pattern = this.args.get(1);
        } 
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
        Path absoluteDir = Paths.get(this.directory.getCurrentDirectory(), dir);
        DirectoryStream<Path> stream = Files.newDirectoryStream(absoluteDir, pattern);
        for (Path entry : stream) {
            this.terminal.writeLine(dir + File.separator + entry.getFileName().toString(), writer, lineSeparator);
        }
    }

    @Override
    protected void redirect() {}

    @Override
    protected void app(BufferedReader reader) throws IOException {}
}
