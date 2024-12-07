package org.example.command.SessionCommand;

import org.example.command.Command;
import org.example.console.CommandParser;
import org.example.console.Session;

import java.io.IOException;

public class DirTreeCommand implements Command {
    private  final Session session;

    public DirTreeCommand(Session session) {
        this.session = session;

    }

    @Override
    public void execute() throws IOException {
        session.handleDirTree();
    }
}
