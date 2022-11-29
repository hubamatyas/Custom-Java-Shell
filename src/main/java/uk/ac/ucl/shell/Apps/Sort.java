package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sort extends Application {

    private boolean reverse;
    private List<String> fileLines;
    public Sort(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
        this.reverse = false;
        this.fileLines = new ArrayList<>();
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new RuntimeException("sort: missing arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        loadOptions();
        if (args.isEmpty()) {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(input));
            sort(reader);
        } else {
            BufferedReader reader = directory.createBufferedReader("uniq", args.get(0));
            sort(reader);
        }
    }

    private void loadOptions() {
        if (!args.isEmpty() && args.get(0).equals("-r")) {
            this.reverse = true;
            args.remove(0);
        }
    }

    private void sort(BufferedReader reader) throws IOException {
        readInput(reader);
        sortInput();
        writeOutput();
    }

    private void readInput(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            this.fileLines.add(line);
        }
    }

    private void sortInput() {
        if (this.reverse) {
            this.fileLines.sort(Comparator.reverseOrder());
        } else {
            this.fileLines.sort(Comparator.naturalOrder());
        }
    }

    private void writeOutput() throws IOException {
        for (String line : this.fileLines) {
            directory.writeLine(line, writer, lineSeparator);
        }
    }
}
