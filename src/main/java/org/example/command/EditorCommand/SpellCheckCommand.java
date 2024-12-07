package org.example.command.EditorCommand;

import org.example.command.Command;
import org.example.console.Editor;


import java.util.Map;

public class SpellCheckCommand implements Command {


    private final Editor editor;

    public SpellCheckCommand(Editor editor) {
        this.editor=editor;
    }

    @Override
    public void execute() {
        Map <String, String> misspelledWords = this.editor.SpellCheck();
        System.out.println("spell check result:\n");
        for (String word : misspelledWords.keySet()) {
            System.out.println("Word: " + word + " wrong: " + misspelledWords.get(word));
        }
    }
}
