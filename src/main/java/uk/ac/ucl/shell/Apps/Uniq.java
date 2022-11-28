package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Uniq extends Application {

    private List<String> fileLines;
    private boolean caseInsensitive;
    public Uniq(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
        caseInsensitive = false;
    }

    @Override
    protected void checkArgs() {
        if ((args.isEmpty() || args.size() == 1) && input != null) {
            args.add(input.toString());
            // TODO: piping
        }
        if (args.isEmpty() || args.size() > 2) {
            throw new RuntimeException("uniq: wrong number of arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        setParameters();
        uniq();
    }

    private void setParameters() throws IOException {
        if (args.get(0).equals("-i")) {
            caseInsensitive = true;
            args.remove(0);
        }
        fileLines = directory.readFile("uniq", args.get(0));
    }

    private void uniq() throws IOException {
        List<String> uniqLines = new ArrayList<>();
        String prevLine = "";
        for (String line : fileLines) {
            if (caseInsensitive) {
                line = line.toLowerCase();
            }
            if (!line.equals(prevLine)) {
                uniqLines.add(line);
            }
            prevLine = line;
        }
        directory.writeFile(uniqLines, writer, lineSeparator);
    }
}
