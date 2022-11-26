package uk.ac.ucl.shell.Apps;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
    protected void eval() throws IOException {
        for (String arg : args) {
            List<String> fileLines = directory.readFile("cat", arg);
            directory.writeFile(fileLines, writer);
        }
    }
}
