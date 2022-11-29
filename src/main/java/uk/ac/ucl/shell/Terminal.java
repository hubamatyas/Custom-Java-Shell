package uk.ac.ucl.shell;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Implements methods related to terminal functions
 */
public class Terminal {
    private static Terminal instance;

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
}
