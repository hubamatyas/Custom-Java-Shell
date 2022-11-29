package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Directory;
import uk.ac.ucl.shell.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * implementation of the IApplication interface
 *
 * Abstract class to define general instance methods, instance variables
 * and application logic for the execution Unix Shell Applications
 */
public abstract class Application implements IApplication {
    protected boolean isPiped;
    protected final String appName;
    protected final String lineSeparator;
    protected final String fileSeparator;

    protected final Directory directory;
    protected final Terminal terminal;

    public final ArrayList<String> args;
    public final InputStream input;
    public final OutputStreamWriter writer;

    /**
     * Instantiates a Unix Shell {@link Application} based on system properties,
     * the given parameters and the singleton {@link Terminal} and {@link Directory}
     * instances. Methods that were only declared must be overridden by subclasses.
     *
     * @param args      the arguments obtained from the command line
     * @param input     the input stream for the Unix Shell Application,
     *                  for example {@code Stdin}, {@code null}
     *                  if input stream is not specified
     * @param output    the output stream for the Unix Shell Application,
     *                  for example {@code Stdout}, {@code null}
     *                  if output stream is not specified
     */
    public Application(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        this.isPiped = false;
        this.appName = appName;
        this.lineSeparator = System.getProperty("line.separator");
        this.fileSeparator = System.getProperty("file.separator");

        this.directory = Directory.getInstance();
        this.terminal = Terminal.getInstance();

        this.args = args;
        this.input = input;
        this.writer = output;
    }

    /**
     * Skeleton for the execution of Unix Shell Applications
     *
     * @throws IOException if an IO exception occurs while running the application
     */
    public void exec() throws IOException {
        checkArgs();
        eval();
    }

    /**
     * Checks whether {@code args} are valid and
     * satisfy the syntax of the Unix Shell Application
     */
    protected abstract void checkArgs();

    /**
     * Calls instance methods to execute the Unix Shell Application,
     * evaluate its output and load it to the output stream
     *
     * @throws IOException if an IO exception occurs while running the application
     */
    protected abstract void eval() throws IOException;

    /**
     * Redirects the Unix Shell Application to use the appropriate
     * input. If {@code isPiped} is true, {@code InputStream input}
     * is used. Otherwise, {@code args} is used.
     *
     * @throws IOException if an IO exception occurs while running the application
     */
    protected abstract void redirect() throws IOException;

    /**
     * Runs the logic of the Unix Shell Application with {@code this.args},
     * {@code this.input} and {@code this.writer} instance variables.
     *
     * @param reader        the reader for the redirected {@code InputStream input},
     *
     * @throws IOException  if an IO exception occurs while running the application
     */
    protected abstract void app(BufferedReader reader) throws IOException;


    /**
     * Calls {@code this.app()} method with the {@code BufferedReader} for
     * the appropriate input. Called when {@code this.isPiped} is false.
     *
     * @param arg the name of file or directory to be processed
     */
    protected void simpleCall(String arg) throws IOException {
        BufferedReader reader = this.directory.createBufferedReader(this.appName, arg);
        app(reader);
    }

    /**
     * Calls {@code this.app()} method with the {@code BufferedReader} for
     * the appropriate input. Called when {@code this.isPiped} is true.
     */
    protected void pipedCall() throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(this.input));
        app(reader);
    }

    /**
     * Sets the boolean value of {@code this.isPiped} to true if the Unix Shell
     * Application is piped. Sets the boolean value of {@code this.isPiped} to false
     * if the Unix Shell Application is simply called with command line arguments.
     */
    protected void setIsPiped() {
        this.isPiped = this.args.isEmpty();
    }
}
