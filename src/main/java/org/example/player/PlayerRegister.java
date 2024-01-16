package org.example.player;

import org.example.CommandLine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerRegister implements IPlayerRegister {
    private int playerCount;
    private int activePlayerId;
    private final int ownId = 0;
    Map<Integer, String> idToName;

    private CommandLine commandLine;

    public PlayerRegister() {
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

        activePlayerId = getPlayerId(commandLine.promptForString("name of starting player"));
    }

    public String getPlayerName(int playerId) {
        if(!idToName.containsKey(playerId)) {
            throw new RuntimeException("Player by id = " + playerId + " not found");
        }
        return idToName.get(playerId);
    }

    @Override
    public Integer getPlayerId(String playerName) {
        for (int i = 0; i < idToName.size(); i++) {
            if(Objects.equals(idToName.get(i), playerName)) {
                return i;
            }
        }
        return null;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getOwnId() {
        return ownId;
    }
}
