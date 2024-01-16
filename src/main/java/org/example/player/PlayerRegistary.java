package org.example.player;

import org.example.CommandLine;

import java.util.HashMap;
import java.util.Map;

public class PlayerRegistary implements IPlayerRegistary {
    private int playerCount;
    private final int ownId = 0;
    Map<Integer, String> idToName;

    private CommandLine commandLine;

    public PlayerRegistary () {
        commandLine = CommandLine.getInstance();
        initPlayers();
    }

    private void initPlayers() {
        idToName = new HashMap<>();
        playerCount = commandLine.promptForInt("player count");

        for (int i = 0; i < playerCount; i++) {
            String name = commandLine.promptForString("name for player " + (i + 1));
            idToName.put(i, name);
        }
    }

    public String getPlayerName(int playerId) {
        if(!idToName.containsKey(playerId)) {
            throw new RuntimeException("Player by id = " + playerId + " not found");
        }
        return idToName.get(playerId);
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getOwnId() {
        return ownId;
    }
}
