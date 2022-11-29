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
    public Grep(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
        this.numOfFiles = 0;
    }

    @Override
    protected void checkArgs() {
        if (args.size() < 2 && input == null) {
            throw new RuntimeException("grep: missing arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        setPattern();
        if (args.isEmpty()) {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(input));
            grep(reader, "");
        } else {
            verifyFiles(args);
            for (String arg : args) {
                BufferedReader reader = directory.createBufferedReader("grep", arg);
                grep(reader, arg);
            }
        }
    }

    private void verifyFiles(ArrayList<String> files) {
        for (String file : files) {
            directory.checkFileToHandle("grep", file);
        }
        this.numOfFiles = files.size();
    }

    private void grep(BufferedReader reader, String file) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            searchPattern(line, file);
        }
    }

    private void searchPattern(String line, String file) throws IOException {
        Matcher matcher = this.pattern.matcher(line);
        if (matcher.find()) {
            if (numOfFiles > 1) {
                directory.writeLine(file + ": " + line, writer, lineSeparator);
            } else {
                directory.writeLine(line, writer, lineSeparator);
            }
        }
    }

    private void setPattern() {
        String pattern = args.remove(0);
        this.pattern = Pattern.compile(pattern);
    }
}
