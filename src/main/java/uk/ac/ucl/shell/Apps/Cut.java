package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import uk.ac.ucl.shell.Exceptions.InvalidByteRangeException;
import uk.ac.ucl.shell.Exceptions.InvalidOptionException;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

public class Cut extends Application {
    private final SortedSet<Integer> bytesRange;
    private int toEnd;

    public Cut(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(appName, args, input, output);
        this.bytesRange = new TreeSet<>();
        this.toEnd = Integer.MAX_VALUE;
    }

    @Override
    protected void checkArgs() {
        if (this.args.size() == 2 && this.input == null) {
            throw new MissingArgumentsException(appName);
        }
        if (this.args.size() > 3) {
            throw new TooManyArgumentsException(appName);
        }
        if (this.args.get(0).compareTo("-b") != 0) {
            throw new InvalidOptionException(appName, this.args.get(0));
        }
    }

    @Override
    protected void eval() throws IOException {
        parseByteString();
        setIsPiped();
        redirect();
    }

    @Override
    protected void redirect() throws IOException {
        if (this.isPiped) {
            pipedCall();
        } else {
            simpleCall(this.args.get(0));
        }
    }

    protected void app(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            String cutLine = cutLine(line);
            this.terminal.writeLine(cutLine, writer, lineSeparator);
        }
    }

    private void parseByteString() {
        this.args.remove(0);
        String byteString = this.args.remove(0);
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
            int newEnd = parseNumber(option.substring(0, lastLoc));
            this.toEnd = Math.min(this.toEnd, newEnd);
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
            throw new InvalidByteRangeException();
        }
    }

    private void addBytesRange(int start, int end) {
        for (int i = start; i <= end; i++) {
            this.bytesRange.add(i);
        }
    }

    private String cutLine(String line) {
        StringBuilder sb = new StringBuilder();
        if (this.toEnd != Integer.MAX_VALUE) {
            notToEOL(sb, line, this.toEnd);
            return toEOL(sb, line);
        } else {
            return notToEOL(sb, line);
        }
    }

    private String toEOL(StringBuilder sb, String line) {
        for (int i : this.bytesRange) {
            if (i < this.toEnd && i <= line.length() && i > 1) {
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

    private void notToEOL(StringBuilder sb, String line, int end) {
        for (int i : this.bytesRange) {
            if (i <= line.length() && i > 0 && i < end) {
                sb.append(line.charAt(i-1));
            }
        }
    }
}
