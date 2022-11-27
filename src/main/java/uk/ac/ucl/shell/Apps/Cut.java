package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Cut extends Application {

    private String byteString;
    private final SortedSet<Integer> bytesRange;
    private int toEnd;
    public Cut(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
        bytesRange = new TreeSet<>();
        toEnd = Integer.MAX_VALUE;
    }

    @Override
    protected void checkArgs() {
        if (args.size() == 2 || input != null) {
            args.add(input.toString());
            // TODO: piping
        }
        if (args.size() != 3) {
            throw new RuntimeException("cut: wrong number of arguments");
        }
        if (args.get(0).compareTo("-b") != 0) {
            throw new RuntimeException("cut: invalid option");
        }
        byteString = args.get(1);
    }

    @Override
    protected void eval() throws IOException {
        parseByteString();
        cut();
    }

    private void parseByteString() {
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
            toEnd = parseNumber(option.substring(0, lastLoc));
        } else if (option.contains("-")) {
            int start = parseNumber(option.substring(0, option.indexOf('-')));
            int end = parseNumber(option.substring(option.indexOf('-')+1));
            addBytesRange(start, end);
        } else {
            int number = parseNumber(option);
            bytesRange.add(number);
        }
    }

    private int parseNumber(String substring) {
        try {
            return Integer.parseInt(substring);
        } catch (Exception e) {
            throw new RuntimeException("cut: invalid byte range");
        }
    }

    private void addBytesRange(int start, int end) {
        for (int i = start; i <= end; i++) {
            bytesRange.add(i);
        }
    }

    private void cut() throws IOException {
        List<String> fileLines = directory.readFile("cut", args.get(2));
        for (String line : fileLines) {
            String newLine = cutLine(line);
            directory.writeLine(newLine, writer, lineSeparator);
        }
    }

    private String cutLine(String line) {
        StringBuilder sb = new StringBuilder();
        if (toEnd != Integer.MAX_VALUE) {
            return toEOL(sb, line);
        } else {
            return notToEOL(sb, line);
        }
    }

    private String toEOL(StringBuilder sb, String line) {
        for (int i : bytesRange) {
            if (i < toEnd && i <= line.length()) {
                sb.append(line.charAt(i-1));
            }
        }
        sb.append(line, toEnd-1, line.length());
        return sb.toString();
    }

    private String notToEOL(StringBuilder sb, String line) {
        for (int i : bytesRange) {
            if (i <= line.length()) {
                sb.append(line.charAt(i-1));
            }
        }
        return sb.toString();
    }
}
