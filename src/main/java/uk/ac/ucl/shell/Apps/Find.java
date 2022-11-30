package uk.ac.ucl.shell.Apps;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

public class Find extends Application {
    private final Queue<String> toVisit;
    private boolean relative;
    private String pattern;

    public Find(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(appName, args, input, writer);
        this.toVisit = new LinkedList<>();
        this.relative = false;
    }

    @Override
    protected void checkArgs() {
        if(this.args.size() == 0){
            throw new MissingArgumentsException(appName);
        }

        int maxArgs = this.args.get(0).equals("-name") ? 2 : 3;
        if (this.args.size() < maxArgs) {
           throw new MissingArgumentsException(appName);
        } else if(this.args.size() > maxArgs){
            throw new TooManyArgumentsException(appName);
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
            this.relative = true;
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
            String line = dir + File.separator + entry.getFileName().toString();
            if (this.relative) {
                this.terminal.writeLine(line, writer, lineSeparator);
            } else {
                this.terminal.writeLine("." + line, writer, lineSeparator);
            }
        }
    }

    @Override
    protected void redirect() {}

    @Override
    protected void app(BufferedReader reader) throws IOException {}
}
