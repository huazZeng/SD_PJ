package org.example.command;


import org.example.console.Editor;
import org.example.model.HTMLModel;

public class InsertCommand implements CanUndoCommand{
    private Editor editor;
    private String tagName;
    private String idValue;
    private String insertLocation;
    private String textContent;

    public InsertCommand(Editor editor, String tagName, String idValue, String insertLocation, String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.insertLocation = insertLocation;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        // Logic to insert the element into the HTML model
        editor.insert(this.tagName,this.idValue,this.insertLocation,this.textContent);
        return ;
    }

    @Override
    public void undo() {
        // Logic to undo the insertion (e.g., delete the element)
        editor.delete(this.idValue);
    }
}

