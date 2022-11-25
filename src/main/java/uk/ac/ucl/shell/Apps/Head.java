package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Head extends Application {

    private int headLines;
    private String fileName;

    public Head(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
        headLines = 10;
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
        getHeadLines();
    }

    private void getHeadLines() {
        if (directory.existsFile(fileName)) {
            try (BufferedReader reader = Files.newBufferedReader(directory.getPathTo(fileName), directory.getEncoding())) {
                for (int i = 0; i < headLines; i++) {
                    String line;
                    if ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.write(System.getProperty("line.separator"));
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("head: cannot open " + fileName);
            }
        } else {
            throw new RuntimeException("head: " + fileName + " does not exist");
        }
    }

    private void loadArgs() {
        if (args.size() == 3) {
            try {
                headLines = Integer.parseInt(args.get(1));
            } catch (Exception e) {
                throw new RuntimeException("head: wrong argument " + args.get(1));
            }
            fileName = args.get(2);
        } else {
            fileName = args.get(0);
        }
    }
}
