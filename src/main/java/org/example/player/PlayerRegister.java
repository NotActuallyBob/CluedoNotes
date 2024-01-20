package org.example.player;

import org.example.CommandLine;
import org.example.cards.Card;
import org.example.cards.Deck;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PlayerRegister implements IPlayerRegister {
    private Player[] players;

    private final CommandLine commandLine;

    public PlayerRegister(CommandLine commandLine) {
        this.commandLine = commandLine;
        initPlayers();
    }

    private void initPlayers() {
        players = new Player[commandLine.promptForInt("player count")];

        for (int i = 0; i < players.length; i++) {
            String name = commandLine.promptForString("name for player " + (i + 1));
            int cardQuantity = commandLine.promptForInt("card quantity for player " + name);
            Player newPlayer = new Player(i, name, cardQuantity);
            players[i] = newPlayer;
        }
    }

    public String getPlayerName(int playerId) {
        if(playerId >= players.length) {
            throw new RuntimeException("Player by id = " + playerId + " does not exist");
        }
        return players[playerId].getName();
    }

    @Override
    public Integer getPlayerId(String playerName) {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            if(Objects.equals(player.getName(), playerName)) {
                return i;
            }
        }
        return null;
    }

    public int getPlayerCount() {
        return players.length;
    }

    public int getOwnId() {
        return 0;
    }

    @Override
    public Player getPlayer(int playerId) {
        if(playerId >= players.length) {
            throw new RuntimeException("Player by id = " + playerId + " does not exist");
        }
        return players[playerId];
    }
}
