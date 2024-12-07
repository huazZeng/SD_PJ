package org.example.console;

import org.example.command.Command;
import org.example.command.CommandInvoker;


import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class console {
    public static void main(String[] args) {

        CommandParser parser = new CommandParser();
        System.out.println("------------------------------------");
        File currentDir = new File(System.getProperty("user.dir"));
        System.out.println("now working on "+currentDir);

        System.out.println("请输入命令（输入 'exit' 退出）： ");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(currentDir+">");
            String input = scanner.nextLine(); // 读取输入命令

            try {
                parser.parseCommand(input);
            }
            catch (IllegalStateException e ){
                System.out.println(e.toString());
            }
            catch (IllegalArgumentException e ){
                System.out.println(e.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
