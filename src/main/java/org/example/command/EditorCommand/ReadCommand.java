package org.example.command.EditorCommand;

import org.example.command.IOCommand;
import org.example.console.Editor;


import java.io.IOException;

public class ReadCommand implements IOCommand {
    private Editor editor;
    private String filePath;
    public ReadCommand(Editor editor, String filePath) {
        this.editor = editor;
        this.filePath = filePath;
    }
    @Override
    public void execute() {
        try {
            this.editor.readFromPath(filePath);
        }
        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
