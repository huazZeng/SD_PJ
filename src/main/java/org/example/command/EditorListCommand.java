package org.example.command;

import org.example.console.Editor;
import org.example.console.Session;

import java.io.IOException;
import java.util.Map;

public class EditorListCommand implements Command{
    private final Session session;

    public EditorListCommand(Session session) {
        this.session = session;

    }

    @Override
    public void execute() throws IOException {
        session.handleEditorList();
    }
}
