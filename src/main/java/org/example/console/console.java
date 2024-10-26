package org.example.console;

import org.example.command.Command;
import org.example.command.CommandInvoker;
import org.example.model.HTMLModel;

import java.io.Console;
import java.util.Scanner;

public class console {
    public static void main(String[] args) {
        CommandInvoker commandInvoker = new CommandInvoker();
        HTMLModel htmlModel = new HTMLModel();
        CommandParser parser = new CommandParser(htmlModel,commandInvoker);
        String exitCommand = "exit"; // 定义推出条件

        System.out.println("请输入命令（输入 'exit' 退出）：");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine(); // 读取输入命令

            if (input.equalsIgnoreCase(exitCommand)) { // 检查是否为推出条件
                break; // 退出循环
            }
            try {
                parser.parseCommand(input);
            }
            catch (IllegalStateException e ){
                System.out.println(e.toString());
            }
            catch (IllegalArgumentException e ){
                System.out.println(e.toString());
            }

        }


        scanner.close();

    }
}
