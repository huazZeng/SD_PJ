package org.example.command.SessionCommand;

import org.example.command.IOCommand;
import org.example.console.Editor;
import org.example.console.Session;


import java.io.IOException;

public class SaveCommand implements IOCommand {
    private final Session session;


    public SaveCommand(Session session) {
        this.session = session;
    }

    @Override
    public void execute() throws IOException {
        session.handleSave();

    }
}
