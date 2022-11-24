package uk.ac.ucl.shell.Apps;
import java.io.IOException;

public class ApplicationFactory {
    
    public ApplicationFactory(){}

    public static Application getApp(String appName) throws IOException{
        return switch(appName){
            case "cd" -> new Cd();
            case "pwd" -> new Pwd();
            case "ls" -> new Ls();
            case "cat" -> new Cat();
            case "echo" -> new Echo();
            case "head" -> new Head();
            case "tail" -> new Tail();
            case "grep" -> new Grep();
            case "exit" -> new Exit();
            default ->throw new RuntimeException(appName + ": unknown application");
        };
    }

}
