package uk.ac.ucl.shell.Parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import uk.ac.ucl.shell.Directory;

public class
Parser {

    /** parses input and return list Commands which are a list of calls
     * @param input : <code>String</code> to be parsed 
     * @return <code> ArrayList </code> representing commands from left to right containing 
     * a <code> ArrayList{@literal <}String> </code> representing pipe seperated calls from left to right
    */ 
    public static ArrayList<ArrayList<String>> parseCommand(String input) {
        CharStream parserInput = CharStreams.fromString(input);
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);

        // use our own error listener
        parser.removeErrorListeners();
        parser.addErrorListener(new ParserErrorListener());

        ParseTree tree = parser.command();
        // use visitor to extract individual parts of commands
        CommandVisitor visitor = new CommandVisitor();
        visitor.visit(tree);
        return visitor.getAtomicCommands();
    }

    /**  Parses a call and returns its seperated parts
     * @param input : <code> String </code> to be parsed
     * @return <code> ParseCall </code> containing appname, args, input file, output file
    */ 
    public static ParsedCall parseCall(String input) throws IOException {

        CharStream parserInput = CharStreams.fromString(input);
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);
        ParseTree tree = parser.call();
        // utilize visitor to extract arguments and input/output files
        CallVisitor visitor = new CallVisitor();
        visitor.visit(tree);
        ArrayList<String> args = visitor.getArgs();

        return new ParsedCall(args.remove(0), args, visitor.getInput(), visitor.getOutput());

    }

    /**
     * @param args : <code> String </code> for raw argument from CallVisitor argument context
     * @return <code> ArrayList{@literal <}String> </code> containing the arguments interpreted using backquote parsing, globbing, and quote removal
     */
    public static ArrayList<String> cleanArg(String arg) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        switch (arg.charAt(0)) {
            case '\'':
                result.add(parseSinglequote(arg));
                break;
            case '`':
                String text = parseBackquote(arg);
                List<String> splitText = Arrays.asList(text.split("[ \\t]+"));
                result.addAll(splitText);
                break;
            case '"':
                result.add(parseDoublequote(arg));
                break;
            default:
                result.addAll(parseGlobbing(arg));
                break;
        }
        return result;
    }

    private static String parseSinglequote(String arg){
        return arg.substring(1, arg.length() - 1);
    }

    private static String parseBackquote(String arg) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Shell.eval(arg.substring(1, arg.length() - 1), output);
        String text = output.toString("UTF-8");
        text = text.replaceAll("[\\r\\n]+", " ");
        return text;
    }

    private static String parseDoublequote(String arg) throws IOException {
        String backquoteRegex = "(\\`[^\\`]*\\`)";
        Pattern regex = Pattern.compile(backquoteRegex);
        Matcher matcher = regex.matcher(arg);
        int endIndex = 1;
        StringBuilder output = new StringBuilder();
        while (matcher.find()) {
            output.append(arg.substring(endIndex, matcher.start()));
            output.append(parseBackquote(matcher.group()));
            endIndex = matcher.end();
        }
        output.append(arg.substring(endIndex, arg.length() - 1));
        return output.toString();
    }

    /**
    * globbing : returns list of files that match pattern
    * @param arg : raw argument
    * @return   if is a valid pattern: <code>arrayList</code> containing all files that match pattern
    *           otherwise: <code>arrayList</code> containing the unchanged arg
    */
    private static ArrayList<String> parseGlobbing(String arg) throws IOException {
        ArrayList<String> globbingResult = new ArrayList<String>();
        if(!arg.contains("*")){
            globbingResult.add(arg);
            return globbingResult;
        }
        String[] splitArg = arg.split("/"); 
        String globArg = splitArg[splitArg.length-1];
        
        String[] globDir = Arrays.copyOfRange(splitArg, 0, splitArg.length-1);
        Path globPath = Paths.get(".", globDir).normalize();

        Path dir = Paths.get(Directory.getInstance().getCurrentDirectory(), globDir);
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir, globArg);
        for (Path entry : stream) {
            globbingResult.add(globPath.resolve(entry.getFileName()).toString());
        }
        return globbingResult;
    }

}
