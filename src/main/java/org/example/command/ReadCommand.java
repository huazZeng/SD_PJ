package org.example.command;

import org.example.model.HTMLModel;

import java.io.IOException;

public class ReadCommand implements IOCommand{
    private HTMLModel htmlModel;
    private String filePath;
    public ReadCommand(HTMLModel htmlModel, String filePath) {
        this.htmlModel = htmlModel;
        this.filePath = filePath;
    }
    @Override
    public void execute() throws IOException {
        this.htmlModel.readFromPath(filePath);
    }
}
