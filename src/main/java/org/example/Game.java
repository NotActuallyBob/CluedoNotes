package org.example;

import org.example.player.IPlayerRegistary;
import org.example.player.PlayerRegistary;

import java.util.Map;

public class Game {
    private boolean running;

    IPlayerRegistary playerRegistary;
    CardTracker cardTracker;
    private final CommandLine commandLine;

    public Game() {
        this.commandLine = CommandLine.getInstance();
        this.playerRegistary = new PlayerRegistary();
        this.cardTracker = new CardTracker(playerRegistary);
        this.running = true;
    }

    public void start() {
        while (running) {
            
        }
    }

}
