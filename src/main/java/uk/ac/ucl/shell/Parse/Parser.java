package uk.ac.ucl.shell.Parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellGrammarLexer;
import uk.ac.ucl.shell.ShellGrammarParser;

public class Parser {

    //returns list of commands as a list of seperated calls based on piping
    public static ArrayList<ArrayList<String>> parseCommand(String input){
        CharStream parserInput = CharStreams.fromString(input);
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);

        //use our own error listener
        parser.removeErrorListeners();
        parser.addErrorListener(new ParserErrorListener());

        ParseTree tree = parser.command();
        //use visitor to extract individual parts of commands
        CommandVisitor visitor = new CommandVisitor();
        visitor.visit(tree);
        return visitor.getAtomicCommands();
    }

    //parses call  and returns class containing appname, args, input file, output file
    public static ParsedCall parseCall(String input) throws IOException {

        CharStream parserInput = CharStreams.fromString(input);
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);
        ParseTree tree = parser.call();
        //utilize visitor to extract arguments and input/output files
        CallVisitor visitor = new CallVisitor();
        visitor.visit(tree);

        ArrayList<String> args = cleanArgs(visitor.getArgs());

        return new ParsedCall(args.remove(0), args, visitor.getInput(), visitor.getOutput());

    }
    
    //Possible Issue: cleaning does not affect redirect operators, should it or should it not?
    private static ArrayList<String> cleanArgs(ArrayList<String> args) throws IOException{
        ArrayList<String> result = new ArrayList<>();
        for(String arg : args){
            result.addAll(clean(arg));
        }
        return result;
    }

    //strips brackets, performs globbing, evaluates backquotes
    private static ArrayList<String> clean(String arg) throws IOException{
        ArrayList<String> cleanArgs = new ArrayList<String>();
        char firstChar = arg.charAt(0);
        if(firstChar == '\''){
            //returns string without quotes
            cleanArgs.add(arg.substring(1, arg.length()-1));

        }else if(firstChar == '`'){
            //eval and output it to a string 
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Shell.eval(arg.substring(1, arg.length()-1), output);
            String text = new String(output.toByteArray(), "UTF-8");
            text = text.replaceAll("[\\r\\n]+", " ");
            cleanArgs.addAll(Arrays.asList(text.split("[ \\t]+")));
        }else if(firstChar == '"'){
            //returns argument with double quotes removed and backquotes evaluated
            String backquoteRegex = "(\\`[^\\`]*\\`)";
            Pattern regex = Pattern.compile(backquoteRegex);
            Matcher matcher = regex.matcher(arg);
            int endIndex = 1;
            StringBuilder output = new StringBuilder();
            while(matcher.find()){
                 output.append(arg.substring(endIndex, matcher.start()));
                 output.append(clean(matcher.group()));
                 endIndex = matcher.end();
            }
            output.append(arg.substring(endIndex, arg.length()-1));
            cleanArgs.add(output.toString());

        }else{
            //globs non quote argument
            cleanArgs.addAll(glob(arg));
        }
        return cleanArgs;
    }
    
    public static ArrayList<String> glob(String arg) throws IOException{
        ArrayList<String> globbingResult = new ArrayList<String>();
        //TODO: update the way of fetching directory;
        Path dir = Paths.get(Shell.getDirectory());
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir, arg);
        for (Path entry : stream) {
            globbingResult.add(entry.getFileName().toString());
        }
        if(globbingResult.isEmpty()){
            globbingResult.add(arg);
        }
        return globbingResult;
    }


}
