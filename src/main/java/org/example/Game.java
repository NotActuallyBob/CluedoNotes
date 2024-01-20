package org.example;

import org.example.cards.CardTracker;
import org.example.cards.Deck;
import org.example.cards.ICardTracker;
import org.example.command.ICommand;
import org.example.player.IPlayerRegister;
import org.example.player.PlayerRegister;

public class Game {
    private static Game instance;
    private Deck deck;

    private boolean running;

    IPlayerRegister playerRegistary;
    ICardTracker cardTracker;
    private final CommandLine commandLine;

    private Game() {
        this.deck = new Deck();
        this.commandLine = new CommandLine(deck);
        this.playerRegistary = new PlayerRegister(commandLine);
        this.cardTracker = new CardTracker(commandLine, playerRegistary, deck);
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
}
