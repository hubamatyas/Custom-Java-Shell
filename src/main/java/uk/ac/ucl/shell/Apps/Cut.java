package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Cut extends Application {

    private final SortedSet<Integer> bytesRange;
    private int toEnd;
    public Cut(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
        this.bytesRange = new TreeSet<>();
        this.toEnd = Integer.MAX_VALUE;
    }

    @Override
    protected void checkArgs() {
        if (args.size() == 2 && input == null) {
            throw new RuntimeException("cut: missing arguments");
        }
        if (args.size() > 3) {
            throw new RuntimeException("cut: wrong number of arguments");
        }
        if (args.get(0).compareTo("-b") != 0) {
            throw new RuntimeException("cut: invalid option");
        }
    }

    @Override
    protected void eval() throws IOException {
        parseByteString();
        if (args.isEmpty()) {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(input));
            cut(reader);
        } else {
            BufferedReader reader = directory.createBufferedReader("cut", args.get(0));
            cut(reader);
        }
    }

    private void parseByteString() {
        args.remove(0);
        String byteString = args.remove(0);
        for (String bytes : byteString.split(",")) {
            parseByte(bytes);
        }
    }

    private void parseByte(String option) {
        int firstLoc = 0;
        int lastLoc = option.length() - 1;

        if (option.charAt(firstLoc) == '-') {
            int end = parseNumber(option.substring(firstLoc+1));
            addBytesRange(0, end);
        } else if (option.charAt(lastLoc) == '-') {
            this.toEnd = parseNumber(option.substring(0, lastLoc));
        } else if (option.contains("-")) {
            int start = parseNumber(option.substring(0, option.indexOf('-')));
            int end = parseNumber(option.substring(option.indexOf('-')+1));
            addBytesRange(start, end);
        } else {
            int number = parseNumber(option);
            this.bytesRange.add(number);
        }
    }

    private int parseNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw new RuntimeException("cut: invalid byte range");
        }
    }

    private void addBytesRange(int start, int end) {
        for (int i = start; i <= end; i++) {
            this.bytesRange.add(i);
        }
    }

    private void cut(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            String cutLine = cutLine(line);
            directory.writeLine(cutLine, writer, lineSeparator);
        }
    }

    private String cutLine(String line) {
        StringBuilder sb = new StringBuilder();
        if (this.toEnd != Integer.MAX_VALUE) {
            return toEOL(sb, line);
        } else {
            return notToEOL(sb, line);
        }
    }

    private String toEOL(StringBuilder sb, String line) {
        for (int i : this.bytesRange) {
            if (i < this.toEnd && i <= line.length()) {
                sb.append(line.charAt(i-1));
            }
        }
        sb.append(line, this.toEnd-1, line.length());
        return sb.toString();
    }

    private String notToEOL(StringBuilder sb, String line) {
        for (int i : this.bytesRange) {
            if (i <= line.length() && i > 0) {
                sb.append(line.charAt(i-1));
            }
        }
        return sb.toString();
    }
}
