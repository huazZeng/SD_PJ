package org.example.command;

import org.example.console.Editor;
import org.example.model.HTMLModel;
import org.example.model.IndentPrintStrategy;
import org.example.model.TreePrintStrategy;

public class PrintIndentCommand implements Command{

    private final Editor editor;
    private final int indent;

    public PrintIndentCommand(Editor editor, int indent) {
        this.editor = editor;
        this.indent = indent;
    }

    @Override
    public void execute() {
        this.editor.setPrintStrategy(new IndentPrintStrategy());

        System.out.println(this.editor.print());
    }
}
