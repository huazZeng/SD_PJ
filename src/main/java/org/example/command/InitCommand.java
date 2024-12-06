package org.example.command;

import org.example.console.Editor;
import org.example.model.HTMLModel;

public class InitCommand implements IOCommand{
    private final Editor editor;

    public InitCommand(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void execute() {
        this.editor.init();
    }
}
