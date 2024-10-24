package org.example.console;

import org.example.command.Command;
import org.example.command.CommandInvoker;

import java.io.Console;

public class console {
    public static void main(String[] args) {
        CommandInvoker commandInvoker = new CommandInvoker();
        CommandParser parser = new CommandParser(null,commandInvoker);

    }
}
