package org.example.command.EditorCommand;

import org.example.command.CanUndoCommand;
import org.example.console.Editor;


public class AppendCommand implements CanUndoCommand {
    private Editor editor;
    private String tagName;       // 要添加的标签名
    private String idValue;       // 元素的ID（可选）
    private String parentElement;  // 父元素的标签名
    private String textContent;
    public AppendCommand(Editor editor, String tagName, String idValue, String parentElement, String textContent) {
        this.editor = editor;
        this.tagName = tagName;
        this.idValue = idValue;
        this.parentElement = parentElement;
        this.textContent = textContent;
    }

    @Override
    public void execute() {
        this.editor.append(this.tagName,this.idValue,this.parentElement,this.textContent);
    }


    @Override
    public void undo() {
        this.editor.delete(this.idValue);
    }
}
