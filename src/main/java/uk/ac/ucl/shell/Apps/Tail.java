package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// TODO refactor by using Directory and abstract Application class
public class Tail extends Application {

    private int tailLines;
    private int index;
    private String fileName;

    public Tail(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
        tailLines = 10;
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
        getTailLines();
    }

    private void getTailLines() {
        if (directory.existsFile(fileName)) {
            try (BufferedReader reader = Files.newBufferedReader(directory.getPathTo(fileName), directory.getEncoding())) {
                // TODO directory.readFile(fileName)
                ArrayList<String> storage = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    storage.add(line);
                }
                getIndex(storage);
                for (int i = index; i < storage.size(); i++) {
                    writer.write(storage.get(i));
                    writer.write(System.getProperty("line.separator"));
                    writer.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException("tail: cannot open " + fileName);
            }
        } else {
            throw new RuntimeException("tail: " + fileName + " does not exist");
        }
    }

    private void loadArgs() {
        if (args.size() == 3) {
            try {
                tailLines = Integer.parseInt(args.get(1));
            } catch (Exception e) {
                throw new RuntimeException("head: wrong argument " + args.get(1));
            }
            fileName = args.get(2);
        } else {
            fileName = args.get(0);
        }
    }

    private void getIndex(ArrayList<String> storage) {
        if (tailLines > storage.size()) {
            index = 0;
        } else {
            index = storage.size() - tailLines;
        }
    }
}
