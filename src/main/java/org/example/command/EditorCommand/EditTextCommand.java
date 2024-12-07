package org.example.command.EditorCommand;

import org.example.command.CanUndoCommand;
import org.example.console.Editor;


public class EditTextCommand implements CanUndoCommand {
    private Editor editor;
    private String element;      // 要编辑的元素
    private String newTextContent; // 新的文本内容
    private String oldTextContent;

    public EditTextCommand(Editor editor, String element, String newTextContent) {
        this.editor = editor;
        this.element = element;
        this.newTextContent = newTextContent;
    }
    @Override
    public void undo() {
        this.editor.editText(this.element,this.oldTextContent);
    }

    @Override
    public void execute() {
        this.oldTextContent = this.editor.getElementContent(this.element);
        this.editor.editText(this.element,this.newTextContent);
    }
}
