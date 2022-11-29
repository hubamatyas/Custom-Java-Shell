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

        ArrayList<String> args = cleanArgs(visitor.getArgs());

        return new ParsedCall(args.remove(0), args, visitor.getInput(), visitor.getOutput());

    }

    /**
     * @param args : <code> ArrayList{@literal <}String> </code> of raw arguments accumulated from CallVisitor
     * @return <code> ArrayList{@literal <}String> </code> containing the arguments interpreted using backquote parsing, globbing, and quote removal
     */
    private static ArrayList<String> cleanArgs(ArrayList<String> args) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        for (String arg : args) {
            switch (arg.charAt(0)) {
                case '\'':
                    result.add(parseSinglequote(arg));
                    break;
                case '`':
                    String text = parseBackquote(arg);
                    List<String> splitText = Arrays.asList(text.split("[ \\t]+"));
                    result.addAll(splitText);
                case '"':
                    result.add(parseDoublequote(arg));
                default:
                    result.addAll(parseGlobbing(arg));
                    break;
            }
        }
        return result;
    }

    private static String parseSinglequote(String arg){
        return arg.substring(1, arg.length() - 1);
    }

    private static String parseBackquote(String arg) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Shell.eval(arg.substring(1, arg.length() - 1), output);
        String text = new String(output.toByteArray(), "UTF-8");
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
        Path dir = Paths.get(Directory.getInstance().getCurrentDirectory());
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir, arg);
        for (Path entry : stream) {
            globbingResult.add(entry.getFileName().toString());
        }
        if (globbingResult.isEmpty()) {
            globbingResult.add(arg);
        }
        return globbingResult;
    }

}
