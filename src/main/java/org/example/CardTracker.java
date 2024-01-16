package org.example;

import org.example.player.IPlayerRegistary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CardTracker {
    Map<Integer, Integer> idToCardCount;
    private final IPlayerRegistary playerRegistary;

    private Set<String> allCards;
    private Set<String> allRoomCards;
    private Set<String> allWeaponCards;
    private Set<String> allSuspectCards;

    private final CommandLine commandLine;

    public CardTracker (IPlayerRegistary playerRegistary) {
        this.playerRegistary = playerRegistary;
        this.commandLine = CommandLine.getInstance();

        init();
    }

    public void init() {
        this.allCards = new HashSet<>();
        this.allRoomCards = new HashSet<>();
        this.allWeaponCards = new HashSet<>();
        this.allSuspectCards = new HashSet<>();

        try {
            readCardType("/weapons.txt", allWeaponCards);
            readCardType("/suspects.txt", allSuspectCards);
            readCardType("/rooms.txt", allRoomCards);
            int a= 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, Integer> idToCardCount = new HashMap<>();

        for (int i = 1; i < playerRegistary.getPlayerCount(); i++) {
            int cardCount = commandLine.promptForInt("card count for player " + playerRegistary.getPlayerName(i));
            idToCardCount.put(i, cardCount);
        }


        int cardsOnTableCount = commandLine.promptForInt("table card count");
        String[] tableCards = new String[cardsOnTableCount];
        for (int i = 0; i < cardsOnTableCount; i++) {
            tableCards[i] = commandLine.promptForString((i + 1) + " card name");
        }


        int ownCardCount = commandLine.promptForInt("own card count");
        String[] ownCards = new String[ownCardCount];
        for (int i = 0; i < ownCardCount; i++) {
            ownCards[i] = commandLine.promptForString((i + 1) + " card name");
        }
    }

    private void readCardType(String path, Set<String> map) throws IOException {
        InputStream is = getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();

        while (line != null) {
            allCards.add(line);
            map.add(line);
            line = reader.readLine();
        }
    }

    public boolean cardExists(String name) {
        return allCards.contains(name);
    }
}
