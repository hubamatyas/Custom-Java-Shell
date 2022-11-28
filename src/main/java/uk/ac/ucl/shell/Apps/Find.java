package uk.ac.ucl.shell.Apps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.*;

public class Find extends Application {

    private String dirToSearch;
    private Queue<String> toVisit;
    public Find(ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(args, input, writer);
        dirToSearch = "";
        toVisit = new LinkedList<>();
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input != null) {
            args.add(input.toString());
            // TODO: piping
        }
        if (args.size() < 2) {
            throw new RuntimeException("find: missing arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        setDirToSearch();
        toVisit.add(dirToSearch);
        find();
    }

    private void setDirToSearch() {
        if (!args.get(0).equals("-name")) {
            dirToSearch = args.get(0);
        }
    }

    private void find() throws IOException {
        if (toVisit.isEmpty()) {
            return;
        }

        String nextDir = toVisit.remove().toString();
        for (String child : directory.getListOfSubDirs("find", nextDir)) {
            toVisit.add(nextDir + File.separator + child);
            System.out.println(child);
            find();
        }
        //for (File file : directory.getListOfFiles("find", dirToSearch)) {
        //    if (!file.isDirectory()) {
        //        System.out.println(file.getName());
        //    }
            //if (file.getName().toString().equals(args.get(1))) {
            //    directory.writeLine(file.toString(), writer, lineSeparator);
            //}
    }
}
