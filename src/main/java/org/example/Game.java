package org.example;

import org.example.player.IPlayerRegistary;
import org.example.player.PlayerRegistary;

import java.util.Map;

public class Game {
    private int playerCount;
    private Map<Integer, String> idToName;

    IPlayerRegistary playerRegistary;
    CardTracker cardTracker;
    private final CommandLine commandLine;

    public Game() {
        this.commandLine = CommandLine.getInstance();
        this.playerRegistary = new PlayerRegistary();
        this.cardTracker = new CardTracker(playerRegistary);

        init();
    }

    private void init() {
    }

    public void start() {
        //Game loop
    }

}
