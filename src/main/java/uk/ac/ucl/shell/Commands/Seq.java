package uk.ac.ucl.shell.Commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

import uk.ac.ucl.shell.Parse.Parser;


public class Seq extends Command {

    public Seq(String inputString) {
        super(inputString);
    }

    @Override
    public void eval(InputStream input, OutputStream output) throws IOException{
        //parses command into atomic commands each containing calls ordered by piping
        ArrayList<ArrayList<String>> atomicCommands = Parser.parseCommand(getInputString());
        for(ArrayList<String> command : atomicCommands){
            InputStream prevInput = input;
            for(int i = 0; i < command.size(); i++){
                PipedInputStream pipedInput = new PipedInputStream();
                PipedOutputStream pipedOutput = new PipedOutputStream(pipedInput);
                new Call(command.get(i)).eval(prevInput, i == command.size()- 1 ? output : pipedOutput);
                prevInput = pipedInput;
            }
        }
    }
}
