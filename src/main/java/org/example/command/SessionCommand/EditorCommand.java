package org.example.command.SessionCommand;


import org.example.command.Command;
import org.example.console.Session;

import java.io.IOException;

public class EditorCommand implements Command {
    private final Session session;
    private final String[] parts;

    public EditorCommand(Session session,String[] parts) {
        this.session = session;
        this.parts = parts;
    }

    @Override
    public void execute() throws IOException {
       session.handleEdit(this.parts);
    }
}
