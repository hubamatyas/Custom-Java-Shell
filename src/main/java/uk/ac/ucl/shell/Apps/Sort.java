package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sort extends Application {

    private boolean reverse;
    private List<String> fileLines;
    public Sort(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
        reverse = false;
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
        sort();
    }

    private void setParameters() throws IOException {
        if (args.get(0).equals("-r")) {
            reverse = true;
            args.remove(0);
        }
        fileLines = directory.readFile("uniq", args.get(0));
    }

    private void sort() throws IOException {
        if (reverse) {
            fileLines.sort(Comparator.reverseOrder());
        } else {
            fileLines.sort(Comparator.naturalOrder());
        }
        directory.writeFile(fileLines, writer, lineSeparator);
    }
}
