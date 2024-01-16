package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLine {
    private static CommandLine instance;
    private Scanner scanner;

    private CommandLine() {
        this.scanner = new Scanner(System.in);
    }

    public static CommandLine getInstance() {
        if(instance == null) {
            instance = new CommandLine();
        }
        return instance;
    }

    public int promptForInt(String prompt) {
        printPrompt(prompt);

        String countString = scanner.nextLine();
        int result = Integer.parseInt(countString);
        return result;
    }

    public String promptForString(String prompt) {
        printPrompt(prompt);

        String countString = scanner.nextLine();
        return countString;
    }

    public void printPrompt(String prompt) {
        System.out.print("Enter " + prompt + ": ");
    }
}
