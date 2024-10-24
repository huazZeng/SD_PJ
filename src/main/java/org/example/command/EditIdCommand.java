package org.example.command;

import org.example.model.HTMLModel;

public class EditIdCommand implements CanUndoCommand {
    private HTMLModel htmlModel;
    private String oldId;  // 原ID
    private String newId;  // 新ID

    public EditIdCommand(HTMLModel htmlModel, String oldId, String newId) {
        this.htmlModel = htmlModel;
        this.oldId = oldId;
        this.newId = newId;
    }
    @Override
    public void execute() {
        this.htmlModel.editId(this.oldId,this.newId);
    }


    @Override
    public void undo() {
        this.htmlModel.editId(this.newId,this.oldId);
    }
}
