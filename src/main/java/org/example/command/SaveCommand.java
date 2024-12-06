package org.example.command;

import org.example.console.Editor;
import org.example.model.HTMLModel;

import java.io.IOException;

public class SaveCommand implements IOCommand{
    private final Editor editor;
    private final String filePath;

    public SaveCommand(Editor editor, String filePath) {
        this.editor = editor;
        this.filePath = filePath;
    }

    @Override
    public void execute()  {
        try{
            this.editor.saveToPath(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
