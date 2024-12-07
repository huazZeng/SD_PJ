package org.example.command.EditorCommand;

import org.example.command.Command;
import org.example.console.Editor;

import org.example.model.visitor.TreeVisitor;

public class PrintTreeCommand implements Command {


    private final Editor editor;

    public PrintTreeCommand(Editor editor) {
        this.editor=editor;
    }

    @Override
    public void execute() {
        this.editor.setVisitor(new TreeVisitor());
        System.out.println(this.editor.print());
    }
}
