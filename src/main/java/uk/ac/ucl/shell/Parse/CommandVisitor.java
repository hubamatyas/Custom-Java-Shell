package uk.ac.ucl.shell.Parse;

import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;

import uk.ac.ucl.shell.ShellGrammarBaseVisitor;
import uk.ac.ucl.shell.ShellGrammarParser.AtomicCommandContext;
import uk.ac.ucl.shell.ShellGrammarParser.CallContext;

public class CommandVisitor extends ShellGrammarBaseVisitor<Void>{
    
    private ArrayList<ArrayList<String>> atomicCommands;

    @Override
    public Void visit(ParseTree tree) {
        atomicCommands = new ArrayList<>();
        return super.visit(tree);
    }

    @Override
    public Void visitAtomicCommand(AtomicCommandContext ctx) {
        atomicCommands.add(new ArrayList<>());
        return super.visitAtomicCommand(ctx);
    }

    @Override
    public Void visitCall(CallContext ctx) {
        atomicCommands.get(atomicCommands.size()-1).add(ctx.getText());
        return null;
    }

    public ArrayList<ArrayList<String>> getAtomicCommands(){
        return atomicCommands;
    }
    
}
