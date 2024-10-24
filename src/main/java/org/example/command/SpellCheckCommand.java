package org.example.command;

import org.example.model.HTMLModel;

public class SpellCheckCommand implements Command{


    private final HTMLModel htmlModel;

    public SpellCheckCommand(HTMLModel htmlModel) {
        this.htmlModel=htmlModel;
    }

    @Override
    public void execute() {

    }
}
