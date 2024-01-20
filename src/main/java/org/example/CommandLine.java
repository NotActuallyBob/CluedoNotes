package org.example;

import org.example.cards.Card;
import org.example.cards.CardType;
import org.example.cards.Deck;
import org.example.command.ICommand;
import org.example.command.commands.*;

import java.util.Optional;
import java.util.Scanner;

public class CommandLine {
    private final Deck deck;
    private final Scanner scanner;

    public CommandLine (Deck deck) {
        this.scanner = new Scanner(System.in);
        this.deck = deck;
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

    public Card promptForCard() {
        while (true) {
            String cardName = promptForString("card name");
            Optional<Card> optionalCard = deck.getCardByName(cardName);
            if(optionalCard.isPresent()) {
                return optionalCard.get();
            }
        }
    }

    public Card promptForCard(CardType cardType) {
        Card card = null;
        while (card == null) {
            String cardName = promptForString("card name");
            if(deck.contains(cardName, cardType)) {
                card = new Card(cardName, cardType);
            }
        }
        return card;
    }

    public void printPrompt(String prompt) {
        print("Enter " + prompt + ": ");
    }

    public void print(String stringToPrint) {
        System.out.print(stringToPrint);
    }

    public void println(String stringToPrint) {
        System.out.println(stringToPrint);
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
            default -> promptCommandAgain();
        };
    }
}
