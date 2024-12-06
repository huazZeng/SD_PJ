package org.example.command;

import org.example.console.Editor;
import org.example.model.HTMLModel;

public class EditIdCommand implements CanUndoCommand {
    private Editor editor;
    private String oldId;  // 原ID
    private String newId;  // 新ID

    public EditIdCommand(Editor editor, String oldId, String newId) {
        this.editor = editor;
        this.oldId = oldId;
        this.newId = newId;
    }
    @Override
    public void execute() {
        this.editor.editId(this.oldId,this.newId);
    }


    @Override
    public void undo() {
        this.editor.editId(this.newId,this.oldId);
    }
}
