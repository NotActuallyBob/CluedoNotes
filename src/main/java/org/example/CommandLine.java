package org.example;

import org.example.command.ICommand;
import org.example.command.commands.*;

import java.util.Scanner;

public class CommandLine {
    private static CommandLine instance;
    private final Scanner scanner;

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
        countString = countString.toLowerCase();
        return countString;
    }

    public Accusation promptForAccustation() {
        String suspectCard = promptForString("suspect");
        String roomCard = promptForString("room");
        String weapon = promptForString("weapon");
        return new Accusation(suspectCard, roomCard, weapon);
    }

    public void printPrompt(String prompt) {
        System.out.print("Enter " + prompt + ": ");
    }

    public ICommand promptCommand() {
        String commandString = promptForString("command");
        return switchCommandString(commandString);
    }

    private ICommand promptCommandAgain() {
        String commandString = promptForString("command (The previous command did not exist)");
        return switchCommandString(commandString);
    }

    private ICommand switchCommandString(String commandString) {
        return switch (commandString) {
            case "list_players" -> new ListPlayersCommand();
            case "list_cards" -> new ListKnownCards();
            case "seen" -> new SeenCardCommand();
            case "showed" -> new ShowedCardCommand();
            case "skipped" -> new SkippedCommand();
            default -> promptCommandAgain();
        };
    }
}
