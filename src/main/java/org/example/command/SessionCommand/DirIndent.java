package org.example.command.SessionCommand;

import org.example.command.Command;
import org.example.console.CommandParser;
import org.example.console.Session;

import java.io.IOException;

public class DirIndent implements Command {
    private  final Session session;
    private final String[] parts;
    public DirIndent(Session session, String[] parts) {
        this.session = session;
        this.parts = parts;
    }

    @Override
    public void execute() throws IOException {
        session.handleDirIndent(parts);
    }
}
