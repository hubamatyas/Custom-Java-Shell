package uk.ac.ucl.shell;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Terminal {
    private static Terminal instance;

    private Terminal() {

    }

    // Singleton class
    public static Terminal getInstance() {
        if (instance == null) {
            instance = new Terminal();
        }
        return instance;
    }

    public void writeLine(String line, OutputStreamWriter writer, String separator) throws IOException {
        writer.write(line);
        writer.write(separator);
        writer.flush();
    }
}
