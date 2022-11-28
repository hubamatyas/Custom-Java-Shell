package uk.ac.ucl.shell.Apps;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ApplicationFactory {
    
    public ApplicationFactory(){}

    public static IApplication getApp(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) throws IOException{
        return switch(appName){
            case "cd" -> new Cd(args, input, writer);
            case "cut" -> new Cut(args, input, writer);
            case "find" -> new Find(args, input, writer);
            case "uniq" -> new Uniq(args, input, writer);
            case "pwd" -> new Pwd(args, input, writer);
            case "ls" -> new Ls(args, input, writer);
            case "cat" -> new Cat(args, input, writer);
            case "echo" -> new Echo(args, input, writer);
            case "head" -> new Head(args, input, writer);
            case "tail" -> new Tail(args, input, writer);
            case "grep" -> new Grep(args, input, writer);
            case "exit" -> new Exit(args, input, writer);
            default ->throw new RuntimeException(appName + ": unknown application");
        };
    }

}
