package org.example.command;

import org.example.console.Session;

import java.io.IOException;

public class LoadCommand implements Command {
    private  final Session session;
    private final String[] parts;
    public LoadCommand(Session session, String[] parts) {
        this.session = session;
        this.parts = parts;
    }

    @Override
    public void execute() throws IOException {
        session.handleLoad(parts);
    }
}
