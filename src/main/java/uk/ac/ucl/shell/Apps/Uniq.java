package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Uniq extends Application {

    private boolean caseInsensitive;
    public Uniq(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
        this.caseInsensitive = false;
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new RuntimeException("uniq: missing arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        loadOptions();
        if (args.isEmpty()) {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(input));
            uniq(reader);
        } else {
            BufferedReader reader = directory.createBufferedReader("uniq", args.get(0));
            uniq(reader);
        }
    }

    private void loadOptions() {
        if (!args.isEmpty() && args.get(0).equals("-i")) {
            this.caseInsensitive = true;
            args.remove(0);
        }
    }

    private void uniq(BufferedReader reader) throws IOException {
        String prevLine = "";
        while (reader.ready()) {
            String line = reader.readLine();
            if (this.caseInsensitive) {
                line = line.toLowerCase();
            }
            if (!line.equals(prevLine)) {
                directory.writeLine(line, writer, lineSeparator);
            }
            prevLine = line;
        }
    }
}
