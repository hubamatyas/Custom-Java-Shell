package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;

public class Sort extends Application {
    private boolean reverse;
    private final List<String> fileLines;

    public Sort(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(appName, args, input, writer);
        this.reverse = false;
        this.fileLines = new ArrayList<>();
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new MissingArgumentsException(appName);
        }
    }

    @Override
    protected void eval() throws IOException {
        loadOptions();
        setIsPiped();
        redirect();
    }

    @Override
    protected void redirect() throws IOException {
        if (this.args.isEmpty()) {
            this.pipedCall();
        } else {
            this.simpleCall(this.args.get(0));
        }
    }

    @Override
    protected void app(BufferedReader reader) throws IOException {
        readInput(reader);
        sortInput();
        writeOutput();
    }

    private void loadOptions() {
        if (!this.args.isEmpty() && this.args.get(0).equals("-r")) {
            this.reverse = true;
            this.args.remove(0);
        }
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
            this.terminal.writeLine(line, writer, lineSeparator);
        }
    }
}
