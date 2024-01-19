package org.example;

import org.example.cards.CardTracker;
import org.example.cards.ICardTracker;
import org.example.command.ICommand;
import org.example.player.IPlayerRegister;
import org.example.player.PlayerRegister;

public class Game {
    private static Game instance;
    private boolean running;

    IPlayerRegister playerRegistary;
    ICardTracker cardTracker;
    private final CommandLine commandLine;

    private Game() {
        this.commandLine = CommandLine.getInstance();
        this.playerRegistary = new PlayerRegister();
        this.cardTracker = new CardTracker(playerRegistary);
        this.running = true;
    }

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void start() {
        while (running) {
            ICommand command = commandLine.promptCommand();
            command.execute();
        }
    }

    public void listAllPlayers() {
        for (int i = 0; i < playerRegistary.getPlayerCount(); i++) {
            System.out.println("[" + i + "] " + playerRegistary.getPlayerName(i));
        }
    }

    public void seenCard() {
        String playerName = commandLine.promptForString("showing player name");
        String cardName = commandLine.promptForString("card name");
        cardTracker.playerShowed(playerRegistary.getPlayerId(playerName), cardName);
    }

    public void showedCard() {
        String playerName = commandLine.promptForString("showing player name");
        Accusation accusation = commandLine.promptForAccustation();

        cardTracker.playerShowed(playerRegistary.getPlayerId(playerName), accusation);
    }

    public void skipped() {
        String playerName = commandLine.promptForString("skipping player name");
        Accusation accusation = commandLine.promptForAccustation();

        cardTracker.playerSkipped(playerRegistary.getPlayerId(playerName), accusation);
    }

    public void listKnownCards() {
        for (int i = 0; i < playerRegistary.getPlayerCount(); i++) {
            cardTracker.printKnownCardsByPlayerId(i);
        }
    }
}
