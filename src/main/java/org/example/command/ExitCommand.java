package org.example.command;


import org.checkerframework.common.returnsreceiver.qual.This;
import org.example.console.Editor;
import org.example.console.Session;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class ExitCommand implements Command {
    private final Session session;

    public ExitCommand(Session session){
        this.session=session;
    }
    @Override
    public void execute() throws IOException {
        session.handleExit();
    }
}
