package uk.ac.ucl.shell.Parse;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

public class Parsing {

    public static void parseSeq(String input){

    }

    public static void parseAtomicCommand(String input){

    }

    public static void parseCall(String input){

    }
    
    //strip brackets
    public static String stripBrackets(String input) throws IOException{
        char firstChar = input.charAt(0);
        if(firstChar == '\''){
            //returns string without quotes
            return input.substring(1, input.length());
        }else if(firstChar == '`'){
            //eval and output it to a string 
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Shell.eval(input.substring(1, input.length()-1), output);
            return new String(output.toByteArray(), "UTF-8");
        }else if(firstChar == '"'){
            //returns argument with double quotes removed and backquotes evaluated
            String backquoteRegex = "(\\`[^\\`]*\\`)";
            Pattern regex = Pattern.compile(backquoteRegex);
            Matcher matcher = regex.matcher(input);
            int endIndex = 1;
            StringBuilder output = new StringBuilder();
            while(matcher.find()){
                 output.append(input.substring(endIndex, matcher.start()));
                 output.append(stripBrackets(matcher.group()));
                 endIndex = matcher.end();
            }
            output.append(input.substring(endIndex, input.length()-1));
            return output.toString();
        }else{
            return input;
        }
    }

    public static ArrayList<String> parse(String input){
        // Parsing input
        CharStream parserInput = CharStreams.fromString(input);
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);
        ParseTree tree = parser.command();
        CallVisitor visitor = new CallVisitor();
        visitor.visit(tree);
        System.out.println(visitor.getArgs());
        System.out.println(visitor.getInput());
        System.out.println(visitor.getOutput());


        // Store commands entered
        ArrayList<String> rawCommands = new ArrayList<String>();
        String lastSubcommand = "";

        for (int i=0; i<tree.getChildCount(); i++) {
            if (!tree.getChild(i).getText().equals(";")) {
                lastSubcommand += tree.getChild(i).getText();
            } else {
                rawCommands.add(lastSubcommand);
                lastSubcommand = "";
            }
        }
        rawCommands.add(lastSubcommand);
        return rawCommands;
    }


    public static ArrayList<String> produceTokens(String currentDirectory, String rawCommand)
            throws IOException{

        String spaceRegex = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
        ArrayList<String> tokens = new ArrayList<String>();
        Pattern regex = Pattern.compile(spaceRegex);
        Matcher regexMatcher = regex.matcher(rawCommand);
        String nonQuote;
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null || regexMatcher.group(2) != null) {
                String quoted = regexMatcher.group(0).trim();
                tokens.add(quoted.substring(1,quoted.length()-1));
            } else {
                nonQuote = regexMatcher.group().trim();
                ArrayList<String> globbingResult = new ArrayList<String>();
                Path dir = Paths.get(currentDirectory);
                DirectoryStream<Path> stream = Files.newDirectoryStream(dir, nonQuote);
                for (Path entry : stream) {
                    globbingResult.add(entry.getFileName().toString());
                }
                if (globbingResult.isEmpty()) {
                    globbingResult.add(nonQuote);
                }
                tokens.addAll(globbingResult);
            }
        }
        return tokens;
    }

}
