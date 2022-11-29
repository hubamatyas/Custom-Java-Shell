package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep extends Application {
    private int numOfFiles;
    private Pattern pattern;
    private String currentFile;

    public Grep(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(appName, args, input, output);
        this.numOfFiles = 0;
        this.currentFile = "";
    }

    @Override
    protected void checkArgs() {
        if (args.size() < 2 && input == null) {
            throw new RuntimeException(this.appName + ": missing arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        setPattern();
        setIsPiped();
        redirect();
    }

    @Override
    protected void redirect() throws IOException {
        if (this.isPiped) {
            this.pipedCall();
        } else {
            verifyFiles(args);
            for (String arg : args) {
                this.currentFile = arg;
                this.simpleCall(arg);
            }
        }
    }

    @Override
    protected void app(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            searchPattern(line);
        }
    }

    private void verifyFiles(ArrayList<String> files) {
        for (String file : files) {
            this.directory.checkFileToHandle("grep", file);
        }
        this.numOfFiles = files.size();
    }


    private void searchPattern(String line) throws IOException {
        Matcher matcher = this.pattern.matcher(line);
        if (matcher.find()) {
            if (this.numOfFiles > 1) {
                this.terminal.writeLine(this.currentFile + ": " + line, writer, lineSeparator);
            } else {
                this.terminal.writeLine(line, writer, lineSeparator);
            }
        }
    }

    private void setPattern() {
        String pattern = this.args.remove(0);
        this.pattern = Pattern.compile(pattern);
    }
}
