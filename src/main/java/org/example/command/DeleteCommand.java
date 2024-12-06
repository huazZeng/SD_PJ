package org.example.command;

import org.example.console.Editor;

import org.example.model.HtmlElement;

public class DeleteCommand implements CanUndoCommand{
    private Editor editor;
    private String element; // 要删除的元素
    private String NextId;
    private String Context;
    private String tagName;
    private  String Parent;
    public DeleteCommand(Editor editor, String element) {
        this.editor = editor;
        this.element = element;
    }
    @Override
    public void undo() {
        if (this.NextId!=null) {
            this.editor.insert(this.tagName, this.element, this.NextId, this.Context);
        }
        else {
            this.editor.append(this.tagName, this.element, this.Parent, this.Context);
        }
    }

    @Override
    public void execute() {
        HtmlElement element1 = this.editor.getElementById(element);
        this.Context=element1.getText();
        this.tagName=element1.getTagName();
        this.NextId=this.editor.getNextElementId(element);
        this.Parent=element1.getParent().getId();
        this.editor.delete(element);
    }
}
