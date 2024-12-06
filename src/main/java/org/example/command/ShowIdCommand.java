package org.example.command;

import org.example.console.Editor;

import java.io.IOException;

public class ShowIdCommand implements Command {

    private final Editor editor;

    private final boolean isshowid;
    public ShowIdCommand(Editor editor, boolean isshowid) {
        this.editor = editor;
        this.isshowid = isshowid;
    }

    @Override
    public void execute()  {
            this.editor.setShowid(isshowid);
    }
}
