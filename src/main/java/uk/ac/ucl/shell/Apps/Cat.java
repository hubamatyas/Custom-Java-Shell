package uk.ac.ucl.shell.Apps;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import uk.ac.ucl.shell.Shell;

public class Cat extends Application {
    public Cat(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new RuntimeException("cat: missing arguments");
        }

        if (args.isEmpty()) {
            args.add(input.toString());
        }
    }

    @Override
    protected void eval() {
        for (String arg : args) {
            // throw exception in Directory?
            if (!directory.existsFile(arg)) {
                throw new RuntimeException("cat: file does not exist");
            }
            try (BufferedReader reader = Files.newBufferedReader(directory.getPathTo(arg), directory.getEncoding())) {
                outputFile(reader);
            }
            catch (IOException e) {
                throw new RuntimeException("cat: cannot open " + arg);
            }
        }
    }

    private void outputFile(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}
