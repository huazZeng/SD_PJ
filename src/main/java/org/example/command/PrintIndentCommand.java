package org.example.command;

import org.example.console.Editor;
import org.example.model.IndentVisitor;


public class PrintIndentCommand implements Command{

    private final Editor editor;
    private final int indent;

    public PrintIndentCommand(Editor editor, int indent) {
        this.editor = editor;
        this.indent = indent;
    }

    @Override
    public void execute() {
        this.editor.setVisitor(new IndentVisitor());

        System.out.println(this.editor.print());
    }
}
