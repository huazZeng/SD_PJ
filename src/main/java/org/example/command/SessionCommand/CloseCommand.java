package org.example.command.SessionCommand;

import org.example.command.Command;
import org.example.console.Session;

import java.io.IOException;

public class CloseCommand implements Command {
    private final Session session;

    public CloseCommand(Session session) {
        this.session = session;
    }


    @Override
    public void execute() throws IOException {
        session.handleClose();
    }
}
