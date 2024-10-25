package org.example.command;

import org.example.model.HTMLModel;

import java.io.IOException;

public class SaveCommand implements IOCommand{
    private final HTMLModel htmlModel;
    private final String filePath;

    public SaveCommand(HTMLModel htmlModel, String filePath) {
        this.htmlModel = htmlModel;
        this.filePath = filePath;
    }

    @Override
    public void execute()  {
        try{
            this.htmlModel.saveToPath(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
