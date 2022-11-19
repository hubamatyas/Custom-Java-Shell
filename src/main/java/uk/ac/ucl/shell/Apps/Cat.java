package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import uk.ac.ucl.shell.Evaluator;

public class Cat extends Application {
    public void exec(ArrayList<String> appArgs, OutputStreamWriter writer) {
        if (appArgs.isEmpty()) {
            throw new RuntimeException("cat: missing arguments");
        } else {
            String currentDirectory = Evaluator.getInstance().getDirectory();
            for (String arg : appArgs) {
                Charset encoding = StandardCharsets.UTF_8;
                File currFile = new File(currentDirectory + File.separator + arg);
                if (currFile.exists()) {
                    Path filePath = Paths.get(currentDirectory + File.separator + arg);
                    try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            writer.write(String.valueOf(line));
                            writer.write(System.getProperty("line.separator"));
                            writer.flush();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("cat: cannot open " + arg);
                    }
                } else {
                    throw new RuntimeException("cat: file does not exist");
                }
            }
        }
    }
}
