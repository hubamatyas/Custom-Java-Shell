package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Unsafe extends Application {
    private final IApplication app;

    public Unsafe(IApplication app) {
        super("unsafe", new ArrayList<>(), null, null);
        this.app = app;
    }

    @Override
    protected void checkArgs() {}

    @Override
    protected void eval() {
        try {
            app.exec();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void redirect() throws IOException {}

    @Override
    protected void app(BufferedReader reader) throws IOException {}
}
