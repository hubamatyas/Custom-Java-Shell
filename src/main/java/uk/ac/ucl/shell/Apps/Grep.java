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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.ac.ucl.shell.Shell;

public class Grep extends Application {

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
        Pattern grepPattern = Pattern.compile(args.get(0));

        verifyFiles(files);
        searchPattern(grepPattern, files);
    }

    private void verifyFiles(ArrayList<String> files) {
        for (String file : files) {
            directory.checkDirectoryToHandle("grep", file);
        }
    }

    private void searchPattern(Pattern grepPattern, ArrayList<String> files) {
        for (String file : files) {
            try (BufferedReader reader = Files.newBufferedReader(directory.getPathTo(file), directory.getEncoding())) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = grepPattern.matcher(line);
                    if (matcher.find()) {
                        if (files.size() > 1) {
                            writer.write(file);
                            writer.write(":");
                        }
                        writer.write(line);
                        writer.write(System.getProperty("line.separator"));
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException("grep: cannot open " + file);
            }
        }
    }
}
