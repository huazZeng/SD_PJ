package org.example.command;


import org.example.model.HTMLModel;

public class InsertCommand implements CanUndoCommand{
    private HTMLModel htmlModel;
    private String tagName;
    private String idValue;
    private String insertLocation;
    private String textContent;

    public InsertCommand(HTMLModel htmlModel, String tagName, String idValue, String insertLocation, String textContent) {
        this.htmlModel = htmlModel;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocation = insertLocation;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        // Logic to insert the element into the HTML model
        htmlModel.insert(this.tagName,this.idValue,this.insertLocation,this.textContent);
        return ;
    }

    @Override
    public void undo() {
        // Logic to undo the insertion (e.g., delete the element)
        htmlModel.delete(this.idValue);
    }
}

