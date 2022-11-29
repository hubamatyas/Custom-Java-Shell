package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ApplicationFactory {

    /**
     * Factory class for creating Unix Shell Application objects
     */
    public ApplicationFactory(){}

    /**
     * Returns the Unix Shell application specified by the first argument
     *
     * @param appName   the name of the application to be created
     * @param args      the arguments of the application
     * @param input     the input stream of the application
     * @param writer    the output stream of the application
     * @return          the Unix Shell Application created with the specified parameters
     */
    public static IApplication getApp(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        appName = appName.toLowerCase();
        boolean unsafe = false;
        if (appName.charAt(0) == '_') {
            unsafe = true;
            appName = appName.substring(1);
        }

        IApplication app = getAppInterface(appName, args, input, writer);
        return unsafe ? new Unsafe(app) : app;

    }

    private static IApplication getAppInterface(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        return switch(appName){
            case "ls" -> new Ls(appName, args, input, writer);
            case "cd" -> new Cd(appName, args, input, writer);
            case "cut" -> new Cut(appName, args, input, writer);
            case "pwd" -> new Pwd(appName, args, input, writer);
            case "cat" -> new Cat(appName, args, input, writer);
            case "find" -> new Find(appName, args, input, writer);
            case "uniq" -> new Uniq(appName, args, input, writer);
            case "sort" -> new Sort(appName, args, input, writer);
            case "echo" -> new Echo(appName, args, input, writer);
            case "head" -> new Head(appName, args, input, writer);
            case "tail" -> new Tail(appName, args, input, writer);
            case "grep" -> new Grep(appName, args, input, writer);
            case "exit" -> new Exit(appName, args, input, writer);
            default ->throw new RuntimeException(appName + ": unknown application");
        };
    }


}
