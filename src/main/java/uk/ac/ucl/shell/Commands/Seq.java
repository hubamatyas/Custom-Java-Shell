package uk.ac.ucl.shell.Commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import uk.ac.ucl.shell.Parse.Parser;


public class Seq extends Command {

    public Seq(String inputString) {
        super(inputString);
    }

    @Override
    public void eval(InputStream input, OutputStream output) throws IOException{
        if(getInputString().equals("")){
            return;
        }
        //parses command into atomic commands each containing calls ordered by piping
        ArrayList<ArrayList<String>> atomicCommands = Parser.parseCommand(getInputString());
        for(ArrayList<String> command : atomicCommands){
            InputStream prevInput = input;
            for(int i = 0; i < command.size(); i++){
                //used ByteArrayStreams instead of PipedStreams due its size limitations of 4096 bytes
                ByteArrayOutputStream pipedOutput = new ByteArrayOutputStream();
                new Call(command.get(i)).eval(prevInput, i == command.size()- 1 ? output : pipedOutput);
                prevInput = new ByteArrayInputStream(pipedOutput.toByteArray()) ;
            }
        }
    }
}
