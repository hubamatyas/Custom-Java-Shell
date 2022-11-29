package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ApplicationFactory {
    
    public ApplicationFactory(){}

    public static IApplication getApp(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) throws IOException{
        appName = appName.toLowerCase();
        boolean unsafe = false;
        if (appName.charAt(0) == '_') {
            unsafe = true;
            appName = appName.substring(1);
        }

        IApplication app = getAppInterface(appName, args, input, writer);
        return unsafe ? new Unsafe(app) : app;

    }

    private static IApplication getAppInterface(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) throws IOException{
        return switch(appName){
            case "ls" -> new Ls("ls", args, input, writer);
            case "cd" -> new Cd("cd", args, input, writer);
            case "cut" -> new Cut("cut", args, input, writer);
            case "pwd" -> new Pwd("pwd", args, input, writer);
            case "cat" -> new Cat("cat", args, input, writer);
            case "find" -> new Find("find", args, input, writer);
            case "uniq" -> new Uniq("uniq", args, input, writer);
            case "sort" -> new Sort("sort", args, input, writer);
            case "echo" -> new Echo("echo", args, input, writer);
            case "head" -> new Head("head", args, input, writer);
            case "tail" -> new Tail("tail", args, input, writer);
            case "grep" -> new Grep("grep", args, input, writer);
            case "exit" -> new Exit("exit", args, input, writer);
            default ->throw new RuntimeException(appName + ": unknown application");
        };
    }


}
