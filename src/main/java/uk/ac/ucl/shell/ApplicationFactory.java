package uk.ac.ucl.shell;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import uk.ac.ucl.shell.Apps.*;

public class ApplicationFactory {
    
    private HashMap<String, Application> applications;
    
    public ApplicationFactory(){
        setApplications();
    }

    private void setApplications(){
        applications = new HashMap<>();
        applications.put("pwd", new Pwd());
    }

    public void execApp(String appName, ArrayList<String> appArgs, OutputStreamWriter writer) throws IOException{
        if(applications.containsKey(appName)){
            applications.get(appName).exec(appArgs, writer);
        }else{
            throw new RuntimeException(appName + ": unknown application");
        }
    }


}
