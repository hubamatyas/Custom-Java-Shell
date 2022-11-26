package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.ac.ucl.shell.Shell;

public class Grep extends Application {

    private int numOfFiles;
    private Pattern grepPattern;
    public Grep(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
    }

    @Override
    protected void checkArgs() {
        if (args.size() == 1 && input != null) {
            args.add(input.toString());
        }
        if (args.size() < 2) {
            throw new RuntimeException("grep: missing arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        ArrayList<String> files = new ArrayList<>(args.subList(1, args.size()));
        verifyFiles(files);
        setPattern();
        grep(files);
    }

    private void verifyFiles(ArrayList<String> files) {
        for (String file : files) {
            directory.checkFileToHandle("grep", file);
        }
        numOfFiles = files.size();
    }

    private void grep(ArrayList<String> files) throws IOException {
        for (String file : files) {
            List<String> fileLines = directory.readFile("grep", file);
            searchPattern(fileLines, file);
        }
    }

    private void searchPattern(List<String> fileLines, String file) throws IOException {
        for (String line : fileLines) {
            Matcher matcher = grepPattern.matcher(line);
            if (matcher.find()) {
                if (numOfFiles > 1) {
                    directory.writeLine(file + ": " + line, writer, lineSeparator);
                } else {
                    directory.writeLine(line, writer, lineSeparator);
                }
            }
        }
    }

    private void setPattern() {
        String pattern = args.get(0);
        grepPattern = Pattern.compile(pattern);
    }
}
