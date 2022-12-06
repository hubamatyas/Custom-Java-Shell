package uk.ac.ucl.shell;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Implements methods related to terminal functions
 */
public class Terminal {
    private static Terminal instance;

    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    // set to false when testing to avoid colour formatted output strings
    public static final boolean enableHighlighting = false;

    private boolean isSysOut;

    private Terminal() {

    }

    /**
     * Initializes a singleton instance of Terminal class
     *
     * @return singleton instance of Terminal class
     */
    public static Terminal getInstance() {
        if (instance == null) {
            instance = new Terminal();
        }
        return instance;
    }

    /**
     * Writes a line to the {@code writer} OutputStream
     * specified by {@code writer}
     *
     * @param line          String to be written to the terminal
     * @param writer        OutputStreamWriter to write to
     * @param separator     String line separator
     *
     * @throws IOException if an IO exception occurs while writing to the OutputStream
     */
    public void writeLine(String line, OutputStreamWriter writer, String separator) throws IOException {
        writer.write(line);
        writer.write(separator);
        writer.flush();
    }

    /**
     * Writes a line to the {@code writer} OutputStream
     * specified by {@code writer}
     *
     * @param line          String to be written to the terminal
     * @param startIndex    start index of pattern match inclusive
     * @param endIndex      end index of pattern match exclusive
     * @param writer        OutputStreamWriter to write to
     * @param separator     String line separator
     *
     * @throws IOException if an IO exception occurs while writing to the OutputStream
     */
    public void writePatternMatch(String line, int startIndex, int endIndex, OutputStreamWriter writer, String separator) throws IOException {
        writer.write(line.substring(0, startIndex));
        if(isSysOut && enableHighlighting) {
            writer.write(ANSI_YELLOW_BACKGROUND + line.substring(startIndex, endIndex) + ANSI_RESET);
        }else{
            writer.write(line.substring(startIndex, endIndex));
        }
        writer.write(line.substring(endIndex,line.length()));
        writer.write(separator);
        writer.flush();
    }

    /**
     * Writes a line to the {@code writer} OutputStream
     * specified by {@code writer}
     *
     * @param message prints an error to standard output in red
     *
     */
    public void printError(String message) {
        System.err.println(ANSI_RED + message + ANSI_RESET);
    }

    /**
     * notifies the Terminal class if the current outputStream is the standard output
     * @param output  Outputstream to compare to System.out
     */
    public void observeOutput(OutputStream output){
        isSysOut = output == System.out;
    }
}
