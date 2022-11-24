package uk.ac.ucl.shell.Apps;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import uk.ac.ucl.shell.Shell;

public class Cat implements Application {

    public void exec(ArrayList<String> args, InputStream input, OutputStreamWriter writer) throws IOException {
        checkArgs(args);
        enumerateArgs(args, writer);
    }

    private void enumerateArgs(ArrayList<String> args, OutputStreamWriter writer) {
        for (String arg : args) {
            Charset encoding = StandardCharsets.UTF_8;
            String rawPath = Shell.getDirectory() + File.separator + arg;
            File currFile = new File(rawPath);
            if (!currFile.exists()) {
                throw new RuntimeException("cat: file does not exist");
            } else {
                Path filePath = Paths.get(rawPath);
                try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                    printFile(reader, writer);
                }
                catch (IOException e) {
                    throw new RuntimeException("cat: cannot open " + arg);
                }
            }
        }
    }

    private void printFile(BufferedReader reader, OutputStreamWriter writer) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }

    private void checkArgs(ArrayList<String> args) {
        if (args.isEmpty()) {
            throw new RuntimeException("cat: missing arguments");
        }
    }
}
